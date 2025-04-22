/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Chris Gross (schtoo@schtoo.com) - patch for bug 16179
 *     Eugene Ostroukhov <eugeneo@symbian.org> - Bug 287887 [Wizards] [api] Cancel button has two distinct roles
 *     Paul Adams <padams@ittvis.com> - Bug 202534 - [Dialogs] SWT error in Wizard dialog when help is displayed and "Finish" is pressed
 *     Jan-Ove Weichel <janove.weichel@vogella.com> - Bug 475879
 *******************************************************************************/
package org.eclipse.jface.wizard;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog to show a wizard to the end user.
 * <p>
 * In typical usage, the client instantiates this class with a particular
 * wizard. The dialog serves as the wizard container and orchestrates the
 * presentation of its pages.
 * <p>
 * The standard layout is roughly as follows: it has an area at the top
 * containing both the wizard's title, description, and image; the actual wizard
 * page appears in the middle; below that is a progress indicator (which is made
 * visible if needed); and at the bottom of the page is message line and a
 * button bar containing Help, Next, Back, Finish, and Cancel buttons (or some
 * subset).
 * </p>
 * <p>
 * Clients may subclass <code>WizardDialog</code>, although this is rarely
 * required.
 * </p>
 */
public class WizardDialog extends TitleAreaDialog implements IWizardContainer2, IPageChangeProvider {
	/**
	 * Image registry key for error message image (value
	 * <code>"dialog_title_error_image"</code>).
	 */

	private IWizard wizard;

	private ArrayList<IWizard> createdWizards = new ArrayList<>();

	private ArrayList<IWizard> nestedWizards = new ArrayList<>();

	private IWizardPage currentPage = null;

	private long activeRunningOperations = 0;

	/**
	 * The time in milliseconds where the last job finished. 'Enter' key presses are ignored for the
	 * next {@link #RESTORE_ENTER_DELAY} milliseconds.
	 * <p>
	 * The value <code>-1</code> indicates that the traverse listener needs to be installed.
	 * </p>
	 *
	 * @since 3.6
	 */
	private long timeWhenLastJobFinished= -1;

	private boolean useCustomProgressMonitorPart= true;

	private String pageMessage;

	private int pageMessageType = IMessageProvider.NONE;

	private String pageDescription;

	private ProgressMonitorPart progressMonitorPart;

	private Cursor waitCursor;

	private Cursor arrowCursor;

	private MessageDialog windowClosingDialog;

	private Button backButton;

	private Button nextButton;

	private Button finishButton;

	private Button cancelButton;

	private Button helpButton;

	private SelectionListener cancelListener;

	private boolean isMovingToPreviousPage = false;

	private Composite pageContainer;

	private PageContainerFillLayout pageContainerLayout = new PageContainerFillLayout(5, 5, 300, 225);

	private int pageWidth = SWT.DEFAULT;

	private int pageHeight = SWT.DEFAULT;


	/**
	 * A delay in milliseconds that reduces the risk that the user accidentally triggers a
	 * button by pressing the 'Enter' key immediately after a job has finished.
	 *
	 * @since 3.6
	 */
	private static final int RESTORE_ENTER_DELAY= 500;

	private boolean lockedUI = false;

	private ListenerList<IPageChangedListener> pageChangedListeners = new ListenerList<>();

	private ListenerList<IPageChangingListener> pageChangingListeners = new ListenerList<>();

	/**
	 * A layout for a container which includes several pages, like a notebook,
	 * wizard, or preference dialog. The size computed by this layout is the
	 * maximum width and height of all pages currently inserted into the
	 * container.
	 */
	protected class PageContainerFillLayout extends Layout {
		/**
		 * The margin width; <code>5</code> pixels by default.
		 */
		public int marginWidth = 5;

		/**
		 * The margin height; <code>5</code> pixels by default.
		 */
		public int marginHeight = 5;

		/**
		 * The minimum width; <code>0</code> pixels by default.
		 */
		public int minimumWidth = 0;

		/**
		 * The minimum height; <code>0</code> pixels by default.
		 */
		public int minimumHeight = 0;

		/**
		 * Creates new layout object.
		 *
		 * @param mw
		 *            the margin width
		 * @param mh
		 *            the margin height
		 * @param minW
		 *            the minimum width
		 * @param minH
		 *            the minimum height
		 */
		public PageContainerFillLayout(int mw, int mh, int minW, int minH) {
			marginWidth = mw;
			marginHeight = mh;
			minimumWidth = minW;
			minimumHeight = minH;
		}

