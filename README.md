Payment Processor

API desenvolvida com Java e Spring Boot para o processamento de transações de pagamento. O sistema gerencia o fluxo de cobranças, controle de estados e implementa mecanismos de idempotência para evitar duplicidade de registros.

Arquitetura do Projeto

O projeto utiliza o padrão de Arquitetura Hexagonal para garantir o desacoplamento entre a lógica de negócio e as tecnologias externas:

core/domain: Contém as regras de negócio puras e o gerenciamento de estados (PENDING, APPROVED, FAILED).

core/ports: Interfaces que definem os contratos de entrada (inbound) e saída (outbound).

core/usecase: Implementação da lógica de orquestração do pagamento.

adapters/inbound/web: Adaptadores de entrada para comunicação via REST API.

adapters/outbound/persistence: Implementação da camada de dados utilizando PostgreSQL.

adapters/outbound/external: Simulação de integração com provedores externos de pagamento.

Funcionalidades Implementadas

Controle de Estados: Gerenciamento do ciclo de vida da transação, garantindo a transição correta entre os status de processamento.

Mecanismo de Idempotência: Validação de requisições através de uma chave única (idempotencyKey). Caso o sistema receba uma chave já existente no banco de dados, ele retorna a transação original sem gerar novos processamentos.

Persistência de Dados: Utilização do Spring Data JPA com separação entre as classes de domínio e as entidades de persistência.

Infraestrutura Automatizada: Configuração de ambiente via Docker Compose para orquestração do banco de dados PostgreSQL (configurado na porta 5433 para evitar conflitos de porta padrão).

Simulação de Gateway: Adaptador de saída que simula o comportamento de autorização de um banco externo de forma assíncrona.

Tecnologias Utilizadas

Java 21

Spring Boot 3.5.13

Spring Data JPA

PostgreSQL

Docker e Docker Compose

Lombok

Status de Desenvolvimento

O projeto encontra-se em fase de construção. As próximas etapas incluem:

Implementação de lógica de Retry com Exponential Backoff para tratar falhas transientes de rede.

Criação de um sistema de tratamento global de exceções para padronização de retornos de erro.

Instruções de Execução
Pré-requisitos

JDK 21 ou superior instalado.

Docker e Docker Compose instalados e em execução.

Passo a Passo

Iniciar o container do banco de dados:

code
Bash
download
content_copy
expand_less
docker-compose up -d

Compilar e executar a aplicação:

code
Bash
download
content_copy
expand_less
./mvnw spring-boot:run

A aplicação será iniciada na porta 8080. O endpoint para criação de pagamentos é POST /v1/payments.

Exemplo de Requisição
code
JSON
download
content_copy
expand_less
{
  "amount": 150.00,
  "currency": "BRL",
  "idempotencyKey": "chave-exemplo-01"
}
