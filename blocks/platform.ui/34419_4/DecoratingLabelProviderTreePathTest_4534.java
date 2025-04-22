package org.eclipse.jface.tests.labelProviders;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DecoratingLabelProviderTests extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new DecoratingLabelProviderTests();
    }

    public DecoratingLabelProviderTests() {
    	addTestSuite(CompositeLabelProviderTableTest.class);
    	addTestSuite(DecoratingLabelProviderTreePathTest.class);
        addTestSuite(DecoratingLabelProviderTreeTest.class);
        addTestSuite(ColorAndFontLabelProviderTest.class);
        addTestSuite(ColorAndFontViewerLabelProviderTest.class);
        addTestSuite(DecoratingStyledCellLabelProviderTest.class);
        addTestSuite(IDecorationContextTest.class);
    }
}
