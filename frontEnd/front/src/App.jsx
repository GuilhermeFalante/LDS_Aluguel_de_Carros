import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Vehicles from './pages/Vehicles';
import Home from './pages/Home';
import TesteConexao from './pages/TesteConexao';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/veiculos" element={<Vehicles />} />
                <Route path="/teste" element={<TesteConexao />} />
            </Routes>
        </Router>
    );
}

export default App;
