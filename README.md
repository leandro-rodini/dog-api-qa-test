# Dog API QA Challenge

Automação de testes de API para a [Dog API](https://dog.ceo/dog-api/documentation), feita com foco em cobertura funcional, leitura simples e execução em CI.

O objetivo aqui não foi criar uma suíte enorme. A ideia foi validar os pontos que realmente importam para o consumo da aplicação: listar raças, buscar imagens por raça e checar a imagem aleatória.


# Diagrama da pipeline

Isso ajuda a mostrar que a suíte não depende só da sua máquina local e que a avaliação pode ser feita com rastreabilidade.

![Fluxo de execução da pipeline no GitHub Actions](docs/diagrams/pipeline-github-actions-flow.gif)

## Relatório público (GitHub Pages)

**Acesse o relatório público no GitHub Pages.**

- https://leandro-rodini.github.io/dog-api-qa-test/

## O que este projeto cobre

- `GET /breeds/list/all`
- `GET /breed/{breed}/images`
- `GET /breeds/image/random`

Os testes validam código de status, estrutura da resposta, conteúdo básico e alguns comportamentos esperados da API.

## Por que montei assim

Usei uma estrutura simples porque esse tipo de desafio costuma ser melhor avaliado quando o código é fácil de entender e fácil de manter.

- `Java 17` porque é estável e comum em contexto corporativo.
- `JUnit 5` porque deixa a suíte organizada e clara.
- `Rest Assured` porque é direto para testar API REST.
- `Maven` porque facilita execução local e em pipeline.
- `Allure` porque entrega relatório com evidências melhores do que só console.

## Diagrama da solução

Esse desenho ajuda a visualizar que a suíte não está testando uma aplicação própria, e sim uma API pública externa, então a cobertura precisa ser objetiva e resiliente.

![Fluxo de validação da Dog API](docs/diagrams/dog-api-validation-flow.gif)

## Estrutura do projeto

```text
dog-api-qa-test/
|-- .github/
|   `-- workflows/
|       `-- pipeline.yml
|-- src/
|   `-- test/
|       |-- java/
|       |   `-- com/dogapi/
|       |       |-- base/
|       |       |   `-- BaseTest.java
|       |       |-- tests/
|       |       |   |-- ListAllBreedsTest.java
|       |       |   |-- BreedImagesTest.java
|       |       |   `-- RandomImageTest.java
|       |       `-- utils/
|       |           `-- DataProvider.java
|       `-- resources/
|-- pom.xml
`-- README.md
```

### Papel de cada parte

- `base/`: configuração compartilhada da suíte.
- `tests/`: cenários de API por endpoint.
- `utils/`: massa de dados usada nos testes parametrizados.
- `.github/workflows/`: pipeline de execução automatizada.

## Estratégia de teste

A estratégia foi simples de propósito:

- Validar o caminho feliz dos endpoints;
- Validar um erro esperado em caso de raça inválida;
- Validar formato e conteúdo básico da resposta;
- Validar uma resposta aleatória sem acoplar o teste a um valor fixo;
- Manter a suíte rápida para rodar em PR e pipeline.

## Configuração da base URL

A URL base pode ser sobrescrita sem mexer no código.

Prioridade usada pela suíte:

1. Propriedade JVM `dog.api.base-url`
2. Variável de ambiente `DOG_API_BASE_URL`
3. Valor padrão `https://dog.ceo/api`

Exemplo com Maven:

```bash
mvn clean test -Ddog.api.base-url=https://dog.ceo/api
```

Exemplo com variável de ambiente:

```bash
export DOG_API_BASE_URL=https://dog.ceo/api
mvn clean test
```

## Como executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.9+
- Git

### Rodar a suíte

```bash
mvn clean test
```

### Gerar o relatório Allure

```bash
mvn allure:report
mvn allure:serve
```

Importante: não abra o arquivo `index.html` do Allure com duplo clique (file://), porque os widgets podem ficar travados em `Loading...`.
O relatório deve ser servido via HTTP (por exemplo com `mvn allure:serve`) ou acessado pela URL publicada no GitHub Pages da pipeline.

### Rodar no Windows

```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn clean test
```

## Pipeline

O workflow está em [.github/workflows/pipeline.yml](.github/workflows/pipeline.yml).

Ele faz o básico que interessa em uma entrega de QA:

- Roda em `push` e `pull_request`;
- Sobe Java 17;
- Executa a suíte;
- Gera o relatório Allure;
- Publica os artefatos da execução.

## Observações práticas

- O projeto foi pensado para ser executável em Windows, Linux e macOS.
- Como a API é externa, oscilações de rede podem acontecer.
- O teste de imagem aleatória aceita variabilidade real do serviço, sem forçar um valor fixo.

## O que eu manteria se fosse evoluir isso para um cenário real

Se isso fosse para produção, eu seguiria por este caminho:

1. Deixar a base URL configurável por ambiente de forma centralizada;
2. Adicionar contrato com schema mais forte;
3. Publicar o Allure em um ponto fixo;
4. Separar smoke de regressão;
5. Incluir métricas de execução no pipeline.