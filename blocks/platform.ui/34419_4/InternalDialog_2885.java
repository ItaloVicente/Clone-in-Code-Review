package org.eclipse.ui.internal.statushandlers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogTray;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.internal.progress.ProgressMessages;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.IViewDescriptor;


public class InternalDialog extends TrayDialog {

	static final int GOTO_ACTION_ID = IDialogConstants.CLIENT_ID + 1;

	static final String LOG_VIEW_ID = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$

	static final String PREF_SKIP_GOTO_ACTION_PROMPT = "pref_skip_goto_action_prompt"; //$NON-NLS-1$

	private Composite dialogArea;
	private Composite listArea;
	private Composite singleStatusDisplayArea;
	private Label singleStatusLabel;
	private TableViewer statusListViewer;
	private Composite linkComposite;
	private Link launchTrayLink;
	private Link showErrorLogLink;
	private Label titleImageLabel;
	private Label mainMessageLabel;
	private Composite titleArea;

	private SupportTray supportTray;

	private DetailsAreaManager detailsManager;

	private Map dialogState;

	public InternalDialog(final Map dialogState, boolean modal) {
		super(ProgressManagerUtil.getDefaultParent());
		this.dialogState = dialogState;
		supportTray = new SupportTray(dialogState, new Listener() {
			@Override
			public void handleEvent(Event event) {
				dialogState.put(IStatusDialogConstants.TRAY_OPENED,
						Boolean.FALSE);
				closeTray();
				getShell().setFocus();
			}
		});
		detailsManager = new DetailsAreaManager(dialogState);
		setShellStyle(SWT.RESIZE | SWT.MAX | SWT.MIN | getShellStyle());
		setBlockOnOpen(false);

		if (!modal) {
			setShellStyle(~SWT.APPLICATION_MODAL & getShellStyle());
		}
	}

	@Override
	protected void buttonPressed(int id) {
		if (id == GOTO_ACTION_ID) {
			IAction gotoAction = getGotoAction();
			if (gotoAction != null) {
				if (isPromptToClose()) {
					okPressed(); // close the dialog
					gotoAction.run(); // run the goto action
				}
			}
		}
		if (id == IDialogConstants.DETAILS_ID) {
			dialogState.put(IStatusDialogConstants.DETAILS_OPENED, new Boolean(
					toggleDetailsArea()));
		} else {
			super.buttonPressed(id);
		}
	}

