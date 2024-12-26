import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/pages/Login';
import User from './components/pages/User';
import UserList from './components/pages/UserList';
import Header from './components/pages/Header';
import AddUser from './components/pages/AddUser';
import Unauthorized from './components/pages/Unauthorized';
import PrivateRoute from './components/pages/PrivateRoute';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/unauthorized" element={<Unauthorized />} />
        
        {/* Private Route Kullanımı */}
        <Route
          path="/user"
          element={
            <PrivateRoute allowedRoles={['USER', 'ADMIN']}>
              <User />
            </PrivateRoute>
          }
        />
        <Route
          path="/userList"
          element={
            <PrivateRoute allowedRoles={['ADMIN']}>
              <UserList />
            </PrivateRoute>
          }
        />
        <Route
          path="/addUser"
          element={
            <PrivateRoute allowedRoles={['ADMIN']}>
              <AddUser />
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
