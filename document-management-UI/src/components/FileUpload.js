import React from 'react';
import { Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { uploadFile, fetchFiles } from '../utils/DataFetcher';
import { useSelector } from 'react-redux';

const SUPPORTED_TYPES = ['text/plain', 'application/json', 'image/jpeg', 'image/png', 'application/pdf'];

const FileUpload = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);

  const handleFileChange = (e) => {
    // Check if a file is selected and if its type is supported
    const file = e.target.files[0];
    if (!file || !SUPPORTED_TYPES.includes(file.type)) {
      alert('Unsupported file type!');
      return;
    }
    // Upload the file and update the Redux store
    uploadFile(file, user.userDetails.userId).then((res) => {
      fetchFiles().then((files) => {
        dispatch({
          type: 'SET_FILES',
          payload: files,
        });
      });
    });
  };

  return (
    <Button variant="contained" component="label">
      Upload File
      <input hidden type="file" onChange={handleFileChange} />
    </Button>
  );
};

export default FileUpload;
