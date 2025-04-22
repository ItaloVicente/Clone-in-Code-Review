
package org.eclipse.ui.tests.statushandlers;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.Geometry;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.statushandlers.IStatusDialogConstants;
import org.eclipse.ui.internal.statushandlers.WorkbenchStatusDialogManagerImpl;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.statushandlers.AbstractStatusAreaProvider;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.statushandlers.WorkbenchErrorHandler;
import org.eclipse.ui.statushandlers.WorkbenchStatusDialogManager;

public class StatusDialogManagerTest extends TestCase {

	private static final String ACTION_NAME = "actionname";
	private static final String JOB_NAME = "jobname";
	private static final String THROWABLE = "throwable";
	private final static String MESSAGE_1 = "TEST_MESSAGE_1";
	private final static String MESSAGE_2 = "TEST_MESSAGE_2";
	private final static String TITLE = "TEST_TITLE";
	private final static NullPointerException NPE = new NullPointerException();
	private final static NullPointerException NPE_WITH_MESSAGE = new NullPointerException(
			THROWABLE);
	private final static String NPE_NAME = NPE.getClass().getName();

	private boolean automatedMode;
	WorkbenchStatusDialogManager wsdm;

	@Override
	protected void setUp() throws Exception {
		automatedMode = ErrorDialog.AUTOMATED_MODE;
		wsdm = new WorkbenchStatusDialogManager(null, null);
		ErrorDialog.AUTOMATED_MODE = false;
		wsdm.setProperty(IStatusDialogConstants.ANIMATION, Boolean.FALSE);
		super.setUp();
	}

