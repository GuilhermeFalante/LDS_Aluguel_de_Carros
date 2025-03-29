@startuml
component "FrontEnd" as Frontend{
    [Web Page]
}

component "Backend (Spring MVC)" as Backend {
    [Gerenciamento de Pedido]
    [Gerenciamento de Contrato]
    [Gerenciamento de Cliente]
    [Gerenciamento de Automóvel]
    [Gerenciamento de Crédito]
}

component "Banco de Dados" as BD {
    database "Entidades" as Entidades {
        [Agente]
        [Emprego]
        [Contrato]
        [Cliente]
        [PedidoAluguel]
        [Automovel]
    }
}

Frontend ..> Backend :"HTTPS"
Backend ..> BD :"TCP/IP"
@enduml