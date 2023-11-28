import React from 'react'
import styles from './SignIn.module.css'
import {Link} from 'react-router-dom'

const SignIn = () => {
  return (
    <div className={styles.mainWrapper}>
      <div className={styles.formWrapper}>
        <h4 className='mb-4'>Login Page</h4>
        <form style={{width:250}}>
          <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">User Email</label>
            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"></input>            
          </div>
          <div className="mb-3">
            <label for="exampleInputPassword1" className="form-label">User Password</label>
            <input type="password" className="form-control" id="exampleInputPassword1"></input>
          </div>
          <div className={styles.buttonsRow}>
            <button className="btn btn-primary btn-sm">Login</button>
            <Link className={styles.link} to={"/signup"}>SignUp</Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default SignIn
