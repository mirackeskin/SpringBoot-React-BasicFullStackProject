import React, { useEffect, useState } from 'react'
import styles from './SignUp.module.css'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'
import LoadingModal from '../../components/loading/LoadingModal'

const SignUp = () => {

  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const Navigate = useNavigate();

  

  const signUpHandler = (e) => {
    e.preventDefault();
    setLoading(true);
    let postData = {
      "userName": userName,
      "email": email,
      "password": password
    }
    axios.post("http://localhost:8080/api/v1/users", postData, { headers: { "Content-Type": "application/json" } })
      .then(response => {
        console.log(response.data);
        Navigate("/");
      })
      .catch(err => console.log(err.response.data))
      .finally(() => { setLoading(false) })
  }

  return (
    <div className={styles.mainWrapper}>
      {
        loading ? <LoadingModal></LoadingModal> :
          <div className={styles.formWrapper}>
            <h4 className='mb-4'>SignUp Page</h4>
            <form style={{ width: 250 }}>
              <div className="mb-3">
                <label htmlFor="userName" className="form-label">User Name</label>
                <input type="text" onChange={(e) => { setUserName(e.target.value) }} value={userName} className="form-control" id="userName" aria-describedby="emailHelp"></input>
              </div>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">User Email</label>
                <input type="text" value={email} onChange={(e) => { setEmail(e.target.value) }} className="form-control" id="email" aria-describedby="emailHelp"></input>
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">User Password</label>
                <input type="password" value={password} onChange={(e) => { setPassword(e.target.value) }} className="form-control" id="password"></input>
              </div>
              <div className={styles.buttonsRow}>
                <button onClick={signUpHandler} className="btn btn-primary btn-sm">Sign Up</button>
                <Link className={styles.link} to={"/"}>Go Back</Link>
              </div>
            </form>
          </div>
      }
    </div>
  )
}

export default SignUp
