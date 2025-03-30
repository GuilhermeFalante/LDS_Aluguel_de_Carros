@startuml
package "pkg" {
    package "controllers" {
    }
    package "models" {
    }
    package "repositories" {
    }
    package "view" {
    }

    
    package "dto" {
        package "Agente" {
        }
        package "Banco" {
        }
        package "Cliente" {
        }
        package "Contrato" {
        }
        package "Empresa" {
        }
        package "Emprego" {
        }
        package "PedidoAluguel" {
        }
        package "Automovel" {
        }
    }
    

    controllers ..> repositories
    controllers ..> models
    controllers ..> dto
    view ..> controllers :HTTP
    
}
@enduml