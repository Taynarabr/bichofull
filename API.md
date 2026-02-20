CONTRATO DA API - BICHO FULL

1. INFORMAÇÕES GERAIS

| Item | Descrição |
|------|-----------|
| Nome do Projeto | BichoFull Backend |
| Versão da API | v1.0.0 |
| URL | http://localhost:8080/api |
| Formato dos dados | JSON |
| Codificação | UTF-8 |


2. PADRÕES DA API

2.1 Headers Padrão
Content-Type: application/json
Accept: application/json


2.2 Códigos de Resposta
| Código | Significado | Quando usar |
|--------|-------------|-------------|
| 200    | OK          | Requisição bem-sucedida |
| 201    | Created     | Recurso criado com sucesso |
| 204    | No Content  | Deleção bem-sucedida |
| 400    | Bad Request | Dados inválidos na requisição |
| 401    | Unauthorized | Não autenticado |
| 404    | Not Found   | Recurso não encontrado |
| 409    | Conflict    | Conflito (ex: email duplicado) |
| 500    | Internal Server Error | Erro no servidor |

2.3 Formato de Erro Padrão
json
{
  "timestamp": "2026-02-19T20:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Descrição do erro",
  "path": "/api/users"
}

3.Modelos de Dados
3.1 User
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456",
  "balance": 1000.00,
  "createdAt": "2026-02-19T20:00:00",
  "updatedAt": "2026-02-19T20:00:00"
}

3.2 Bet
{
  "id": 1,
  "userId": 1,
  "drawId": 1,
  "type": "MILHAR",
  "value": 10.00,
  "choice": "1234",
  "status": "PENDENTE",
  "createdAt": "2026-02-19T20:00:00",
  "updatedAt": "2026-02-19T20:00:00"
}

3.3 Draw
{
  "id": 1,
  "drawDate": "2026-02-19T20:00:00",
  "milhar_1": "1234",
  "milhar_2": "5678",
  "milhar_3": "9012",
  "milhar_4": "3456",
  "milhar_5": "7890"
}

4. VALIDAÇÕES E REGRAS DE NEGÓCIO
4.1 Usuários
Email: Deve ser único no sistema
Senha: Mínimo 6 caracteres
Nome: Não pode ser vazio
Saldo: Não pode ficar negativo

4.2 Apostas
Valor: Deve ser maior que zero

Saldo: Usuário deve ter saldo suficiente
Choice:
    GRUPO: 2 dígitos (01-25)
    DEZENA: 2 dígitos (00-99)
    MILHAR: 4 dígitos (0000-9999)

4.3 Sorteios
Milhares: Sempre 4 dígitos com zeros à esquerda

5. ENDPOINTS DE USUÁRIOS
5.1 Criar Usuário
POST /api/users
Request Body:
json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
Resposta (201 Created):
json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "balance": 1000.00,
  "createdAt": "2026-02-19T20:00:00"
}

5.2 Buscar Usuário por ID
GET /api/users/{id}
Resposta (200 OK):
json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "balance": 1000.00,
  "createdAt": "2026-02-19T20:00:00"
}

5.3 Listar Todos os Usuários
GET /api/users

Resposta (200 OK):

json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "balance": 1000.00
  },
  {
    "id": 2,
    "name": "Maria Souza",
    "email": "maria@email.com",
    "balance": 1500.00
  }
]
5.4 Atualizar Usuário
PUT /api/users/{id}

Request Body:

json
{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com"
}
Resposta (200 OK):

json
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "balance": 1000.00,
  "updatedAt": "2026-02-19T21:00:00"
}
5.5 Deletar Usuário
DELETE /api/users/{id}

Resposta: 204 No Content

5.6 Login
POST /api/users/login

Request Body:

json
{
  "email": "joao@email.com",
  "password": "123456"
}
Resposta (200 OK):

json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "balance": 1000.00
}

5.7 Depositar
POST /api/users/{id}/deposit

Request Body:

json
{
  "value": 100.00
}
Resposta (200 OK):

json
{
  "id": 1,
  "name": "João Silva",
  "balance": 1100.00
}

5.8 Sacar
POST /api/users/{id}/withdraw

Request Body:
json
{
  "value": 50.00
}

