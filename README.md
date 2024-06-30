# sprinBlogCom_mongoDB

## Link para documentação



[Spring Data MongoDB - documentação de referência](https://docs.spring.io/spring-data/mongodb/reference/)

## Tipos de ordenação

 1. *Ordenação por @Query*

Exemplo: 

````java
@Query(value = "{'status':  {$eq: ?0}}", sort = "{'titulo':  1}")
public List<Artigo> obterArtigoPorStatusComOrdenacao(InternalError status);

````

Esta anotação @Query é usada em repositórios do Spring Data MongoDB para definir consultas personalizadas diretamente em métodos de repositório. Aqui está o que cada parte faz:

2. @Query(value = "{'status': {$eq: ?0}}", sort = "{'titulo': 1}"):

- value = "{'status': {$eq: ?0}}": Esta parte define a consulta real em JSON (estilo MongoDB). Está procurando documentos onde o campo status é igual ao parâmetro fornecido. O ?0 indica o primeiro parâmetro do método (no caso, status).
- sort = "{'titulo': 1}": Esta parte define a ordenação dos resultados. Está ordenando pelo campo titulo em ordem ascendente (1).
- 
3. public List<Artigo> obterArtigoPorStatusComOrdenacao(InternalError status):

public List<Artigo>: Este método retorna uma lista de objetos do tipo Artigo.
obterArtigoPorStatusComOrdenacao(InternalError status): Nome do método, que recebe um parâmetro do tipo InternalError.


## Indexação

A indexação no banco de dados serve para que possamos buscar de forma mais rápida a informação que queremos recuperar.
- Podemos buscar frases dentro de um documento.

- Exemplo:

````java
 @Override
    public List<Artigo> findByTexto(String searchTerm) {
        TextCriteria criteria =
                TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);
        
        Query query = TextQuery.queryText(criteria).sortByScore();
        
        return mongoTemplate.find(query, Artigo.class);
    }
````


