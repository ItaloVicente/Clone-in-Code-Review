package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.EHelpService;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.e4.ui.model.LocalizationHelper;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ShowViewDialog extends Dialog implements ISelectionChangedListener,
		IDoubleClickListener {

	private static final String DIALOG_SETTING_SECTION_NAME = "ShowViewDialog"; //$NON-NLS-1$

	private static final int LIST_HEIGHT = 300;

	private static final int LIST_WIDTH = 250;

	private static final String STORE_EXPANDED_CATEGORIES_ID = DIALOG_SETTING_SECTION_NAME
			+ ".STORE_EXPANDED_CATEGORIES_ID"; //$NON-NLS-1$

	private static final String STORE_SELECTED_VIEW_ID = DIALOG_SETTING_SECTION_NAME
			+ ".STORE_SELECTED_VIEW_ID"; //$NON-NLS-1$

	private FilteredTree filteredTree;

	private Color dimmedForeground;

	private Button okButton;

	private MApplication application;
	private MPartDescriptor[] viewDescs = new MPartDescriptor[0];

	private Label descriptionHint;

	private IEclipseContext context;

	private EModelService modelService;

	private MWindow window;

	private EPartService partService;

	public ShowViewDialog(Shell shell, MApplication application, MWindow window, EModelService modelService,
			EPartService partService, IEclipseContext context) {
		super(shell);
		this.application = application;
		this.window = window;
		this.modelService = modelService;
		this.partService = partService;
		this.context = context;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			saveWidgetValues();
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void cancelPressed() {
		viewDescs = new MPartDescriptor[0];
		super.cancelPressed();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(WorkbenchMessages.ShowView_shellTitle);
		EHelpService helpService = context.get(EHelpService.class);
		if (helpService != null) {
			helpService.setHelp(shell, IWorkbenchHelpContextIds.SHOW_VIEW_DIALOG);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY), true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY), false);
		updateButtons();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setFont(parent.getFont());

		createFilteredTreeViewer(composite);

		layoutTopControl(filteredTree);

		descriptionHint = new Label(composite, SWT.WRAP);
		descriptionHint.setText(WorkbenchMessages.ShowView_selectViewHelp);
		descriptionHint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		descriptionHint.setVisible(false);

		restoreWidgetValues();

		applyDialogFont(composite);

		return composite;
	}

	private static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}

	private static int blend(int v1, int v2, int ratio) {
		int b = (ratio * v1 + (100 - ratio) * v2) / 100;
		return Math.min(255, b);
	}

	private void createFilteredTreeViewer(Composite parent) {
		PatternFilter filter = new ViewPatternFilter();
		int styleBits = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
		filteredTree = new FilteredTree(parent, styleBits, filter, true);
		filteredTree.setQuickSelectionMode(true);
		filteredTree.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		TreeViewer treeViewer = filteredTree.getViewer();
		Control treeControl = treeViewer.getControl();
		RGB dimmedRGB = blend(treeControl.getForeground().getRGB(), treeControl.getBackground()
				.getRGB(), 60);
		dimmedForeground = new Color(treeControl.getDisplay(), dimmedRGB);
		treeControl.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dimmedForeground.dispose();
			}
		});

		treeViewer.setLabelProvider(new ViewLabelProvider(context, modelService, partService, window,
				dimmedForeground));
		treeViewer.setContentProvider(new ViewContentProvider(application));
		treeViewer.setComparator(new ViewComparator());
		treeViewer.setInput(application);
		treeViewer.addSelectionChangedListener(this);
		treeViewer.addDoubleClickListener(this);
		treeViewer.getControl().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				handleTreeViewerKeyPressed(e);
			}
		});

		if (hasAtMostOneView(filteredTree.getViewer())) {
			Text filterText = filteredTree.getFilterControl();
			if (filterText != null) {
				filterText.setEnabled(false);
			}
		}
	}

	private boolean hasAtMostOneView(TreeViewer tree) {
		ITreeContentProvider contentProvider = (ITreeContentProvider) tree.getContentProvider();
		Object[] children = contentProvider.getElements(tree.getInput());

		if (children.length <= 1) {
			if (children.length == 0) {
				return true;
			}
			return !contentProvider.hasChildren(children[0]);
		}
		return false;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection) event.getSelection();
		Object element = s.getFirstElement();
		if (filteredTree.getViewer().isExpandable(element)) {
			filteredTree.getViewer().setExpandedState(element,
					!filteredTree.getViewer().getExpandedState(element));
		} else if (viewDescs.length > 0) {
			saveWidgetValues();
			setReturnCode(OK);
			close();
		}
	}

	protected IDialogSettings getDialogSettings() {
		IDialogSettings workbenchSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection(DIALOG_SETTING_SECTION_NAME);
		if (section == null) {
			section = workbenchSettings.addNewSection(DIALOG_SETTING_SECTION_NAME);
		}
		return section;
	}

	public MPartDescriptor[] getSelection() {
		return viewDescs;
	}

	private void layoutTopControl(Control control) {
		GridData spec = new GridData(GridData.FILL_BOTH);
		spec.widthHint = LIST_WIDTH;
		spec.heightHint = LIST_HEIGHT;
		control.setLayoutData(spec);
	}

	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();

		String[] expandedCategoryIds = settings.getArray(STORE_EXPANDED_CATEGORIES_ID);
		if (expandedCategoryIds == null)
			return;

		if (expandedCategoryIds.length > 0)
			filteredTree.getViewer().setExpandedElements(expandedCategoryIds);

		String selectedPartId = settings.get(STORE_SELECTED_VIEW_ID);
		if (selectedPartId != null) {
			List<MPartDescriptor> descriptors = application.getDescriptors();
			for (MPartDescriptor descriptor : descriptors) {
				if (selectedPartId.equals(descriptor.getElementId())) {
					filteredTree.getViewer()
							.setSelection(new StructuredSelection(descriptor), true);
					break;
				}
			}
		}
	}

	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();

		Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
		String[] expandedCategoryIds = new String[expandedElements.length];
		for (int i = 0; i < expandedElements.length; ++i) {
			if (expandedElements[i] instanceof MPartDescriptor)
				expandedCategoryIds[i] = ((MPartDescriptor) expandedElements[i]).getElementId();
			else
				expandedCategoryIds[i] = expandedElements[i].toString();
		}

		settings.put(STORE_EXPANDED_CATEGORIES_ID, expandedCategoryIds);

		String selectedViewId = ""; //$NON-NLS-1$
		if (viewDescs.length > 0) {
			selectedViewId = viewDescs[0].getElementId();
		}
		settings.put(STORE_SELECTED_VIEW_ID, selectedViewId);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		updateSelection(event);
		updateButtons();
		String tooltip = ""; //$NON-NLS-1$
		if (viewDescs.length > 0) {
			tooltip = viewDescs[0].getTooltip();
			tooltip = LocalizationHelper.getLocalized(tooltip, viewDescs[0], context);
		}

		boolean hasTooltip = tooltip != null && tooltip.length() > 0;
		descriptionHint.setVisible(viewDescs.length == 1 && hasTooltip);
	}

	protected void updateButtons() {
		if (okButton != null) {
			okButton.setEnabled(getSelection().length > 0);
		}
	}

	protected void updateSelection(SelectionChangedEvent event) {
		ArrayList<MPartDescriptor> descs = new ArrayList<MPartDescriptor>();
		IStructuredSelection sel = (IStructuredSelection) event.getSelection();
		for (Iterator<?> i = sel.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof MPartDescriptor) {
				descs.add((MPartDescriptor) o);
			}
		}

		viewDescs = new MPartDescriptor[descs.size()];
		descs.toArray(viewDescs);
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		return getDialogSettings();
	}

	void handleTreeViewerKeyPressed(KeyEvent event) {
		if (descriptionHint.isVisible() && event.keyCode == SWT.F2 && event.stateMask == 0) {
			ITreeSelection selection = filteredTree.getViewer().getStructuredSelection();
			if (selection.size() == 1) {
				Object o = selection.getFirstElement();
				if (o instanceof MPartDescriptor) {
					String description = ((MPartDescriptor) o).getTooltip();
					description = LocalizationHelper.getLocalized(description, (MPartDescriptor) o,
							context);
					if (description != null && description.length() == 0)
						description = WorkbenchMessages.ShowView_noDesc;
					popUp(description);
				}
			}
		}
	}

	private void popUp(final String description) {
		new PopupDialog(filteredTree.getShell(), PopupDialog.HOVER_SHELLSTYLE, true, false, false,
				false, false, null, null) {
			private static final int CURSOR_SIZE = 15;

			@Override
			protected Point getInitialLocation(Point initialSize) {
				Display display = getShell().getDisplay();
				Point location = display.getCursorLocation();
				location.x += CURSOR_SIZE;
				location.y += CURSOR_SIZE;
				return location;
			}

			@Override
			protected Control createDialogArea(Composite parent) {
				Label label = new Label(parent, SWT.WRAP);
				label.setText(description);
				label.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent event) {
						close();
					}
				});
				GridData gd = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
				gd.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
				gd.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
				label.setLayoutData(gd);
				return label;
			}
		}.open();
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}
