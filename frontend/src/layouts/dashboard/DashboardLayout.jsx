import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Outlet, useNavigate,Link } from 'react-router-dom'
import styles from './DashboardLayout.module.css'
import { authActions } from '../../store/auth'

const DashboardLayout = () => {
    const dispatch = useDispatch();
    const userName = useSelector(state => state.auth.userName);
    const Navigate = useNavigate();
    const logoutHandler = async () => {
        dispatch(authActions.logout());
        await localStorage.clear();        
    }
    return (
        <>
            <div className={styles.navbarWrapper}>
                <Link className={styles.text} to={"/"}><h3 >SpringBoot&ReactJs App</h3></Link>                
                <div className="dropdown">
                    <button className="btn btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        {userName}
                    </button>
                    <ul className="dropdown-menu">
                        <li><Link to={"/profile"} className="dropdown-item" href="#">Profile</Link></li>
                        <li><button onClick={logoutHandler} className="dropdown-item">LogOut</button></li>
                    </ul>
                </div>
            </div>
            <Outlet></Outlet>
        </>
    )
}

export default DashboardLayout