Resposta (200 OK):
json
{
  "id": 1,
  "name": "João Silva",
  "balance": 1050.00
}

6. ENDPOINTS DE APOSTAS
6.1 Criar Aposta
POST /api/bets

Request Body:
json
{
  "userId": 1,
  "type": "MILHAR",
  "value": 10.00,
  "choice": "1234"
}

Resposta (201 Created):
json
{
  "id": 1,
  "userId": 1,
  "type": "MILHAR",
  "value": 10.00,
  "choice": "1234",
  "status": "PENDENTE",
  "createdAt": "2026-02-19T20:00:00"
}

6.2 Listar Apostas do Usuário
GET /api/bets/user/{userId}

Resposta (200 OK):

json
[
  {
    "id": 1,
    "type": "MILHAR",
    "value": 10.00,
    "choice": "1234",
    "status": "PENDENTE",
    "createdAt": "2026-02-19T20:00:00"
  },
  {
    "id": 2,
    "type": "DEZENA",
    "value": 5.00,
    "choice": "12",
    "status": "PERDEU",
    "createdAt": "2026-02-19T20:05:00"
  }
]

6.3 Buscar Aposta por ID
GET /api/bets/{id}

Resposta (200 OK):
json
{
  "id": 1,
  "userId": 1,
  "type": "MILHAR",
  "value": 10.00,
  "choice": "1234",
  "status": "PENDENTE",
  "createdAt": "2026-02-19T20:00:00"
}

6.4 Atualizar Status da Aposta
PUT /api/bets/{id}/status

Request Body:
json
{
  "status": "GANHOU"
}

Resposta (200 OK):
json
{
  "id": 1,
  "status": "GANHOU",
  "updatedAt": "2026-02-19T21:00:00"
}

6.5 Listar Apostas por Status
GET /api/bets/status/{status}

Resposta (200 OK):
json
[
  {
    "id": 1,
    "userId": 1,
    "type": "MILHAR",
    "value": 10.00,
    "choice": "1234",
    "status": "PENDENTE"
  }
]

7. ENDPOINTS DE SORTEIOS
7.1 Criar Sorteio (Admin)
POST /api/draws

Request Body:
json
{
  "milhar_1": "1234",
  "milhar_2": "5678",
  "milhar_3": "9012",
  "milhar_4": "3456",
  "milhar_5": "7890"
}
Resposta (201 Created):
json
{
  "id": 1,
  "drawDate": "2026-02-19T20:00:00",
  "milhar_1": "1234",
  "milhar_2": "5678",
  "milhar_3": "9012",
  "milhar_4": "3456",
  "milhar_5": "7890"
}

7.2 Buscar Último Sorteio
GET /api/draws/latest

Resposta (200 OK):
json
{
  "id": 1,
  "drawDate": "2026-02-19T20:00:00",
  "milhar_1": "1234",
  "milhar_2": "5678",
  "milhar_3": "9012",
  "milhar_4": "3456",
  "milhar_5": "7890"
}

7.3 Buscar Sorteio por ID
GET /api/draws/{id}

Resposta (200 OK):
json
{
  "id": 1,
  "drawDate": "2026-02-19T20:00:00",
  "milhar_1": "1234",
  "milhar_2": "5678",
  "milhar_3": "9012",
  "milhar_4": "3456",
  "milhar_5": "7890"
}

7.4 Listar Todos os Sorteios
GET /api/draws

Resposta (200 OK):
json
[
  {
    "id": 1,
    "drawDate": "2026-02-19T20:00:00",
    "milhar_1": "1234",
    "milhar_2": "5678",
    "milhar_3": "9012",
    "milhar_4": "3456",
    "milhar_5": "7890"
  },
  {
    "id": 2,
    "drawDate": "2026-02-20T20:00:00",
    "milhar_1": "4321",
    "milhar_2": "8765",
    "milhar_3": "2109",
    "milhar_4": "6543",
    "milhar_5": "0987"
  }
]

8. EXEMPLOS DE TESTE (POSTMAN/INSOMNIA)
Criar usuário
text
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "Teste",
  "email": "teste@email.com",
  "password": "123456"
}
Fazer login
text
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "teste@email.com",
  "password": "123456"
}