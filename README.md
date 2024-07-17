#Introdução 

Neste desafio, o objetivo é dockerizar uma aplicação que inclui funcionalidades de login e alteração de senha. A estrutura do projeto envolve a criação de imagens Docker para executar a aplicação de forma isolada e escalável. Além disso, é necessário configurar uma pipeline de CI/CD para automação dos testes, construção das imagens Docker e o push para um registry de imagens.

#Estrutura dos Diretorios

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
