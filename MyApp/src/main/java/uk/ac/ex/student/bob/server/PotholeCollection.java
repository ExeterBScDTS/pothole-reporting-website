package uk.ac.ex.student.bob.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class PotholeCollection {

    static Logger log = Log.getRootLogger();

    private MongoCollection<Document> collection;

    PotholeCollection(String name){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        collection = database.getCollection("testCollection");
        log.info("Have MongoDB collection", collection);
        collection.createIndex(Indexes.geo2dsphere("location"));
    }
    
    public void insert(PotholeReportDocument report){
        collection.insertOne(report.to_document());
    }
}