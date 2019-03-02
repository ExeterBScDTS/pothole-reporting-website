package uk.ac.ex.student.bob.client;

import uk.ac.ex.student.bob.shared.PotholeReport;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import org.junit.Test;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class myAppTest extends GWTTestCase {

  /**
   * Must refer to a valid module that sources this class.
   */
  public String getModuleName() {
    return "uk.ac.ex.student.bob.myAppJUnit";
  }

  
  /**
   * This test will send a request to the server using the reportServer method in
   * PotholeReportService and verify the response.
   */
  public void testReportService() {
    // Create the service that we will test.
    PotholeReportServiceAsync reportService = GWT.create(PotholeReportService.class);
    ServiceDefTarget target = (ServiceDefTarget) reportService;
    target.setServiceEntryPoint(GWT.getModuleBaseURL() + "myapp/report");

    // Since RPC calls are asynchronous, we will need to wait for a response
    // after this test method returns. This line tells the test runner to wait
    // up to 10 seconds before timing out.
    delayTestFinish(10000);

    // Send a request to the server.
    PotholeReport report = new PotholeReport.Builder(0.0,0.0).build();
    reportService.reportServer(report, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        // The request resulted in an unexpected error.
        fail("Request failure: " + caught.getMessage());
      }

      public void onSuccess(String result) {
        // Verify that the response is correct.
        assertTrue(result.startsWith("Thank you!"));

        // Now that we have received a response, we need to tell the test runner
        // that the test is complete. You must call finishTest() after an
        // asynchronous test finishes successfully, or the test will time out.
        finishTest();
      }
    });
  }


}
