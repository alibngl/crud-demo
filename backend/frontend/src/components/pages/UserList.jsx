import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "../css/UserList.css"

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const roles = JSON.parse(localStorage.getItem('roles')) || [];
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/users', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        setUsers(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching user list:', error);
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  const handleDelete = async (userId) => {
    try {
      await axios.delete(`http://localhost:8080/api/users/${userId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });
      setUsers(users.filter((user) => user.id !== userId));
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  const isAdmin = roles.includes('ADMIN');

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Çalışan Listesi</h1>
      {(
        <table border="1" style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead>
            <tr>
              <th className='id'>ID</th>
              <th>İsim</th>
              <th>Email</th>
              <th>Roller</th>
              <th className='deleteTh'> {isAdmin && "İşlem"}</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.roles.join(', ')}</td>
                <td>
                    {isAdmin && (
                        <button className="delete" onClick={() => handleDelete(user.id)}>
                        <i className="fa fa-trash"></i> Delete
                        </button>
                    )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UserList;
