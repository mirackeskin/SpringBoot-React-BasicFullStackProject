import React, { useState, useEffect } from 'react'
import { Routes, Route } from 'react-router-dom'
import Home from '../pages/home/Home'
import AuthLayout from '../layouts/auth/AuthLayout'
import SignIn from '../pages/signin/SignIn'
import SignUp from '../pages/signup/SignUp'
import DashboardLayout from '../layouts/dashboard/DashboardLayout'
import DashboardList from '../pages/dashboard/list/List'
import DashboardCreate from '../pages/dashboard/create/Create'
import DashboardUpdate from '../pages/dashboard/update/Update'
import ActivationPage from '../pages/activation/Activation'
import { useDispatch, useSelector } from 'react-redux'
import NotFound from '../pages/notfound/NotFound'
import axios from 'axios'
import LoadingModal from '../components/loading/LoadingModal'
import { authActions } from '../store/auth'
import Profile from '../pages/profile/Profile'

const Navigations = () => {
  const dispatch = useDispatch();
  const [isValid, setIsValid] = useState(false);
  const [loading, setLoading] = useState(false);
  const [isTryingLogin, setIsTryingLogin] = useState(true);
  const token = useSelector(state => state.auth.token);

  const validateToken =async (token) => {
    setLoading(true);
    if (token != "" && token != null) {
      await axios.post("http://localhost:8080/api/v1/verifyToken", {}, { headers: { "Authorization": "Basic " + token } })
        .then(response => {
          setIsValid(response.data);
        })
        .catch(err => console.log(err))
        .finally(() => { setLoading(false); })
    }
  }

  useEffect(() => {
    async function fetchToken() {
      let userToken = "";
      let userName = "";
      let userId = 0;
      let email = "";

      try {
        userToken = localStorage.getItem('token');
        userName = localStorage.getItem('userName');
        userId = localStorage.getItem('userId');
        email = localStorage.getItem('email');

        await validateToken(userToken);

      } catch (e) {
        // Restoring token failed
      }

      if (userToken) {
        dispatch(authActions.restoreUser({
          token: userToken,
          userName: userName,
          userId: userId,
          email: email
        }))
      }
      setIsTryingLogin(false);
    }
    fetchToken();
  }, [token])

  if (isTryingLogin) {
    return <LoadingModal></LoadingModal>;
 }

  return (
    
      (isValid && token) ?
        <Routes>
          <Route path='/' element={<DashboardLayout></DashboardLayout>}>
            <Route path='/' element={<DashboardList></DashboardList>}></Route>
            <Route path='/dashboard/create' element={<DashboardCreate></DashboardCreate>}></Route>
            <Route path='/dashboard/update/:id' element={<DashboardUpdate></DashboardUpdate>}></Route>
            <Route path='/profile' element={<Profile></Profile>}></Route>
          </Route>          
          <Route path='*' element={<NotFound></NotFound>}></Route>
          <Route path='/activation/:token' element={<ActivationPage></ActivationPage>}></Route>
        </Routes>
        :
        <Routes>
          <Route path='/' element={<AuthLayout></AuthLayout>}>
            <Route path='/' element={<SignIn></SignIn>}></Route>
            <Route path='/signup' element={<SignUp></SignUp>}></Route>
          </Route>
          <Route path='*' element={<NotFound></NotFound>}></Route>
          <Route path='/activation/:token' element={<ActivationPage></ActivationPage>}></Route>
        </Routes>

  )
}

export default Navigations

/*
<Routes>      
        <Route path='/' element={<AuthLayout></AuthLayout>}>
            <Route path='/' element={<SignIn></SignIn>}></Route>
            <Route path='/signup' element={<SignUp></SignUp>}></Route>
        </Route>
        <Route path='/dashboard' element={<DashboardLayout></DashboardLayout>}>
            <Route path='/dashboard' element={<DashboardList></DashboardList>}></Route>
            <Route path='/dashboard/create' element={<DashboardCreate></DashboardCreate>}></Route>
            <Route path='/dashboard/update/:id' element={<DashboardUpdate></DashboardUpdate>}></Route>
        </Route>
        <Route path='/activation/:token' element={<ActivationPage></ActivationPage>}></Route>
    </Routes>
*/