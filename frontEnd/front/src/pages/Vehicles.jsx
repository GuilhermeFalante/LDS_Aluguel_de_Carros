// src/pages/Automoveis.jsx
import React, { useEffect, useState } from 'react';
import api from '../services/api';

function Automoveis() {
    const [automoveis, setAutomoveis] = useState([]);

    useEffect(() => {
        api.get('/automoveis')
            .then((response) => setAutomoveis(response.data))
            .catch((error) => console.error('Erro ao buscar automóveis:', error));
    }, []);

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">Lista de Automóveis</h1>
            <ul>
                {automoveis.map((auto) => (
                    <li key={auto.matricula}>
                        <strong>{auto.modelo}</strong> - Placa: {auto.placa}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Automoveis;
