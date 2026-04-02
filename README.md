# 🎲 BichoFull - Simulador Full Stack do Jogo do Bicho

O **BichoFull** é um sistema completo, moderno e responsivo para gestão de apostas e simulação do clássico Jogo do Bicho. Este é um projeto acadêmico desenvolvido para o **Laboratório de Engenharia de Software**, focado em regras de negócio complexas, integração contínua (CI/CD) e atualização de dados em tempo real.

---

## 🚀 Status do Projeto: [![CI BichoFull](https://github.com/Taynarabr/bichofull/actions/workflows/ci.yml/badge.svg)](https://github.com/Taynarabr/bichofull/actions)

O selo acima indica que o código passou em todos os testes automatizados, builds e verificações de padrões (Lint) no GitHub Actions.

---

## ✨ Funcionalidades Principais

### 👤 Painel do Jogador
* **Autenticação Segura:** Login e Cadastro com JWT e criptografia de senhas.
* **Motor de Apostas:** Apostas em Grupos (Animais), Dezenas e Milhares, seguindo a tabela oficial.
* **Cálculo de Prêmios:** Pagamentos automáticos de **18x (Grupo)** a **4000x (Milhar)** o valor apostado.
* **Gestão Financeira:** Simulação de depósitos via Pix e saques inteligentes com validação de saldo fictício (Saldo inicial de R$ 1.000,00).
* **Dashboard em Tempo Real:** Atualização automática (Polling) a cada 10 segundos para exibir novos sorteios e status de apostas sem recarregar a página.

### ⚙️ Painel do Administrador (Admin)
* **Geração de Sorteios:** Motor para sorteio de resultados oficiais (1º ao 5º prêmio).
* **Auditoria:** Visão geral de todos os sorteios e apostas registradas no banco de dados.

---

## 🛠️ Tecnologias Utilizadas

**Frontend:**
* [Angular 17+](https://angular.dev/)
* [Bootstrap 5](https://getbootstrap.com/) (UI Responsivo)
* [SweetAlert2](https://sweetalert2.github.io/) (Feedbacks Visuais)
* Vitest & Playwright (Testes Unitários e Headless)

**Backend:**
* [Java 21](https://www.oracle.com/java/technologies/downloads/) + [Spring Boot 3](https://spring.io/projects/spring-boot)
* Spring Security + JWT
* Spring Data JPA + Hibernate
* Maven (Gerenciador de Dependências)

**Banco de Dados:**
* MySQL / MariaDB

---

## 🚀 Como Executar o Projeto na sua Máquina

### 📋 Pré-requisitos
Certifique-se de ter instalado em sua máquina:
1. **Java JDK 21** ou superior.
2. **Node.js** (versão 20 recomendada) e **NPM**.
3. **Maven**.
4. **MySQL Server** rodando na porta padrão (3306).

### 1. Configurando o Banco de Dados
1. No seu MySQL, crie o banco de dados:
   ```sql
   CREATE DATABASE bichofull;
