import axios from 'axios';

/* This method fetches the list of files from the server */
export const fetchFiles = async () => {
  const response = await axios.get(`/v1/files`);
  console.log("fetch files response", response.data)
  return response.data;
};

/* This method uploads a file to the server */
export const uploadFile = async (file, userId) => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await axios.post(`/v1/files/upload`, formData, {
    headers: { 'Content-Type': 'multipart/form-data',
      'uploadedBy': userId
     },
  });
  console.log("file upload response", response.data)
  return response.data;
};

/* This method downloads a file from the server */
export const downloadFile = async (fileId) => {
  const response = await axios.get(`/v1/files/download/${fileId}`, {
    responseType: 'blob',
  });
  console.log("download file response", response.data)
  return response;
}

/* This method fetches user details */
export const fetchUser = () => {
  // This is a mock function to simulate fetching user details
  // In a real application, you would replace this with an API call to fetch user details
  const userDetails = {
    name: 'Shivam Ghosh',
    userId: 'sghosh',
  };
  console.log("user details", userDetails)
  return userDetails;
}
