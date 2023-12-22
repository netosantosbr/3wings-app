import React from 'react'
import styles from './Modal.module.css'

const Modal = ({ isOpen, product, closeModal }) => {
  
  const {name, description, price, created_at, updated_at } = product;

  if(isOpen) {
    return (
        <>
          <div className={styles.modalContainer}>
            <div className={styles.modal}>
                <h2 className={styles.title}>{name}</h2>
                <div className={styles.infoContainer}>
                  <label>Descrição:</label><span>{description}</span>
                  <label>Preço:</label><span>R$ {price}</span>
                  <label>Data de criação:</label><span>{created_at}</span>
                  <label>Data de modificação:</label> <span>{updated_at}</span>
                </div>
                <button className={styles.btnClose} onClick={closeModal}>X</button>
            </div>
          </div>
        </>
    )
  }
  
  return (
  <>
  </>
  )
}

export default Modal