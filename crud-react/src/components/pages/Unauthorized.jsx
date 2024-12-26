import React from 'react';
import { Link } from 'react-router-dom';

const Unauthorized = () => {
  return (
    <div style={{ textAlign: 'center', padding: '2rem' }}>
      <h1>Yetkisiz Erişim</h1>
      <p>Bu sayfaya erişim izniniz yok.</p>
      <Link to="/login">Giriş Sayfasına Git</Link>
    </div>
  );
};

export default Unauthorized;
