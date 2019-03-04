# election-service
Microsserviço de criação e votação de uma eleição/pauta.

## Sobre
Election-service é um serviço de backend para atender a criação de uma pauta/eleição e também permite realizar a votação e a contagem dos votos.

### Como usar o serviço
Primeiramente deve ser cadastrar um associado, informando apenas o numero do CPF.

[Cadastrar um Associado](https://election-service.herokuapp.com/swagger-ui.html#/associate-api/postElectionUsingPOST)
 

Logo após vem a criação da pauta/eleição, informando apenas o nome da pauta/eleição e o tempo duração que essa pauta/eleição irá durar.

[Cadastrar uma pauta/eleição](https://election-service.herokuapp.com/swagger-ui.html#/election-api/postElectionUsingPOST_1)


Ao criares a pauta/eleição você receberá no retorno o código da mesma. EX: 

{
  "electionCode": "5c7d2956db02de000449976c"
} 



Com o código da pauta/eleição em mãos, você poderá realizar o seu voto junto com seu CPF **cadastrado** como associado e sua decisão de voto (SIM/NAO)

[Realizar o voto](https://election-service.herokuapp.com/swagger-ui.html#/election-api/postVoteUsingPOST)

Lembrando que o seu CPF deve ser válido e você só pode realizar um **voto** por **ELEIÇÃO/PAUTA**

Para realizar a busca pela contagem dos votos da eleição/pauta, basta apenas informar o código da eleição/pauta

[Contagem dos votos](https://election-service.herokuapp.com/swagger-ui.html#/election-api/getResultVoteUsingGET)