	@Override
	final protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getString(IStatusDialogConstants.TITLE));
	}
	
	@Override
	protected void setButtonLayoutData(Button button) {
		GridData data = new GridData(SWT.END, SWT.CENTER, false, false);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		createTitleArea(parent);
		createListArea(parent);
		dialogArea = parent;
		Dialog.applyDialogFont(dialogArea);
		return parent;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void createTitleArea(Composite parent) {
		titleArea = new Composite(parent, SWT.NONE);
		titleArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.marginLeft = 10;
		layout.marginTop = 10;
		layout.marginBottom = 0;
		titleArea.setLayout(layout);

		titleImageLabel = new Label(titleArea, SWT.NONE);
		titleImageLabel.setImage(getLabelProviderWrapper()
				.getImage(getCurrentStatusAdapter()));
		GridData layoutData = new GridData();
		layoutData.verticalSpan = 2;
		layoutData.verticalAlignment = SWT.TOP;
		titleImageLabel.setLayoutData(layoutData);

		GridData messageData = new GridData(SWT.FILL, SWT.FILL, true, true);
		messageData.widthHint = convertWidthInCharsToPixels(50);
		mainMessageLabel = new Label(titleArea, SWT.WRAP);
		mainMessageLabel.setLayoutData(messageData);
		mainMessageLabel.setText(getLabelProviderWrapper()
				.getMainMessage(getCurrentStatusAdapter()));
		if (!isMulti()) {
			singleStatusDisplayArea = createSingleStatusDisplayArea(titleArea);
		}
	}

	private void createListArea(Composite parent) {
		listArea = new Composite(parent, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.heightHint = 0;
		layoutData.widthHint = 0;
		listArea.setLayoutData(layoutData);
		GridLayout layout = new GridLayout();
		listArea.setLayout(layout);
		if (isMulti()) {
			fillListArea(listArea);
		}
	}

	public boolean isModal() {
		return ((getShellStyle() & SWT.APPLICATION_MODAL) == SWT.APPLICATION_MODAL);
	}

	public SupportTray getSupportTray() {
		return supportTray;
	}

	public void setSupportTray(SupportTray supportTray) {
		this.supportTray = supportTray;
	}

	@Override
	public int open() {
		boolean modalitySwitch = getBooleanValue(IStatusDialogConstants.MODALITY_SWITCH);
		int result = super.open();
		if (modalitySwitch) {
			if (getBooleanValue(IStatusDialogConstants.DETAILS_OPENED)) {
				showDetailsArea();
			}
			if (getBooleanValue(IStatusDialogConstants.TRAY_OPENED)) {
				openTray();
			}
		} else {
			if (getBooleanValue(IStatusDialogConstants.ANIMATION)) {
				Rectangle shellPosition = getShell().getBounds();
				ProgressManagerUtil.animateUp(shellPosition);
			}
		}
		return result;
	}

	@Override
	public void closeTray() throws IllegalStateException {
		if (getTray() != null) {
			super.closeTray();
		}
		if (!getBooleanValue(IStatusDialogConstants.MODALITY_SWITCH)) {
			dialogState.put(IStatusDialogConstants.TRAY_OPENED, Boolean.FALSE);
		}
		if (launchTrayLink != null && !launchTrayLink.isDisposed()) {
			launchTrayLink.setEnabled(providesSupport()
					&& !getBooleanValue(IStatusDialogConstants.TRAY_OPENED));
		}
	}
	
	void refresh() {
		if (dialogArea == null || dialogArea.isDisposed()) {
			return;
		}
		updateTitleArea();
		updateListArea();
		updateEnablements();
		Point currentSize = getShell().getSize();
		Point desiredSize = getShell()
				.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (currentSize.x < desiredSize.x) {
			getShell().setSize(desiredSize.x, currentSize.y);
		} else {
			getShell().layout();
		}
	}

	void refreshDialogSize() {
		Point newSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		getShell().setSize(newSize);
	}

	private void showDetailsArea() {
		if (dialogArea != null && !dialogArea.isDisposed()) {
			if (detailsManager.isOpen()) {
				detailsManager.close();
				detailsManager.createDetailsArea(dialogArea,
						getCurrentStatusAdapter());
				dialogState.put(IStatusDialogConstants.DETAILS_OPENED,
						Boolean.TRUE);
			} else {
				toggleDetailsArea();
				dialogState.put(IStatusDialogConstants.DETAILS_OPENED,
						Boolean.TRUE);
			}
			dialogArea.layout();
		}
	}

	private boolean toggleDetailsArea() {
		boolean opened = false;
		Point windowSize = getShell().getSize();
		if (detailsManager.isOpen()) {
			detailsManager.close();
			getButton(IDialogConstants.DETAILS_ID).setText(
					IDialogConstants.SHOW_DETAILS_LABEL);
			opened = false;
		} else {
			detailsManager.createDetailsArea(dialogArea,
					getCurrentStatusAdapter());
			getButton(IDialogConstants.DETAILS_ID).setText(
					IDialogConstants.HIDE_DETAILS_LABEL);
			opened = true;
		}

		GridData listAreaGridData = (GridData) listArea.getLayoutData();
		if (!isMulti()) {
			listAreaGridData.heightHint = 0;
		}
		if (opened) {
			listAreaGridData.grabExcessVerticalSpace = false;
		} else {
			listAreaGridData.grabExcessVerticalSpace = true;
		}
		listArea.setLayoutData(listAreaGridData);

		Point newSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int diffY = newSize.y - windowSize.y;
		if ((opened && diffY > 0) || (!opened && diffY < 0)) {
			getShell().setSize(
					new Point(windowSize.x, windowSize.y + (diffY)));
		}
		dialogArea.layout();
		return opened;
	}

	@Override
	protected void initializeBounds() {
		super.initializeBounds();
		refreshDialogSize();
		boolean modalitySwitch = getBooleanValue(IStatusDialogConstants.MODALITY_SWITCH);
		if (modalitySwitch) {
			getShell().setBounds(getShellBounds());
		}
	}

	@Override
	public Point getInitialLocation(Point initialSize) {
		return super.getInitialLocation(initialSize);
	}

	private void handleSelectionChange() {
		StatusAdapter newSelection = getSingleSelection();
		if (newSelection != null) {
			dialogState.put(IStatusDialogConstants.CURRENT_STATUS_ADAPTER,
					newSelection);
			showDetailsArea();
			refresh();
		}
	}

	private void fillListArea(Composite parent) {
		GridData listAreaGD = (GridData) parent.getLayoutData();
		listAreaGD.grabExcessHorizontalSpace = true;
		if (!detailsManager.isOpen()) {
			listAreaGD.grabExcessVerticalSpace = true;
		}
		listAreaGD.heightHint = SWT.DEFAULT;

		statusListViewer = new TableViewer(parent, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		statusListViewer.setComparator(getLabelProviderWrapper());
		Control control = statusListViewer.getControl();
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = convertHeightInCharsToPixels(5);
		control.setLayoutData(data);
		initContentProvider();
		initLabelProvider();
		statusListViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChange();
				if ((getTray() == null) && getBooleanValue(IStatusDialogConstants.TRAY_OPENED)
						&& providesSupport()) {
					silentTrayOpen();
					return;
				}
				if ((getTray() != null) && !providesSupport()) {
					silentTrayClose();
					return;
				}
				supportTray.selectionChanged(event);
			}
		});
		Dialog.applyDialogFont(parent);
	}

	private void silentTrayClose() {
		super.closeTray();
	}

	private void silentTrayOpen() {
		if (getTray() == null)
			super.openTray(supportTray);
	}
	private void updateListArea() {
		if (isMulti()) {
			if (singleStatusDisplayArea != null) {
				singleStatusDisplayArea.dispose();
			}
			if (statusListViewer == null
					|| statusListViewer.getControl().isDisposed()) {
				fillListArea(listArea);
				listArea.layout();
				listArea.getParent().layout();
				getShell().setSize(
						getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
			refreshStatusListArea();
		}
	}

	private void updateTitleArea() {
		Image image = getLabelProviderWrapper().getImage(
				getCurrentStatusAdapter());
		titleImageLabel.setImage(image);
		if (getCurrentStatusAdapter() != null) {
			mainMessageLabel.setText(getLabelProviderWrapper()
					.getMainMessage(getCurrentStatusAdapter()));
		}
		if (singleStatusDisplayArea != null) {
			if (isMulti()) {
				singleStatusDisplayArea.dispose();
			} else {
				refreshSingleStatusArea();
			}
		}
		titleArea.layout();
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		linkComposite = createLinkComposite(composite);

		createButtonsForButtonBar(composite);

		composite.layout();
		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		IAction gotoAction = getGotoAction();
		String text = null;
		if (gotoAction != null) {
			text = gotoAction.getText();
		}
		Button button = createButton(parent, GOTO_ACTION_ID,
				text == null ? "" : text, //$NON-NLS-1$
				false);
		if (text == null)
			hideButton(button, true);

		createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);

		createButton(parent, IDialogConstants.DETAILS_ID,
				IDialogConstants.SHOW_DETAILS_LABEL, false);
	}

	private Composite createSingleStatusDisplayArea(Composite parent) {
		Composite singleStatusParent = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		singleStatusParent.setLayout(gridLayout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		singleStatusParent.setLayoutData(gd);

		singleStatusLabel = new Label(singleStatusParent, SWT.WRAP);
		GridData labelLayoutData = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		labelLayoutData.widthHint = convertWidthInCharsToPixels(50);
		singleStatusLabel.setLayoutData(labelLayoutData);
		singleStatusLabel.setText(getLabelProviderWrapper()
				.getColumnText(getCurrentStatusAdapter(), 0));

		singleStatusLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				showDetailsArea();
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		return singleStatusParent;
	}

	@Override
	public boolean close() {
		boolean modalitySwitch = getBooleanValue(IStatusDialogConstants.MODALITY_SWITCH);
		if (detailsManager.isOpen()) {
			dialogState.put(IStatusDialogConstants.DETAILS_OPENED, Boolean.TRUE);
			toggleDetailsArea();
		}
		if (getBooleanValue(IStatusDialogConstants.TRAY_OPENED)) {
			closeTray();
			if (modalitySwitch) {
				dialogState.put(IStatusDialogConstants.DETAILS_OPENED, Boolean.TRUE);
			}
		}
		dialogState.put(IStatusDialogConstants.SHELL_BOUNDS, getShell().getBounds());
		statusListViewer = null;
		boolean result = super.close();
		if (!modalitySwitch && getBooleanValue(IStatusDialogConstants.ANIMATION)) {
			ProgressManagerUtil.animateDown(getShellBounds());
		}
		return result;
	}

	private void hideButton(Button button, boolean hide) {
		((GridData) button.getLayoutData()).exclude = hide;
		button.setVisible(!hide);
		button.setEnabled(!hide);
	}

	private void updateEnablements() {
		Button details = getButton(IDialogConstants.DETAILS_ID);
		if (details != null) {
			details.setEnabled(true);
		}
		Button gotoButton = getButton(GOTO_ACTION_ID);
		if (gotoButton != null) {
			IAction gotoAction = getGotoAction();
			boolean hasValidGotoAction = (gotoAction != null)
					&& (gotoAction.getText() != null);
			if (hasValidGotoAction) {
				hideButton(gotoButton, false);
				gotoButton.setText(gotoAction.getText());

				((GridData) gotoButton.getLayoutData()).widthHint = gotoButton
						.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				gotoButton.getParent().layout();
			} else
				hideButton(gotoButton, true);
		}
		if (providesSupport() && !getBooleanValue(IStatusDialogConstants.HIDE_SUPPORT_BUTTON)) {
			if (launchTrayLink == null || launchTrayLink.isDisposed()) {
				launchTrayLink = createGetSupportLink();
			}
			launchTrayLink
					.setEnabled(!getBooleanValue(IStatusDialogConstants.TRAY_OPENED));
		} else {
			if (launchTrayLink != null && !launchTrayLink.isDisposed()) {
				launchTrayLink.dispose();
				launchTrayLink = null;
			}
		}
		IViewDescriptor descriptor = shouldDisplayLinkToErrorLog();
		if (descriptor != null) {
			if (showErrorLogLink == null || showErrorLogLink.isDisposed()) {
				showErrorLogLink = createShowErrorLogLink();
			}
		} else {
			if (showErrorLogLink != null && !showErrorLogLink.isDisposed()) {
				showErrorLogLink.dispose();
			}
		}
		linkComposite.getParent().layout();
	}

	private IViewDescriptor shouldDisplayLinkToErrorLog() {
		if (!getBooleanValue(IStatusDialogConstants.ERRORLOG_LINK)) {
			return null;
		}
		boolean shouldDisplay = false;
		Iterator it = ((Collection) dialogState
				.get(IStatusDialogConstants.STATUS_ADAPTERS)).iterator();
		while (it.hasNext()) {
			StatusAdapter adapter = (StatusAdapter) it.next();
			Integer hint = (Integer) adapter.getProperty(WorkbenchStatusDialogManagerImpl.HINT);
			if (hint != null
					&& ((hint.intValue() & StatusManager.LOG) != 0)) {
				shouldDisplay |= true;
				break;
			}
		}
		if (!shouldDisplay) {
			return null;
		}
		return Workbench.getInstance().getViewRegistry().find(LOG_VIEW_ID);
	}

	@Override
	public void openTray(DialogTray tray) throws IllegalStateException,
			UnsupportedOperationException {
		if (launchTrayLink != null && !launchTrayLink.isDisposed()) {
			launchTrayLink.setEnabled(false);
		}
		if (providesSupport()) {
			super.openTray(tray);
		}
		dialogState.put(IStatusDialogConstants.TRAY_OPENED, Boolean.TRUE);
	}

	private void refreshSingleStatusArea() {
		String description = getLabelProviderWrapper()
				.getColumnText(getCurrentStatusAdapter(), 0);
		if (description.equals(singleStatusLabel.getText()))
			singleStatusLabel.setText(" "); //$NON-NLS-1$
		singleStatusLabel.setText(description);
		singleStatusDisplayArea.layout();
		getShell().setText(getString(IStatusDialogConstants.TITLE));
	}

	private void refreshStatusListArea() {
		if (statusListViewer != null
				&& !statusListViewer.getControl().isDisposed()) {
			statusListViewer.refresh();
			if (statusListViewer.getTable().getItemCount() > 1) {
				getShell()
						.setText(
								WorkbenchMessages.WorkbenchStatusDialog_MultipleProblemsHaveOccured);
			} else {
				getShell().setText(
						getString(IStatusDialogConstants.TITLE));
			}
		}
	}

	private void initContentProvider() {
		IContentProvider provider = new IStructuredContentProvider() {
			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return ((Collection) dialogState
						.get(IStatusDialogConstants.STATUS_ADAPTERS)).toArray();
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				if (newInput != null) {
					refreshStatusListArea();
				}
			}
		};
		statusListViewer.setContentProvider(provider);
		statusListViewer.setInput(this);
		statusListViewer.setSelection(new StructuredSelection(
				getCurrentStatusAdapter()));
	}

	private Composite createLinkComposite(Composite parent) {
		Composite linkArea = new Composite(parent, SWT.NONE) {

			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				Point newSize = super.computeSize(wHint, hHint, changed);
				if (getChildren().length == 0) {
					newSize.x = 0;
					newSize.y = 0;
				}
				return newSize;
			}

		};
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		linkArea.setLayout(layout);

		((GridLayout) parent.getLayout()).numColumns++;

		GridData layoutData = new GridData(SWT.BEGINNING, SWT.CENTER, true,
				false);
		linkArea.setLayoutData(layoutData);
		return linkArea;
	}

	private Link createGetSupportLink() {
		if (!providesSupport() || getBooleanValue(IStatusDialogConstants.HIDE_SUPPORT_BUTTON)) {
			return null;
		}

		Link link = new Link(linkComposite, SWT.NONE);
		link
				.setText(WorkbenchMessages.WorkbenchStatusDialog_SupportHyperlink);
		link
				.setToolTipText(WorkbenchMessages.WorkbenchStatusDialog_SupportTooltip);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openTray();
			}
		});
		Dialog.applyDialogFont(link);
		return link;
	}

	private Link createShowErrorLogLink() {
		Link link = new Link(linkComposite, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Workbench.getInstance().getActiveWorkbenchWindow()
							.getActivePage().showView(LOG_VIEW_ID);
				} catch (CoreException ce) {
					StatusManager.getManager().handle(ce,
							WorkbenchPlugin.PI_WORKBENCH);
				}
			}
		});
		link.setText(WorkbenchMessages.ErrorLogUtil_ShowErrorLogHyperlink);
		link
				.setToolTipText(WorkbenchMessages.ErrorLogUtil_ShowErrorLogTooltip);
		Dialog.applyDialogFont(link);
		return link;
	}

	private void initLabelProvider() {
		statusListViewer.setLabelProvider(getLabelProviderWrapper());
	}

	private IAction getGotoAction() {
		Object property = null;

		Job job = (Job) (getCurrentStatusAdapter().getAdapter(Job.class));
		if (job != null) {
			property = job.getProperty(IProgressConstants.ACTION_PROPERTY);
		}

		if (property instanceof IAction) {
			return (IAction) property;
		}
		return null;
	}

	private StatusAdapter getSingleSelection() {
		ISelection rawSelection = statusListViewer.getSelection();
		if (rawSelection != null
				&& rawSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) rawSelection;
			if (selection.size() == 1) {
				return (StatusAdapter) selection.getFirstElement();
			}
		}
		return null;
	}

	private boolean isPromptToClose() {
		IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		if (!store.contains(PREF_SKIP_GOTO_ACTION_PROMPT)
				|| !store.getString(PREF_SKIP_GOTO_ACTION_PROMPT).equals(
						MessageDialogWithToggle.ALWAYS)) {
			MessageDialogWithToggle dialog = MessageDialogWithToggle.open(
					MessageDialog.CONFIRM, getShell(),
					ProgressMessages.JobErrorDialog_CloseDialogTitle,
					ProgressMessages.JobErrorDialog_CloseDialogMessage,
					ProgressMessages.JobErrorDialog_DoNotShowAgainMessage,
					false, store, PREF_SKIP_GOTO_ACTION_PROMPT, SWT.SHEET);
			return dialog.getReturnCode() == Window.OK;
		}
		return true;
	}

	public void openTray() {
		openTray(supportTray);
	}

	public boolean providesSupport() {
		return supportTray.providesSupport(getCurrentStatusAdapter()) != null;
	}

	private String getString(Object key) {
		return (String) dialogState.get(key);
	}

	private StatusAdapter getCurrentStatusAdapter() {
		return (StatusAdapter) dialogState
				.get(IStatusDialogConstants.CURRENT_STATUS_ADAPTER);
	}

	private boolean getBooleanValue(Object key) {
		Boolean b = (Boolean) dialogState.get(key);
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	private Rectangle getShellBounds() {
		return (Rectangle) dialogState.get(IStatusDialogConstants.SHELL_BOUNDS);
	}

	private LabelProviderWrapper getLabelProviderWrapper() {
		return (LabelProviderWrapper) dialogState
				.get(IStatusDialogConstants.LABEL_PROVIDER);
	}

	private boolean isMulti() {
		return ((Collection) dialogState
				.get(IStatusDialogConstants.STATUS_ADAPTERS)).size() > 1;
	}
}
