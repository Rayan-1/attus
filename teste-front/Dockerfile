# Estágio de build
FROM node:lts AS front-build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do pacote npm
COPY package*.json ./

# Instala todas as dependências (incluindo devDependencies) para construção
RUN npm install

# Copia todos os arquivos do código-fonte
COPY . .

# Compila a aplicação
RUN npm run build

# Estágio de produção
FROM node:lts-slim

# Instala o servidor HTTP para servir os arquivos estáticos
RUN npm install -g http-server

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos compilados do estágio de build para o estágio de produção
COPY --from=front-build /app/dist /app

# Expõe a porta necessária
EXPOSE 5000

# Comando para iniciar o servidor HTTP
CMD ["http-server", "-p", "5000"]
