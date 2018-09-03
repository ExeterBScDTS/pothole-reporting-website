package uk.ac.ex.student.bob.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.ac.ex.student.bob.shared.PotholeReport;

/**
 * The async counterpart of <code>PotholeReportService</code>.
 */
public interface PotholeReportServiceAsync {
  void reportServer(PotholeReport input, AsyncCallback<String> callback)
      throws IllegalArgumentException;
}
