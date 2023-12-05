import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'
import LoadingModal from '../../components/loading/LoadingModal'


const Profile = () => {
  const userId = useSelector(state => state.auth.userId);
  const token = useSelector(state => state.auth.token);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState({});
  const [file, setFile] = useState(null);
  const [byteImage,setByteImage]=useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/user/${userId}`, { headers: { "Authorization": "Basic " + token } })
      .then(response => {
        //console.log(response.data);
        setUser(response.data);
      })
      .catch(err => console.log(err))
      .finally(() => { setLoading(false) })
  }, [])

  const fileHandler = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file",file);
    axios.post(`http://localhost:8080/api/v1/user/fileUpload`,formData,{headers:{"Content-Type":"multipart/form-data","Authorization":"Basic "+token}})
    .then(response => {
      console.log(response.data.content)
      setByteImage(response.data.content);
    })
    .catch(err => console.log(err))
    .finally(()=>{})
    
  }

  return (
    loading ? <LoadingModal></LoadingModal> :
      <div className='container mt-4 w-50'>
        <div className="card" >
          {
            user.image == null ? <img style={{ width: "50%", alignSelf: 'center' }} src="https://static.vecteezy.com/system/resources/previews/019/896/008/original/male-user-avatar-icon-in-flat-design-style-person-signs-illustration-png.png" className="card-img-top" alt="..."></img>
              : <img src={`data:image/png;base64,${byteImage}`} className="card-img-top" alt="..."></img>
          }
          <img src={`data:image/png;base64,${byteImage}`} style={{width:100,height:100}} className="card-img-top" alt="..."></img>
          <div className="card-body">
            <h5 className="card-title">Username : {user.userName}</h5>
            <p className="card-text">User Email : {user.email}</p>
            <div className="d-flex align-items-center">
              <form onSubmit={fileHandler} encType='multipart/form-data'>
                <input type="file" onChange={(e)=>setFile(e.target.files[0])}/>
                <button type='submit' className="btn btn-primary">Add Image</button>
              </form>
            </div>
          </div>
        </div>
      </div>
  )
}

export default Profile
