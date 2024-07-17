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
- **WORKDIR /app ->** Define o diretório /app como o diretório de trabalho dentro do contêiner.
- **COPY package.json ./ ->** Copia os arquivos package.json e package-lock.json (ou yarn.lock, dependendo do gerenciador de pacotes) para o diretório de trabalho.
- **RUN npm install ->** Executa npm install para instalar todas as dependências listadas no package.json.
- **COPY . . ->** Copia todos os arquivos do projeto para o diretório de trabalho no contêiner.
- **RUN npm run build ->** Executa o comando de build definido no package.json, geralmente algo como npm run build, que compila os arquivos da aplicação para o diretório dist.

# Print 2

- **FROM node:lts-slim ->** Usa a versão "slim" da imagem Node.js LTS como base, que é menor e mais eficiente.
- **RUN npm install -g http-server ->** Instala o http-server globalmente para servir a aplicação estática.
- **WORKDIR /app ->** Define o diretório /app ->  como o diretório de trabalho dentro do contêiner.
- **COPY --from=front-build /app/dist /app ->** Copia os arquivos construídos na primeira etapa (front-build) do diretório /app/dist para o diretório de trabalho /app na etapa final.
- **EXPOSE 5000 ->** Informa ao Docker que o contêiner escutará na porta 5000 em tempo de execução.
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


















