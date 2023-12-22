import React, { ChangeEvent, FormEvent, useState } from 'react'
import styles from './CreateProduct.module.css'
import Product from '../../models/Product'
import ProductService from '../../services/ProductService';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const CreateProduct = () => {
  const navigate = useNavigate();
  const productService = new ProductService();
  const [formData, setFormData] = useState<Product>({
    name: '',
    description: '',
    price: 0,
  })

  const showToast = (status: string)  => {
    if(status == "success") {
      toast.success("Criado com sucesso!", {
        position: toast.POSITION.TOP_RIGHT,
      });
    } else if(status == "error") {
      toast.error("Ocorreu um erro", {
        position: toast.POSITION.TOP_RIGHT,
      });
    }
  };

  const handleChangeTextarea = (e: ChangeEvent<HTMLTextAreaElement>): void => {
    setFormData((prevData) => ({
      ...prevData,
      [e.target.name]: e.target.value,
    }));
  }

  const handleChange = (e: ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  }

  const handleSubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();

    const response = productService.createProduct(formData);
    
    response.then(promisedProduct => {
      if(promisedProduct.id) {
        showToast("success");

        setTimeout(() => {
          navigate('/');
        }, 2000)
      } else {
        showToast("error");
      }     
      
    });
  }

  return (
    <>    
        <div className={styles.formContainer}>
            <ToastContainer autoClose={1000} progressStyle={{background: 'linear-gradient(to bottom, rgb(12, 158, 12), rgb(6, 114, 6))'}} />
            <form className={styles.form} onSubmit={handleSubmit}>
                <button className={styles.btnReturn} onClick={() => navigate('/')}><i className='fa fa-arrow-left'></i></button>
                <h1>Cadastro de Produto</h1>
                <label>Nome:</label><input type='text' name="name" value={formData.name} onChange={handleChange} required maxLength={255} />
                <label>Descrição:</label><textarea name="description" value={formData.description} onChange={handleChangeTextarea} rows={6} maxLength={255} />
                <label>Preço:</label><input type='number' name="price" value={formData.price} onChange={handleChange} required />

                <button className={styles.btnSubmit} type='submit'><i className='fa fa-plus' /> Criar produto</button> 
            </form>
        </div>
        
    </>
  )
}

export default CreateProduct