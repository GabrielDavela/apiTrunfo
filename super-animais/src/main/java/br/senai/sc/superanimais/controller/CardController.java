package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.CardService;
import br.senai.sc.superanimais.utils.AmazonUtil;
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
import java.util.ArrayList;
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
    public ResponseEntity<Card> create(@RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.create(cardDTO));
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<PutObjectRequest> uploadImageFromCard(@RequestParam MultipartFile image,
                                                 @PathVariable Long id) throws IOException {

        try {
            AmazonS3Client amazonS3Client = AmazonUtil.returnAmazonS3Client(chaveacesso, chavesecreta);
            UUID keyName = UUID.randomUUID();

            PutObjectRequest imageObject = new PutObjectRequest(bucketname, keyName.toString(), image.getInputStream(), null);
            amazonS3Client.putObject(imageObject);

            Card card = cardService.listOne(id);
            card.setImageRef(keyName.toString());
            cardService.save(card);
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/request/image/{keyName}")
    public ResponseEntity<URL> listAUrlCard(@PathVariable String keyName) {
        URL url = null;
        try {
            AmazonS3Client amazonS3Client = AmazonUtil.returnAmazonS3Client(chaveacesso, chavesecreta);
            if (amazonS3Client.doesBucketExist(bucketname)) {
                url = amazonS3Client.generatePresignedUrl(bucketname, keyName,
                        DateTime.now().plusDays(1).toDate());
            }
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok(url);
    }

    @GetMapping("/request/image/all")
    public ResponseEntity<List<URL>> listAllUrlsCards() {
        List<URL> urls = new ArrayList<>();
        try {
            AmazonS3Client amazonS3Client = AmazonUtil.returnAmazonS3Client(chaveacesso, chavesecreta);
            if (amazonS3Client.doesBucketExist(bucketname)) {
                List<Card> cardList = cardService.listAll();
                for (Card card : cardList) {
                    URL url = amazonS3Client.generatePresignedUrl(bucketname, card.getImageRef(),
                            DateTime.now().plusDays(1).toDate());
                    urls.add(url);
                }
            }
        } catch (AmazonS3Exception e) {
            System.exit(0);
        }
        return ResponseEntity.ok(urls);
    }

    @GetMapping
    public ResponseEntity<List<Card>> listAll() {
        return ResponseEntity.ok(cardService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> listOne(@PathVariable long id) {
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
