// src/pages/TesteConexao.jsx
import React, { useEffect, useState } from 'react';
import api from '../services/api';

function TesteConexao() {
    const [mensagem, setMensagem] = useState('Conectando...');

    useEffect(() => {
        api.get('/automoveis')
            .then(response => {
                setMensagem(`Conectado! Recebi ${response.data.length} automóvel(is).`);
                console.log(response.data);
            })
            .catch(error => {
                setMensagem('Erro ao conectar com o back-end.');
                console.error(error);
            });
    }, []);

    return <h1>{mensagem}</h1>;
}

export default TesteConexao;
