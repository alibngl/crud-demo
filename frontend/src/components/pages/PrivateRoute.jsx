import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children, allowedRoles }) => {
  const roles = JSON.parse(localStorage.getItem('roles'));
  const hasAccess = roles && allowedRoles.some((role) => roles.includes(role));

  if (!hasAccess) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default PrivateRoute;
