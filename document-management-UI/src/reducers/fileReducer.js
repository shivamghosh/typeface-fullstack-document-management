const initialState = {
  files: []
};
  
  const fileReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'SET_FILES':
        return {
          ...state,
          files: [...action.payload],
        };
      default:
        return state;
    }
  };
  
  export default fileReducer;