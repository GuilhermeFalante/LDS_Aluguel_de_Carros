@startuml
package "pkg" {
    package "configs" {
    }
    package "controllers" {
    }
    package "models" {
    }
    package "repositories" {
    }
    package "enumerators" {
    }
    package "utils" {
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
    
    controllers ..> configs
    controllers ..> repositories
    controllers ..> models
    controllers ..> utils
    controllers ..> dto
    
    models ..> enumerators
}
@enduml
