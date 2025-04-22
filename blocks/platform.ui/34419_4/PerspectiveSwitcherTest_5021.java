package org.eclipse.ui.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.tests.api.TrimLayoutTest;
import org.eclipse.ui.tests.markers.Bug75909Test;
import org.eclipse.ui.tests.markers.DeclarativeFilterActivityTest;
import org.eclipse.ui.tests.markers.DeclarativeFilterDeclarationTest;
import org.eclipse.ui.tests.markers.MarkerSortUtilTest;
import org.eclipse.ui.tests.markers.MarkerSupportRegistryTests;
import org.eclipse.ui.tests.markers.MarkerTesterTest;
import org.eclipse.ui.tests.markers.MarkerViewTests;
import org.eclipse.ui.tests.markers.ResourceMappingMarkersTest;

public class InternalTestSuite extends TestSuite {

    public static Test suite() {
        return new InternalTestSuite();
    }

    public InternalTestSuite() {
        addTest(new TestSuite(TrimLayoutTest.class));
        addTest(new TestSuite(AnimationEngineTest.class));
        addTest(new TestSuite(EditorActionBarsTest.class));
        addTest(new TestSuite(ActionSetExpressionTest.class));
        addTest(new TestSuite(PopupMenuExpressionTest.class));
        addTest(new TestSuite(Bug41931Test.class));
        addTest(Bug75909Test.suite());
        addTest(new TestSuite(Bug78470Test.class));
        addTest(new TestSuite(DeclarativeFilterActivityTest.class));
        addTest(new TestSuite(DeclarativeFilterDeclarationTest.class));
        addTest(new TestSuite(ResourceMappingMarkersTest.class));
        addTest(new TestSuite(MarkerSupportRegistryTests.class));
        addTest(new TestSuite(MarkerSortUtilTest.class));
        addTest(new TestSuite(MarkerViewTests.class));
        addTest(Bug99858Test.suite());
        addTest(new TestSuite(WorkbenchWindowSubordinateSourcesTests.class));
        addTest(new TestSuite(ReopenMenuTest.class));
        addTest(new TestSuite(UtilTest.class));
		addTest(new TestSuite(MarkerTesterTest.class));
		addTest(new TestSuite(TextHandlerTest.class));
        addTest(new TestSuite(PerspectiveSwitcherTest.class));
        addTest(new TestSuite(StickyViewManagerTest.class));
        addTest(new TestSuite(FileEditorMappingTest.class));
        addTest(new TestSuite(WorkbenchSiteProgressServiceModelTagsTest.class));
    }
}
