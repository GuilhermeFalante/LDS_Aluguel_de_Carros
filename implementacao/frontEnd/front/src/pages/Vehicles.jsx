// src/pages/Vehicles.jsx
import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Vehicles() {
    const [carros, setCarros] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/automoveis')
            .then(res => setCarros(res.data))
            .catch(err => console.error('Erro ao buscar carros:', err));
    }, []);

    return (
        <div style={{ padding: '2rem' }}>
            <h1>Lista de Carros</h1>
            <ul>
                {carros.map((carro, index) => (
                    <li key={index}>{carro.modelo} - {carro.placa}</li>
                ))}
            </ul>
        </div>
    );
}
