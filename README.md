# Book store

	Ver 1.0.2
	---------------------------------------------------------------------
	Add delete message and modify HTTP status code for Delete method.
	

- #### Product API
    
    > **GET all product** http://localhost:8082/products/
    
    > **POST product** http://localhost:8082/products/
    
    > **GET product by id** http://localhost:8082/products/${id}
    
    > **PUT product by id** http://localhost:8082/products/${id}
    
    > **DELETE product by id** http://localhost:8082/products/${id}

                                                           
    Request Body 
    ```
    {
        "id": "648d2ad884afbb0007148554",
        "name": "The Light We Carry: Overcoming in Uncertain Times",
        "category": "Humanity & Social Science",
        "language": "English",
        "publisher": "Crown Publishing Group (NY)",
        "author": "Michelle Obama",
        "price": 0
    },
    ``` 
