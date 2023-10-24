package tesis.backend.backend.file.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "file", schema = "public")
public class File {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "fileid", updatable = false)
    private UUID id;

    @Column(name = "anteproyectoid")
    private UUID anteproyectoId;
    
    @Column(name = "filename")
    private String filename;    

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "data")
    private byte[] data;
    
    public File(UUID anteproyectoId, String description, byte[] data) {
        this.anteproyectoId = anteproyectoId;
        this.description = description;
        this.data = data;
    }

}
