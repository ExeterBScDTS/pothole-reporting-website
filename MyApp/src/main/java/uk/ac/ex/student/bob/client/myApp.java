package uk.ac.ex.student.bob.client;

// for logging to browser console
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Map; // clashes with openlayers

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Window;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.control.DrawFeature;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.event.VectorFeatureAddedListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.handler.PointHandler;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import uk.ac.ex.student.bob.shared.PotholeReport;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class myApp implements EntryPoint {

  private static Logger logger = Logger.getLogger("Logger");
  
  private static class MultiBox extends Composite {

    private ListBox multiBox = new ListBox();
    private ReportDialog reportDialog;

    public MultiBox(Vector layer, java.util.Map<String, PotholeReport> reports){
      VerticalPanel panel = new VerticalPanel();
      // Add a list box with multiple selection enabled
      multiBox.ensureDebugId("cwListBox-multiBox");
      multiBox.setWidth("11em");
      multiBox.setVisibleItemCount(10);

      // Using a ChangeHandler works too, but give slightly 
      // different UI experience.
      multiBox.addClickHandler( new ClickHandler() {
        public void onClick(ClickEvent event) {
          String selected = multiBox.getSelectedValue();
          VectorFeature feat = layer.getFeatureById(selected);
          PotholeReport report = reports.get(selected);
          logger.log(Level.INFO, "selection: " + selected);
          logger.log(Level.INFO, "feature: " + feat); 
          reportDialog.showReport(report);
        }});
      panel.setSpacing(4);
      panel.add(multiBox);
      initWidget(panel);
      // Give the overall composite a style name.
      setStyleName("example-MultiBox");
    }
    public void clear(){
      this.multiBox.clear();
    }
    public void addItem(String label, String fid){
      this.multiBox.addItem(label, fid);
    }
    public void addDialog(ReportDialog reportDialog){
      this.reportDialog = reportDialog;
    }
  }

private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  private static class ReportDialog extends DialogBox{
    private DialogBox box;
    private PotholeReport report;
    /**
    * Create a remote service proxy to talk to the server-side PotholeReport service.
    */
    private final PotholeReportServiceAsync reportService = GWT.create(PotholeReportService.class);

    final Button closeButton = new Button("Close");
    final Button submitButton = new Button("Submit");
    final TextBox descriptionField = new TextBox(); 
    final TextBox diameterField = new TextBox();
    final TextBox depthField = new TextBox();

    
    /**
     * Send the report to the server and wait for a response.
     */
    private void sendReportToServer() {
        
        reportService.reportServer(report, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            Window.alert(SERVER_ERROR);
          }

          public void onSuccess(String result) {
            Window.alert(result);
          }
        });
    }

    public ReportDialog(){
      this.setAnimationEnabled(true);
      box = this;

      VerticalPanel dialogVPanel = new VerticalPanel();
      dialogVPanel.addStyleName("dialogVPanel");
      dialogVPanel.add(new HTML("<b>description:</b>"));
      dialogVPanel.add(descriptionField);
      dialogVPanel.add(new HTML("<b>diameter (cm):</b>"));
      dialogVPanel.add(diameterField);
      dialogVPanel.add(new HTML("<b>depth (cm):</b>"));
      dialogVPanel.add(depthField);
      dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
      HorizontalPanel buttonsHPanel = new HorizontalPanel();
      buttonsHPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
      buttonsHPanel.add(submitButton);
      buttonsHPanel.add(closeButton);
      dialogVPanel.add(buttonsHPanel);
      this.setWidget(dialogVPanel);

      // Add a handler to close the DialogBox  
      closeButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          box.hide();
        }
      });
    
      // Add a handler to submit the report
      submitButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          // copy values from TextBoxes to the Report
          int diameter = Integer.valueOf(diameterField.getValue());
          int depth = Integer.valueOf(depthField.getValue());
          report.diameter(diameter);
          report.depth(depth);
          report.reportedAs(descriptionField.getValue());
          sendReportToServer();
          box.hide();
        }
      });
    }
    public void showReport(PotholeReport report){
        this.report = report;
        descriptionField.setText(report.reportedAs());
        diameterField.setText( Integer.toString(report.diameter()) );
        depthField.setText( Integer.toString(report.depth()) );
        box.center();
        closeButton.setFocus(true);
    }
  }

  public void onModuleLoad() {
  
    String[] defaultNames = {"pothole A", "pothole B", "pothole C", "pothole D",
                            "pothole E", "pothole F", "pothole G", "pothole H"};

    MapOptions defaultMapOptions = new MapOptions();
    MapWidget mapWidget = new MapWidget("500px", "500px", defaultMapOptions);
    OSM osmMapnik = OSM.Mapnik("Open street map");
 
    osmMapnik.setIsBaseLayer(true);
    mapWidget.getMap().addLayer(osmMapnik);
 
    final Map map = mapWidget.getMap();
    final Vector vectorLayer = new Vector("Vectorlayer");

    DrawFeature drawPointFeatureControl = new DrawFeature(vectorLayer, new PointHandler());
    map.addControl(drawPointFeatureControl);
    // We might add other map drawing features in future.
    // So we activate and deactivate as needed.
    drawPointFeatureControl.activate();

    LonLat lonLat = new LonLat(-3.53, 50.74);
    lonLat.transform("EPSG:4326", map.getProjection());
    map.setCenter(lonLat, 12);

    java.util.Map<String, PotholeReport> reports = new java.util.HashMap<>();
    final MultiBox multiBoxPanel = new MultiBox(vectorLayer, reports);

    vectorLayer.addVectorFeatureAddedListener(new VectorFeatureAddedListener(){

      public void onFeatureAdded(FeatureAddedEvent event){
        logger.log(Level.INFO, event.toString());
        multiBoxPanel.clear();
        int nextName = 0;
        for(VectorFeature v : vectorLayer.getFeatures()){
          LonLat lonLat = v.getCenterLonLat();
          //Transform lonlat to more readable format
          //Notice the parameters are reversed from when we called setCentre()
          //as this in now the inverse transformation.
          String fid = v.getFeatureId();
          String reportedAs = defaultNames[nextName];
          nextName++;
          multiBoxPanel.addItem( reportedAs, fid );
          
          if(reports.get(fid) == null){
            reports.put(fid, new PotholeReport.Builder(lonLat.lon(), lonLat.lat()).withReportedAs(reportedAs).build());
          }
          lonLat.transform(map.getProjection(), "EPSG:4326"); 
          logger.log(Level.INFO, "LonLat = (" + lonLat.lon() + " ; " + lonLat.lat() + ")");
          
        }
      }
    }
    );

    mapWidget.getMap().addLayer(vectorLayer);
    mapWidget.getMap().addControl(new LayerSwitcher());

    // Create the popup dialog box
    final ReportDialog reportDialogBox = new ReportDialog();

    multiBoxPanel.addDialog( reportDialogBox );

    DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
    p.addNorth(new HTML("Pothole report"), 2);
    p.addSouth(new HTML("Prototype"), 2);
    p.addWest(multiBoxPanel, 10);
    p.add(mapWidget);
    RootLayoutPanel.get().add(p);
    
  }
}
