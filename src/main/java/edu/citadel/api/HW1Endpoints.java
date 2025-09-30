package edu.citadel.api;

import edu.citadel.hw1.InheritanceDemo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/hw1")
public class HW1Endpoints {

    @GetMapping(value = "/main", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> runMain() {
        try {
            InheritanceDemo.main(new String[]{});
            return new ResponseEntity<>("Function main() executed on server successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getMainOutput", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<FileSystemResource> getMainOutput() {
        try {
            File outputFile = InheritanceDemo.getMainOutputFile();
            FileSystemResource resource = new FileSystemResource(outputFile);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "alex-bailey-hw1-output.txt")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(outputFile.length())
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
