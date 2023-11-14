package tesis.backend.backend.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.repository.AnteproyectoRepository;
import tesis.backend.backend.anteproyecto.service.AnteproyectoService;
import tesis.backend.backend.file.entity.File;
import tesis.backend.backend.file.repository.FileRepository;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.util.FileUtils;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private AnteproyectoService anteproyectoService;

    public ResponseEntity<String> uploadFile(MultipartFile file, String description, UUID idAsociado, Boolean isAnteproyecto, Integer nroEntrega) throws IOException {
        File fileEnt = new File();
        fileEnt.setIdAsociado(idAsociado);
        fileEnt.setIsAnteproyecto(isAnteproyecto);
        fileEnt.setFilename(file.getOriginalFilename());
        fileEnt.setDescription(description);

        if(nroEntrega > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solo se pueden realizar máximo 3 entregas.");
        }
        Integer currentEntrega = fileRepository.getCurrentNroEntrega(idAsociado);
        if ( currentEntrega == null) {currentEntrega = 0;}
        if((currentEntrega + 1) != nroEntrega) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El numero de entrega no corresponde con la entra que se debe hacer. El número de entrega actual es: " + currentEntrega);
        }
        fileEnt.setNroEntrega(nroEntrega);
        fileEnt.setData(FileUtils.compressFile(file.getBytes()));

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

    public Boolean isAssociated(UUID idUser, UUID idAnteproyecto) {
        Set<User> autores = anteproyectoService.findAutoresOfAnteproyecto(idAnteproyecto);
        for (User user : autores) {
            if (user.getUserId().equals(idUser)) {
                return true;
            }
        }
        return false;
    }
}
