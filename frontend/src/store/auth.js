import { createSlice } from '@reduxjs/toolkit';

const initialAuthState = {
    userId:'',
    userName : '',
    email : '',
    token: ''
}

const authSlice = createSlice({
    name : 'authentication',
    initialState :initialAuthState,
    reducers : {
        login(state, action) {
            state.token = action.payload.token;
            state.userName = action.payload.userName;
            state.userId = action.payload.userId;
            state.email = action.payload.email;
         },
         restoreUser(state, action) {
            state.token = action.payload.token;
            state.userName = action.payload.userName;
            state.userId = action.payload.userId;
            state.email = action.payload.email;
         },
         logout(state, action) {
            state.token = '';
            state.userName = '';
            state.userId = '';
            state.email = '';
         },
    }
})

export const authActions = authSlice.actions;

export default authSlice.reducer;