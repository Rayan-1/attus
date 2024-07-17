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


### Diretório `teste-back`

- **Dockerfile**: Arquivo de configuração do Docker para o backend.
- **HELP.md**: Arquivo de ajuda e documentação.
- **mvnw** e **mvnw.cmd**: Scripts para executar o Maven Wrapper.
- **out/**: Diretório de saída para compilação (geralmente ignorado pelo controle de versão).
- **pom.xml**: Arquivo de configuração do Maven.
- **src/**: Diretório de código-fonte.
  - **main/**: Contém o código-fonte principal.
    - **java/**: Código-fonte Java.
    - **resources/**: Arquivos de recursos.
  - **test/**: Contém os testes do projeto.

### Diretório `teste-front`

- **Dockerfile**: Arquivo de configuração do Docker para o frontend.
- **README.md**: Arquivo de documentação do projeto frontend.
- **babel.config.js**: Arquivo de configuração do Babel.
- **jsconfig.json**: Arquivo de configuração do JavaScript.
- **node_modules/**: Diretório de dependências do Node.js (geralmente ignorado pelo controle de versão).
- **package-lock.json**: Arquivo de bloqueio de dependências do Node.js.
- **package.json**: Arquivo de configuração do projeto Node.js.
- **public/**: Diretório de arquivos públicos.
- **src/**: Diretório de código-fonte.
  - **assets/**: Diretório de arquivos estáticos.
  - **components/**: Diretório de componentes Vue.js.
  - **views/**: Diretório de visualizações (views) Vue.js.
  - **App.vue**: Componente principal da aplicação Vue.js.
  - **main.js**: Arquivo de entrada principal da aplicação Vue.js.

---















