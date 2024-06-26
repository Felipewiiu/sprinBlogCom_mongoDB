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

Esta anotação @Query é usada em repositórios do Spring Data MongoDB para definir consultas personalizadas diretamente em
métodos de repositório. Aqui está o que cada parte faz:

2. @Query(value = "{'status': {$eq: ?0}}", sort = "{'titulo': 1}"):

- value = "{'status': {$eq: ?0}}": Esta parte define a consulta real em JSON (estilo MongoDB). Está procurando
  documentos onde o campo status é igual ao parâmetro fornecido. O ?0 indica o primeiro parâmetro do método (no caso,
  status).
- sort = "{'titulo': 1}": Esta parte define a ordenação dos resultados. Está ordenando pelo campo titulo em ordem
  ascendente (1).
-

3. public List<Artigo> obterArtigoPorStatusComOrdenacao(InternalError status):

public List<Artigo>: Este método retorna uma lista de objetos do tipo Artigo.
obterArtigoPorStatusComOrdenacao(InternalError status): Nome do método, que recebe um parâmetro do tipo InternalError.

## Indexação

A indexação no banco de dados serve para que possamos buscar de forma mais rápida a informação que queremos recuperar.

- Podemos buscar frases dentro de um documento como no exemplo logo abaixo.

- OBS: para que isso funcione recisamos adicionar uma anotação ``@TexIndexed`` no atributo que queremos indexar
  na entidade

- criar um index para o campo usando o mongoshell com o comando ``db.artigo.createIndex({texto: "text"})``

````java

@Override
public List<Artigo> findByTexto(String searchTerm) {
    TextCriteria criteria =
            TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);

    Query query = TextQuery.queryText(criteria).sortByScore();

    return mongoTemplate.find(query, Artigo.class);
}
````

## Maneira de se criar uma agregação

No MongoDB, o conceito de agregação refere-se a um processo utilizado para processar dados em documentos e retornar
resultados computados. As operações de agregação podem agrupar valores de vários documentos, realizar operações em dados
agrupados e retornar resultados computados em uma única operação.

A principal ferramenta para realizar agregações no MongoDB é o pipeline de agregação, que é uma sequência de estágios.
Cada estágio realiza uma operação nos documentos e passa os resultados para o próximo estágio no pipeline. 

*Modo de implementação:*

1. Crie uma variável do tipo ``TypedAggregation`` que receba uma classe do tipo ``Aggregation``
2. Passe como parâmetro a Entidade, o grupo de dados e a projeção como no [ArtigoServiceImpl.java](springblog%2Fsrc%2Fmain%2Fjava%2Fcom%2Ffiap%2Fspringblog%2Fservice%2Fimpl%2FArtigoServiceImpl.java)


