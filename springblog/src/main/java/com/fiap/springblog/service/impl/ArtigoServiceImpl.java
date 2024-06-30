package com.fiap.springblog.service.impl;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.model.Autor;
import com.fiap.springblog.repository.ArtigoRepository;
import com.fiap.springblog.repository.AutorRepository;
import com.fiap.springblog.service.ArtigoService;
import com.fiap.springblog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArtigoServiceImpl implements ArtigoService {


    private final MongoTemplate mongoTemplate;

    @Autowired
    private ArtigoRepository artigoRepository;

    @Autowired
    private AutorRepository autorRepository;

    public ArtigoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Artigo> obterTodos() {
        return this.artigoRepository.findAll();
    }

    @Override
    public Artigo obterPorCodigo(String codigo) {
        return this.artigoRepository.findById(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Artigo não existe dentro da coleção"));
    }

    @Override
    public Artigo criar(Artigo artigo) {

        if(artigo.getAutor().getCodigo() != null){
            Autor autor = this.autorRepository.findById(artigo.getAutor().getCodigo())
                    .orElseThrow(() -> new IllegalArgumentException("autor não encontrado"));

            artigo.setAutor(autor);
        }else {
            artigo.setAutor(null);
        }

        return  this.artigoRepository.save(artigo);
    }

    @Override
    public List<Artigo> findByDataGreaterThan(LocalDateTime data) {
        // Cria o critério de busca
        Query query = new Query(Criteria.where("data").gt(data));

        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<Artigo> findByDataEndStatus(LocalDateTime data, Integer status) {
        // a data deve ser a data vindo do parâmetro e o status tambám
        Query query = new Query(Criteria.where("data")
                .is(data).and("status").is(status));
        System.out.println(" ---->  chamou a service");
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public void atualizar(Artigo updateArtigo) {
        this.artigoRepository.save(updateArtigo);
    }

    @Override
    public void atualizarArtigo(String id, String novaURL) {

        //critério de busca pelo id
        Query query = new Query(Criteria.where("_id").is(id));
        //Definindo os campos que serão atualizados
        Update update = new Update().set("url", novaURL);

        this.mongoTemplate.updateFirst(query, update, Artigo.class);
    }

    @Override
    public void deleteById(String id) {
        this.artigoRepository.deleteById(id);
    }

    @Override
    public void deleteArtigoById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        mongoTemplate.remove(query, Artigo.class);
    }

    @Override
    public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data) {
        return this.artigoRepository.findByStatusAndDataGreaterThan(status, data);
    }

    @Override
    public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate) {
        return this.artigoRepository.obterArtigoPorDataHora(de, ate);
    }

    @Override
    public List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo) {
        Criteria criteria = new Criteria();

        criteria.and("data").lte(data);

        if(status != null){
            criteria.and("status").is(status);
        }

        if(titulo != null && !titulo.isEmpty()){
            criteria.and("titulo").is(titulo).regex(titulo, "i");
        }

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public Page<Artigo> listaArtigosPaginados(Pageable pageable) {
        Sort sort = Sort.by("data").ascending();

        Pageable paginacao = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return this.artigoRepository.findAll(pageable);
    }

    @Override
    public List<Artigo> findByStatusOrderByTituloAsc(Integer status) {
        return this.artigoRepository.findByStatusOrderByTituloAsc(status);
    }

    @Override
    public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status) {
        return this.artigoRepository.obterArtigoPorStatusComOrdenacao(status);
    }

    @Override
    public List<Artigo> findByTexto(String searchTerm) {
        TextCriteria criteria =
                TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);

        Query query = TextQuery.queryText(criteria).sortByScore();

        return mongoTemplate.find(query, Artigo.class);
    }


}
