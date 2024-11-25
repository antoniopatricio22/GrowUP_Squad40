![thumbnail](https://recifevirado.recife.pe.gov.br/wp-content/uploads/2021/10/logo-embarque-digital-1.png)




---
# Apresentação
Repositório do Projeto do Squad 40 (UNIT), para Desafio proposto pela Compesa e Porto Digital como parte da Residência Tecnológica do Programa Embarque Digital Criado pela Prefeitura da Cidade do Recife, PE - Brasil . 

# Desafio

A Compesa enfrenta desafios na gestão de ativos devido a um sistema poluído e ineficiente,
dificultando a gestão e o armazenamento de dados, ao depender de várias plataformas para
essas tarefas.

Desenvolver o BackEnd para um portal onde os dados serão inseridos, armazenados, consultados e quando
necessário editados por usuários ‘Gestores’ . O sistema visa otimizar o armazenamento de
dados, e diminuir o risco de erros humanos durante o processo.





Grow_Up: Squad40-Unit.

Integrantes:
- Adriana Freitas Gomes
- Antônio Patricio Macena de Barros
- Beatriz Conde Carvalho
- Bruno Gabriel de Lima Souza
- Caio Stuart Rozeno Tabosa
- Gabriel Oliveira Parisio

Mentores:
- Nilton Pontes
- Flavio Fernandes


Recife, Novembro de 2024.

---

## Stack de Desenvolvimento

- **Java 21**
- **Spring Boot 3.3.4**
  - Spring Data JPA
  - Spring Security
  - Spring Web
- **Hibernate 6.6.1**
- **SQLite** (padrão) ou **MySQL** (opcional)
- **JWT** para autenticação e autorização
- **Maven** para gerenciamento de dependências
- **Java-Dotenv** para configuração via variáveis de ambiente


## Pré-requisitos

1. **Java JDK 21** ou superior
2. **Maven 3.9+**
3. Um editor de texto ou IDE (ex.: IntelliJ IDEA, VSCode, Eclipse)
4. Banco de dados (SQLite incluído; para MySQL, é necessário instalá-lo e configurá-lo)


## Configuração do Ambiente

### 1. Obtenha o Código Fonte

Clone este repositório ou faça o download:

```bash
git clone https://github.com/antoniopatricio22/compesa-backend.git
cd compesa
```
### 2. Configure as Variáveis de Ambiente
Modifique o arquivo .env na raiz do projeto e defina os valores necessários:

```bash
JWT_SECRET=seu_segredo_aqui #Chave para encriptação
JWT_EXPIRATION=86400000 # Tempo de expiração do token em milissegundos
```

### 3. Configure o Banco de Dados
SQLite (Padrão)
O banco SQLite será criado automaticamente no diretório database/ ao iniciar a aplicação.

MySQL (Opcional) modifique como no exemplo abaixo.

1 Atualize o arquivo application.properties para usar MySQL como no exemplo:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/compesa
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

```
2 Crie um banco de dados vazio chamado compesa no MySQL:
```bash
CREATE DATABASE compesa;
```

## Como Executar
### 1. Build e Executar Localmente
Compile e inicie o projeto com Maven::
```bash
mvn clean install
mvn spring-boot:run

```
A aplicação estará disponível em http://localhost:8080.


## APIs Disponíveis

- Autenticação
  - POST /auth/login - Gera token JWT

- Usuários
  - GET /usuarios - Lista todos os usuários (Admin)
  - POST /usuarios - Cria um novo usuário

- Atividades (em Desenvolvimento)
  - GET /atividades - Lista atividades associadas a um turno
  - POST /atividades - Cria uma nova atividade

Mais detalhes podem ser encontrados na Documentação para o Gerenciamento de Usuários.



## Deploy
### Opção 1: Servidor Local (Tomcat, Nginx, etc.)
1. Gere o arquivo .jar do projeto:
```bash
mvn clean package

```
2. Suba o .jar em seu servidor:
```bash
java -jar target/compesa-0.0.1-SNAPSHOT.jar

```

### Opção 2: Render
Render é uma plataforma de nuvem simples para hospedar aplicações. Siga os passos abaixo para realizar o deploy:

1. Preparação do Repositório:
  - Certifique-se de que o repositório do projeto está no GitHub ou GitLab.
  - O arquivo target/compesa-0.0.1-SNAPSHOT.jar será gerado ao executar o comando mvn clean package.

2. Criação do Serviço no Render:
  - Acesse sua conta no Render.
  - Clique em New + e selecione Web Service.
  - Conecte o repositório do projeto ao Render.

3. Configuração do Serviço:
    - Defina as configurações do serviço:
      - Environment: Configure como Java.
      - Build Command:
        ```bash
        mvn clean install
        ```
      - Start Command:
        ```bash
        java -jar target/compesa-0.0.1-SNAPSHOT.jar
        ```

4. Configuração de Variáveis de Ambiente:
    - No painel de variáveis de ambiente do Render, configure as variáveis usadas no projeto:
      - JWT_SECRET
      - JWT_EXPIRATION

5. Implantação:
    - Após salvar as configurações, o Render iniciará a implantação.
    - Quando finalizado, será fornecida uma URL pública para acessar a aplicação.
      

---
# Documentação para o Gerenciamento de Usuários
Esse documento descreve os endpoints para Autenticar e Gerencias contas de usuário na API. As responses seguem o 'Standart Response Structure in JSON format" como no exemplo abaixo: 

json
```bash
{
  "status": "success" or "error",
  "message": "Description of the outcome",
  "data": { /* Object with relevant data */ },
  "error": null or { /* Object with error details if any */ }
}
```

## Autenticação
1. **Login**
Endpoint: /auth/login
<br>Method: POST
<br>Description: Autentica um usuário e retorna um JWT token temporário.

Request Body:

json
```bash
{
  "username": "admin",
  "password": "admin123"
}
```
Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Login Bem Sucedido",
  "data": {
    "token": "jwt_token_here"
    },
  "error": null
}
```

- Error:

json
```bash
{
  "status": "error",
  "message": "Invalid username or password",
  "data": null,
  "error": {
    "code": "AUTH_INVALID",
    "details": "Provided credentials are incorrect"
  }
}
```

## Gerenciamento de contas (Admin Endpoints)
2. **Create User**
Endpoint: /admin/usuarios
<br>Method: POST
<br>Description: Cria uma nova conta de usuário. Somente Acessivel para ADMINISTRADOR.

Request Headers:

Authorization: Bearer <jwt_token_here>

Request Body:

json
```bash
{
  "username": "newuser",
  "password": "password123",
  "role": "CONTROLADOR"
}
```
Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Usuário Criado com Sucesso",
  "data": {
    "id": 124,
    "username": "newuser",
    "role": "CONTROLADOR",
    "createdAt": "2024-11-01T10:00:00Z"
  },
  "error": null
}
```

- Error:

json
```bash
{
  "status": "error",
  "message": "Acesso negado",
  "data": null,
  "error": {
    "code": "ACCESS_FORBIDDEN",
    "details": "Usuário não tem permissão para criar contas"
  }
}
```

3. **Update User**
Endpoint: /admin/usuarios/{id}
<br>Method: PUT
<br>Description: Atualiza os valores de um usuário, identificado pelo ID.

Request Headers:

Authorization: Bearer <jwt_token_here>

Request Body:

json
```bash
{
  "username": "updatedUser",
  "password": "newpassword123",
  "role": "ADMINISTRADOR"
}
```

Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Usuário Atualizado com Sucesso",
  "data": {
    "id": 124,
    "username": "updatedUser",
    "role": "ADMINISTRADOR",
    "updatedAt": "2024-11-01T11:00:00Z"
  },
  "error": null
}
```

