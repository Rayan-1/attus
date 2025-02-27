# Introdução 

Neste desafio, o objetivo é dockerizar uma aplicação que inclui funcionalidades de login e alteração de senha. A estrutura do projeto envolve a criação de imagens Docker para executar a aplicação de forma isolada e escalável. Além disso, é necessário configurar uma pipeline de CI/CD para automação dos testes, construção das imagens Docker e o push para um registry de imagens.

# Estrutura dos Diretorios

![image](https://github.com/user-attachments/assets/46bc1506-20d0-4d89-a913-956d35158974)

        
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

- **FROM maven:3.8.5-openjdk-17 AS build** -> Utiliza a imagem oficial do Maven com o OpenJDK 17 como base. Esta etapa é nomeada como build

- **WORKDIR /app** -> Define o diretório /app como o diretório de trabalho dentro do contêiner.

- **COPY pom.xml ** -> Copia o arquivo pom.xml (arquivo de configuração do Maven) para o diretório de trabalho.

- **RUN mvn dependency:go-offline -B** -> Executa o comando Maven para baixar todas as dependências do projeto, permitindo que a construção do projeto possa ser feita offline.

- **COPY ./src ./src** -> Copia o diretório src (contendo o código-fonte do projeto) para o diretório de trabalho no contêiner

- **RUN mvn clean install -DskipTests** -> Executa o comando Maven para construir o projeto, gerando o arquivo JAR. Os testes são pulados durante este processo (-DskipTests).

# Print 2

- **FROM openjdk:17-jdk-slim** -> Utiliza a versão "slim" da imagem OpenJDK 17 como base para a execução, que é menor e mais eficiente.

- **WORKDIR /app** -> Define o diretório /app como o diretório de trabalho dentro do contêiner.

- **COPY --from=build /app/target/back-0.0.1-SNAPSHOT.jar ./back.jar** -> Copia o arquivo JAR gerado na etapa de build (/app/target/back-0.0.1-SNAPSHOT.jar) para o diretório de trabalho na nova imagem (/app), renomeando-o para back.jar.

- **EXPOSE 8080** -> Informa ao Docker que o contêiner irá escutar na porta 8080 em tempo de execução.

- **CMD ["java", "-jar", "back.jar"]** -> Define o comando a ser executado quando o contêiner iniciar: executa o JAR usando o comando java -jar back.jar.


## Falando um pouco sobre o Docker Compose Seguindo as Imagens abaixo
# Front
![image](https://github.com/user-attachments/assets/f0343df2-5676-498d-9b36-cbe84cdcc475)

- **container_name** -> Define o nome do contêiner como teste-front.

- **build** -> Especifica o diretório (./teste-front) onde o Dockerfile do frontend está localizado.

- **ports** -> Mapeia a porta 5000 do contêiner para a porta 5000 do host.

- **environment** -> Define a variável de ambiente NODE_ENV como production.

- **networks** -> Conecta o serviço à rede front.

- **depends_on** -> Define que o serviço front depende do serviço back, ou seja, back será iniciado antes de front.

# Back
![image](https://github.com/user-attachments/assets/3414658b-a31f-4d04-96de-d384d36e8e0b)

- **container_name** -> Define o nome do contêiner como teste-back.

- **build** -> Especifica o diretório (./teste-back) onde o Dockerfile do backend está localizado.

- **ports:** -> Mapeia a porta 8080 do contêiner para a porta 8080 do host.

- **environment:** -> Define variáveis de ambiente para configurar a conexão com o banco de dados PostgreSQL.

- **networks** -> Conecta o serviço às redes front e db.

- **depends_on** -> Define que o serviço back depende do serviço postgres, ou seja, postgres será iniciado antes de back.

# Postgres
![image](https://github.com/user-attachments/assets/d751a476-5519-4af7-87e9-9afa129c5e1d)

- **image** -> Utiliza a imagem oficial do PostgreSQL na versão 13.

- **environment** -> Define variáveis de ambiente para configurar o banco de dados PostgreSQL.

- **ports** -> Mapeia a porta 5432 do contêiner para a porta 5432 do host.

-  **volumes** -> Monta o volume postgres-data para persistir os dados do PostgreSQL.

- **networks** -> Conecta o serviço à rede db.

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


# Execução do Docker Compose
- Certifique-se de que você está no diretório correto onde o arquivo docker-compose.yml está localizado. No seu caso, o diretório é ~/attus.
- Execute o seguinte comando para iniciar os serviços definidos no docker-compose.yml: **docker-compose up --build** ou **docker-compose up -d**:
-  **docker-compose up --build**: Este comando instrui o Docker Compose a construir (ou reconstruir) as imagens para os serviços definidos no arquivo docker-compose.yml, mesmo que elas já existam
- ![image](https://github.com/user-attachments/assets/598a70c5-9a3e-4b74-8503-255adec5c018)
- ![image](https://github.com/user-attachments/assets/659eae95-4f0d-45b8-a38e-7b637e6d108e)

- **docker-compose up -d**: Este comando instrui o Docker Compose a iniciar os contêineres em segundo plano (detached mode), sem reconstruir as imagens.
-   ![image](https://github.com/user-attachments/assets/c1ed1f76-089d-4736-8612-2ae1c8e70a7d)

- Como foi mostrados nas imagens anteriores como podemos ver como contruir as imagens biuldar e executar os containes

![image](https://github.com/user-attachments/assets/de5d6d76-77ea-458e-a669-701c4b24a311)

**Agora vamos expor as portas dos containeres**

- http://localhost:5000
  ![image](https://github.com/user-attachments/assets/dc039629-26a4-435c-9d11-3227ad6a5450)
- http://localhost:8080
  ![image](https://github.com/user-attachments/assets/e63e1611-cf55-45e7-aed3-cdfbdf30b5cd)

**Validação da Conexão do Front com o Back garantindo que os containeres estão se comunicando entre si dentro da mesma rede.
- Usando o exemplo do usuario teste no codigo
- **Email:** "devops@attus.com"
- **Senha:** "12136677"
![image](https://github.com/user-attachments/assets/3e175d73-a927-49d2-803a-f223e4063838)

![image](https://github.com/user-attachments/assets/cdbb1428-efd9-4d9b-ac53-ec6b7b23955b)
# Login
![image](https://github.com/user-attachments/assets/63aad1fa-50eb-46b1-be00-1e3264bdaadd)
![image](https://github.com/user-attachments/assets/f11f4095-f3d6-48a3-b848-c553b48ce10c)

# Mudar Senha
![image](https://github.com/user-attachments/assets/fa0dd83e-3aa6-4db0-b717-4d8bcfc45032)
![image](https://github.com/user-attachments/assets/12246df4-accb-4cc1-8657-729f20425a79)
![image](https://github.com/user-attachments/assets/e049f1d8-19a7-472c-b2da-b15d85dd0e54)

**Validamos que a conexão do Back e Front ta Ok**
- **Agora iremos parar os containeres com o comando **docker-compose down****
![image](https://github.com/user-attachments/assets/ce0bf62e-afd9-4bc0-b2aa-9039810746d1)
- ** Com isso as paginas ficam indisponiveis**
  ![image](https://github.com/user-attachments/assets/cf59296b-12b4-403d-b466-40c6f5ba6866)
  ![image](https://github.com/user-attachments/assets/2fcc11ec-d832-4583-a5d7-9b57c08bb930)
  ![image](https://github.com/user-attachments/assets/7beaba98-d3dc-463b-8c54-f5f7e295a9d7)

# Pipeline CI/CD

![image](https://github.com/user-attachments/assets/701df6ed-feb2-42ac-b261-0db0dac4e525)


# Trigger: O pipeline é acionado em push para a branch main e em pull requests abertos, sincronizados ou reabertos.

- **Job 'build-and-deploy':**

- Configura um serviço PostgreSQL.
- Realiza o checkout do repositório e instala dependências como Docker, Docker Compose e JDK 17.
- Constrói e implanta serviços usando Docker Compose.
- Testa a conectividade com os serviços teste-front e teste-back.
  
- **Job 'sonarcloud':**

- Realiza o checkout completo do repositório.
- Configura o ambiente Java com JDK 17.
- Configura e instala o SonarScanner para análise estática.
- Executa o SonarScanner para análise de código.
- Este pipeline automatiza a construção, implantação e análise estática de código em um ambiente de desenvolvimento e integração contínua.

- Instala todas as dependencias e executa os containeres faz um teste d conexão 
![image](https://github.com/user-attachments/assets/94b18880-8f98-465e-af2d-5a2fb20ebdee)

- Faz a inspenção continua para fazer analise no codigo (code small)
  ![image](https://github.com/user-attachments/assets/71b0b162-9e48-4b58-a9dc-bda02ddc8b82)
- Resultados da Analise
  ![image](https://github.com/user-attachments/assets/1a7301c9-0a3b-4470-a4d8-bb62a3f4e3a6)

- Validando pelo Site do Sonar para validar se os resultados Batem
- ![image](https://github.com/user-attachments/assets/69f80f86-af43-482f-999d-45dd48e9adaf)
- ![image](https://github.com/user-attachments/assets/1536de2c-c5e2-4961-82db-36297a46224a)

















