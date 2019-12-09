package data.source.jigsaw.example;

import data.source.jigsaw.JigsawCrawler;
import data.source.jigsaw.JigsawHttpClient;
import kafka.Producer;

import java.io.IOException;
import java.net.URISyntaxException;

public class JigsawCrawlerDemo {
    public void getSampleData() throws IOException, URISyntaxException {
        JigsawHttpClient jigsawHttpClient = new JigsawHttpClient();
        Producer producer = new Producer("");
        JigsawCrawler jigsawCrawler = new JigsawCrawler(jigsawHttpClient, producer);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new JigsawCrawlerDemo().getSampleData();
    }
}
