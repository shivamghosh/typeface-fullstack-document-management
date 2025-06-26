import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { List, ListItemButton, ListItemText, Typography } from '@mui/material';
import { fetchFiles } from '../utils/DataFetcher';
import { downloadFile } from '../utils/DataFetcher';

const FileList = () => {
  const dispatch = useDispatch();
  const files = useSelector((state) => state.files.files);

  useEffect(() => {
    // Fetch files from the server and update the Redux store
    fetchFiles().then((files) => {
      dispatch({
        type: 'SET_FILES',
        payload: files,
      });
    });
  }, []);

  const handleFileViewer = (file) => {
    // Download the file and open it in a new tab
    downloadFile(file.id).then((res) => {
      const blob = new Blob([res.data], { type: res.headers['content-type'] });
      const url = URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  };

  return (
    <div>
      <List>
        {(files.length > 0) ? 
        (<> <Typography>Click on a file to view it</Typography>
        {files.map((file, index) => (
          <ListItemButton key={file.id} onClick={() => handleFileViewer(file)}>
            <ListItemText primary={file.fileName} />
          </ListItemButton>
        ))} </>) :
        <Typography>No files available</Typography>}
      </List>
    </div>
  );
};

export default FileList;
