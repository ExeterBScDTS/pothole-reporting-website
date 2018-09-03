package uk.ac.ex.student.bob.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import uk.ac.ex.student.bob.shared.PotholeReport;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("report")
public interface PotholeReportService extends RemoteService {
  String reportServer(PotholeReport report) throws IllegalArgumentException;
}
