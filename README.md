# Conversor de Moedas

Projeto Java + Spring Boot + HTML/Bootstrap para conversão de moedas com autenticação de usuário.

## Tecnologias
- Backend: Java 17, Spring Boot, JPA, Spring Security
- Frontend: HTML5, Bootstrap, JavaScript
- Banco de dados: MySQL
- API externa: exchangeratesapi.io

## Funcionalidades
- Login com autenticação
- Conversão entre BRL, USD e CAD
- Histórico de conversões
- Taxa de câmbio armazenada em cache
- Paginação do histórico


## Como rodar localmente
1. Crie um banco MySQL e configure o `application.properties`.
2. Rode a aplicação com `./mvnw spring-boot:run`.
3. Acesse `http://localhost:8080/frontend/login.html`.

## Observações
- A criação de usuários é manual no banco (com senha criptografada em BCrypt).
- O frontend está dentro de `src/main/resources/static/frontend/`.


