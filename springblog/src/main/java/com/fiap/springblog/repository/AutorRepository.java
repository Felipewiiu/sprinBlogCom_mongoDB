package com.fiap.springblog.repository;

import com.fiap.springblog.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends MongoRepository<Autor, String> {

}
