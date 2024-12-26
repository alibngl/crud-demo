import React from 'react';
import { Link } from 'react-router-dom';
import "../css/Header.css"

function Header() {
  const token = localStorage.getItem('token');
  const roles = JSON.parse(localStorage.getItem('roles')) || [];

  const isAdmin = roles.includes('ADMIN');

  const isManager = roles.includes('MANAGER') 

  return (
    <header className='header'>
        <a href='https://www.adesso.com.tr/en/index-3.jsp'>
            <img className='logo' src='/img/adesso.jpg' alt='HTML tutorial'></img>
        </a>
      <nav>
        
        {token && <Link to="/user" className='link'>Ana Sayfa</Link>}

        {token && isManager && <Link to="/userList" className='link'>Kullanıcı Listesi</Link>}

        {token && isAdmin && <Link to="/addUser" className='link'>Kullanıcı Ekleme</Link>}
      </nav>
      <button
        onClick={() => {
          localStorage.clear();
          window.location.href = '/login';
        }}
        className='logout'
      >
        Logout
      </button>
    </header>
  );
}

export default Header;
