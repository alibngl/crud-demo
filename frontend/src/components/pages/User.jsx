import React, { useEffect, useState } from 'react';

const User = () => {
  const [username, setUsername] = useState('');
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    if (storedUsername) {
      setUsername(storedUsername);
    }

    const storedRoles = localStorage.getItem('roles');
    if (storedRoles) {
      setRoles(JSON.parse(storedRoles));
    }
  }, []);

  return (
    <div>
      <h1>Hoş Geldin, {username}!</h1>
      <h2>Sahip Olduğun Roller:</h2>
      <ul>
        {roles.map((role, index) => (
          <li key={index}>{role}</li>
        ))}
      </ul>
    </div>
  );
};

export default User;