		@Override
		public Point computeSize(Composite composite, int wHint, int hHint,
				boolean force) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}
			Point result = null;
			Control[] children = composite.getChildren();
			if (children.length > 0) {
				result = new Point(0, 0);
				for (Control element : children) {
					Point cp = element.computeSize(wHint, hHint, force);
					result.x = Math.max(result.x, cp.x);
					result.y = Math.max(result.y, cp.y);
				}
				result.x = result.x + 2 * marginWidth;
				result.y = result.y + 2 * marginHeight;
			} else {
				Rectangle rect = composite.getClientArea();
				result = new Point(rect.width, rect.height);
			}
			result.x = Math.max(result.x, minimumWidth);
			result.y = Math.max(result.y, minimumHeight);
			if (wHint != SWT.DEFAULT) {
				result.x = wHint;
			}
			if (hHint != SWT.DEFAULT) {
				result.y = hHint;
			}
			return result;
		}

		/**
		 * Returns the client area for the given composite according to this
		 * layout.
		 *
		 * @param c
		 *            the composite
		 * @return the client area rectangle
		 */
		public Rectangle getClientArea(Composite c) {
			Rectangle rect = c.getClientArea();
			rect.x = rect.x + marginWidth;
			rect.y = rect.y + marginHeight;
			rect.width = rect.width - 2 * marginWidth;
			rect.height = rect.height - 2 * marginHeight;
			return rect;
		}

		@Override
		public void layout(Composite composite, boolean force) {
			Rectangle rect = getClientArea(composite);
			Control[] children = composite.getChildren();
			for (Control element : children) {
				element.setBounds(rect);
			}
		}

		/**
		 * Lays outs the page according to this layout.
		 *
		 * @param w
		 *            the control
		 */
		public void layoutPage(Control w) {
			w.setBounds(getClientArea(w.getParent()));
		}

		/**
		 * Sets the location of the page so that its origin is in the upper left
		 * corner.
		 *
		 * @param w
		 *            the control
		 */
		public void setPageLocation(Control w) {
			w.setLocation(marginWidth, marginHeight);
		}
	}

	/**
	 * Creates a new wizard dialog for the given wizard.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param newWizard
	 *            the wizard this dialog is working on
	 */
	public WizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER
				| SWT.APPLICATION_MODAL | SWT.RESIZE | getDefaultOrientation());
		setWizard(newWizard);
		cancelListener = widgetSelectedAdapter(e -> cancelPressed());
	}

	/**
	 * About to start a long running operation triggered through the wizard.
	 * Shows the progress monitor and disables the wizard's buttons and
	 * controls.
	 *
	 * @param enableCancelButton
	 *            <code>true</code> if the Cancel button should be enabled,
	 *            and <code>false</code> if it should be disabled
	 * @return the saved UI state
	 */
	private Object aboutToStart(boolean enableCancelButton) {
		Map<String, Object> savedState = null;
		if (getShell() != null) {
			Control focusControl = getShell().getDisplay().getFocusControl();
			if (focusControl != null && focusControl.getShell() != getShell()) {
				focusControl = null;
			}
			boolean needsProgressMonitor = wizard.needsProgressMonitor();

			Display d = getShell().getDisplay();
			waitCursor = new Cursor(d, SWT.CURSOR_WAIT);
			setDisplayCursor(waitCursor);

			if (useCustomProgressMonitorPart) {
				cancelButton.removeSelectionListener(cancelListener);
				arrowCursor = new Cursor(d, SWT.CURSOR_ARROW);
				cancelButton.setCursor(arrowCursor);
			}

			savedState = saveUIState(useCustomProgressMonitorPart && needsProgressMonitor && enableCancelButton);
			if (focusControl != null) {
				savedState.put(FOCUS_CONTROL, focusControl);
			}
			if (needsProgressMonitor) {
				if (enableCancelButton || useCustomProgressMonitorPart) {
					progressMonitorPart.attachToCancelComponent(cancelButton);
				}
				progressMonitorPart.setVisible(true);
			}

			if (timeWhenLastJobFinished == -1) {
				timeWhenLastJobFinished= 0;
				getShell().addTraverseListener(e -> {
					if (e.detail == SWT.TRAVERSE_RETURN || (e.detail == SWT.TRAVERSE_MNEMONIC && e.keyCode == 32)) {
						if (timeWhenLastJobFinished != 0 && System.currentTimeMillis() - timeWhenLastJobFinished < RESTORE_ENTER_DELAY) {
							e.doit= false;
							return;
						}
						timeWhenLastJobFinished= 0;
					}});
			}
		}
		return savedState;
	}

	/**
	 * The Back button has been pressed.
	 */
	protected void backPressed() {
		IWizardPage page = currentPage.getPreviousPage();
		if (page == null) {
			return;
		}

		isMovingToPreviousPage = true;
		showPage(page);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.HELP_ID: {
			helpPressed();
			break;
		}
		case IDialogConstants.BACK_ID: {
			backPressed();
			break;
		}
		case IDialogConstants.NEXT_ID: {
			nextPressed();
			break;
		}
		case IDialogConstants.FINISH_ID: {
			finishPressed();
			break;
		}
		}
	}

	/**
	 * Calculates the difference in size between the given page and the page
	 * container. A larger page results in a positive delta.
	 *
	 * @param page
	 *            the page
	 * @return the size difference encoded as a
	 *         <code>new Point(deltaWidth,deltaHeight)</code>
	 */
	private Point calculatePageSizeDelta(IWizardPage page) {
		Control pageControl = page.getControl();
		if (pageControl == null) {
			return new Point(0, 0);
		}
		Point contentSize = pageControl.computeSize(SWT.DEFAULT, SWT.DEFAULT,
				true);
		Rectangle rect = pageContainerLayout.getClientArea(pageContainer);
		Point containerSize = new Point(rect.width, rect.height);
		return new Point(Math.max(0, contentSize.x - containerSize.x), Math
				.max(0, contentSize.y - containerSize.y));
	}

	@Override
	protected void cancelPressed() {
		if (activeRunningOperations <= 0) {
			setReturnCode(CANCEL);
			close();
		} else {
			cancelButton.setEnabled(false);
		}
	}

	@Override
	public boolean close() {
		if (okToClose()) {
			return hardClose();
		}
		return false;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.addHelpListener(event -> {
			if (currentPage != null) {
				currentPage.performHelp();
			}
		});
	}

	/**
	 * Creates the buttons for this dialog's button bar.
	 * <p>
	 * The <code>WizardDialog</code> implementation of this framework method
	 * prevents the parent composite's columns from being made equal width in
	 * order to remove the margin between the Back and Next buttons.
	 * </p>
	 *
	 * @param parent
	 *            the parent composite to contain the buttons
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).makeColumnsEqualWidth = false;
		if (wizard.isHelpAvailable()) {
			helpButton = createButton(parent, IDialogConstants.HELP_ID, IDialogConstants.HELP_LABEL, false);
		}
		if (wizard.needsPreviousAndNextButtons()) {
			createPreviousAndNextButtons(parent);
		}
		finishButton = createButton(parent, IDialogConstants.FINISH_ID, IDialogConstants.FINISH_LABEL, true);
		cancelButton = createCancelButton(parent);

		if (parent.getDisplay().getDismissalAlignment() == SWT.RIGHT) {
			finishButton.moveBelow(null);
		}
	}

	@Override
	protected void setButtonLayoutData(Button button) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);

		widthHint = Math.min(widthHint,
				button.getDisplay().getBounds().width / 5);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);

		button.setLayoutData(data);
	}

	/**
	 * Creates the Cancel button for this wizard dialog. Creates a standard (<code>SWT.PUSH</code>)
	 * button and registers for its selection events. Note that the number of
	 * columns in the button bar composite is incremented. The Cancel button is
	 * created specially to give it a removeable listener.
	 *
	 * @param parent
	 *            the parent button bar
	 * @return the new Cancel button
	 */
	private Button createCancelButton(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(IDialogConstants.CANCEL_LABEL);
		setButtonLayoutData(button);
		button.setFont(parent.getFont());
		button.setData(Integer.valueOf(IDialogConstants.CANCEL_ID));
		button.addSelectionListener(cancelListener);
		return button;
	}

	/**
	 * Return the cancel button if the id is a the cancel id.
	 *
	 * @param id
	 *            the button id
	 * @return the button corresponding to the button id
	 */
	@Override
	protected Button getButton(int id) {
		if (id == IDialogConstants.CANCEL_ID) {
			return cancelButton;
		}
		return super.getButton(id);
	}

	/**
	 * The <code>WizardDialog</code> implementation of this
	 * <code>Window</code> method calls call <code>IWizard.addPages</code>
	 * to allow the current wizard to add extra pages, then
	 * <code>super.createContents</code> to create the controls. It then calls
	 * <code>IWizard.createPageControls</code> to allow the wizard to
	 * pre-create their page controls prior to opening, so that the wizard opens
	 * to the correct size. And finally it shows the first page.
	 */
	@Override
	protected Control createContents(Composite parent) {
		wizard.addPages();
		Control contents = super.createContents(parent);
		createPageControls();
		showStartingPage();
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		pageContainer = createPageContainer(composite);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = pageWidth;
		gd.heightHint = pageHeight;
		pageContainer.setLayoutData(gd);
		pageContainer.setFont(parent.getFont());
		progressMonitorPart= createProgressMonitorPart(composite, new GridLayout());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		if (!wizard.needsProgressMonitor()) {
			gridData.exclude = true;
		}
		progressMonitorPart.setLayoutData(gridData);
		progressMonitorPart.setVisible(false);
		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		applyDialogFont(progressMonitorPart);
		return composite;
	}

	/**
	 * Hook method for subclasses to create a custom progress monitor part.
	 * <p>
	 * The default implementation creates a progress monitor with a stop button will be created.
	 * </p>
	 *
	 * @param composite the parent composite
	 * @param pmlayout the layout
	 * @return ProgressMonitorPart the progress monitor part
	 */
	protected ProgressMonitorPart createProgressMonitorPart(
			Composite composite, GridLayout pmlayout) {
		useCustomProgressMonitorPart= false;
		return new ProgressMonitorPart(composite, pmlayout, true) {
			String currentTask = null;

			@Override
			public void setBlocked(IStatus reason) {
				super.setBlocked(reason);
				if (!lockedUI) {
					getBlockedHandler().showBlocked(getShell(), this, reason,
							currentTask);
				}
			}

			@Override
			public void clearBlocked() {
				super.clearBlocked();
				if (!lockedUI) {
					getBlockedHandler().clearBlocked();
				}
			}

			@Override
			public void beginTask(String name, int totalWork) {
				super.beginTask(name, totalWork);
				currentTask = name;
			}

			@Override
			public void setTaskName(String name) {
				super.setTaskName(name);
				currentTask = name;
			}

			@Override
			public void subTask(String name) {
				super.subTask(name);
				if (currentTask == null) {
					currentTask = name;
				}
			}
		};
	}

	/**
	 * Creates the container that holds all pages.
	 *
	 * @param parent
	 * @return Composite
	 */
	private Composite createPageContainer(Composite parent) {
		Composite result = new Composite(parent, SWT.NULL);
		result.setLayout(pageContainerLayout);
		return result;
	}

	/**
	 * Allow the wizard's pages to pre-create their page controls. This allows
	 * the wizard dialog to open to the correct size.
	 */
	private void createPageControls() {
		wizard.createPageControls(pageContainer);
		IWizardPage[] pages = wizard.getPages();
		for (IWizardPage page : pages) {
			if (page.getControl() != null) {
				page.getControl().setVisible(false);
			}
		}
	}

	/**
	 * Creates the Previous and Next buttons for this wizard dialog. Creates
	 * standard (<code>SWT.PUSH</code>) buttons and registers for their
	 * selection events. Note that the number of columns in the button bar
	 * composite is incremented. These buttons are created specially to prevent
	 * any space between them.
	 *
	 * @param parent
	 *            the parent button bar
	 * @return a composite containing the new buttons
	 */
	private Composite createPreviousAndNextButtons(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		backButton = createButton(composite, IDialogConstants.BACK_ID, IDialogConstants.BACK_LABEL, false);
		nextButton = createButton(composite, IDialogConstants.NEXT_ID, IDialogConstants.NEXT_LABEL, false);

		final String backReaderText = IDialogConstants.BACK_LABEL.replace('<', ' ');
		backButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result = backReaderText;
			}
		});
		final String nextReaderText = IDialogConstants.NEXT_LABEL.replace('>', ' ');
		nextButton.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result = nextReaderText;
			}
		});
		return composite;
	}

	/**
	 * Creates and return a new wizard closing dialog without opening it.
	 *
	 * @return MessageDalog
	 */
	private MessageDialog createWizardClosingDialog() {
		MessageDialog result = new MessageDialog(getShell(),
				JFaceResources.getString("WizardClosingDialog.title"), //$NON-NLS-1$
				null,
				JFaceResources.getString("WizardClosingDialog.message"), //$NON-NLS-1$
				MessageDialog.QUESTION,
				0, IDialogConstants.OK_LABEL) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		return result;
	}

	/**
	 * The Finish button has been pressed.
	 */
	protected void finishPressed() {
		if (wizard.performFinish()) {
			for (int i = 0; i < nestedWizards.size() - 1; i++) {
				nestedWizards.get(i).performFinish();
			}
			setReturnCode(OK);
			hardClose();
		}
	}

	@Override
	public IWizardPage getCurrentPage() {
		return currentPage;
	}

	/**
	 * Returns the progress monitor for this wizard dialog (if it has one).
	 *
	 * @return the progress monitor, or <code>null</code> if this wizard
	 *         dialog does not have one
	 */
	protected IProgressMonitor getProgressMonitor() {
		return progressMonitorPart;
	}

	/**
	 * Returns the wizard this dialog is currently displaying.
	 *
	 * @return the current wizard
	 */
	protected IWizard getWizard() {
		return wizard;
	}

	/**
	 * Closes this window.
	 *
	 * @return <code>true</code> if the window is (or was already) closed, and
	 *         <code>false</code> if it is still open
	 */
	private boolean hardClose() {
		for (int i = 0; i < createdWizards.size(); i++) {
			IWizard createdWizard = createdWizards.get(i);
			try {
				createdWizard.dispose();
			} catch (Exception e) {
				Status status = new Status(IStatus.ERROR, Policy.JFACE, IStatus.ERROR, e.getMessage(), e);
				Policy.getLog().log(status);
			}
			createdWizard.setContainer(null);
		}
		setTitleImage(null);
		return super.close();
	}

	/**
	 * The Help button has been pressed.
	 */
	protected void helpPressed() {
		if (currentPage != null) {
			currentPage.performHelp();
		}
	}

	/**
	 * The Next button has been pressed.
	 */
	protected void nextPressed() {
		IWizardPage page = currentPage.getNextPage();
		if (page == null) {
			return;
		}

		showPage(page);
	}

	/**
	 * Notifies page changing listeners and returns result of page changing
	 * processing to the sender.
	 *
	 * @param eventType
	 * @return <code>true</code> if page changing listener completes
	 *         successfully, <code>false</code> otherwise
	 */
	private boolean doPageChanging(IWizardPage targetPage) {
		PageChangingEvent e = new PageChangingEvent(this, getCurrentPage(), targetPage);
		firePageChanging(e);
		return e.doit;
	}

	/**
	 * Checks whether it is alright to close this wizard dialog and performed
	 * standard cancel processing. If there is a long running operation in
	 * progress, this method posts an alert message saying that the wizard
	 * cannot be closed.
	 *
	 * @return <code>true</code> if it is alright to close this dialog, and
	 *         <code>false</code> if it is not
	 */
	private boolean okToClose() {
		if (activeRunningOperations > 0) {
			synchronized (this) {
				windowClosingDialog = createWizardClosingDialog();
			}
			windowClosingDialog.open();
			synchronized (this) {
				windowClosingDialog = null;
			}
			return false;
		}
		return wizard.performCancel();
	}

	/**
	 * Restores the enabled/disabled state of the given control.
	 *
	 * @param control
	 *            the control
	 * @param saveState
	 *            a map containing the enabled/disabled state of the wizard dialog's buttons
	 * @param key
	 *            the key
	 * @see #saveEnableStateAndSet
	 */
	private void restoreEnableState(Control control, Map<String,Object> saveState, String key) {
		if (control != null) {
			Boolean b = (Boolean) saveState.get(key);
			if (b != null) {
				control.setEnabled(b.booleanValue());
			}
		}
	}

	/**
	 * Restores the enabled/disabled state of the wizard dialog's buttons and
	 * the tree of controls for the currently showing page.
	 *
	 * @param saveState
	 *            a map containing the saved state as returned by
	 *            <code>saveUIState</code>
	 * @see #saveUIState
	 */
	private void restoreUIState(Map<String, Object> saveState) {
		restoreEnableState(backButton, saveState, "back"); //$NON-NLS-1$
		restoreEnableState(nextButton, saveState, "next"); //$NON-NLS-1$
		restoreEnableState(finishButton, saveState, "finish"); //$NON-NLS-1$
		restoreEnableState(cancelButton, saveState, "cancel"); //$NON-NLS-1$
		restoreEnableState(helpButton, saveState, "help"); //$NON-NLS-1$
		if (pageValue != null) {
			((ControlEnableState) pageValue).restore();
		}
	}

	/**
	 * This implementation of IRunnableContext#run(boolean, boolean,
	 * IRunnableWithProgress) blocks until the runnable has been run, regardless
	 * of the value of <code>fork</code>. It is recommended that
	 * <code>fork</code> is set to true in most cases. If <code>fork</code>
	 * is set to <code>false</code>, the runnable will run in the UI thread
	 * and it is the runnable's responsibility to call
	 * <code>Display.readAndDispatch()</code> to ensure UI responsiveness.
	 *
	 * UI state is saved prior to executing the long-running operation and is
	 * restored after the long-running operation completes executing. Any
	 * attempt to change the UI state of the wizard in the long-running
	 * operation will be nullified when original UI state is restored.
	 *
	 */
	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException,
			InterruptedException {
		Object state = null;
		if (activeRunningOperations++ == 0) {
			state = aboutToStart(fork && cancelable);
		}
		IProgressMonitor progressMonitor = getProgressMonitor();
		if (progressMonitor == null) {
			progressMonitor = new NullProgressMonitor();
		}
		try {
			if (!fork) {
				lockedUI = true;
			}
			ModalContext.run(runnable, fork, progressMonitor, getShell()
					.getDisplay());
			lockedUI = false;
		} finally {
			progressMonitor.done();
			if (state != null) {
				timeWhenLastJobFinished= System.currentTimeMillis();
				stopped(state);
			}
			activeRunningOperations--;
		}
	}

	/**
	 * Saves the enabled/disabled state of the given control in the given map,
	 * which must be modifiable.
	 *
	 * @param control
	 *            the control, or <code>null</code> if none
	 * @param saveState
	 *            a map containing the enabled/disabled state of the wizard dialog's buttons
	 * @param key
	 *            the key
	 * @param enabled
	 *            <code>true</code> to enable the control, and
	 *            <code>false</code> to disable it
	 * @see #restoreEnableState(Control, Map, String)
	 */
	private void saveEnableStateAndSet(Control control, Map<String, Object> saveState, String key, boolean enabled) {
		if (control != null) {
			saveState.put(key, control.getEnabled() ? Boolean.TRUE : Boolean.FALSE);
			control.setEnabled(enabled);
		}
	}

	/**
	 * Captures and returns the enabled/disabled state of the wizard dialog's
	 * buttons and the tree of controls for the currently showing page. All
	 * these controls are disabled in the process, with the possible exception
	 * of the Cancel button.
	 *
	 * @param keepCancelEnabled
	 *            <code>true</code> if the Cancel button should remain
	 *            enabled, and <code>false</code> if it should be disabled
	 * @return a map containing the saved state suitable for restoring later
	 *         with <code>restoreUIState</code>
	 * @see #restoreUIState
	 */
	private Map<String, Object> saveUIState(boolean keepCancelEnabled) {
		Map<String, Object> savedState = new HashMap<>(10);
		saveEnableStateAndSet(backButton, savedState, "back", false); //$NON-NLS-1$
		saveEnableStateAndSet(nextButton, savedState, "next", false); //$NON-NLS-1$
		saveEnableStateAndSet(finishButton, savedState, "finish", false); //$NON-NLS-1$
		saveEnableStateAndSet(cancelButton, savedState,	"cancel", keepCancelEnabled); //$NON-NLS-1$
		saveEnableStateAndSet(helpButton, savedState, "help", false); //$NON-NLS-1$
		if (currentPage != null) {
			savedState.put("page", ControlEnableState.disable(currentPage.getControl())); //$NON-NLS-1$
		}
		return savedState;
	}

	/**
	 * Sets the given cursor for all shells currently active for this window's
	 * display.
	 *
	 * @param c
	 *            the cursor
	 */
	private void setDisplayCursor(Cursor c) {
		Shell[] shells = getShell().getDisplay().getShells();
		for (Shell shell : shells) {
			shell.setCursor(c);
		}
	}

	/**
	 * Sets the minimum page size used for the pages.
	 *
	 * @param minWidth
	 *            the minimum page width
	 * @param minHeight
	 *            the minimum page height
	 * @see #setMinimumPageSize(Point)
	 */
	public void setMinimumPageSize(int minWidth, int minHeight) {
		Assert.isTrue(minWidth >= 0 && minHeight >= 0);
		pageContainerLayout.minimumWidth = minWidth;
		pageContainerLayout.minimumHeight = minHeight;
	}

	/**
	 * Sets the minimum page size used for the pages.
	 *
	 * @param size
	 *            the page size encoded as <code>new Point(width,height)</code>
	 * @see #setMinimumPageSize(int,int)
	 */
	public void setMinimumPageSize(Point size) {
		setMinimumPageSize(size.x, size.y);
	}

	/**
	 * Sets the size of all pages. The given size takes precedence over computed
	 * sizes.
	 *
	 * @param width
	 *            the page width
	 * @param height
	 *            the page height
	 * @see #setPageSize(Point)
	 */
	public void setPageSize(int width, int height) {
		pageWidth = width;
		pageHeight = height;
	}

	/**
	 * Sets the size of all pages. The given size takes precedence over computed
	 * sizes.
	 *
	 * @param size
	 *            the page size encoded as <code>new Point(width,height)</code>
	 * @see #setPageSize(int,int)
	 */
	public void setPageSize(Point size) {
		setPageSize(size.x, size.y);
	}

	/**
	 * Sets the wizard this dialog is currently displaying.
	 *
	 * @param newWizard
	 *            the wizard
	 */
	protected void setWizard(IWizard newWizard) {
		wizard = newWizard;
		wizard.setContainer(this);
		if (!createdWizards.contains(wizard)) {
			createdWizards.add(wizard);
			nestedWizards.add(wizard);
			if (pageContainer != null) {
				createPageControls();
				updateSizeForWizard(wizard);
				pageContainer.layout(true);
			}
		} else {
			int size = nestedWizards.size();
			if (size >= 2 && nestedWizards.get(size - 2) == wizard) {
				nestedWizards.remove(size - 1);
			} else {
				nestedWizards.add(wizard);
			}
		}
	}

	@Override
	public void showPage(IWizardPage page) {
		if (page == null || page == currentPage) {
			return;
		}

		if (!isMovingToPreviousPage) {
			page.setPreviousPage(currentPage);
		} else {
			isMovingToPreviousPage = false;
		}

		if (!doPageChanging(page))
			return;

		if (getContents() == null) {
			updateForPage(page);
		} else {
			final IWizardPage finalPage = page;
			BusyIndicator.showWhile(getContents().getDisplay(), () -> updateForPage(finalPage));
		}
	}

	/**
	 * Update the receiver for the new page.
	 *
	 * @param page
	 */
	private void updateForPage(IWizardPage page) {
		if (wizard != page.getWizard()) {
			setWizard(page.getWizard());
		}
		if (page.getControl() == null) {
			page.createControl(pageContainer);
			Assert.isNotNull(page.getControl(),
					JFaceResources.format(JFaceResources.getString("WizardDialog.missingSetControl"), //$NON-NLS-1$
							page.getName()));
			updateSize(page);
		}
		IWizardPage oldPage = currentPage;
		currentPage = page;

		currentPage.setVisible(true);
		if (oldPage != null) {
			oldPage.setVisible(false);
		}
		update();
	}

	/**
	 * Shows the starting page of the wizard.
	 */
	private void showStartingPage() {
		currentPage = wizard.getStartingPage();
		if (currentPage == null) {
			return;
		}
		if (currentPage.getControl() == null) {
			currentPage.createControl(pageContainer);
			Assert.isNotNull(currentPage.getControl());
		}
		currentPage.setVisible(true);
		update();
	}

	/**
	 * A long running operation triggered through the wizard was stopped either
	 * by user input or by normal end. Hides the progress monitor and restores
	 * the enable state wizard's buttons and controls.
	 *
	 * @param savedState
	 *            the saved UI state as returned by <code>aboutToStart</code>
	 * @see #aboutToStart
	 */
	private void stopped(Object savedState) {
		if (getShell() != null && !getShell().isDisposed()) {
			if (wizard.needsProgressMonitor()) {
				progressMonitorPart.setVisible(false);
				progressMonitorPart.removeFromCancelComponent(cancelButton);
			}

			@SuppressWarnings("unchecked")
			Map<String,Object> state = (Map<String,Object>) savedState;
			restoreUIState(state);
			setDisplayCursor(null);
			if (useCustomProgressMonitorPart) {
				cancelButton.addSelectionListener(cancelListener);
				cancelButton.setCursor(null);
				arrowCursor.dispose();
				arrowCursor = null;
			}
			waitCursor.dispose();
			waitCursor = null;
			Control focusControl = (Control) state.get(FOCUS_CONTROL);
			if (focusControl != null && !focusControl.isDisposed()) {
				focusControl.setFocus();
			}
		}
	}

	/**
	 * Updates this dialog's controls to reflect the current page.
	 */
	protected void update() {
		updateWindowTitle();
		updateTitleBar();
		updateButtons();

		firePageChanged(new PageChangedEvent(this, getCurrentPage()));
	}

	@Override
	public void updateButtons() {
		boolean canFlipToNextPage = false;
		boolean canFinish = wizard.canFinish();
		if (backButton != null) {
			boolean backEnabled = currentPage != null && currentPage.getPreviousPage() != null;
			backButton.setEnabled(backEnabled);
		}
		if (nextButton != null) {
			canFlipToNextPage = currentPage != null && currentPage.canFlipToNextPage();
			nextButton.setEnabled(canFlipToNextPage);
		}
		finishButton.setEnabled(canFinish);
		if (canFlipToNextPage && !canFinish) {
			getShell().setDefaultButton(nextButton);
		} else {
			getShell().setDefaultButton(finishButton);
		}
	}

	/**
	 * Update the message line with the page's description.
	 * <p>
	 * A description is shown only if there is no message or error message.
	 * </p>
	 */
	private void updateDescriptionMessage() {
		pageDescription = currentPage.getDescription();
		setMessage(pageDescription);
	}

	@Override
	public void updateMessage() {

		if (currentPage == null) {
			return;
		}

		pageMessage = currentPage.getMessage();
		if (pageMessage != null && currentPage instanceof IMessageProvider) {
			pageMessageType = ((IMessageProvider) currentPage).getMessageType();
		} else {
			pageMessageType = IMessageProvider.NONE;
		}
		if (pageMessage == null) {
			setMessage(pageDescription);
		} else {
			setMessage(pageMessage, pageMessageType);
		}
		setErrorMessage(currentPage.getErrorMessage());
	}

	/**
	 * Changes the shell size to the given size, ensuring that it is no larger
	 * than the display bounds.
	 *
	 * @param width
	 *            the shell width
	 * @param height
	 *            the shell height
	 */
	private void setShellSize(int width, int height) {
		Rectangle size = getShell().getBounds();
		size.height = height;
		size.width = width;
		getShell().setBounds(getConstrainedShellBounds(size));
	}

	/**
	 * Computes the correct dialog size for the current page and resizes its shell if necessary.
	 * Also causes the container to refresh its layout.
	 *
	 * @param page the wizard page to use to resize the dialog
	 * @since 2.0
	 */
	protected void updateSize(IWizardPage page) {
		if (page == null || page.getControl() == null) {
			return;
		}
		updateSizeForPage(page);
		pageContainerLayout.layoutPage(page.getControl());
	}

	@Override
	public void updateSize() {
		updateSize(currentPage);
	}

	/**
	 * Computes the correct dialog size for the given page and resizes its shell if necessary.
	 *
	 * @param page the wizard page
	 */
	private void updateSizeForPage(IWizardPage page) {
		Point delta = calculatePageSizeDelta(page);
		if (delta.x > 0 || delta.y > 0) {
			Shell shell = getShell();
			Point shellSize = shell.getSize();
			setShellSize(shellSize.x + delta.x, shellSize.y + delta.y);
			constrainShellSize();
		}
	}

	/**
	 * Computes the correct dialog size for the given wizard and resizes its shell if necessary.
	 *
	 * @param sizingWizard the wizard
	 */
	private void updateSizeForWizard(IWizard sizingWizard) {
		Point delta = new Point(0, 0);
		IWizardPage[] pages = sizingWizard.getPages();
		for (IWizardPage page : pages) {
			Point pageDelta = calculatePageSizeDelta(page);
			delta.x = Math.max(delta.x, pageDelta.x);
			delta.y = Math.max(delta.y, pageDelta.y);
		}
		if (delta.x > 0 || delta.y > 0) {
			Shell shell = getShell();
			Point shellSize = shell.getSize();
			setShellSize(shellSize.x + delta.x, shellSize.y + delta.y);
		}
	}

	@Override
	public void updateTitleBar() {
		String s = null;
		if (currentPage != null) {
			s = currentPage.getTitle();
		}
		if (s == null) {
		}
		setTitle(s);
		if (currentPage != null) {
			setTitleImage(currentPage.getImage());
			updateDescriptionMessage();
		}
		updateMessage();
	}

	@Override
	public void updateWindowTitle() {
		if (getShell() == null) {
			return;
		}
		String title = wizard.getWindowTitle();
		if (title == null) {
		}
		getShell().setText(title);
	}

	@Override
	public Object getSelectedPage() {
		return getCurrentPage();
	}

	@Override
	public void addPageChangedListener(IPageChangedListener listener) {
		pageChangedListeners.add(listener);
	}

	@Override
	public void removePageChangedListener(IPageChangedListener listener) {
		pageChangedListeners.remove(listener);
	}

	/**
	 * Notifies any selection changed listeners that the selected page has
	 * changed. Only listeners registered at the time this method is called are
	 * notified.
	 *
	 * @param event
	 *            a selection changed event
	 *
	 * @see IPageChangedListener#pageChanged
	 *
	 * @since 3.1
	 */
	protected void firePageChanged(final PageChangedEvent event) {
		for (IPageChangedListener l : pageChangedListeners) {
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.pageChanged(event);
				}
			});
		}
	}

	/**
	 * Adds a listener for page changes to the list of page changing listeners
	 * registered for this dialog. Has no effect if an identical listener is
	 * already registered.
	 *
	 * @param listener
	 *            a page changing listener
	 * @since 3.3
	 */
	public void addPageChangingListener(IPageChangingListener listener) {
		pageChangingListeners.add(listener);
	}

	/**
	 * Removes the provided page changing listener from the list of page
	 * changing listeners registered for the dialog.
	 *
	 * @param listener
	 *            a page changing listener
	 * @since 3.3
	 */
	public void removePageChangingListener(IPageChangingListener listener) {
		pageChangingListeners.remove(listener);
	}

	/**
	 * Notifies any page changing listeners that the currently selected dialog
	 * page is changing. Only listeners registered at the time this method is
	 * called are notified.
	 *
	 * @param event
	 *            a selection changing event
	 *
	 * @see IPageChangingListener#handlePageChanging(PageChangingEvent)
	 * @since 3.3
	 */
	protected void firePageChanging(final PageChangingEvent event) {
		for (IPageChangingListener l : pageChangingListeners) {
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.handlePageChanging(event);
				}
			});
		}
	}
}
