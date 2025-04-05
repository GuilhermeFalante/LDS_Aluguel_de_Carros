@startuml
node "Computador do usuário" as user {
    component "Navegador"
}

node "Servidor de aplicação" as app_server {
    component "Frontend"
    component "Backend"
}

node "Banco de dados" as banco {
 component "datas"
}

user --> app_server : HTTPS
"Navegador" ..> "Frontend" 
"Frontend" ..> "Backend"
"Backend" ..> "datas"

app_server --> banco
@enduml