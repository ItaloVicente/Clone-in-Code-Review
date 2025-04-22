package org.eclipse.ui.tests.api.workbenchpart;

import junit.framework.Assert;

import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class RawIViewPartTest extends UITestCase {
    public RawIViewPartTest(String testName) {
        super(testName);
    }

    IWorkbenchWindow window;

    IWorkbenchPage page;

    RawIViewPart view;

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
        view = (RawIViewPart) page
                .showView("org.eclipse.ui.tests.workbenchpart.RawIViewPart");
        ref = page
                .findViewReference("org.eclipse.ui.tests.workbenchpart.RawIViewPart");
        ref.addPropertyListener(propertyListener);
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

    private void verifySettings(IWorkbenchPart part, String expectedTitle,
            String expectedPartName, String expectedContentDescription)
            throws Exception {
        Assert.assertEquals("Incorrect view title", expectedTitle, part
                .getTitle());

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
        verifySettings("SomeTitle", "RawIViewPart", "SomeTitle");
        verifyEvents(false, false, false);
    }

    public void XXXtestCustomTitle() throws Throwable {
        view.setTitle("CustomTitle");
        verifySettings("CustomTitle", "RawIViewPart", "CustomTitle");
        verifyEvents(true, false, true);
    }

    public void XXXtestEmptyContentDescription() throws Throwable {
        view.setTitle("RawIViewPart");
        verifySettings("RawIViewPart", "RawIViewPart", "");
        verifyEvents(true, false, true);
    }
}
