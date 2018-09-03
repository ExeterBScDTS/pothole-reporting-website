package uk.ac.ex.student.bob.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// This is the service we are implementing here.
import uk.ac.ex.student.bob.client.PotholeReportService;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import uk.ac.ex.student.bob.shared.PotholeReport;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PotholeReportServiceImpl extends RemoteServiceServlet implements
    PotholeReportService {

static Logger log = Log.getRootLogger();

  public String reportServer(PotholeReport input) throws IllegalArgumentException {
    // Verify that the input is valid. 
    
    log.info("Pothole report received");

    String serverInfo = getServletContext().getServerInfo();
    
    // String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    //input = escapeHtml(input);
    // userAgent = escapeHtml(userAgent);

    return "Thank you! "
        + "Your pothole report was received. " + serverInfo;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
