package com.sebas.demo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sebas.demo.config.TransaccionDTOConverter;
import com.sebas.demo.dto.TransaccionListDTO;
import com.sebas.demo.dto.TransaccionSaveDTO;
import com.sebas.demo.repositories.RepositoryCliente;
import com.sebas.demo.repositories.RepositoryProducto;
import com.sebas.demo.repositories.RepositoryTransaccion;
import com.sebas.demo.repositories.entities.Cliente;
import com.sebas.demo.repositories.entities.Producto;
import com.sebas.demo.repositories.entities.Transaccion;
import com.sebas.demo.repositories.entities.TransaccionProducto;
import com.sebas.demo.services.ServiceTransaccion;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTransaccionImpl implements ServiceTransaccion {

    @Autowired
    private RepositoryTransaccion repositoryTransaccion;

    @Autowired
    private RepositoryProducto repositoryProducto;

    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private TransaccionDTOConverter convert;

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionListDTO> findAll() {
        List<Transaccion> transacciones = (List<Transaccion>) repositoryTransaccion.findAll();
        return transacciones.stream()
                .map(transaccion -> convert.convertListToDTO(transaccion))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Transaccion findById(Long id) {
        return repositoryTransaccion.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                        "Error! Transaccion no existente"));
    }

    @Override
    @Transactional
    public TransaccionSaveDTO save(TransaccionSaveDTO transaccionSaveDTO) {
        // Buscar el cliente por cédula
        Cliente cliente = repositoryCliente.findByCedula(transaccionSaveDTO.getCedulaCliente());
        if (cliente == null) {
            throw new RuntimeException(
                    "El cliente con cédula " + transaccionSaveDTO.getCedulaCliente() + " no existe.");
        }

        // Crear la transacción
        Transaccion transaccion = convert.convertSaveToEntity(transaccionSaveDTO);
        transaccion.setCliente(cliente);

        // Crear los detalles de la transacción
        List<TransaccionProducto> transaccionProductos = transaccionSaveDTO.getProductos().stream()
                .map(dto -> {
                    Producto producto = repositoryProducto.findById(dto.getIdProducto())
                            .orElseThrow(() -> new RuntimeException(
                                    "El producto con ID " + dto.getIdProducto() + " no existe."));
                    // Verificar si hay stock suficiente
                    if (producto.getStock() < dto.getCantidad()) {
                        throw new RuntimeException(
                                "No hay suficiente stock del producto con ID " + dto.getIdProducto());
                    }

                    // Disminuir la cantidad del producto
                    producto.setStock(producto.getStock() - dto.getCantidad());
                    repositoryProducto.save(producto);

                    TransaccionProducto transaccionProducto = new TransaccionProducto();
                    transaccionProducto.setProducto(producto);
                    transaccionProducto.setCantidad(dto.getCantidad());
                    transaccionProducto.setTotalProductos(producto.getPrecio() * dto.getCantidad());
                    transaccionProducto.setTransaccion(transaccion);
                    return transaccionProducto;
                })
                .collect(Collectors.toList());
        transaccion.setTransaccionProductos(transaccionProductos);

        // Calcular el total de la transacción
        double total = transaccionProductos.stream()
                .mapToDouble(TransaccionProducto::getTotalProductos)
                .sum();
        transaccion.setTotal(total);

        // Guardar la transacción
        Transaccion savedTransaccion = repositoryTransaccion.save(transaccion);

        // Convertir la transacción guardada a DTO
        return convert.convertSaveToDTO(savedTransaccion);
    }

    @Override
    @Transactional
    public TransaccionSaveDTO update(Long id, TransaccionSaveDTO transaccionSaveDTO) {
        Optional<Transaccion> transaccionOptional = repositoryTransaccion.findById(id);

        if (transaccionOptional.isPresent()) {
            Transaccion transaccionCurrent = transaccionOptional.get();
            transaccionCurrent.setCliente(convert.convertSaveToEntity(transaccionSaveDTO).getCliente());
            transaccionCurrent
                    .setTransaccionProductos(convert.convertSaveToEntity(transaccionSaveDTO).getTransaccionProductos());
            transaccionCurrent.setTotal(transaccionSaveDTO.getTotal());
            Transaccion transaccionActualizada = repositoryTransaccion.save(transaccionCurrent);
            return convert.convertSaveToDTO(transaccionActualizada);
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error! Transacción no existente");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Transaccion> transaccionOptional = repositoryTransaccion.findById(id);
        if (transaccionOptional.isPresent()) {
            repositoryTransaccion.delete(transaccionOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error! Transaccion no existente");
        }
    }

}
