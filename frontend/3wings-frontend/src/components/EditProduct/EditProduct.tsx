import React, { useEffect, useState } from 'react'
import styles from './EditProduct.module.css'
import Product from '../../models/Product'
import { useParams, useNavigate } from 'react-router-dom'
import ProductService from '../../services/ProductService'
import { toast, ToastContainer } from 'react-toastify'
import "react-toastify/dist/ReactToastify.css";

const EditProduct = () => {
  const productService = new ProductService();
  const { productId } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<Product>({
    name: '',
    price: 0,
    description: '',
    created_at: '',
    updated_at: ''
  });

  const showToast = (status: string)  => {
    if(status == "success") {
      toast.success("Editado com sucesso!", {
        position: toast.POSITION.TOP_RIGHT,
      });
    } else if(status == "error") {
      toast.error("Ocorreu um erro!", {
        position: toast.POSITION.TOP_RIGHT,
      });
    }
  };

  useEffect(() => {
    const fillFormData = async () => {
      setFormData(await productService.getProductById(Number(productId)));
    };

    fillFormData();
  }, [])

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

    const response = productService.updateProduct(Number(productId), formData);

    response.then(promisedProduct => {
      if(promisedProduct.id) {
        showToast("success");

        setTimeout(() => {
          navigate('/');
        }, 2000)
      } else {
        showToast("error");
      }
    })
  }

  return (
    <>
      <div className={styles.mainContainer}>
        <ToastContainer autoClose={1000} progressStyle={{background: 'linear-gradient(to bottom, rgb(13, 62, 167), rgba(13, 62, 167, 0.2))'}}/>
        <form className={styles.form} onSubmit={handleSubmit}>
              <button className={styles.btnReturn} onClick={() => navigate('/')}><i className='fa fa-arrow-left'></i></button>
              <h1>Edição de Produto</h1>
              <label>Nome:</label><input type='text' name="name" value={formData.name} onChange={handleChange} required maxLength={255} />
              <label>Descrição:</label><textarea name="description" value={formData.description} onChange={handleChangeTextarea} rows={6} maxLength={255} />
              <label>Preço:</label><input type='number' name="price" value={formData.price} onChange={handleChange} required />

              <button className={styles.btnSubmit} type='submit'><i className='fa fa-pencil'/>Finalizar edição</button> 
          </form>

      </div>
    </>
  )
}

export default EditProduct