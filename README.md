# 3Wings Application

## • Inicialização do Projeto

Certifique-se de ter o **Docker Desktop** instalado antes de prosseguir. Será necessário tê-lo para usar o "docker compose" mais a frente. Você pode baixá-lo para [Windows](https://docs.docker.com/desktop/install/windows-install/) ou [Mac](https://docs.docker.com/desktop/install/mac-install/).

### 1. Baixe o Código Fonte

Clone o repositório do projeto em sua máquina local:

```
git clone https://github.com/netosantosbr/3wings-app.git
```

### 2. Inicialize o Backend

Navegue até o diretório backend do projeto no terminal:

```
cd 3wings-app/backend
```
Execute o seguinte comando para limpar, compilar e gerar o arquivo **JAR**:
##### **OBS:** Este comando além de limpar, compilar e gerar o arquivo JAR, também irá realizar os testes. Mas caso queira executar apenas os testes separadamente, use "*./mvnw test*"

```
./mvnw clean package
```

### 3. Inicialize o Docker Compose

Volte para o diretório principal do projeto:

```
cd ..
```

Execute o seguinte comando para iniciar os serviços usando o Docker Compose:

```
docker-compose up
```
Aguarde até que todos os serviços (frontend, backend e MySQL) estejam totalmente levantados.

### 4. Acesse o Frontend
Abra seu navegador e acesse o frontend em http://localhost:5173.

🎉 Agora você está pronto para começar a usar o aplicativo! 🎉

