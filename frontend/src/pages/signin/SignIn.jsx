import React, { useState, useEffect } from 'react'
import styles from './SignIn.module.css'
import { Link } from 'react-router-dom'
import axios from 'axios'
import { useSelector, useDispatch } from 'react-redux'
import { authActions } from '../../store/auth'

const SignIn = () => {
  const [loading, setLoading] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();

  const loginHandler = (e) => {
    e.preventDefault();
    setLoading(true);
    let postData = {
      "email": email,
      "password": password
    }
    axios.post(`http://localhost:8080/api/v1/login`, postData, { headers: { "Content-Type": "application/json" } })
      .then(response => {
        console.log(response.data);

        dispatch(authActions.login({
          token: response.data.token,
          userName: response.data.userName,
          userId: response.data.userId,
          email: response.data.email
        }));

        localStorage.setItem("token",response.data.token.token);
        localStorage.setItem("userName",response.data.user.userName);
        localStorage.setItem("userId",response.data.user.userId);
        localStorage.setItem("email",response.data.user.email);

      })
      .catch(err => console.log(err))
      .finally(() => { setLoading(false) })

  }

  return (
    <div className={styles.mainWrapper}>
      <div className={styles.formWrapper}>
        <h4 className='mb-4'>Login Page</h4>
        <form style={{ width: 250 }}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">User Email</label>
            <input type="text" onChange={(e) => { setEmail(e.target.value) }} value={email} className="form-control" id="email" aria-describedby="emailHelp"></input>
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">User Password</label>
            <input type="password" onChange={(e) => setPassword(e.target.value)} value={password} className="form-control" id="password"></input>
          </div>
          <div className={styles.buttonsRow}>
            <button onClick={loginHandler} className="btn btn-primary btn-sm">Login</button>
            <Link className={styles.link} to={"/signup"}>SignUp</Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default SignIn
