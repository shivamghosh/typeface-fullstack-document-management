const initialState = {
  userDetails: {
      name: 'Guest User',
      userId: 'guest'
    }
  };
  
  const userReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'SET_USER':
        return {
          ...state,
          userDetails: {
            ...state.userDetails,
            ...action.payload,
          },
        };
      default:
        return state;
    }
  };
  
  export default userReducer;