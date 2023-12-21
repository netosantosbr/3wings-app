import axios, { AxiosResponse } from "axios";
import Product from "../models/Product";
import { format } from "date-fns";
 

class ProductService {

    BASE_URL = 'http://localhost:8080/product';

    async createProduct(product: Product): Promise<Product> {
        try {
            const response: AxiosResponse<Product> = await axios.post(this.BASE_URL, product);
            return response.data;
        } catch(error) {
            console.log("Error creating product: " + error);
            throw error;
        }
    }

    async getAllProducts(): Promise<Product[]> {
        try {
            const response: AxiosResponse<Product[]> = await axios.get(this.BASE_URL);
            return response.data.map((product) => ({
                ...product,
                created_at: format(new Date(product.created_at), 'dd/MM/yyyy HH:mm:ss'),
                updated_at: format(new Date(product.updated_at), 'dd/MM/yyyy HH:mm:ss')
            }))
        } catch(error) {
            console.error("Error fetching all products: " + error);
            throw error;
        }
    }

    async getProductById(productId: number): Promise<Product> {
        try {
            const response: AxiosResponse<Product> = await axios.get(`${this.BASE_URL}/${productId}`);
            return response.data;
        } catch(error) {
            console.error("Error fetching a specific product: " + error);
            throw error;
        }
    }

    async updateProduct(productId: number, product: Product) {
        try {
            const response: AxiosResponse<Product> = await axios.patch(`${this.BASE_URL}/${productId}`, product);
            return response.data;
        } catch (error) {
            console.error("Error updating a specific product: " + error)
            throw error;
        }
    }

    async deleteProduct(productId: number): Promise<Product[]> {
        try {
            await axios.delete(`${this.BASE_URL}/${productId}`);

            const updatedProducts = await this.getAllProducts();
            return updatedProducts;
        } catch(error) {
            console.error("Error deleting product: " + error)
            throw error;
        }
    }
}


export default ProductService;