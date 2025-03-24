@startuml
!define RECTANGLE class

package "Usuarios" as usuarios {
    class Usuario
    class Cliente
    class Agente
}

package "AgentesFinanceiros" as financeiro {
    class Banco
    class Empresa
}

package "Transacoes" as transacoes {
    class PedidoAluguel
    class Automovel
}

package "Contratos" as contratos {
    class Contrato
    enum TIPO_CONTRATO
}

package "Servidor" as servidor {
    class Servidor
}

usuarios -[hidden]> financeiro
usuarios -[hidden]> transacoes
transacoes -[hidden]> contratos
contratos -[hidden]> usuarios
contratos -[hidden]> financeiro

@enduml
