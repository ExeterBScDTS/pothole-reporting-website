package uk.ac.ex.student.bob.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;



// This is the service we are implementing here.
// N.B. defined in client code.
import uk.ac.ex.student.bob.client.PotholeReportService;

// The type of object we expect to receive.
// N.B. defined in shared (client and server) code.
import uk.ac.ex.student.bob.shared.PotholeReport;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PotholeReportServiceImpl extends RemoteServiceServlet implements
    PotholeReportService {

static Logger log = Log.getRootLogger();

static PotholeCollection db_collection = new PotholeCollection("testCollection");

  public String reportServer(PotholeReport report) throws IllegalArgumentException {
     
    log.info("Pothole report received");

    String serverInfo = getServletContext().getServerInfo();
    
    //db_collection.insert( new PotholeReportDocument(report) );
    return "Thank you! "
        + "Your pothole report was received. " + serverInfo;
  }

 }
