package org.eclipse.ui.tests.api.workbenchpart;

import junit.framework.Assert;

import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ViewPartTitleTest extends UITestCase {

    public ViewPartTitleTest(String testName) {
        super(testName);
    }

    IWorkbenchWindow window;

    IWorkbenchPage page;

    EmptyView view;

    IWorkbenchPartReference ref;

    boolean titleChangeEvent = false;

    boolean nameChangeEvent = false;

    boolean contentChangeEvent = false;

    private IPropertyListener propertyListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            switch (propId) {
            case IWorkbenchPartConstants.PROP_TITLE:
                titleChangeEvent = true;
                break;
            case IWorkbenchPartConstants.PROP_PART_NAME:
                nameChangeEvent = true;
                break;
            case IWorkbenchPartConstants.PROP_CONTENT_DESCRIPTION:
                contentChangeEvent = true;
                break;
            }
        }
    };

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        window = openTestWindow();
        page = window.getActivePage();
        String viewId = "org.eclipse.ui.tests.workbenchpart.EmptyView";
        view = (EmptyView) page.showView(viewId);
        ref = page.findViewReference(viewId);
        view.addPropertyListener(propertyListener);
        titleChangeEvent = false;
        nameChangeEvent = false;
        contentChangeEvent = false;
    }

    @Override
	protected void doTearDown() throws Exception {
        view.removePropertyListener(propertyListener);
        page.hideView(view);
        super.doTearDown();
    }

    private void verifySettings(IWorkbenchPart2 part, String expectedTitle,
            String expectedPartName, String expectedContentDescription)
            throws Exception {
        Assert.assertEquals("Incorrect view title", expectedTitle, part
                .getTitle());
        Assert.assertEquals("Incorrect part name", expectedPartName, part
                .getPartName());
        Assert.assertEquals("Incorrect content description",
                expectedContentDescription, part.getContentDescription());

        Assert.assertEquals("Incorrect title in view reference", expectedTitle,
                ref.getTitle());
        Assert.assertEquals("Incorrect part name in view reference",
                expectedPartName, ref.getPartName());
        Assert.assertEquals("Incorrect content description in view reference",
                expectedContentDescription, ref.getContentDescription());
    }

    private void verifySettings(String expectedTitle, String expectedPartName,
            String expectedContentDescription) throws Exception {
        verifySettings(view, expectedTitle, expectedPartName,
                expectedContentDescription);
    }

    private void verifyEvents(boolean titleEvent, boolean nameEvent,
            boolean descriptionEvent) {
        if (titleEvent) {
            Assert.assertEquals("Missing title change event", titleEvent,
                    titleChangeEvent);
        }
        if (nameEvent) {
            Assert.assertEquals("Missing name change event", nameEvent,
                    nameChangeEvent);
        }
        if (descriptionEvent) {
            Assert.assertEquals("Missing content description event",
                    descriptionEvent, contentChangeEvent);
        }
    }

    public void testDefaults() throws Throwable {
        verifySettings("EmptyView", "EmptyView", "");
        verifyEvents(false, false, false);
    }

    public void testNullTitle() throws Throwable {
        view.setTitle(null);

        verifySettings("", "EmptyView", "");
        verifyEvents(true, false, false);
    }

    public void XXXtestCustomName() throws Throwable {
        view.setPartName("CustomPartName");
        verifySettings("CustomPartName", "CustomPartName", "");
        verifyEvents(true, true, false);
    }

    public void testCustomTitle() throws Throwable {
        view.setTitle("CustomTitle");
        verifySettings("CustomTitle", "EmptyView", "CustomTitle");
        verifyEvents(true, false, true);
    }

    public void testCustomContentDescription() throws Throwable {
        view.setContentDescription("CustomContentDescription");
        verifySettings("EmptyView (CustomContentDescription)", "EmptyView",
                "CustomContentDescription");
        verifyEvents(true, false, true);
    }

    public void XXXtestCustomNameAndContentDescription() throws Throwable {
        view.setPartName("CustomName");
        view.setContentDescription("CustomContentDescription");
        verifySettings("CustomName (CustomContentDescription)", "CustomName",
                "CustomContentDescription");
        verifyEvents(true, true, true);
    }
}
