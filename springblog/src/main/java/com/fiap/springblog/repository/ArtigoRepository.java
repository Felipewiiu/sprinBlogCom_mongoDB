package com.fiap.springblog.repository;

import com.fiap.springblog.model.Artigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String>{
    //Toda declaração de método feito no repositório precisamos obrigatóriamente
    //declara-la na interface de serviso
    public void deleteById(String id);
}