	public void testBlockingAppearance() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), true);
		Shell shell = StatusDialogUtil.getStatusShell();
		assertNotNull(shell);
		assertTrue((shell.getStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL);
	}

	public void testNonBlockingAppearance() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		Shell shell = StatusDialogUtil.getStatusShell();
		assertNotNull(shell);
		assertFalse((shell.getStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL);
	}

	public void testModalitySwitch1() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		Shell shell = StatusDialogUtil.getStatusShell();
		assertNotNull(shell);
		assertFalse((shell.getStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL);

		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), true);
		shell = StatusDialogUtil.getStatusShell();
		assertNotNull(shell);
		assertTrue((shell.getStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL);
	}

	public void testCheckingForMessageDuplication1() {
		IStatus status = new IStatus() {

			@Override
			public IStatus[] getChildren() {
				return new IStatus[0];
			}

			@Override
			public int getCode() {
				return IStatus.ERROR;
			}

			@Override
			public Throwable getException() {
				return new ArrayIndexOutOfBoundsException();
			}

			@Override
			public String getMessage() {
				return null;
			}

			@Override
			public String getPlugin() {
				return "plugin";
			}

			@Override
			public int getSeverity() {
				return IStatus.ERROR;
			}

			@Override
			public boolean isMultiStatus() {
				return false;
			}

			@Override
			public boolean isOK() {
				return false;
			}

			@Override
			public boolean matches(int severityMask) {
				return true;
			}

		};
		wsdm.addStatusAdapter(new StatusAdapter(status), false);

		assertEquals(status.getException().getClass().getName(),
				StatusDialogUtil.getTitleLabel().getText());
		assertEquals(WorkbenchMessages.WorkbenchStatusDialog_SeeDetails,
				StatusDialogUtil.getSingleStatusLabel().getText());
	}

	public void testModalitySwitch2() {
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		final Composite[] details = new Composite[] { null };
		setupDetails(passed, details);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1);
		wsdm.addStatusAdapter(sa, false);

		selectWidget(StatusDialogUtil.getDetailsButton());
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), true);

		assertNotNull(details[0]);
		assertFalse(details[0].isDisposed());
		assertEquals(sa, passed[0]);
	}

	public void testModalitySwitch3() {
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		final Composite[] support = new Composite[] { null };
		setupSupportArea(passed, support);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1);
		wsdm.addStatusAdapter(sa, false);

		selectWidget(StatusDialogUtil.getSupportLink());
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), true);

		assertNotNull(support[0]);
		assertFalse(support[0].isDisposed());
		assertEquals(sa, passed[0]);
	}

	public void testModalitySwitch4() {
		final boolean[] disposed = new boolean[] { false };
		ITableLabelProvider provider = new ITableLabelProvider() {

			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			@Override
			public String getColumnText(Object element, int columnIndex) {
				return "";
			}

			@Override
			public void addListener(ILabelProviderListener listener) {
			}

			@Override
			public void dispose() {
				disposed[0] = true;
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

		};
		wsdm.setStatusListLabelProvider(provider);

		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), true);

		assertFalse(
				"Label provider should not be disposed during modality switch.",
				disposed[0]);

		selectWidget(StatusDialogUtil.getOkButton());
		assertTrue("Label should be disposed when the dialog is closed.",
				disposed[0]);
	}

	public void testWithStatusAdapter1() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(MESSAGE_1, titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(WorkbenchMessages.WorkbenchStatusDialog_SeeDetails,
				secondaryLabel.getText());

		Button actionButton = StatusDialogUtil.getActionButton();
		assertNotNull(actionButton);
		assertFalse(actionButton.isVisible());
		Object layoutData = actionButton.getLayoutData();
		assertTrue(layoutData instanceof GridData);
		assertTrue(((GridData) layoutData).exclude);
	}

	public void testWithStatusAdapterAndLabelProvider1(){
		wsdm.setMessageDecorator(new ILabelDecorator(){

			@Override
			public Image decorateImage(Image image, Object element) {
				return null;
			}

			@Override
			public String decorateText(String text, Object element) {
				return text.replaceAll("[A-Z][A-Z][A-Z][0-9][0-9]", "");
			}

			@Override
			public void addListener(ILabelProviderListener listener) {

			}

			@Override
			public void dispose() {

			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {

			}

		});
		wsdm.addStatusAdapter(createStatusAdapter("XYZ01" + MESSAGE_1), false);
		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(MESSAGE_1, titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(WorkbenchMessages.WorkbenchStatusDialog_SeeDetails,
				secondaryLabel.getText());
	}


	public void testWithStatusAdapter2() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1, TITLE),
				false);
		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(TITLE, titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(MESSAGE_1, secondaryLabel.getText());

		selectWidget(StatusDialogUtil.getOkButton());
		assertNull(StatusDialogUtil.getStatusShell());

		assertEquals(0, wsdm.getStatusAdapters().size());
	}

	public void testWithStatusAdapter3() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1,
				NPE_WITH_MESSAGE), false);
		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(MESSAGE_1, titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(THROWABLE, secondaryLabel.getText());
	}

	public void testWithStatusAdapter4() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1, NPE), false);

		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(MESSAGE_1, titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(NPE_NAME, secondaryLabel.getText());
	}

	public void testWithStatusAdapter5() {
		String message = "testmessage";
		StatusAdapter statusAdapter = new StatusAdapter(new Status(
				IStatus.ERROR, "testplugin", message));
		Job job = new Job("job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return null;
			}
		};
		statusAdapter.addAdapter(Job.class, job);
		wsdm.addStatusAdapter(statusAdapter, false);
		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(titleLabel.getText(), NLS.bind(
				WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurredInJob,
				job.getName()));
		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(secondaryLabel.getText(), message);
	}

	public void testWithStatusAdapter6() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1, JOB_NAME,
				ACTION_NAME), false);

		Label titleLabel = StatusDialogUtil.getTitleLabel();
		assertNotNull(titleLabel);
		assertEquals(NLS.bind(
				WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurredInJob,
				JOB_NAME), titleLabel.getText());

		Label secondaryLabel = StatusDialogUtil.getSingleStatusLabel();
		assertNotNull(secondaryLabel);
		assertEquals(MESSAGE_1, secondaryLabel.getText());

		Button actionButton = StatusDialogUtil.getActionButton();
		assertNotNull(actionButton);
		assertTrue(actionButton.isVisible());

		Object layoutData = actionButton.getLayoutData();
		assertTrue(layoutData instanceof GridData);
		assertFalse(((GridData) layoutData).exclude);
		assertEquals(ACTION_NAME, actionButton.getText());

		Link supportLink = StatusDialogUtil.getSupportLink();
		assertNull(supportLink);
	}

	public void testSupport1() {
		StatusAdapter statusAdapter = createStatusAdapter(MESSAGE_1);
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		Composite[] support = new Composite[] { null };
		setupSupportArea(passed, support);
		wsdm.addStatusAdapter(statusAdapter, false);
		openSupportArea(statusAdapter, passed);
	}

	public void testSupport2(){
		StatusAdapter statusAdapter = createStatusAdapter(MESSAGE_1);
		wsdm.setProperty(IStatusDialogConstants.SHOW_SUPPORT, Boolean.TRUE);
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		Composite[] support = new Composite[] { null };
		setupSupportArea(passed, support);
		wsdm.addStatusAdapter(statusAdapter, false);
		assertEquals(statusAdapter, passed[0]);
	}

	public void testDetails1() {
		StatusAdapter statusAdapter = createStatusAdapter(MESSAGE_1);
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		final Composite[] details = new Composite[] { null };
		setupDetails(passed, details);
		wsdm.addStatusAdapter(statusAdapter, false);
		for (int i = 0; i < 2; i++) {
			passed[0] = null;
			Point sizeBefore = StatusDialogUtil.getStatusShell().getSize();
			Button detailsButton = StatusDialogUtil.getDetailsButton();
			assertNotNull(detailsButton);
			assertTrue(detailsButton.isEnabled());
			assertEquals(IDialogConstants.SHOW_DETAILS_LABEL, detailsButton
					.getText());

			selectWidget(detailsButton);

			Point sizeAfter = StatusDialogUtil.getStatusShell().getSize();
			assertEquals(statusAdapter, passed[0]);
			assertTrue(sizeAfter.y > sizeBefore.y);
			assertEquals(IDialogConstants.HIDE_DETAILS_LABEL, detailsButton
					.getText());
			assertNotNull(details[0]);
			assertFalse(details[0].isDisposed());

			selectWidget(detailsButton);

			Point sizeAfterAfter = StatusDialogUtil.getStatusShell().getSize();
			assertTrue(sizeAfterAfter.y < sizeAfter.y);
			assertEquals(IDialogConstants.SHOW_DETAILS_LABEL, detailsButton
					.getText());
			assertTrue(details[0].isDisposed());
		}
	}

	public void testNullLabelProvider(){
		try {
			wsdm.setStatusListLabelProvider(null);
			fail();
		} catch (IllegalArgumentException iae){
			assertTrue(true);
		}
	}

	public void testNonNullLabelProvider(){
		try {
			final boolean [] called = new boolean[]{false};
			wsdm.setStatusListLabelProvider(new ITableLabelProvider(){

				@Override
				public Image getColumnImage(Object element, int columnIndex) {
					return null;
				}

				@Override
				public String getColumnText(Object element, int columnIndex) {
					called[0] = true;
					return "";
				}

				@Override
				public void addListener(ILabelProviderListener listener) {

				}

				@Override
				public void dispose() {

				}

				@Override
				public boolean isLabelProperty(Object element, String property) {
					return false;
				}

				@Override
				public void removeListener(ILabelProviderListener listener) {

				}

			});
			wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), true);
			assertTrue(called[0]);
		} catch (Exception e){
			fail();
		}
	}

	public void testList1() {
		StatusAdapter statusAdapter1 = createStatusAdapter(MESSAGE_1);
		StatusAdapter statusAdapter2 = createStatusAdapter(MESSAGE_2);

		StatusAdapter[] passed = new StatusAdapter[] { null };
		Composite[] support = new Composite[] { null };
		setupSupportArea(passed, support);

		wsdm.addStatusAdapter(statusAdapter1, false);
		wsdm.addStatusAdapter(statusAdapter2, false);

		Table table = StatusDialogUtil.getTable();
		assertNotNull(table);
		assertEquals(0, table.getSelectionIndex());
		assertEquals(MESSAGE_1, table.getItem(0).getText());
		assertEquals(MESSAGE_2, table.getItem(1).getText());

		openSupportArea(statusAdapter1, passed);
		selectTable(table, 1);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertEquals(statusAdapter2, passed[0]);
	}

	public void testList2() {
		StatusAdapter statusAdapter1 = createStatusAdapter(MESSAGE_1);
		StatusAdapter statusAdapter2 = createStatusAdapter(MESSAGE_2);

		Composite[] details = new Composite[] { null };
		StatusAdapter[] passed = new StatusAdapter[] { null };
		setupDetails(passed, details);

		wsdm.addStatusAdapter(statusAdapter1, false);
		wsdm.addStatusAdapter(statusAdapter2, false);

		selectWidget(StatusDialogUtil.getDetailsButton());
		assertNotNull(details[0]);
		assertFalse(details[0].isDisposed());
		assertEquals(statusAdapter1, passed[0]);

		Table table = StatusDialogUtil.getTable();
		selectTable(table, 1);

		assertNotNull(details[0]);
		assertFalse(details[0].isDisposed());
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}

		assertEquals(statusAdapter2, passed[0]);
	}

	public void testList3() {
		StatusAdapter sa1 = createStatusAdapter(MESSAGE_1);
		StatusAdapter sa2 = createStatusAdapter(MESSAGE_2, JOB_NAME,
				ACTION_NAME);

		wsdm.addStatusAdapter(sa1, false);
		wsdm.addStatusAdapter(sa2, false);

		Table table = StatusDialogUtil.getTable();
		Label titleLabel = StatusDialogUtil.getTitleLabel();

		assertEquals(WorkbenchMessages.WorkbenchStatusDialog_SeeDetails,
				titleLabel.getText());
		assertEquals(MESSAGE_1, table.getItem(0).getText());

		selectTable(table, 1);

		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}

		assertEquals(MESSAGE_2, titleLabel.getText());
		assertEquals(JOB_NAME, table.getItem(1).getText());
	}

	public void testBug260937(){
		WorkbenchStatusDialogManager wsdm = new WorkbenchStatusDialogManager(
				IStatus.CANCEL, null);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1);
		try {
			wsdm.addStatusAdapter(sa, false);
			assertTrue(true);
		} catch (NullPointerException npe){
			fail();
		}
	}

	public void testBug276371(){
		StatusAdapter bomb = new StatusAdapter(new Status(IStatus.ERROR,
				"org.eclipse.ui.tests", "bomb"){
			int i = 0;

			@Override
			public String getMessage() {
				i++;
				if (i == 1) {
					throw new RuntimeException("the bomb!");
				}
				return super.getMessage();

			}
		});
		try{
			wsdm.addStatusAdapter(bomb, false);
		} catch (Throwable t) {
			fail("no exception should be thrown");
		}
		assertTrue("Dialog should not display on failure",
				StatusDialogUtil.getStatusShell() == null);
		wsdm.addStatusAdapter(createStatusAdapter("normal one"), false);
		assertTrue("Dialog could not be initialized after failure",
				StatusDialogUtil.getStatusShell() != null);
	}

	public void testOKStatus1() {
		try {
			wsdm.addStatusAdapter(new StatusAdapter(Status.OK_STATUS), false);
			assertTrue(true);
		} catch (NullPointerException npe) {
			fail();
		}
		assertNull("Shell should not be created.", StatusDialogUtil
				.getStatusShell());
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		assertEquals(
				"Only one status should be visible (OK should be ignored)", 1,
				wsdm.getStatusAdapters().size());
	}

	public void testOKStatus2(){
		final WorkbenchStatusDialogManager wsdm[] = new WorkbenchStatusDialogManager[] { null };
		WorkbenchErrorHandler weh = new WorkbenchErrorHandler() {

			@Override
			protected void configureStatusDialog(
					WorkbenchStatusDialogManager statusDialog) {
				wsdm[0] = statusDialog;
				super.configureStatusDialog(statusDialog);
			}

		};
		weh.handle(new StatusAdapter(Status.OK_STATUS), StatusManager.SHOW);
		assertEquals(1, wsdm[0].getStatusAdapters().size());
	}

	public void testBug211933() {
		MultiStatus multi = new MultiStatus("testplugin", 0, "message", null);
		multi.add(new Status(IStatus.CANCEL, "testplugin", "message 1"));
		final WorkbenchStatusDialogManager wsdm[] = new WorkbenchStatusDialogManager[] { null };
		WorkbenchErrorHandler weh = new WorkbenchErrorHandler() {

			@Override
			protected void configureStatusDialog(
					WorkbenchStatusDialogManager statusDialog) {
				wsdm[0] = statusDialog;
				super.configureStatusDialog(statusDialog);
			}

		};
		StatusAdapter sa = new StatusAdapter(multi);
		weh.handle(sa, StatusManager.SHOW);
		assertTrue(wsdm[0].getStatusAdapters().contains(sa));
	}

	public void testBug275867(){
		StatusAdapter statusAdapter = createStatusAdapter(MESSAGE_1);
		final StatusAdapter[] passed = new StatusAdapter[] { null };
		Composite[] support = new Composite[] { null };
		setupSupportArea(passed, support);
		wsdm.setProperty(IStatusDialogConstants.SHOW_SUPPORT, Boolean.TRUE);
		wsdm.addStatusAdapter(statusAdapter, false);
		assertNotNull(StatusDialogUtil.getStatusShell());
		Shell shell = StatusDialogUtil.getStatusShell();
		assertEquals("Dialog is not centered correctly",getInitialLocation(shell), shell.getLocation());
	}

	public void testBug278965_1(){
		final WorkbenchStatusDialogManager wsdm[] = new WorkbenchStatusDialogManager[] { null };
		WorkbenchErrorHandler weh = new WorkbenchErrorHandler() {

			@Override
			protected void configureStatusDialog(
					WorkbenchStatusDialogManager statusDialog) {
				wsdm[0] = statusDialog;
				super.configureStatusDialog(statusDialog);
			}

		};
		weh.handle(createStatusAdapter(MESSAGE_1), StatusManager.SHOW | StatusManager.LOG);
		assertEquals(1, wsdm[0].getStatusAdapters().size());
		assertNotNull("Link to error log should be present", StatusDialogUtil
				.getErrorLogLink());
		assertFalse("Link to error log should not be disposed",
				StatusDialogUtil.getErrorLogLink().isDisposed());
		assertTrue("Link to error log should be enabled",
				StatusDialogUtil.getErrorLogLink().isEnabled());
		assertTrue("Link to error log should be visible",
				StatusDialogUtil.getErrorLogLink().isVisible());
	}

	public void testBug278965_2(){
		final WorkbenchStatusDialogManager wsdm[] = new WorkbenchStatusDialogManager[] { null };
		WorkbenchErrorHandler weh = new WorkbenchErrorHandler() {

			@Override
			protected void configureStatusDialog(
					WorkbenchStatusDialogManager statusDialog) {
				wsdm[0] = statusDialog;
				super.configureStatusDialog(statusDialog);
			}

		};
		weh.handle(createStatusAdapter(MESSAGE_1), StatusManager.SHOW);
		assertEquals(1, wsdm[0].getStatusAdapters().size());
		boolean status = StatusDialogUtil.getErrorLogLink() == null
				|| StatusDialogUtil.getErrorLogLink().isDisposed()
				|| !StatusDialogUtil.getErrorLogLink().isVisible();
		assertTrue("Error log link should be null, disposed or invisible", status);
	}

	public void testBug278965_3(){
		final WorkbenchStatusDialogManager wsdm[] = new WorkbenchStatusDialogManager[] { null };
		WorkbenchErrorHandler weh = new WorkbenchErrorHandler() {

			@Override
			protected void configureStatusDialog(
					WorkbenchStatusDialogManager statusDialog) {
				wsdm[0] = statusDialog;
				super.configureStatusDialog(statusDialog);
			}

		};
		weh.handle(createStatusAdapter(MESSAGE_1), StatusManager.SHOW);
		weh.handle(createStatusAdapter(MESSAGE_2), StatusManager.SHOW | StatusManager.LOG);
		assertNotNull("Link to error log should be present", StatusDialogUtil
				.getErrorLogLink());
		assertFalse("Link to error log should not be disposed",
				StatusDialogUtil.getErrorLogLink().isDisposed());
		assertTrue("Link to error log should be enabled",
				StatusDialogUtil.getErrorLogLink().isEnabled());
		assertTrue("Link to error log should be visible",
				StatusDialogUtil.getErrorLogLink().isVisible());
	}

	public void testBug288770_1(){
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), false);
		assertTrue("Details should be closed initially", StatusDialogUtil
				.getDetailsButton().getText().equals(
						IDialogConstants.SHOW_DETAILS_LABEL));
		assertTrue("The list should be visible", StatusDialogUtil.getTable() != null);
		selectWidget(StatusDialogUtil.getDetailsButton());
		int height = StatusDialogUtil.getTable().getSize().y;
		Shell statusShell = StatusDialogUtil.getStatusShell();
		Point shellSize = statusShell.getSize();
		statusShell.setSize(shellSize.x, shellSize.y + 100);
		statusShell.layout(true);
		int newHeight = StatusDialogUtil.getTable().getSize().y;
		assertEquals("All height should be consumed by details", height,
				newHeight);
	}

	public void testBug288770_2(){
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		assertTrue("Details should be closed initially", StatusDialogUtil
				.getDetailsButton().getText().equals(
						IDialogConstants.SHOW_DETAILS_LABEL));
		selectWidget(StatusDialogUtil.getDetailsButton());
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), false);
		assertTrue("The list should be visible", StatusDialogUtil.getTable() != null);
		int height = StatusDialogUtil.getTable().getSize().y;
		Shell statusShell = StatusDialogUtil.getStatusShell();
		Point shellSize = statusShell.getSize();
		statusShell.setSize(shellSize.x, shellSize.y + 100);
		statusShell.layout(true);
		int newHeight = StatusDialogUtil.getTable().getSize().y;
		assertEquals("All height should be consumed by details", height,
				newHeight);
	}

	public void testBug288770_3(){
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		assertTrue("Details should be closed initially", StatusDialogUtil
				.getDetailsButton().getText().equals(
						IDialogConstants.SHOW_DETAILS_LABEL));
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), false);
		assertTrue("The list should be visible", StatusDialogUtil.getTable() != null);
		int height = StatusDialogUtil.getTable().getSize().y;
		Shell statusShell = StatusDialogUtil.getStatusShell();
		Point shellSize = statusShell.getSize();
		statusShell.setSize(shellSize.x, shellSize.y + 100);
		statusShell.layout(true);
		int newHeight = StatusDialogUtil.getTable().getSize().y;
		assertTrue("List should resize when details are closed", height < newHeight);
	}

	public void testBug288770_4(){
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		assertTrue("Details should be closed initially", StatusDialogUtil
				.getDetailsButton().getText().equals(
						IDialogConstants.SHOW_DETAILS_LABEL));
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), false);
		selectWidget(StatusDialogUtil.getDetailsButton());
		selectWidget(StatusDialogUtil.getDetailsButton());
		assertTrue("Details should be closed", StatusDialogUtil
				.getDetailsButton().getText().equals(
						IDialogConstants.SHOW_DETAILS_LABEL));
		assertTrue("The list should be visible", StatusDialogUtil.getTable() != null);
		int height = StatusDialogUtil.getTable().getSize().y;
		Shell statusShell = StatusDialogUtil.getStatusShell();
		Point shellSize = statusShell.getSize();
		statusShell.setSize(shellSize.x, shellSize.y + 100);
		statusShell.layout(true);
		int newHeight = StatusDialogUtil.getTable().getSize().y;
		assertTrue("List should resize when details are closed", height < newHeight);
	}

	public void testBug288765() {
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		selectWidget(StatusDialogUtil.getDetailsButton());
		int sizeY = StatusDialogUtil.getStatusShell().getSize().y;
		selectWidget(StatusDialogUtil.getOkButton());
		MultiStatus ms = new MultiStatus("org.eclipse.ui.tests", 0, MESSAGE_1,
				null);
		for (int i = 0; i < 50; i++) {
			ms
					.add(new Status(IStatus.ERROR, "org.eclipse.ui.tests",
							MESSAGE_2));
		}
		wsdm.addStatusAdapter(new StatusAdapter(ms), false);
		selectWidget(StatusDialogUtil.getDetailsButton());
		Shell shell = StatusDialogUtil.getStatusShell();
		Rectangle newSize = shell.getBounds();
		assertTrue(newSize.height > sizeY);
	}

	public void testIgnoringOpenTrayOnShow() {
		wsdm.enableDefaultSupportArea(true);
		wsdm.enableErrorDialogCompatibility();
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1), false);
		WorkbenchStatusDialogManagerImpl manager = (WorkbenchStatusDialogManagerImpl) wsdm
				.getProperty(IStatusDialogConstants.MANAGER_IMPL);
		assertNull("Tray should not be opened", manager.getDialog().getTray());

		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2,
				new NullPointerException()), false);

		Table table = StatusDialogUtil.getTable();
		selectTable(table, 1);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertNotNull("Tray should be opened", manager.getDialog().getTray());

		selectTable(table, 0);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertNull("Tray should not be opened", manager.getDialog().getTray());
	}

	public void testAutoOpeningTrayOnShow() {
		wsdm.enableDefaultSupportArea(true);
		wsdm.enableErrorDialogCompatibility();
		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_1, new NullPointerException()), false);
		WorkbenchStatusDialogManagerImpl manager = (WorkbenchStatusDialogManagerImpl) wsdm
				.getProperty(IStatusDialogConstants.MANAGER_IMPL);
		assertNotNull("Tray should be opened", manager.getDialog().getTray());
	}

	public void testModalitySwitch5() {
		wsdm.enableDefaultSupportArea(true);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1, new NullPointerException());
		wsdm.addStatusAdapter(sa, false);

		assertNotNull(StatusDialogUtil.getSupportLink());
		assertTrue(StatusDialogUtil.getSupportLink().isEnabled());
		selectWidget(StatusDialogUtil.getSupportLink());
		WorkbenchStatusDialogManagerImpl manager = (WorkbenchStatusDialogManagerImpl) wsdm
				.getProperty(IStatusDialogConstants.MANAGER_IMPL);
		assertNotNull("Tray should be opened", manager.getDialog().getTray());

		wsdm.addStatusAdapter(createStatusAdapter(MESSAGE_2), true);
		assertNotNull("Tray should be opened", manager.getDialog().getTray());

		Table table = StatusDialogUtil.getTable();
		selectTable(table, 1);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertNull("Tray should not be opened", manager.getDialog().getTray());
		assertNull(StatusDialogUtil.getSupportLink());
	}

	public void testSupportLinkVisibility1(){
		wsdm.enableDefaultSupportArea(true);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1, new NullPointerException());
		wsdm.addStatusAdapter(sa, false);
		assertNotNull(StatusDialogUtil.getSupportLink());
		StatusAdapter sa2 = createStatusAdapter(MESSAGE_2);
		wsdm.addStatusAdapter(sa2, false);
		Table table = StatusDialogUtil.getTable();
		selectTable(table, 1);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertNull(StatusDialogUtil.getSupportLink());
	}

	public void testSupportLinkVisibility2() {
		wsdm.enableDefaultSupportArea(true);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1);
		wsdm.addStatusAdapter(sa, false);
		assertNull(StatusDialogUtil.getSupportLink());
		StatusAdapter sa2 = createStatusAdapter(MESSAGE_2, new NullPointerException());
		wsdm.addStatusAdapter(sa2, false);
		Table table = StatusDialogUtil.getTable();
		selectTable(table, 1);
		while(Display.getCurrent().readAndDispatch() && (StatusDialogUtil.getStatusShell() != null)) {
			;
		}
		assertNotNull(StatusDialogUtil.getSupportLink());
	}

	public void testProvidingCustomSupportAreaProvider() {
		final boolean[] consulted = new boolean[]{false};
		AbstractStatusAreaProvider customProvider = new AbstractStatusAreaProvider() {

			@Override
			public Control createSupportArea(Composite parent,
					StatusAdapter statusAdapter) {
				return null;
			}

			@Override
			public boolean validFor(StatusAdapter statusAdapter) {
				consulted[0] = true;
				return false;
			}
		};

		wsdm.setSupportAreaProvider(customProvider);
		StatusAdapter sa = createStatusAdapter(MESSAGE_1);
		wsdm.addStatusAdapter(sa, false);
		assertTrue("Custom support area provider should be consulted", consulted[0]);
	}


	private void setupSupportArea(final StatusAdapter[] passed,
			final Composite[] support) {
		Policy.setErrorSupportProvider(new AbstractStatusAreaProvider() {
			@Override
			public Control createSupportArea(Composite parent,
					StatusAdapter statusAdapter) {
				passed[0] = statusAdapter;
				Composite c = new Composite(parent, SWT.NONE);
				GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true,
						true);
				layoutData.minimumHeight = 400;
				c.setLayoutData(layoutData);
				support[0] = c;
				return c;
			}
		});
	}

	private void openSupportArea(StatusAdapter statusAdapter,
			final StatusAdapter[] passed) {
		Point sizeBefore = StatusDialogUtil.getStatusShell().getSize();
		Link supportLink = StatusDialogUtil.getSupportLink();
		assertNotNull(supportLink);
		assertTrue(supportLink.isEnabled());

		selectWidget(supportLink);
		Point sizeAfter = StatusDialogUtil.getStatusShell().getSize();
		assertEquals(statusAdapter, passed[0]);
		assertTrue(sizeAfter.x > sizeBefore.x);
		assertTrue(sizeAfter.y > sizeBefore.y);
	}

	private void setupDetails(final StatusAdapter[] passed,
			final Composite[] details) {
		wsdm.setDetailsAreaProvider(new AbstractStatusAreaProvider() {
			@Override
			public Control createSupportArea(Composite parent,
					StatusAdapter statusAdapter) {
				passed[0] = statusAdapter;
				Composite c = new Composite(parent, SWT.NONE);
				GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true,
						true);
				layoutData.minimumHeight = 400;
				c.setLayoutData(layoutData);
				details[0] = c;
				return c;
			}
		});
	}

	private void selectTable(Table table, int i) {
		table.setSelection(i);
		Event event = new Event();
		event.item = table.getItem(i);
		table.notifyListeners(SWT.Selection, event);
	}

	private void selectWidget(Widget control) {
		Event event = new Event();
		event.item = control;
		control.notifyListeners(SWT.Selection, event);
	}

	private StatusAdapter createStatusAdapter(String message) {
		return new StatusAdapter(new Status(IStatus.ERROR,
				"org.eclipse.ui.tests", message));
	}

	private StatusAdapter createStatusAdapter(String message,
			Throwable throwable) {
		return new StatusAdapter(new Status(IStatus.ERROR,
				"org.eclipse.ui.tests", message, throwable));
	}

	private StatusAdapter createStatusAdapter(String message, String jobname,
			String actionName) {
		StatusAdapter sa = createStatusAdapter(message);
		if (jobname == null) {
			return sa;
		}
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return null;
			}
		};
		sa.addAdapter(Job.class, job);
		if (actionName == null) {
			return sa;
		}
		Action action = new Action(actionName) {
		};
		job.setProperty(IProgressConstants.ACTION_PROPERTY, action);
		return sa;
	}

	private StatusAdapter createStatusAdapter(String message, String title) {
		StatusAdapter sa = createStatusAdapter(message);
		sa.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, title);
		return sa;
	}

	@Override
	protected void tearDown() throws Exception {
		wsdm = null;
		Shell shell = StatusDialogUtil.getStatusShell();
		if (shell != null) {
			shell.dispose();
		}
		ErrorDialog.AUTOMATED_MODE = automatedMode;
		Policy.setErrorSupportProvider(null);
		super.tearDown();
	}

	private Point getInitialLocation(Shell shell){
		Point initialSize = shell.getSize();
		Composite parent = shell.getParent();

		Monitor monitor = shell.getDisplay().getPrimaryMonitor();
		if (parent != null) {
			monitor = parent.getMonitor();
		}

		Rectangle monitorBounds = monitor.getClientArea();
		Point centerPoint;
		if (parent != null) {
			centerPoint = Geometry.centerPoint(parent.getBounds());
		} else {
			centerPoint = Geometry.centerPoint(monitorBounds);
		}

		return new Point(centerPoint.x - (initialSize.x / 2), Math.max(
				monitorBounds.y, Math.min(centerPoint.y
						- (initialSize.y * 2 / 3), monitorBounds.y
						+ monitorBounds.height - initialSize.y)));
	}
}
