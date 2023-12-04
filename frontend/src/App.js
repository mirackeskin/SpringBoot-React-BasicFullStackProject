import { useDispatch, useSelector } from 'react-redux';
import Navigations from './navigations/Navigations';
import React,{ useEffect,useState } from 'react';
import { authActions } from './store/auth';

function App() {

  const dispatch = useDispatch();
  const token = useSelector(state => state.auth.token);

  useEffect(() => {
    async function fetchToken() {
       let userToken = "";
       let userName = "";
       let userId = "";
       let email = "";

       try {
          userToken = localStorage.getItem('token');
          userName = localStorage.getItem('userName');
          userId = localStorage.getItem('userId');
          email = localStorage.getItem('email');

       } catch (e) {
          // Restoring token failed
       }

       if (userToken) {
          dispatch(authActions.restoreUser({
             token: userToken,
             userName: userName,
             userId: userId,
             email:email
          }))
       }
    }

    fetchToken();

 }, [token]);

  return (
    <div className="App">
      <Navigations></Navigations>
    </div>
  );
}

export default App;
