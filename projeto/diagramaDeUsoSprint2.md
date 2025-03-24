@startuml
left to right direction

actor Cliente
actor Agente
actor Empresa as Empresa
actor Banco as Banco
actor Usuario

Agente <|-- Empresa
Agente <|-- Banco
Usuario <|-- Cliente
Usuario <|-- Agente

rectangle "Sistema de Aluguel de Automóveis" {
    usecase "Introduzir Pedido de Aluguel" as UC2
    usecase "Modificar Pedido de Aluguel" as UC3
    usecase "Consultar Pedido de Aluguel" as UC4
    usecase "Cancelar Pedido de Aluguel" as UC5
    usecase "Avaliar Pedido" as UC6
    usecase "Modificar Pedido" as UC7
    usecase "Conceder contrato de crédito" as UC8
    usecase "Executar Contrato" as UC9
    usecase "Realizar Cadastro" as UC14
    usecase "Analisar Pedido Financeiramente" as UC15
    usecase "Contrato de cliente" as UC16
    usecase "Contrato de empresa" as UC17
    usecase "Contrato de banco" as UC18
}

UC9 <|-- UC16
UC9 <|-- UC17
UC9 <|-- UC18
Cliente --> UC2
Cliente --> UC3
Cliente --> UC4
Cliente --> UC5

Usuario --> UC14

Agente --> UC6
Agente --> UC7
Banco --> UC8
Agente --> UC9
Agente --> UC15
@enduml