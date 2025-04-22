
package org.eclipse.jface.tests.wizards;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.util.ILogger;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WizardTest extends TestCase {
	protected static final int NUM_PAGES = 3;

	static boolean DID_FINISH = false; //accessed from this test AND wizard

	protected final static String WIZARD_TITLE = "TEST WIZARD TITLE";
	protected final static String PAGE_TITLE = "TEST PAGE TITLE";
	protected RGB color1;
	protected RGB color2;

	protected TheTestWizard wizard;
	protected TheTestWizardDialog dialog;

	boolean pageChanged = false;
	boolean pageChangingFired = false;


	public WizardTest() {
		super("NewWizardTest");
	}


	public void testEndingWithFinish() {
        assertEquals("Wizard has wrong number of pages", NUM_PAGES, wizard.getPageCount());

        assertEquals("WizardPage.getName() returned wrong name", wizard.page1Name, wizard.page1.getName());

        assertSame("Wizard.getPage() returned wrong page", wizard.getPage(wizard.page1Name), wizard.page1);

		wizard.setWindowTitle(WIZARD_TITLE);
        assertEquals("Wizard has wrong title", wizard.getWindowTitle(), WIZARD_TITLE);
        wizard.page1.setTitle(PAGE_TITLE);
        assertEquals("Wizard has wrong title", wizard.page1.getTitle(), PAGE_TITLE);

        wizard.setTitleBarColor(color1);
        assertEquals("Wizard has wrong title color", wizard.getTitleBarColor(), color1);
        wizard.setTitleBarColor(color2);
        assertEquals("Wizard has wrong title color", wizard.getTitleBarColor(), color2);

		assertSame("Wizard has wrong starting page", wizard.page1, wizard.getStartingPage());
		assertSame("Wizard not on starting page", wizard.page1, dialog.getCurrentPage());

        assertSame("WizardPage error message should be null", null, wizard.page1.getErrorMessage());
		wizard.page1.textInputField.setText(TheTestWizardPage.BAD_TEXT_FIELD_CONTENTS);
        assertEquals("WizardPage error message set correctly", TheTestWizardPage.BAD_TEXT_FIELD_STATUS, wizard.page1.getErrorMessage());

		wizard.page1.textInputField.setText(TheTestWizardPage.GOOD_TEXT_FIELD_CONTENTS);
        assertEquals("Page should be completed", true, wizard.page1.canFlipToNextPage());
        assertSame("WizardPage error message should be null", null, wizard.page1.getErrorMessage());

        assertSame("WizardPage.getNexPage() wrong page", wizard.page2, wizard.page1.getNextPage());
        assertSame("Wizard.getNexPage() wrong page", wizard.page2, wizard.getNextPage(wizard.page1));
        assertSame("WizardPage.getPreviousPage() wrong page", wizard.page1, wizard.page2.getPreviousPage());
        assertSame("Wizard.getPreviousPage() wrong page", wizard.page1, wizard.getPreviousPage(wizard.page2));
        assertSame("WizardPage.getNexPage() wrong page", wizard.page3, wizard.page2.getNextPage());
        assertSame("Wizard.getPreviousPage() wrong page", wizard.page2, wizard.getPreviousPage(wizard.page3));

        wizard.page2.textInputField.setText(TheTestWizardPage.BAD_TEXT_FIELD_CONTENTS);
        assertEquals("Wizard should not be able to finish", false, wizard.canFinish());
        wizard.page2.textInputField.setText(TheTestWizardPage.GOOD_TEXT_FIELD_CONTENTS);
        assertEquals("Wizard should be able to finish", true, wizard.canFinish());

		dialog.finishPressed();
        assertEquals("Wizard didn't perform finish", true, DID_FINISH);
	}

	public void testEndingWithCancel() {
		assertSame("Wizard not on starting page", wizard.page1, dialog.getCurrentPage());

		wizard.performCancel();
        assertEquals("Wizard finished but should not have", false, DID_FINISH);

        dialog.cancelPressed();
        assertEquals("Wizard performed finished but should not have", false, DID_FINISH);
	}

	public void testPageChanging() {
	    assertSame("Wizard started on wrong page", wizard.page1, dialog.getCurrentPage());
		assertEquals("Back button should be disabled on first page", false, dialog.getBackButton().getEnabled());
		assertEquals("Next button should be enabled on first page", true, dialog.getNextButton().getEnabled());

		dialog.nextPressed();
	    assertSame("Wizard.nextPressed() set wrong page", wizard.page2, dialog.getCurrentPage());
		assertEquals("Back button should be enabled on middle page", true, dialog.getBackButton().getEnabled());
		assertEquals("Next button should be enabled on middle page", true, dialog.getNextButton().getEnabled());

		wizard.page2.textInputField.setText(TheTestWizardPage.BAD_TEXT_FIELD_CONTENTS);
		assertEquals("Finish should be disabled when bad field value", false, dialog.getFinishedButton().getEnabled());
		assertEquals("Cancel should always be enabled", true, dialog.getCancelButton().getEnabled());

        wizard.page2.textInputField.setText(TheTestWizardPage.GOOD_TEXT_FIELD_CONTENTS);
		assertEquals("Finish should be enabled when good field value", true, dialog.getFinishedButton().getEnabled());

		dialog.nextPressed();
	    assertSame("Wizard.nextPressed() set wrong page", wizard.page3, dialog.getCurrentPage());
		assertEquals("Back button should be enabled on last page", true, dialog.getBackButton().getEnabled());
		assertEquals("Next button should be disenabled on last page", false, dialog.getNextButton().getEnabled());

		dialog.backPressed();
	    assertSame("Wizard.backPressed() set wrong page", wizard.page2, dialog.getCurrentPage());
		assertEquals("Back button should be enabled on middle page", true, dialog.getBackButton().getEnabled());
		assertEquals("Next button should be enabled on middle page", true, dialog.getNextButton().getEnabled());

		dialog.backPressed();
	    assertSame("Wizard.backPressed() set wrong page", wizard.page1, dialog.getCurrentPage());
		assertEquals("Back button should be disabled on first page", false, dialog.getBackButton().getEnabled());
		assertEquals("Next button should be enabled on first page", true, dialog.getNextButton().getEnabled());

		dialog.buttonPressed(IDialogConstants.NEXT_ID);
	    assertSame("Wizard.backPressed() set wrong page", wizard.page2, dialog.getCurrentPage());
		dialog.buttonPressed(IDialogConstants.BACK_ID);
	    assertSame("Wizard.backPressed() set wrong page", wizard.page1, dialog.getCurrentPage());
	}

	public void testShowPage() {
		dialog.nextPressed();
		dialog.nextPressed();
	    assertSame("Wizard.nextPressed() set wrong page", wizard.page3, dialog.getCurrentPage());

		dialog.showPage(wizard.page1);

		assertSame("Wizard.showPage() set wrong page", wizard.page1, dialog.getCurrentPage());

		assertEquals("Next button should be enabled on first page", true, dialog.getNextButton().getEnabled());
	}

	public void testPageChangeListening() {
		pageChanged = false;
		pageChangingFired = false;

		IPageChangedListener changedListener = new IPageChangedListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				pageChanged = true;
			}

		};

		IPageChangingListener changingListener = new IPageChangingListener() {
			@Override
			public void handlePageChanging(PageChangingEvent event) {
				assertEquals("Page should not have changed yet", false, pageChanged);
				pageChangingFired = true;
			}

		};

		dialog.addPageChangedListener(changedListener);
		dialog.addPageChangingListener(changingListener); //assert is in the listener
		assertEquals("Page change notified unintentially", false, pageChanged);
		dialog.nextPressed();
		assertEquals("Wasn't notified of page change", true, pageChanged);
		assertEquals("Wasn't notified of page changing", true, pageChangingFired);

		dialog.removePageChangingListener(changingListener); //if not removed, its assert will fail on next nextPressed()
		dialog.nextPressed();

		pageChanged = false;
		dialog.removePageChangedListener(changedListener);
		dialog.nextPressed();
		assertEquals("Page change notified unintentially", false, pageChanged);
	}


	public void testWizardDispose() {
		wizard.setThrowExceptionOnDispose(true);

		final boolean logged[] = new boolean[1];
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				logged[0] = true;
			}
		});
		Shell shell = dialog.getShell();
		dialog.close();

        assertTrue(logged[0]);

        shell.dispose();
	}

	public void testWizardPageDispose() {
        wizard.page2.setThrowExceptionOnDispose(true);
        final boolean logged[] = new boolean[1];
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				logged[0] = true;
			}
		});
        dialog.close();

        assertTrue(logged[0]);
        assertTrue(wizard.page1.getControl().isDisposed());
        assertTrue(wizard.page3.getControl().isDisposed());

	}



	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DID_FINISH = false;
		color1 = new RGB(255, 0, 0);
		color2 = new RGB(0, 255, 0);

		createWizardDialog();
	}

	@Override
	protected void tearDown() throws Exception {
		if(dialog.getShell() != null && ! dialog.getShell().isDisposed()) {
		    dialog.close();
		}
	}

	protected void createWizardDialog() {
		Display.getDefault();

		wizard = new TheTestWizard();
        dialog = new TheTestWizardDialog(null, wizard);
        dialog.create();

        dialog.open();
	}

}
