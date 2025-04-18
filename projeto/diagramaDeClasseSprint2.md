@startuml
abstract Usuario {
    -nome: string
    -email: string
    -senha: string
    +cadastrar()
    +logar()
}

enum TIPO_DONO {
    CLIENTE,
    EMPRESA,
    BANCO
}

enum STATUS_PEDIDO {
    FECHADO,
    ABERTO,
    APROVADO
}

class Cliente {
    -rg: string
    -cpf: string
    -endereco: string
    -emprego: Emprego
    -LimRendimentos: int = 3
    +introduzirPedido(): void
    +modificarPedido(idPedido: int): void
    +consultarPedido(idPedido: int): pedido
    +cancelarPedido(idPedido: int): pedido
}

class Agente {
    -cpf: String
    +modificarPedido(idPedido: int): void
    +avaliarPedido(idPedido: int): pedido
    +executarContrato(idPedido: int, tipoCliente: cpfCliente): pedido
    +gerenciarAutomoveis(): void
}

class Empresa {
}

class Banco {
    -cnpj: String
    +concederContratoCredito(idContrato: int): contrato
    +analisarPedidoFinanceiramente(idPedido: int): pedido
}

class PedidoAluguel {
    -id: int
    -Status: boolean
    -Valor: float
    -cliente: Cliente
    -automovel: Automovel
    -contratoDeCredito: Double
    
}

class Contrato {
    -id: int
    -pedido: Pedido
    -status: boolean
    -valor: float
    -contratoDeCredito: int
}

class Automovel {
    -matricula: string
    -ano: int
    -marca: string
    -modelo: string
    -placa: string
    -tipoDono: TIPO_DONO
    -disponivel: boolean
}

class Emprego {
    -empregador: String
    -salario: Double
}

Usuario <|-- Cliente
Usuario <|-- Agente
Agente <|-- Empresa
Agente <|-- Banco
Emprego "0..3" --* "1"Cliente

Contrato "0..N" -- "1" Cliente
Contrato "1" -- "1" PedidoAluguel
Cliente "1" -- "N" PedidoAluguel
Agente "1" -- "N" PedidoAluguel 
Banco "1" -- "N" PedidoAluguel 
PedidoAluguel "1" -- "N" Automovel
@enduml