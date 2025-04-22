package org.eclipse.ui.tests.dialogs;

import org.junit.runner.RunWith;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(org.junit.runners.AllTests.class)
public class FilteredResourcesSelectionDialogTestSuite extends TestSuite {

    public static Test suite() {
        return new FilteredResourcesSelectionDialogTestSuite();
    }

    public FilteredResourcesSelectionDialogTestSuite() {
		addTestSuite(ResourceItemlLabelTest.class);
    }
}
