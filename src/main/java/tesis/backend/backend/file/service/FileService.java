package tesis.backend.backend.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<String> uploadFile(MultipartFile file, String description, UUID anteproyectoId) throws IOException {
        File fileEnt = new File();
        fileEnt.setAnteproyectoId(anteproyectoId);
        fileEnt.setFilename(file.getOriginalFilename());
        fileEnt.setDescription(description);
        fileEnt.setData(FileUtils.compressFile(file.getBytes()));

        //Add numero de entrega to anteproyecto
        Anteproyecto anteproyecto = anteproyectoRepository.findById(anteproyectoId).get();
        anteproyecto.setNroEntrega(anteproyecto.getNroEntrega()+1);
        anteproyectoRepository.save(anteproyecto);

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
    
    public File getFile(UUID anteproyectoId) {
        Optional<File> file = fileRepository.findById(anteproyectoId);
        return file.get();
    }

    public byte[] downloadFile(File file) {
        byte[] document = FileUtils.decompressFile(file.getData());
        return document;
    }
}
