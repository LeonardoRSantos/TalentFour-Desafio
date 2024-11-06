# Sumário

# Índice
- [backend](#back-end)
  - [Sumário](#sumário)
  - [1 - Sistema BackEnd - Guia do Desenvolvedor ](#sistema-de-backend)
  - [2 - Tecnologias Utilizadas no BackEnd](#tecnologias-utilizadas-backend)
  - [3 - Setup e Execução Sistema de BackEnd ](#estrutura-diretorios-backend)




<br></br>
<a id="sistema-de-backend"></a>
> IMPORTANTE
> - Informações Cruciais para o Sucesso dos Desenvolvedores
    >   - Leia atentamente todo o arquivo readme.md: Este documento contém todas as informações atualizadas do projeto, apresentadas de forma sequencial e autoexplicativa. Confira abaixo 👇
> - Legenda de Ícones:
    >   - ℹ️ Informação: Linha azul com ícone, indicando um direcionamento sobre um determinado assunto. A quantidade de ícones representa o nível de importância.
>   - 👉 - Linha azul com icone direcionamento de pasta📂 / arquivo🗒️

<br></br>


## 1 - Sistema BackEnd - Guia do Desenvolvedor
<br></br>
Bem-vindo ao Guia do Sistema de BackEnd Desenvolvido para TalentFour
<br></br>


<ul>
  <li>✅ Login Seguro - Acesso autenticado para usuários utilizando Spring Security e JWT</li>    
  <li>✅ Padrão API Restful - Conformidade com a descrição do que foi solicitado</li>
  <li>✅ Query Nativa - Realização de atribuição de query nativa na service de cliente</li>
  <li>✅ Testes Unitários - Testes na controller validando a implementação</li>
  <li>✅ Testes Mock - Utilização de @WithMockUser para simular o acesso do usuário</li>
  <li>✅ Flyway - Utilização do flyway para gerenciar as minhas tabelas e os meus dados</li>
</ul>

<a id="sistema-de-backend"></a>

<br></br>

## 2 - Tecnologias Utilizadas Sistema de BackEnd  ℹ️

<br></br>

O sistema de frontend utiliza as seguintes tecnologias para criar uma aplicação robusta e dinâmica:
</br></br>

<ul align="justify">
  <li>📚 <strong>JAVA:</strong> Uma linguagem de programação amplamente utilizada para construir aplicações robustas, escaláveis e de alto desempenho.</li>
  <li>📚 <strong>SPRING BOOT:</strong> Um framework Java que facilita a criação de aplicações backend, proporcionando configurações automáticas e simplificação do desenvolvimento.</li>
  <li>📚 <strong>SPRING SECURITY:</strong> Um módulo do Spring Framework que oferece autenticação e controle de acesso, garantindo segurança em aplicações web.</li>
  <li>📚 <strong>FLYWAY:</strong> Uma ferramenta de migração de banco de dados para Java, permitindo a versionamento e gerenciamento de esquemas de banco de dados de forma automatizada.</li>
</ul>

<a id="tecnologias-utilizadas-backend"></a>
<br></br>


## 3 - Setup e Execução Sistema de BackEnd  ℹ️

1. Clone o repositório no Github.
- ```Java 
    git clone https://simplifysoft-it@dev.azure.com/simplifysoft-it/simplify/_git/simplify-frontend
    ``` 
2. Importar o projeto no ambiente de desenvolvimento utilizando o intelij.

3. Em Project Structure, na aba project, defina que a versão do Java 17 Amazon Corretto 17.0.12

4. Localize o application.yml

5. Modifique o usuário e a senha do banco Postgres para sua senha local do banco.

6. Execute o projeto

<a id="estrutura-diretorios-backend"></a>