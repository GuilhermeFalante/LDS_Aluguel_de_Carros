// src/pages/Home.jsx
import { Link } from 'react-router-dom';

export default function Home() {
    return (
        <div style={{ padding: '2rem' }}>
            <h1>Bem-vindo ao Sistema de Aluguel de Carros</h1>
            <p>Escolha uma das opções abaixo para navegar:</p>

            <nav style={{ marginTop: '1rem' }}>
                <ul>
                    <li><Link to="/veiculos">Ver Veículos</Link></li>
                    <li><Link to="/clientes">Ver Clientes</Link></li>
                    <li><Link to="/pedidos">Ver Pedidos de Aluguel</Link></li>
                    <li><Link to="/login">Fazer login</Link></li>
                </ul>
            </nav>
        </div>
    );
}
