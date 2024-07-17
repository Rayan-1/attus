# Introdução 

Neste desafio, o objetivo é dockerizar uma aplicação que inclui funcionalidades de login e alteração de senha. A estrutura do projeto envolve a criação de imagens Docker para executar a aplicação de forma isolada e escalável. Além disso, é necessário configurar uma pipeline de CI/CD para automação dos testes, construção das imagens Docker e o push para um registry de imagens.

# Estrutura dos Diretorios

attus/
├── teste-back/
│   ├── Dockerfile
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── out/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
└── teste-front/
    ├── Dockerfile
    ├── README.md
    ├── babel.config.js
    ├── jsconfig.json
    ├── node_modules/
    ├── package-lock.json
    ├── package.json
    ├── public/
    └── src/
        ├── assets/
        ├── components/
        ├── views/
        ├── App.vue
        └── main.js
        
![image](https://github.com/user-attachments/assets/dbf650dd-0022-47ec-a55e-687096e92a30)

## Dockerfiles: Cada diretório (teste-back e teste-front) contém seu próprio Dockerfile que define como as imagens Docker para backend e frontend devem ser construídas.
## Docker Compose: O arquivo docker-compose.yml na raiz do projeto define como os serviços (backend e frontend) serão coordenados e conectados, facilitando o desenvolvimento e a execução do projeto em ambiente Dockerizado.

### Falando um pouco sobre o Dockerfile do Front-End Seguindo a Imagem a baixo:

![image](https://github.com/user-attachments/assets/424fe000-2c6e-4c77-86fe-c455f253b97f)


# Print 1

- **FROM node:lts AS front-build ->** Usa a imagem oficial do Node.js na versão LTS como base. A etapa é nomeada front-build.
- 
- **WORKDIR /app ->** Define o diretório /app como o diretório de trabalho dentro do contêiner.
- 
- **COPY package.json ./ ->** Copia os arquivos package.json e package-lock.json (ou yarn.lock, dependendo do gerenciador de pacotes) para o diretório de trabalho.
- 
- **RUN npm install ->** Executa npm install para instalar todas as dependências listadas no package.json.
- 
- **COPY . . ->** Copia todos os arquivos do projeto para o diretório de trabalho no contêiner.
- 
- **RUN npm run build ->** Executa o comando de build definido no package.json, geralmente algo como npm run build, que compila os arquivos da aplicação para o diretório dist.

# Print 2

- **FROM node:lts-slim ->** Usa a versão "slim" da imagem Node.js LTS como base, que é menor e mais eficiente.
- 
- **RUN npm install -g http-server ->** Instala o http-server globalmente para servir a aplicação estática.
- 
- **WORKDIR /app ->** Define o diretório /app ->  como o diretório de trabalho dentro do contêiner.
- 
- **COPY --from=front-build /app/dist /app ->** Copia os arquivos construídos na primeira etapa (front-build) do diretório /app/dist para o diretório de trabalho /app na etapa final.
- 
- **EXPOSE 5000 ->** Informa ao Docker que o contêiner escutará na porta 5000 em tempo de execução.
- 
- **CMD ["http-server", "-p", "5000"] ->** Define o comando que será executado quando o contêiner iniciar: inicia o http-server na porta 5000.

### Falando um pouco sobre o Dockerfile do Back-End Seguindo a Imagem a baixo:
 ![image](https://github.com/user-attachments/assets/11f86d33-6494-4ea0-a1f9-3a630f202078)

# Print 1

**FROM maven:3.8.5-openjdk-17 AS build** -> Utiliza a imagem oficial do Maven com o OpenJDK 17 como base. Esta etapa é nomeada como build

**WORKDIR /app** -> Define o diretório /app como o diretório de trabalho dentro do contêiner.

**COPY pom.xml ** -> Copia o arquivo pom.xml (arquivo de configuração do Maven) para o diretório de trabalho.

**RUN mvn dependency:go-offline -B** -> Executa o comando Maven para baixar todas as dependências do projeto, permitindo que a construção do projeto possa ser feita offline.

**COPY ./src ./src** -> Copia o diretório src (contendo o código-fonte do projeto) para o diretório de trabalho no contêiner

**RUN mvn clean install -DskipTests** -> Executa o comando Maven para construir o projeto, gerando o arquivo JAR. Os testes são pulados durante este processo (-DskipTests).

# Print 2

**FROM openjdk:17-jdk-slim** -> Utiliza a versão "slim" da imagem OpenJDK 17 como base para a execução, que é menor e mais eficiente.

**WORKDIR /app** -> Define o diretório /app como o diretório de trabalho dentro do contêiner.

**COPY --from=build /app/target/back-0.0.1-SNAPSHOT.jar ./back.jar** -> Copia o arquivo JAR gerado na etapa de build (/app/target/back-0.0.1-SNAPSHOT.jar) para o diretório de trabalho na nova imagem (/app), renomeando-o para back.jar.

**EXPOSE 8080** -> Informa ao Docker que o contêiner irá escutar na porta 8080 em tempo de execução.

**CMD ["java", "-jar", "back.jar"]** -> Define o comando a ser executado quando o contêiner iniciar: executa o JAR usando o comando java -jar back.jar.


## Falando um pouco sobre o Docker Compose Seguindo as Imagens abaixo
# Front
![image](https://github.com/user-attachments/assets/f0343df2-5676-498d-9b36-cbe84cdcc475)

**container_name** -> Define o nome do contêiner como teste-front.
**build** -> Especifica o diretório (./teste-front) onde o Dockerfile do frontend está localizado.
**ports** -> Mapeia a porta 5000 do contêiner para a porta 5000 do host.
**environment** -> Define a variável de ambiente NODE_ENV como production.
**networks** -> Conecta o serviço à rede front.
**depends_on** -> Define que o serviço front depende do serviço back, ou seja, back será iniciado antes de front.

# Back
![image](https://github.com/user-attachments/assets/3414658b-a31f-4d04-96de-d384d36e8e0b)

**container_name** -> Define o nome do contêiner como teste-back.
**build** -> Especifica o diretório (./teste-back) onde o Dockerfile do backend está localizado.
**ports:** -> Mapeia a porta 8080 do contêiner para a porta 8080 do host.
**environment:** -> Define variáveis de ambiente para configurar a conexão com o banco de dados PostgreSQL.
**networks** -> Conecta o serviço às redes front e db.
**depends_on** -> Define que o serviço back depende do serviço postgres, ou seja, postgres será iniciado antes de back.

# Postgres
![image](https://github.com/user-attachments/assets/d751a476-5519-4af7-87e9-9afa129c5e1d)

**image** -> Utiliza a imagem oficial do PostgreSQL na versão 13.
**environment** -> Define variáveis de ambiente para configurar o banco de dados PostgreSQL.
**ports** -> Mapeia a porta 5432 do contêiner para a porta 5432 do host.
**volumes** -> Monta o volume postgres-data para persistir os dados do PostgreSQL.
**networks** -> Conecta o serviço à rede db.

# Redes
![image](https://github.com/user-attachments/assets/83df1f2b-5352-4198-8eac-9af4bf150b34)

**Define duas redes, front e db, utilizando o driver bridge. Isso permite que os contêineres se comuniquem entre si dentro dessas redes.**

# Volumes
![image](https://github.com/user-attachments/assets/15359571-486a-4516-b18d-e27bfd3cd213)
**Define um volume chamado postgres-data para persistir os dados do banco de dados PostgreSQL. Este volume garante que os dados não sejam perdidos quando o contêiner do PostgreSQL for reiniciado ou removido.**

**Resumo greal Sobre o Docker Compose**
- *Frontend (front):* Uma aplicação Node.js servida por HTTP na porta 5000
- *Backend (back):* Uma aplicação Java Spring Boot conectada ao PostgreSQL, escutando na porta 8080.
- *PostgreSQL (postgres):* Um banco de dados PostgreSQL, escutando na porta 5432 e persistindo dados em um volume Docker.

















