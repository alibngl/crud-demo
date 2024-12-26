import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AddUser = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    roles: [],
  });

  const [message, setMessage] = useState('');
  const rolesList = ['USER', 'ADMIN', 'MANAGER'];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleRoleChange = (e) => {
    const { value, checked } = e.target;
    setFormData((prevState) => {
      const updatedRoles = checked
        ? [...prevState.roles, value]
        : prevState.roles.filter((role) => role !== value);
      return { ...prevState, roles: updatedRoles };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const requestBody = {
      username: formData.username,
      password: formData.password,
      email: formData.email,
      enabled: true,
      userRoleTables: formData.roles.map((role) => ({
        userRoleEnum: role,
      })),
    };
  
    console.log('Gönderilen Veri:', requestBody);
  
    try {
      await axios.post('http://localhost:8080/api/users/register', requestBody, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setMessage('Çalışan başarıyla eklendi!');
      setFormData({ username: '', email: '', password: '', roles: []});
    } catch (error) {
      console.error('Çalışan eklenirken hata:', error);
      if (error.response) {
        console.error('Hata Detayı:', error.response.data);
      }
      setMessage('Çalışan eklenirken bir hata oluştu.');
    }
  };
  

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Çalışan Ekle</h1>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', maxWidth: '400px' }}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            style={{ marginBottom: '1rem', padding: '0.5rem', width: '100%' }}
          />
        </label>
        <label>
          Email:
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            style={{ marginBottom: '1rem', padding: '0.5rem', width: '100%' }}
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            style={{ marginBottom: '1rem', padding: '0.5rem', width: '100%' }}
          />
        </label>
        <div style={{ marginBottom: '1rem' }}>
          <h3>Roller:</h3>
          {rolesList.map((role) => (
            <label key={role} style={{ display: 'block', marginBottom: '0.5rem' }}>
              <input
                type="checkbox"
                value={role}
                onChange={handleRoleChange}
                checked={formData.roles.includes(role)}
              />
              {role}
            </label>
          ))}
        </div>
        <button
          type="submit"
          style={{
            padding: '0.5rem',
            backgroundColor: '#007BFF',
            color: '#FFF',
            border: 'none',
            cursor: 'pointer',
          }}
        >
          Çalışan Ekle
        </button>
      </form>
      {message && <p style={{ marginTop: '1rem' }}>{message}</p>}
    </div>
  );
};

export default AddUser;
