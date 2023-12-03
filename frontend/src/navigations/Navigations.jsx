import React from 'react'
import {Routes,Route} from 'react-router-dom'
import Home from '../pages/home/Home'
import AuthLayout from '../layouts/auth/AuthLayout'
import SignIn from '../pages/signin/SignIn'
import SignUp from '../pages/signup/SignUp'
import DashboardLayout from '../layouts/dashboard/DashboardLayout'
import DashboardList from '../pages/dashboard/list'
import DashboardCreate from '../pages/dashboard/create'
import DashboardUpdate from '../pages/dashboard/update'
import ActivationPage from '../pages/activation/Activation'

const Navigations = () => {
  return (
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
  )
}

export default Navigations
