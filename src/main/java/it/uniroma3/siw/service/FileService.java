package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    // Salva il file nella directory specificata
    public String saveFile(MultipartFile file, String uploadDirPath) throws IOException {
        Path uploadDir = Paths.get(uploadDirPath);
        
        // Crea la directory se non esiste
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Genera un nome univoco per il file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = uploadDir.resolve(fileName);
        
        // Scrive i bytes del file nella directory
        Files.write(path, file.getBytes());

        // Converte il percorso per usare le barre oblique
        String relativePath = uploadDir.subpath(1, uploadDir.getNameCount()).resolve(fileName).toString().replace("\\", "/");
        return "/" + relativePath;
    }

    // Elimina il file dalla directory specificata
    public void deleteFile(String fileUrl, String uploadDirPath) throws IOException {
        Path path = Paths.get(uploadDirPath).resolve(Paths.get(fileUrl).getFileName().toString());
        
        // Controlla se il file esiste e lo elimina
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
