#Mini Autorizador

###Stack utilizada
* Java 17
* Spring Boot 3
* MySQL
* RabbitMQ

###Visão geral
A aplicação segue o padrão REST e consiste nas seguintes funcionalidades:
* Criação de Cartão
* Consulta de saldo de Cartão
* Lançamento de Transações

###Testes Unitários
Nos testes foram utilizados JUnit e Mockito.

###Desafios
Não foi utilizado nenhum <b>if</b> nos fluxos descritos acima, nem mecanismos
de loop como <b>for</b> e <b>while</b>. Também não utilizei <b>switch/case</b>.

Em relação à concorrência, optei por implementar um sistema de filas utilizando o 
<b>RabbitMQ</b>. Trabalhando desta forma, mesmo que duas ou mais requisições sejam feitas
ao mesmo tempo, a fila nos garante a entrega de apenas uma requisição por vez.

###Collection
Na raiz do projeto há uma pasta chamada <b>postman</b> com a collection
utilizada durante o desenvolvimento.

###Instruções de run
Para executar a aplicação em um container execute os comandos abaixo no diretório
raiz do projeto na seguinte ordem:

1- Build do Maven:
```
mvn clean package
```

2- Build da Imagem:
```
docker build -t mini-autorizador .
```

3- Abrir o diretório do docker-compose no projeto:
```
cd docker/
```

4- Criação do container:
```
docker-compose up -d
```