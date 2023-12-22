import React, { useEffect, useState } from 'react'
import styles from './Table.module.css';
import ProductService from '../../services/ProductService';
import Product from '../../models/Product';
import { Link } from 'react-router-dom';
import Modal from '../Modal/Modal';
import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog'


const Table = () => {
  const productService = new ProductService();
  const [openModal, setOpenModal] = useState(false);
  const [product, setProduct] = useState<Product>({
    id: 0,
    name: '',
    price: 0,
    description: '',
    created_at: '',
    updated_at: ''
  });

  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const searchProducts = async () => {        
        setProducts(await productService.getAllProducts());
        (await productService.getAllProducts()).length > 0 ? setLoading(false) : setLoading(true);
    };

    searchProducts();
  }, []);

  const deleteProduct = (id: number) => async () => {
    
    confirmDialog({
        message: 'Tem certeza que deseja excluir?',
        header: 'Exclusão de Produto',
        icon: 'fa fa-exclamation-triangle',
        acceptLabel: 'Sim',  
        acceptIcon: 'fa fa-check',
        acceptClassName: 'bg-bluegray-800',
        rejectClassName: 'mr-1 bg-bluegray-800',
        rejectLabel: 'Não',
        showHeader: false,
        contentStyle: {color: 'black', marginBottom: '10px'},     
        style: {width: 'auto', height:'auto', alignItems: 'center', backgroundColor: 'white', padding: '20px', boxShadow: '1px 1px 6px black'},
        closable: false,
        draggable: false,
        accept: async () => {
            const updatedProducts = productService.deleteProduct(id);

            setProducts(await updatedProducts);
            if((await updatedProducts).length == 0) {
                setLoading(true);
            }
        },
        reject: () => null
    });
  }

    function handleClick(product: Product): void {
        setOpenModal(true);
        setProduct(product);
    }

  return (
    <>
    <ConfirmDialog />
    <Modal isOpen={openModal} product={product} closeModal={() => setOpenModal(false)}/>
    <div className={styles.tableContainer}>

        <div className={styles.linkCreateProductContainer}>        
            <Link className={styles.linkCreateProduct} to="/create-product">
                <div className={styles.btnAddContainer}>
                    <button className={styles.btnAdd}>
                        <i className='fa fa-plus fa-lg'/>Adicionar produto
                    </button>
                </div>
            </Link>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Preço</th>
                    <th>#</th>
                </tr>
            </thead>        

            <tbody>
            {loading ? (
            <tr>
                <td colSpan={5}>A lista está vazia.</td>
            </tr>) : (
            products.map((product) => (
                <tr className={styles.rowTable} key={product.id}>
                    <td onClick={() => handleClick(product)}>{product.id}</td>
                    <td onClick={() => handleClick(product)}>{product.name}</td>
                    <td onClick={() => handleClick(product)}>R$ {product.price}</td>
                    <td className={styles.optionsContainerTd}>
                        <Link to={`/edit-product/${product.id}`}><button className={styles.btnEditar}><i className='fa fa-pencil fa-lg'/>Editar</button></Link>
                        <button className={styles.btnExcluir} onClick={deleteProduct(product.id)}><i className='fa fa-trash fa-lg'/>Excluir</button>
                    </td>
                </tr>
            ))
            )}
            </tbody>
        </table>
    </div>
    </>
  )
}

export default Table