package com.sebas.demo.config;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sebas.demo.dto.TransaccionListDTO;
import com.sebas.demo.dto.TransaccionProductoListDTO;
import com.sebas.demo.dto.TransaccionProductoSaveDTO;
import com.sebas.demo.dto.TransaccionSaveDTO;
import com.sebas.demo.repositories.entities.Transaccion;

@Component
public class TransaccionDTOConverter {

    @Autowired
    private ModelMapper dbm;

    @Autowired
    public TransaccionDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public TransaccionListDTO convertListToDTO(Transaccion transaccion) {
        TransaccionListDTO transaccionDTO = dbm.map(transaccion, TransaccionListDTO.class);
        if (transaccion.getCliente() != null) {
            transaccionDTO.setCedulaCliente(transaccion.getCliente().getCedula());
            transaccionDTO.setNombreCliente(
                    transaccion.getCliente().getNombre() + " " + transaccion.getCliente().getApellido());
            transaccionDTO.setEmailCliente(transaccion.getCliente().getEmail());
            transaccionDTO.setTelefonoCliente(transaccion.getCliente().getTelefono());
            transaccionDTO.setDireccionCliente(transaccion.getCliente().getDireccion());
        }
        if (transaccion.getTransaccionProductos() != null) {
            List<TransaccionProductoListDTO> transaccionProductoListDTO = transaccion.getTransaccionProductos().stream()
                    .map(transacciones -> new TransaccionProductoListDTO(transacciones.getProducto().getNombre(),
                            transacciones.getProducto().getDescripcion(), transacciones.getCantidad(),
                            transacciones.getTotalProductos()))
                    .collect(Collectors.toList());
            transaccionDTO.setListaProductos(transaccionProductoListDTO);
        }
        transaccionDTO.setTotalTransaccion(transaccion.getTotal());
        return transaccionDTO;
    }

    public Transaccion convertListToEntity(TransaccionListDTO transaccionDTO) {
        Transaccion transaccion = dbm.map(transaccionDTO, Transaccion.class);
        return transaccion;
    }

    public TransaccionSaveDTO convertSaveToDTO(Transaccion transaccion) {
        TransaccionSaveDTO transaccionDTO = dbm.map(transaccion, TransaccionSaveDTO.class);
        if (transaccion.getCliente() != null) {
            transaccionDTO.setCedulaCliente(transaccion.getCliente().getCedula());
        }
        if (transaccion.getTransaccionProductos() != null) {
            List<TransaccionProductoSaveDTO> transaccionProductoSaveDTO = transaccion.getTransaccionProductos().stream()
                    .map(transacciones -> new TransaccionProductoSaveDTO(transacciones.getId(),
                            transacciones.getCantidad()))
                    .collect(Collectors.toList());
            transaccionDTO.setProductos(transaccionProductoSaveDTO);
        }
        transaccionDTO.setTotal(transaccion.getTotal());
        return transaccionDTO;
    }

    public Transaccion convertSaveToEntity(TransaccionSaveDTO transaccionDTO) {
        Transaccion transaccion = dbm.map(transaccionDTO, Transaccion.class);
        return transaccion;
    }

}
