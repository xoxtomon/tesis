package tesis.backend.backend.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.repository.AnteproyectoRepository;
import tesis.backend.backend.file.entity.File;
import tesis.backend.backend.file.repository.FileRepository;
import tesis.backend.backend.util.FileUtils;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AnteproyectoRepository anteproyectoRepository;

    public ResponseEntity<String> uploadFile(MultipartFile file, String description, UUID idAsociado, Boolean isAnteproyecto, Integer nroEntrega) throws IOException {
        File fileEnt = new File();
        fileEnt.setIdAsociado(idAsociado);
        fileEnt.setIsAnteproyecto(isAnteproyecto);
        fileEnt.setFilename(file.getOriginalFilename());
        fileEnt.setDescription(description);

        // TODO chack that current numero entrega is (nroEntrega - 1) verify that no more that 3 entregas have been made for anteproyecto
        /* if nroEntrega > 3 break no more than 3 entregas can be made 
        if( isAnteproyecto) {
            Integer getnroEntrega = service.getCurrentNroEntregaAnteproyecto()
            if getnroEntrega + 1 != nroEntrega break
        } else {
            Integer getnroEntrega = service.getCurrentNroEntregaProyecto()
            if getnroEntrega + 1 != nroEntrega break
        }
        ok set fileEnt.setNroEntrega(nroEntrega); */
        if(nroEntrega > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solo se pueden realizar máximo 3 entregas.");
        }
        Integer currentEntrega = fileRepository.getCurrentNroEntrega(idAsociado);
        if ( currentEntrega == null) {currentEntrega = 0;}
        if((currentEntrega + 1) != nroEntrega) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El numero de entrega no corresponde con la entra que se debe hacer. El número de entrega actual es: " + currentEntrega);
        }

        fileEnt.setData(FileUtils.compressFile(file.getBytes()));

        //Add numero de entrega to anteproyecto
        /* Anteproyecto anteproyecto = anteproyectoRepository.findById(idAsociado).get();
        anteproyecto.setNroEntrega(anteproyecto.getNroEntrega()+1);
        anteproyectoRepository.save(anteproyecto); */

        try {
            fileRepository.save(fileEnt);
            return ResponseEntity.status(HttpStatus.CREATED).body("Documento subido satisfactoriamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al subir documento.");
        }
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
    
    public File getFile(UUID id) {
        Optional<File> file = fileRepository.findById(id);
        return file.get();
    }

    public byte[] downloadFile(File file) {
        byte[] document = FileUtils.decompressFile(file.getData());
        return document;
    }
}
