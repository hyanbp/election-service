# election-service
Microsserviço de criação e votação de uma eleição/pauta.

## Técnologias ultilizadas
* Java 8+
* Spring - Framework WebFlux
* Springboot
* MongoDB
* RabbitMq

### Sobre
Election-service é um serviço de backend para atender a criação de uma pauta/eleição e também permite realizar a votação e a contagem dos votos.

#### Como usar o serviço
Primeiramente deve ser cadastrar um associado, informando apenas o numero do CPF.

[Cadastrar um Associado](https://election-service.herokuapp.com/swagger-ui.html#/associate-api/postElectionUsingPOST)
 

Logo após vem a criação da pauta/eleição, informando apenas o nome da pauta/eleição e o tempo duração que essa pauta/eleição irá durar.

[Cadastrar uma pauta/eleição](https://election-service.herokuapp.com/swagger-ui.html#/election-api/postElectionUsingPOST_1)


Ao criares a pauta/eleição você receberá no retorno o código da mesma. EX: 

{
  "electionCode": "5c7d2956db02de000449976c"
} 



Com o **código da pauta/eleição** em mãos, você poderá realizar o seu voto junto com seu CPF **cadastrado** como associado e sua decisão de voto (SIM/NAO). EX bodyRequest:
```
{
  "decision": "string",
  "taxIdAssociate": "string"
}

```
[Realizar o voto](https://election-service.herokuapp.com/swagger-ui.html#/election-api/postVoteUsingPOST)

Lembrando que o seu CPF deve ser válido e você só pode realizar um **voto** por **ELEIÇÃO/PAUTA**

Para realizar a busca pela contagem dos votos da eleição/pauta, basta apenas informar o código da eleição/pauta

[Contagem dos votos](https://election-service.herokuapp.com/swagger-ui.html#/election-api/getResultVoteUsingGET)

@Scheduled
Também existe um job que roda de minuto a minuto, vendo as sessões que estão encerradas pelo tempo de expiração da sessão e executa o fechamento.
após isso ele informa via *fila* todos os dados da eleição/pauta encerrados pelo tempo de sessão. 

##### Acessos

Url MongoDB : https://www.mlab.com/databases/heroku_vggztlbz/
```
user: heroku_vggztlbz
pwd: 8qRE9BkfVN2b5mp
```

Url Painel RabbitMQ : https://chimpanzee.rmq.cloudamqp.com 
``` 
user: ittfprrf
pwd: cbatqRK0soL4xmXibyUSbiG-ijtqnZB3
```
