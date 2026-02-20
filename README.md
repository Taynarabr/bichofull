# BichoFull - Backend

Sistema de apostas online (jogo do bicho) desenvolvido com Spring Boot.

# Sobre o Projeto

API REST para gerenciamento de usuários, apostas e sorteios do jogo do bicho.

# Arquitetura

O projeto segue uma arquitetura em camadas:
┌─────────────────|
│ Controller │ → Camada de apresentação (REST API)               
├─────────────────|
│ Service │ → Camada de negócio (regras)
├──────────────────|
│ Repository │ → Camada de acesso a dados (JPA)
├─────────────────|
│ Model │ → Entidades do banco
└─────────────────|
┌─────────────────────|
│ Database │ → MySQL
|─────────────────────|




# Tecnologias

  Java 21 + Spring Boot 3.5.11
  Spring Web
  Spring Data JPA
  JPA / Hibernate (mapeamento objeto-relacional automatico)
  Maven
  MySQL 8.0
  GitHub Actions
  Swagger/OpenAPI (documentação automatica da API)
  angular


