import React from 'react';
import logo from '../../assets/3wings.webp'; 
import styles from './Header.module.css';

const Header = () => {
  return (
    <>
        <header className={styles.header}>
            <img className={styles.logoImage} src={logo}/>
        </header>
    </>
  )
}

export default Header