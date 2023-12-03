import React, { useState, useEffect } from 'react'
import axios from 'axios'
import LoadingModal from '../../components/loading/LoadingModal'
import { useNavigate, useParams } from 'react-router-dom'

const Activation = () => {
  const [loading, setLoading] = useState(true);
  const { token } = useParams();
  const Navigate = useNavigate();

  useEffect(() => {
    axios.patch(`http://localhost:8080/api/v1/users/activation/${token}`)
      .then(response => {
        console.log(response.data)
        if (response.data) {
          setLoading(false)
          Navigate("/");
        }
      })
      .catch(err => console.log(err))
  }, [])

  return (
    <div className='d-flex justify-content-center align-items-center ' style={{ height: "100vh", flexDirection: "column" }}>
      <h3>Hesabınız Aktif Ediliyor Lütfen Bekleyiniz</h3>
      {
        loading ? <LoadingModal></LoadingModal> : null
      }
    </div>
  )
}

export default Activation
