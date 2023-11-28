import React from 'react'
import styles from './SignUp.module.css'
import {Link} from 'react-router-dom'

const SignUp = () => {
  return (
    <div className={styles.mainWrapper}>
      <div className={styles.formWrapper}>
        <h4 className='mb-4'>SignUp Page</h4>
        <form style={{width:250}}>
        <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">User Name</label>
            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"></input>            
          </div>
          <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">User Email</label>
            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"></input>            
          </div>
          <div className="mb-3">
            <label for="exampleInputPassword1" className="form-label">User Password</label>
            <input type="password" className="form-control" id="exampleInputPassword1"></input>
          </div>
          <div className={styles.buttonsRow}>
            <button className="btn btn-primary btn-sm">Sign Up</button>
            <Link className={styles.link} to={"/"}>Go Back</Link>
          </div>
        </form>
      </div>
    </div>
  )
}

export default SignUp
