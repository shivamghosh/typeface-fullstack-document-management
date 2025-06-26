import { configureStore } from '@reduxjs/toolkit';
import userReducer from '../reducers/userReducer';
import fileReducer from '../reducers/fileReducer';

export const store = configureStore({
  reducer: {
    user: userReducer,
    files: fileReducer
  },
  devTools: process.env.NODE_ENV !== 'production'
});
