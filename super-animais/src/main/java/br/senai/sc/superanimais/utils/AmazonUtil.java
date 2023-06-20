package br.senai.sc.superanimais.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonUtil {

    public static AmazonS3Client returnAmazonS3Client(String chaveacesso, String chavesecreta){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(chaveacesso, chavesecreta);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }

}
