package com.fiap.springblog.service;

import com.fiap.springblog.model.Artigo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface ArtigoService {

    public List<Artigo> obterTodos();

    public Artigo obterPorCodigo(String codigo);

    public Artigo criar (Artigo artigo);

    public List<Artigo> findByDataGreaterThan(LocalDateTime data);

    public List<Artigo> findByDataEndStatus(LocalDateTime data, Integer status);

    public void atualizar(Artigo updateArtigo);

    public void atualizarArtigo(String id, String novaURL);

    public void deleteById(String id);

    public void deleteArtigoById(String id);

    public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);

    public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate);

    public List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo);

    public Page<Artigo> listaArtigosPaginados(Pageable pageable);

    public List<Artigo> findByStatusOrderByTituloAsc(Integer status);

    public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status);

    public List<Artigo> findByTexto(String searchTerm);
}
