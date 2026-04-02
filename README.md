# 🎰 Jogo do Bicho Full (Bichofull)

Um sistema web completo, moderno e responsivo para gestão de apostas do clássico Jogo do Bicho, desenvolvido com **Spring Boot (Java)** no backend e **Angular (TypeScript)** no frontend. 

Projeto acadêmico focado na implementação de regras de negócio complexas, controle de fluxo financeiro, autenticação e atualização de dados em tempo real.

---

## ✨ Funcionalidades Principais

### 👤 Painel do Jogador
* **Autenticação Segura:** Registro e Login com senhas criptografadas.
* **Motor de Apostas:** Suporte para apostas em Grupos (Animais), Dezenas e Milhares, com cálculo instantâneo do potencial de ganho.
* **Sistema Financeiro (PIX):** Simulação de ambiente transacional com depósito via QR Code/Pix Copia e Cola, saque inteligente com validação de saldo e histórico de movimentações.
* **Dashboard em Tempo Real:** Atualização silenciosa (Auto-Refresh) a cada 10 segundos, trazendo novos sorteios e status das apostas sem a necessidade de recarregar a página (F5).
* **Gestão de Perfil:** Atualização de dados cadastrais (nome, e-mail e redefinição de senha).
* **Estatísticas:** Histórico completo de apostas, taxa de vitórias e total de ganhos.

### ⚙️ Painel do Administrador (Admin)
* Geração de resultados oficiais (Sorteios).
* Distribuição de prêmios em até 5 faixas (1º ao 5º prêmio).
* Visão geral de todos os sorteios registrados no sistema.

---

## 🛠️ Tecnologias Utilizadas

**Frontend:**
* [Angular](https://angular.dev/) (Framework principal)
* [TypeScript](https://www.typescriptlang.org/)
* [Bootstrap 5](https://getbootstrap.com/) & CSS3 (UI/UX Responsivo)
* [SweetAlert2](https://sweetalert2.github.io/) (Pop-ups e Notificações interativas)
* RxJS (Programação reativa e Polling)

**Backend:**
* [Java](https://www.java.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* Spring Security (Autenticação e Criptografia)
* Spring Data JPA / Hibernate (ORM)

**Banco de Dados:**
* [MySQL](https://www.mysql.com/)

---

## 🚀 Como executar o projeto na sua máquina

### Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
* Node.js e NPM
* Angular CLI (`npm install -g @angular/cli`)
* Java JDK (versão 17 ou superior)
* Maven
* MySQL Server rodando na porta padrão (3306)

### 1. Configurando o Banco de Dados (Backend)
1. Crie um banco de dados no MySQL chamado `bichofull`.
2. Acesse a pasta `backend/src/main/resources` e abra o arquivo `application.properties`.
3. Verifique e ajuste as credenciais do seu banco de dados:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bichofull
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha

**Desenvolvido por:** Taynara Batista Ribeiro - Engenharia de Software 6º Período.
