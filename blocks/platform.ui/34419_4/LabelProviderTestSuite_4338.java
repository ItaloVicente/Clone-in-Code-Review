
package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class LabelProviderTestSuite extends TestSuite {

    public static Test suite() {
        return new LabelProviderTestSuite();
    }

    public LabelProviderTestSuite() {        
        addTest(new LabelProviderTest("DecoratingStyledCellLabelProvider with Colors", true, true));
        addTest(new LabelProviderTest("DecoratingStyledCellLabelProvider", true, false));
        addTest(new LabelProviderTest("DecoratingLabelProvider with Colors", false, true));
        addTest(new LabelProviderTest("DecoratingLabelProvider", false, false));
    }
}
