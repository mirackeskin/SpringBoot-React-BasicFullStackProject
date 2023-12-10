import React, { useEffect, useState } from 'react'
import styles from './List.module.css'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'
import axios from 'axios'
import LoadingModal from '../../../components/loading/LoadingModal'

const List = () => {
  const token = useSelector(state => state.auth.token);
  const userId = useSelector(state => state.auth.userId);

  const [loading, setLoading] = useState(false);
  const [users, setUsers] = useState([]);
  const [refresh,setRefresh]=useState(false);

  useEffect(() => {
    setLoading(true);
    axios.get(`http://localhost:8080/api/v1/users/${userId}`, { headers: { "Authorization": "Basic " + token } })
      .then(response => {
        setUsers(response.data);
      })
      .catch(err => console.log(err))
      .finally(() => { setLoading(false); })
  }, [])
  useEffect(() => {
    setLoading(true);
    axios.get(`http://localhost:8080/api/v1/users/${userId}`, { headers: { "Authorization": "Basic " + token } })
      .then(response => {
        setUsers(response.data);
      })
      .catch(err => console.log(err))
      .finally(() => { setLoading(false); })
  }, [refresh])

  const deleteHandler = (id) =>{
    setLoading(true);
    axios.delete(`http://localhost:8080/api/v1/users/${id}`,{ headers: { "Authorization": "Basic " + token } })
    .then(response => {
      console.log(response.data)
    })
    .catch(err => console.log(err))
    .finally(()=>{setRefresh(!refresh);setLoading(false)})
  }


  return (
    loading ? <LoadingModal></LoadingModal> :
    <div className={styles.mainWrapper}>
      <h2>User List Table</h2>
      <div className='container'>
        <div className={styles.customRow}>
          <Link to={"dashboard/create"} className='btn btn-dark'>Add User</Link>
        </div>
        <table className="table table-dark">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Image</th>
              <th scope="col">UserName</th>
              <th scope="col">Email</th>
              <th scope="col">Operations</th>
            </tr>
          </thead>
          <tbody>
            {
              users.map((item,index) => (
                <tr key={item.userId}>
                  <td scope="row">{index+1}</td>
                  <td scope="col">
                    {
                      item.image == null ? <img className={styles.image} src="https://static.vecteezy.com/system/resources/previews/019/896/008/original/male-user-avatar-icon-in-flat-design-style-person-signs-illustration-png.png" alt="" /> 
                      : <img className={styles.image} src={`http://localhost:8080/images/${item.userName}/${item.image}`} alt="" />
                    }                    
                  </td>
                  <td>{item.userName}</td>
                  <td>{item.email}</td>
                  <td>
                    <Link className='btn btn-outline-light me-1'>Edit</Link>
                    <button onClick={()=>{deleteHandler(item.userId)}} className='btn btn-outline-light ms-1'>Delete</button>
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default List
