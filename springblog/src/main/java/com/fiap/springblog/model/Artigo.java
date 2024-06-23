package com.fiap.springblog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "artigo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artigo {

    @Id
    private String codigo;

    private String titulo;

    private LocalDate data;

    private String texto;

    private String url;

    private Integer status;
    @DBRef// permite a referência de uma coleção dentro da outra
    private Autor autor;
}
