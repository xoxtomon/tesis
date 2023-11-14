package tesis.backend.backend.file.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tesis.backend.backend.file.entity.File;
import tesis.backend.backend.file.service.FileService;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idAsociado") UUID idAsociado, @RequestParam("description") String description, @RequestParam("isAnteproyecto") Boolean isAnteproyecto, @RequestParam("nroEntrega") Integer nroEntrega) throws IOException {
        return fileService.uploadFile(file, description, idAsociado, isAnteproyecto, nroEntrega);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") UUID anteproyectoId) {
        File file = fileService.getFile(anteproyectoId);
        byte[] data = fileService.downloadFile(file);
        return ResponseEntity.ok()
        .contentType(MediaType.valueOf("application/pdf"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getFilename() + "\"" )
        .body(data);
    }

    @GetMapping()
    public List<File> getFiles() {
        return fileService.getAllFiles();
    }
}
