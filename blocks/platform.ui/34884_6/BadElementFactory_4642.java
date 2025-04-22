package org.eclipse.ui.tests.api;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.tests.api.workbenchpart.ArbitraryPropertyTest;
import org.eclipse.ui.tests.api.workbenchpart.LifecycleViewTest;
import org.eclipse.ui.tests.api.workbenchpart.OverriddenTitleTest;
import org.eclipse.ui.tests.api.workbenchpart.RawIViewPartTest;
import org.eclipse.ui.tests.api.workbenchpart.ViewPartTitleTest;
import org.eclipse.ui.tests.ide.api.FileEditorInputTest;

public class ApiTestSuite extends TestSuite {

    public static Test suite() {
        return new ApiTestSuite();
    }

    public ApiTestSuite() {
        addTest(new TestSuite(IPageLayoutTest.class));
        addTest(new TestSuite(PlatformUITest.class));
        addTest(new TestSuite(IWorkbenchTest.class));
        addTest(new TestSuite(IWorkbenchWindowTest.class));
        addTest(new TestSuite(IWorkbenchPageTest.class));
        addTest(new TestSuite(IDeprecatedWorkbenchPageTest.class));
        addTest(new TestSuite(IActionFilterTest.class));
        addTest(new TestSuite(IPageListenerTest.class));
        addTest(new TestSuite(IPageServiceTest.class));
        addTest(new TestSuite(IPerspectiveRegistryTest.class));
        addTest(new TestSuite(IPerspectiveDescriptorTest.class));
        addTest(new TestSuite(IFileEditorMappingTest.class));
        addTest(new TestSuite(IEditorDescriptorTest.class));
        addTest(new TestSuite(IEditorRegistryTest.class));
        addTest(new TestSuite(IPerspectiveListenerTest.class));
        addTest(new TestSuite(IWorkbenchWindowActionDelegateTest.class));
        addTest(new TestSuite(IViewActionDelegateTest.class));
        addTest(new TestSuite(IViewSiteTest.class));
        addTest(new TestSuite(IEditorSiteTest.class));
        addTest(new TestSuite(IActionBarsTest.class));
        addTest(new TestSuite(IViewPartTest.class));
        addTest(new TestSuite(IEditorPartTest.class));
        addTest(new TestSuite(IEditorActionBarContributorTest.class));
        addTest(new TestSuite(IPartServiceTest.class));
        addTest(new TestSuite(ISelectionServiceTest.class));
        addTest(new TestSuite(IWorkingSetTest.class));
        addTest(new TestSuite(IWorkingSetManagerTest.class));
        addTest(new TestSuite(IWorkingSetElementAdapterTests.class));
        addTest(new TestSuite(IAggregateWorkingSetTest.class));
        addTest(new TestSuite(MockWorkingSetTest.class));
        addTest(new TestSuite(Bug42616Test.class));
        addTest(new TestSuite(StickyViewTest.class));
        addTest(new TestSuite(EditorIconTest.class));
        addTest(new TestSuite(RawIViewPartTest.class));
        addTest(new TestSuite(ViewPartTitleTest.class));
        addTest(new TestSuite(OverriddenTitleTest.class));
        addTest(new TestSuite(UIJobTest.class));
        addTest(new TestSuite(Bug75118Test.class));
        addTest(new TestSuite(FileEditorInputTest.class));
        addTest(new TestSuite(IEditorMatchingStrategyTest.class));
        addTest(new TestSuite(XMLMementoTest.class));
        addTest(new TestSuite(ArbitraryPropertyTest.class));
        addTest(new TestSuite(LifecycleViewTest.class));
        addTest(new TestSuite(Bug407422Test.class));
    }
}
