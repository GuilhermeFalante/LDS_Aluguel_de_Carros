// src/services/api.js
import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:5173', // URL do seu back-end
});

export default api;
