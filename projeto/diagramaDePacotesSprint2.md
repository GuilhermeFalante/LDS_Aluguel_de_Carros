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
    package "service" {
    }
    package "enum" {
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
    controllers ..> service
    controllers ..> enum
    view ..> controllers :HTTP
    
}
@enduml