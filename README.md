# FitFlow Service

## Descrição
Este repositório contém o código do back-end da aplicação FitFlow. 

## Documentação
- [Link para o documento de visão](link)
- [Link para User Stories](link)
- [Link para HLD](link)
- [Link para LLD](link)

## Setup Guide

Este projeto utiliza Docker para facilitar a configuração do banco de dados PostgreSQL e a aplicação Spring Boot.

### Pré-requisitos

1. **Docker Desktop**
    - Certifique-se de que o Docker Desktop está instalado e rodando. Você pode baixar e instalar o Docker Desktop [aqui](https://www.docker.com/products/docker-desktop).

2. **JDK 17+**
    - Certifique-se de ter o JDK instalado:
      ```bash
      java -version
      ```

### Instruções para rodar o projeto

#### 1. Clone o repositório
Faça o clone deste repositório no seu ambiente local:
```bash
  git clone git@github.com:FitFlowPI/fit-flow-service.git
  cd seu-repositorio
 ```

#### 2. Rodar a aplicação com Docker
Com o Docker Desktop rodando, tudo que você precisa fazer é rodar o seguinte comando para subir o banco de dados e a aplicação Spring Boot:
```bash
  docker-compose up
 ```
Isso irá:
- Subir um container do PostgreSQL com as configurações definidas no `docker-compose.yml`.
- Iniciar a aplicação Spring Boot e conectar ao banco de dados automaticamente.

A aplicação deverá estar acessível em [http://localhost:8080](http://localhost:8080).

#### 3. Parar a aplicação
Para parar a aplicação e os containers, use o seguinte comando:

```bash
docker-compose down
```

Isso encerrará tanto a aplicação Spring Boot quanto o banco de dados PostgreSQL.

