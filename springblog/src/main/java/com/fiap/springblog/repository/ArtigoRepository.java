package com.fiap.springblog.repository;

import com.fiap.springblog.model.Artigo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String>{
    //Toda declaração de método feito no repositório precisamos obrigatóriamente
    //declara-la na interface de serviso
    public void deleteById(String id);

    public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);

    //?o --> significa que vamos utilizar o primeiro parâmetro que é um arrai de parâmetros
    @Query("{$and: [{'data': { $gte: ?0}}, {'data': {$lte: ?1}}]}")
    public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate);

    public Page<Artigo> findAll(Pageable pageable);

    public List<Artigo> findByStatusOrderByTituloAsc(Integer status);

    @Query(value = "{'status':  {$eq: ?0}}", sort = "{'titulo':  1}")
    public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status);
}

