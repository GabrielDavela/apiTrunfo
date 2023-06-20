package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.LoginReqDTO;
import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.Valid;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/person")
@CrossOrigin
public class PersonController {

    @Autowired
    private PersonService personService;

    @Value("${chaveacesso}")
    private String chaveacesso;

    @Value("${chavesecreta}")
    private String chavesecreta;

    @Value("${bucketname}")
    private String bucketname;

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<PutObjectRequest> post(@RequestParam MultipartFile image,
                                                 @PathVariable Long id) throws IOException {

        try{
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            String keyName = UUID.randomUUID().toString();

            PutObjectRequest imageObject = new PutObjectRequest(bucketname, keyName, image.getInputStream(), null);
            amazonS3Client.putObject(imageObject);

            Person user = personService.listOne(id);
            user.setProfileImage(keyName);

            personService.save(user);
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/s3/{keyName}")
    public ResponseEntity<URL> list(@PathVariable String keyName) {
        URL url = null;
        try{
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            if(amazonS3Client.doesBucketExist(bucketname)){
                url = amazonS3Client.generatePresignedUrl(bucketname, keyName,
                        DateTime.now().plusDays(1).toDate());
            }

        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok(url);
    }


    @PostMapping
    public ResponseEntity<Person> create(@RequestBody @Valid PersonDTO personDTO){
        return ResponseEntity.ok(personService.create(personDTO));
    }

    @GetMapping
    public ResponseEntity<List<Person>> listAll(){
        return ResponseEntity.ok(personService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> listOne(@PathVariable Long id){
        return ResponseEntity.ok(personService.listOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@RequestBody @Valid PersonDTO personDTO, @PathVariable Long id) {
        return ResponseEntity.ok(personService.update(personDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id){
        return ResponseEntity.ok(personService.delete(id));
    }

    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestBody LoginReqDTO loginReqDTO) {
        Person person = personService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return ResponseEntity.ok(person);
    }



}
