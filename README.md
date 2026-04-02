# 🐾 BichoFull - Sistema de Apostas Full Stack

O **BichoFull** é um simulador completo do 'Jogo do Bicho', desenvolvido como projeto prático para a disciplina de Laboratório de Software. O sistema permite que usuários realizem apostas em animais, dezenas ou milhares, enquanto administradores gerenciam os sorteios e a premiação automática.

## 🚀 Tecnologias Utilizadas

### Backend
* **Java 21**
* **Spring Boot 3.x**
* **Spring Data JPA** (Persistência)
* **MySQL 8.0** (Banco de Dados)

### Frontend
* **Angular 18/19**
* **Bootstrap 5** (Interface UI)
* **TypeScript**

## 🛠️ Funcionalidades Principais

### Para o Jogador:
- **Dashboard Dinâmico:** Visualização de todos os animais e grupos.
- **Sistema de Apostas:** Realização de apostas por Grupo (Animal), Dezena ou Milhar.
- **Histórico em Tempo Real:** Acompanhamento do status das apostas (Pendente, Ganhou ou Perdeu).
- **Carteira Virtual:** Saldo atualizado automaticamente após cada sorteio premiado.

### Para o Administrador:
- **Painel de Controle:** Acesso exclusivo para geração de novos sorteios.
- **Motor de Sorteio:** Geração aleatória de 5 milhares (1º ao 5º prêmio).
- **Processamento Automático:** O sistema varre as apostas pendentes e distribui prêmios instantaneamente após o sorteio.

---

## 🔧 Como Executar o Projeto

### 1. Rodar o Backend (Java)
\\\ash
cd backend
./mvnw spring-boot:run
\\\

### 2. Rodar o Frontend (Angular)
\\\ash
cd frontend
ng serve
\\\

---

**Desenvolvido por:** Taynara Batista Ribeiro - Engenharia de Software 6º Período.
