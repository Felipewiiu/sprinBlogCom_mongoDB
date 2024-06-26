package com.fiap.springblog.service.impl;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.model.Autor;
import com.fiap.springblog.repository.ArtigoRepository;
import com.fiap.springblog.repository.AutorRepository;
import com.fiap.springblog.service.ArtigoService;
import com.fiap.springblog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
}
