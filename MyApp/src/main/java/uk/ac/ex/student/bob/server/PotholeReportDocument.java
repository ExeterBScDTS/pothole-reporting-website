package uk.ac.ex.student.bob.server;

import uk.ac.ex.student.bob.shared.PotholeReport;

// Types required for the MongoDb document
import org.bson.Document;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.model.geojson.Point;

public class PotholeReportDocument implements ToDocument {

    PotholeReport report;

    public PotholeReportDocument( PotholeReport report ){
        this.report = report;
    }

    public Document to_document(){
           // Always include these properties.
        Document doc = new Document("location", 
            new Point(new Position( report.location() )))
        .append("date reported", report.dateReported());
        
        // Only include others if not null
        if( report.reportedBy() != null){
            doc.append("reported by", report.reportedBy());
        }
        if( report.dateObserved() != null){
            doc.append("date observed", report.dateObserved());
        }
        if( report.depth() != 0){
            doc.append("depth", report.depth());
        }
        if( report.diameter() != 0){
            doc.append("diameter", report.diameter());
        }
        return doc;
    }    
}