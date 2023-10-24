package tesis.backend.backend.file.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tesis.backend.backend.file.entity.File;
import tesis.backend.backend.file.repository.FileRepository;
import tesis.backend.backend.util.FileUtils;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;

    public ResponseEntity<String> uploadFile(MultipartFile file, String description, UUID anteproyectoId) throws IOException {
        File fileEnt = new File();
        fileEnt.setAnteproyectoId(anteproyectoId);
        fileEnt.setDescription(description);
        fileEnt.setData(FileUtils.compressFile(file.getBytes()));

        try {
            fileRepository.save(fileEnt);
            return ResponseEntity.status(HttpStatus.CREATED).body("Documento subido satisfactoriamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al subir documento.");
        }
    }
}
