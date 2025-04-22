import org.eclipse.ui.tests.markers.ResourceMappingMarkersTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the UI Implementation.
 */
public class InternalTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new InternalTestSuite();
    }
