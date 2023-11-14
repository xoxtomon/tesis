package tesis.backend.backend.file.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.engine.query.spi.ReturnMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tesis.backend.backend.file.entity.File;
import tesis.backend.backend.file.service.FileService;
import tesis.backend.backend.security.config.JwtService;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ESTUDIANTE')")
    public ResponseEntity<String> uploadFile(@RequestHeader(name = "Authorization") String Authorization,
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAsociado") UUID idAsociado,
            @RequestParam("description") String description,
            @RequestParam("isAnteproyecto") Boolean isAnteproyecto,
            @RequestParam("nroEntrega") Integer nroEntrega) throws IOException {

        String jwt = Authorization.substring(7);
        UUID id = UUID.fromString(jwtService.extractUserId(jwt));
        if (!fileService.isAssociated(id, idAsociado)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario que intenta enviar una entrega no está asociado al anteproyecto.");
        }

        return fileService.uploadFile(file, description, idAsociado, isAnteproyecto, nroEntrega);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") UUID anteproyectoId) {
        File file = fileService.getFile(anteproyectoId);
        byte[] data = fileService.downloadFile(file);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getFilename() + "\"")
                .body(data);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<File> getFiles() {
        return fileService.getAllFiles();
    }
}
