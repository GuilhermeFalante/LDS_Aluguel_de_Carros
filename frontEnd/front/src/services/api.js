// src/services/api.js
import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080', // REMOVIDO o /api
});

export default api;
