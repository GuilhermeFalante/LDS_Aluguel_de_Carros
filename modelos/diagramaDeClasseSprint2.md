@startuml
abstract Usuario {
    -nome: string
    -email: string
    -senha: string
    +cadastrar()
}

enum TIPO_CONTRATO {
    CLIENTE,
    EMPRESA,
    BANCO
}

class Cliente {
    -rg: string
    -cpf: string
    -endereco: string
    -profissao: string
    -LimRendimentos: int = 3
    +introduzirPedido(): void
    +modificarPedido(idPedido: int): void
    +consultarPedido(idPedido: int): pedido
    +cancelarPedido(idPedido: int): pedido
}

class Agente {
    +modificarPedido(idPedido: int): void
    +avaliarPedido(idPedido: int): pedido
    +executarContrato(idPedido: int, tipoCliente: cpfCliente): pedido
}

class Empresa {
}

class Banco {
    +concederContratoCredito(idContrato: int): contrato
    +analisarPedidoFinanceiramente(idPedido: int): pedido
}

class PedidoAluguel {
    -id: int
    -Status: boolean
    -Valor: float
    -idCliente: int
    -matriculaCarro: String
    
}

class Contrato {
    -id: int
    -idPedido: int
    -status: boolean
    -valor: float
    -contratoDeCredito: int
    -tipoContrato : TIPO_CONTRATO
}

class Automovel {
    -matricula: string
    -ano: int
    -marca: string
    -modelo: string
    -placa: string
}

class Servidor {
    +gerenciarVeiculos(): void
    +gerenciarPedidos(): void
}

Usuario <|-- Cliente
Usuario <|-- Agente
Agente <|-- Empresa
Agente <|-- Banco

Cliente "1" -- "N" PedidoAluguel
Agente "1" -- "N" PedidoAluguel 
Banco "1" -- "N" PedidoAluguel 
PedidoAluguel "1" -- "N" Automovel
Servidor -- PedidoAluguel  
Servidor -- Automovel
@enduml