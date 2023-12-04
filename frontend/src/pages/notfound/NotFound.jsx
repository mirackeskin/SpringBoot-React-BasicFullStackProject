import React, { useEffect, useState } from 'react'
import styles from './NotFound.module.css'
import { useNavigate } from 'react-router-dom';

const NotFound = () => {
    const [counter, setCounter] = useState(5);
    const Navigate = useNavigate();
    useEffect(() => {
        setTimeout(() => {
            if (counter > 0) {
                setCounter(counter - 1);
            } else {
                Navigate("/");
            }
        }, [1000])
    }, [counter])
    return (
        <div className={styles.wrapper}>
            <h1>Page Not Found!</h1>
            <h5>You Are Navigate...</h5>
            <h6>{counter}</h6>
        </div>
    )
}

export default NotFound
