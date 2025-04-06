// Login.jsx
import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import './login.css';

export default function LoginPage() {
  useEffect(() => {
    const btn = document.querySelector('.img__btn');
    const container = document.querySelector('.cont');

    if (btn && container) {
      const toggleClass = () => container.classList.toggle('s--signup');
      btn.addEventListener('click', toggleClass);

      // Limpa o event listener ao desmontar o componente
      return () => {
        btn.removeEventListener('click', toggleClass);
      };
    }
  }, []);

  return (
    <div className="cont">
      <div className="form sign-in">
        <h2>Bem-vindo</h2>
        <label>
          <span>Email</span>
          <input type="email" />
        </label>
        <label>
          <span>Senha</span>
          <input type="password" />
        </label>
        <p className="forgot-pass">Esqueceu sua senha?</p>
        <button type="button" className="submit">Entrar</button>
      </div>

      <div className="sub-cont">
        <div className="img">
          <div className="img__text m--up">
            <h3>Não tem uma conta? Por favor, cadastre-se!</h3>
          </div>
          <div className="img__text m--in">
            <h3>Se você já tem uma conta, basta fazer o login.</h3>
          </div>
          <div className="img__btn">
            <span className="m--up">Cadastre-se</span>
            <span className="m--in">Entrar</span>
          </div>
        </div>

        <div className="form sign-up">
          <h2>Crie sua conta</h2>
          <label>
            <span>Nome</span>
            <input type="text" />
          </label>
          <label>
            <span>Email</span>
            <input type="email" />
          </label>
          <label>
            <span>Senha</span>
            <input type="password" />
          </label>
          <button type="button" className="submit">Cadastre-se</button>
        </div>
      </div>
    </div>
  );
}
