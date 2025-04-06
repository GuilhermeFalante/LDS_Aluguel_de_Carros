// App.jsx
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Vehicles from './pages/Vehicles';
import Home from './pages/Home';
import LoginPage from './pages/Login';



function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/veiculos" element={<Vehicles />} />
                <Route path="/login" element={<LoginPage />} />
                {/* <Route path="/clientes" element={<Clientes />} /> */}
                {/* <Route path="/pedidos" element={<Pedidos />} /> */}
            </Routes>
        </Router>
    );
}

export default App;