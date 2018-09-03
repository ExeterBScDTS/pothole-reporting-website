package uk.ac.ex.student.bob.shared;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.io.Serializable;


public class PotholeReport implements Serializable{

    private static final long serialVersionUID = 0L;

    private List<Double> location; // required
    private Date date_reported; // required
    private Date date_observed; // optional
    private String reported_by; // optional
    private String reported_as; // optional
    private int depth; // optional
    private int diameter; // optional

    // There are muliple properties to initialise, some of 
    // which are optional, so we use the Builder pattern.
    public static class Builder{

        private List<Double> location; // required
        private Date date_reported; // required
        private Date date_observed; // optional
        private String reported_by; // optional
        private String reported_as; // optional
        private int depth; // optional
        private int diameter; // optional

        public Builder( double longitude, double latitude ){
            this.location = Arrays.asList(longitude, latitude);
            // date_reported is also required so we set it to 'now',
            // but this value can be modified by chaining withDateReported().
            this.date_reported = new Date();
            // All other parameters are optional so we leave them as null.

            // Builder is a constructor so returns a Builder object (i.e. this)
        }

        // These further methods are chained to set extra parameters.
        // For example
        // PotholeReport sample_report = new 
        //   PotholeReport.Builder(-73.92502,40.8279556)
        //      .withDateReported( new Date() )
        //      .withReportedBy("bob").build();
        //
        public Builder withDateReported( Date date_reported ){
            this.date_reported = date_reported;
            return this;
        }

        public Builder withDateObserved( Date date_observed ){
            this.date_observed = date_observed;
            return this;
        }

        public Builder withReportedBy( String reported_by ){
            this.reported_by = reported_by;
            return this;
        }

        public Builder withReportedAs( String reported_as ){
            this.reported_as = reported_as;
            return this;
        }

        public PotholeReport build(){
            // The PotholeReport constructor is private, but as
            // an internal class we can access it.  However,
            // with great power comes great responsibility. So
            // we must now ensure that any PotholeReport object
            // we create has all the required properties.
            PotholeReport report = new PotholeReport();

            report.location = this.location;
            report.date_reported = this.date_reported;
            report.date_observed = this.date_observed;
            report.reported_by = this.reported_by;
            report.reported_as = this.reported_as;
            report.depth = this.depth;
            report.diameter = this.diameter;

            return report;
        }

    }

    // By making the default constructor private we prevent
    // situations where empty reports are created. 
    private PotholeReport(){};

    public List<Double> location(){
        return this.location;
    }

    public String reportedBy(){
        return this.reported_by;
    }

    public void reportedBy(String reported_by){
        this.reported_by = reported_by;
    }

    public String reportedAs(){
        return this.reported_as;
    }

    public void reportedAs(String reported_as){
        this.reported_as = reported_as;
    }

    public Date dateReported(){
        return this.date_reported;
    }

    public Date dateObserved(){
        return this.date_observed;
    }

    public int diameter(){
        return this.diameter;
    }

    public void diameter(int diameter){
        this.diameter = diameter;
    }

    public int depth(){
        return this.depth;
    }

    public void depth(int depth){
        this.depth = depth;
    }
}