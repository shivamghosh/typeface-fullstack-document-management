import React, { useEffect } from 'react';
import { Container, Typography } from '@mui/material';
import FileUpload from './FileUpload';
import FileList from './FileList';
import { useDispatch, useSelector } from 'react-redux';
import logo from '../assets/logo.svg';
import { fetchUser } from '../utils/DataFetcher';

const HomePage = () => {
  const user = useSelector((state) => state.user);
  const dispatch = useDispatch();

  useEffect(() => {
    // Call fetchUser to set the user in the Redux store
    dispatch({
      type: 'SET_USER',
      payload: fetchUser(),
    });
  }, []);

  return (
    <Container>
      <img src={logo} className="players-logo" alt="logo" style={{ width: '100px', height: 'auto' }}/>
      <Typography variant="h4" gutterBottom>Welcome, {user.userDetails && user.userDetails.name ? user.userDetails.name : 'Guest'}</Typography>
      <FileUpload />
      <FileList />
    </Container>
  );
};

export default HomePage;
