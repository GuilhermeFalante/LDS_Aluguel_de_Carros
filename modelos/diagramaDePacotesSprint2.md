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

' Servidor abaixo de tudo
package "Servidor" as servidor {
    class Servidor
}

' Organizando o layout quadriculado
' A linha a seguir pode ser usada para forçar a distribuição dos pacotes

' Definindo a posição das entidades no diagrama
' Pacotes na parte superior
usuarios -[hidden]> financeiro
usuarios -[hidden]> transacoes
' Pacotes na parte inferior
transacoes -[hidden]> contratos
contratos -[hidden]> usuarios
contratos -[hidden]> financeiro

@enduml
