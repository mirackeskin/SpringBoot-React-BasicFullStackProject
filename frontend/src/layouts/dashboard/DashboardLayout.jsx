import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Outlet, useNavigate } from 'react-router-dom'
import styles from './DashboardLayout.module.css'
import { authActions } from '../../store/auth'

const DashboardLayout = () => {
    const dispatch = useDispatch();
    const userName = useSelector(state => state.auth.userName);
    const Navigate = useNavigate();
    const logoutHandler = () => {
        dispatch(authActions.logout());
        localStorage.clear();
        
    }
    return (
        <>
            <div className={styles.navbarWrapper}>
                <h3 className={styles.text}>SpringBoot&ReactJs App</h3>
                <div className="dropdown">
                    <button className="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        {userName}
                    </button>
                    <ul className="dropdown-menu">
                        <li><button onClick={logoutHandler} className="dropdown-item" href="#">LogOut</button></li>
                    </ul>
                </div>
            </div>
            <Outlet></Outlet>
        </>
    )
}

export default DashboardLayout
