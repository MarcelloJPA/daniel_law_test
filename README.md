# Sistema de Integração WIPO - Daniel Law

**Projeto Técnico** para o processo seletivo da **Daniel Law**.

---

## Descrição

Sistema web em **Java Web** que integra dados de patentes do portal WIPO, permitindo buscar informações de um processo pelo número e salvá-las em banco de dados.


  <img src="Tela 1.png" alt="Tela 1" width="800"/>
  <img src="Tela 2.png" alt="Tela 2" width="800"/>



---

## Tecnologias

- Java 21

- Spring Boot 3.4.5

- Maven (WAR) 3.9.9

- Hibernate / Spring Data JPA

- jQuery / AJAX / JSP 

- Selenium WebDriver 4.20.0

- jsoup 1.19.1

- PostgreSQL 17

- JUnit 5

---

---

## Instalação e execução

1. Clone o repositório:

   ```bash
   git clone https://github.com/MarcelloJPA/daniel_law_test.git
   ```

2. Configure o banco em `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DB>
   spring.datasource.username=<USUARIO>
   spring.datasource.password=<SENHA>
   spring.jpa.hibernate.ddl-auto=create
   ```

3. Execute:

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

Acesse `http://localhost:8080//patents/`.

---

## Funcionalidades

### 1. Cadastro de Processo WIPO

- Tela JSP com campo para número de processo (ex.: `WO2002008676`) e botão **Buscar**.

- Em vez de `HttpGet`, utiliza **Selenium WebDriver** para navegar até a URL:

  ```
  https://patentscope.wipo.int/search/pt/detail.jsf?docId=<numeroProcesso>&redirectedID=true
  ```

- Extrai o HTML com **jsoup**, capturando:

  - Nº de publicação
  - Nº do pedido internacional
  - Data de publicação
  - Requerentes
  - Título

- Preenchimento via **jQuery/AJAX** e botão **Salvar** para persistir com **Hibernate**.

### 2. Pesquisa de Processos

- Tela JSP com filtros por **Número de Processo** e **Requerente**.
- Consulta ao banco e exibição dos resultados em tabela, acionada por **jQuery/AJAX**.

### 3. Testes Unitários

- **JUnit 5**:
  - Verifica a inicialização do WebDriver e conexão à URL WIPO.
  - Valida extração e formato dos campos do HTML retornado.

---

> **Observação**: toda a comunicação com o WIPO é feita por meio de Selenium para contornar bloqueios de acesso via HTTP direto.

