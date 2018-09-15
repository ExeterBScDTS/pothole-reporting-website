package uk.ac.ex.student.bob.server;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.model.geojson.Point;
import org.bson.Document;

public class PotholeCollection {

    private MongoCollection<Document> collection;

    PotholeCollection(String name){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        collection = database.getCollection("testCollection");
        collection.createIndex(Indexes.geo2dsphere("location"));
    }
    
    public void insert(PotholeReportDocument report){
        collection.insertOne(report.to_document());
    }
}