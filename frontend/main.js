// API endpoints
const clientesAPI = 'http://localhost:8080/clientes';
const productosAPI = 'http://localhost:8080/productos';
const transaccionesAPI = 'http://localhost:8080/transacciones';

// Datos del carrito de compras
let carrito = [];

// Función para mostrar los clientes
function mostrarClientes() {
    // Código anterior para mostrar los clientes
}

// Función para mostrar los productos
function mostrarProductos() {
    fetch(productosAPI)
        .then(response => response.json())
        .then(data => {
            const productosContainer = document.getElementById('productos-container');
            productosContainer.innerHTML = '';

            data.forEach(producto => {
                const card = document.createElement('div');
                card.classList.add('col-md-4');
                card.classList.add('product-card');
                card.innerHTML = `
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${producto.nombre}</h5>
                            <p class="card-text">${producto.descripcion}</p>
                            <p class="card-text">Precio: $${producto.precio}</p>
                            <p class="card-text">Stock: ${producto.stock}</p>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-primary add-to-cart" data-id="${producto.id}">Agregar al carrito</button>
                        </div>
                    </div>
                `;
                productosContainer.appendChild(card);
            });

            // Agregar evento a los botones "Agregar al carrito"
            const addToCartButtons = document.querySelectorAll('.add-to-cart');
            addToCartButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const productoId = button.dataset.id;
                    agregarAlCarrito(productoId);
                });
            });
        })
        .catch(error => console.error('Error al obtener los productos:', error));
}

// Función para agregar un producto al carrito
function agregarAlCarrito(productoId) {
    fetch(`${productosAPI}/${productoId}`)
        .then(response => response.json())
        .then(producto => {
            carrito.push(producto);
            actualizarCarrito();
        })
        .catch(error => console.error('Error al agregar producto al carrito:', error));
}

// Función para actualizar el carrito
function actualizarCarrito() {
    const carritoContainer = document.getElementById('carrito-container');
    carritoContainer.innerHTML = '';

    carrito.forEach(producto => {
        const cartItem = document.createElement('div');
        cartItem.classList.add('cart-item');
        cartItem.innerHTML = `
            <div class="product-info">
                <img src="https://via.placeholder.com/50" alt="${producto.nombre}">
                <div>
                    <h6>${producto.nombre}</h6>
                    <p>$${producto.precio}</p>
                </div>
            </div>
            <div class="quantity-control">
                <button class="btn-minus">-</button>
                <input type="number" value="1" min="1" max="${producto.stock}">
                <button class="btn-plus">+</button>
            </div>
        `;
        carritoContainer.appendChild(cartItem);
    });

    actualizarResumenCarrito();
}

// Función para actualizar el resumen del carrito
function actualizarResumenCarrito() {
    const totalContainer = document.getElementById('total-container');
    const total = carrito.reduce((acc, producto) => acc + producto.precio, 0);
    totalContainer.textContent = `Total: $${total.toFixed(2)}`;
}

// Función para realizar el proceso de pago
function realizarPago() {
    // Código para procesar el pago
    console.log('Procesando pago...');
}
