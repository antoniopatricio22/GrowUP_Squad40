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


Recife, Outubro de 2024.

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


