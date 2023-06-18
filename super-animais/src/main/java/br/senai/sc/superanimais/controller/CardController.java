package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.CardService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/card")
@CrossOrigin
public class CardController {

    @Autowired
    private CardService cardService;

    @Value("${chaveacesso}")
    private String chaveacesso;

    @Value("${chavesecreta}")
    private String chavesecreta;

    @Value("${bucketname}")
    private String bucketname;

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody CardDTO cardDTO){
        return ResponseEntity.ok(cardService.create(cardDTO));
    }

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
            System.out.println(imageObject);
            amazonS3Client.putObject(imageObject);

            Card card = cardService.listOne(id);
            card.setProfileImage(keyName);

            cardService.save(card);
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

    @GetMapping
    public ResponseEntity<List<Card>> listAll(){
        return ResponseEntity.ok(cardService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> listOne(@PathVariable long id){
        return ResponseEntity.ok(cardService.listOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> editCard(@RequestBody CardDTO cardDTO, @PathVariable long id) {
        return ResponseEntity.ok(cardService.update(cardDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Card> delete(@PathVariable long id) {
        return ResponseEntity.ok(cardService.delete(id));
    }

}
