package uk.ac.ex.student.bob;

import uk.ac.ex.student.bob.client.myAppTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class myAppSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for myApp");
    suite.addTestSuite(myAppTest.class);
    return suite;
  }
}
