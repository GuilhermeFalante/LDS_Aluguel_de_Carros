@startuml
left to right direction

actor Cliente
actor Agente
actor Banco as Banco
actor Usuario

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
    usecase "Vincular automóvel" as UC16
    usecase "Cadastrar automóveis" as UC19
    usecase "Fazer login" as UC20
}

UC8 .> UC19 : "include"

Cliente --> UC2
Cliente --> UC3
Cliente --> UC4
Cliente --> UC5

Usuario --> UC14
Usuario --> UC20

Agente --> UC16
Agente --> UC19
Agente --> UC6
Agente --> UC7
Banco --> UC8
Agente --> UC9
Agente --> UC15
@enduml