- Error (Usuário não encontrado): 

json
```bash
{
  "status": "error",
  "message": "Usuário não encontrado",
  "data": null,
  "error": {
    "code": "USER_NOT_FOUND",
    "details": "Não Existe Usuário com Esse ID"
  }
}
```

4. **Delete User**
Endpoint: /admin/usuarios/{id}
<br>Method: DELETE
<br>Description: Remove uma conta de usuário.

Request Headers:

Authorization: Bearer <jwt_token_here>

Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Usuário Removido com Sucesso",
  "data": null,
  "error": null
}
```

- Error (Usuário não encontrado): 

json
```bash
{
  "status": "error",
  "message": "Usuário não encontrado",
  "data": null,
  "error": {
    "code": "USER_NOT_FOUND",
    "details": "Não Existe Usuário com Esse ID"
  }
}
```

5. **Get All Users**
Endpoint: /admin/usuarios
<br>Method: GET
<br>Description: Retorna uma Lista com os usuários cadastrados.

Request Headers:

Authorization: Bearer <jwt_token_here>

Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Lista de Usuários Recuperada com Sucesso",
  "data": [
    {
      "id": 124,
      "username": "user1",
      "role": "CONTROLADOR"
    },
    {
      "id": 125,
      "username": "user2",
      "role": "ADMINISTRADOR"
    }
  ],
  "error": null
}
```

6. **Get User by ID**
Endpoint: /admin/usuarios/{id}
<br>Method: GET
<br>Description: Busca um usuário especifico pelo ID.

Request Headers:

Authorization: Bearer <jwt_token_here>

Response:

- Success:

json
```bash
{
  "status": "success",
  "message": "Usuário Recuperado com Sucesso",
  "data": {
    "id": 124,
    "username": "user1",
    "role": "CONTROLADOR"
  },
  "error": null
}
```

- Error (Usuário não encontrado):

json
```bash
{
  "status": "error",
  "message": "User not found",
  "data": null,
  "error": {
    "code": "USER_NOT_FOUND",
    "details": "No user exists with the specified ID"
  }
}
```


