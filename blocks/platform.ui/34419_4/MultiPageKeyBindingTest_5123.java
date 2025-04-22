package org.eclipse.ui.tests.multipageeditor;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MultiPageEditorTestSuite extends TestSuite {

    public static Test suite() {
        return new MultiPageEditorTestSuite();
    }

    public MultiPageEditorTestSuite() {
        addTestSuite(MultiEditorInputTest.class);
        addTestSuite(MultiVariablePageTest.class);
    }
}
