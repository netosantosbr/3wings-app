import './App.css'
import CreateProduct from './components/CreateProduct/CreateProduct'
import EditProduct from './components/EditProduct/EditProduct'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import Table from './components/Table/Table'
import { Route, Routes } from 'react-router-dom';

function App() {

  return (
    <>
      <Header />
      <Routes>
        <Route path="/create-product" element={<CreateProduct/>} />
        <Route path="/" element={<Table/>} />
        <Route path={`/edit-product/:productId`} element={<EditProduct/>} />
      </Routes>
      <Footer />
    </>
  )
}

export default App
