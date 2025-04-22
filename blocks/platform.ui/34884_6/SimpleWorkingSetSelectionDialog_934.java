package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.ViewRegistry;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class ShowViewDialog extends Dialog implements
        ISelectionChangedListener, IDoubleClickListener {

    private static final String DIALOG_SETTING_SECTION_NAME = "ShowViewDialog"; //$NON-NLS-1$

    private static final int LIST_HEIGHT = 300;

    private static final int LIST_WIDTH = 250;

    private static final String STORE_EXPANDED_CATEGORIES_ID = DIALOG_SETTING_SECTION_NAME
            + ".STORE_EXPANDED_CATEGORIES_ID"; //$NON-NLS-1$

    private static final String STORE_SELECTED_VIEW_ID = DIALOG_SETTING_SECTION_NAME
            + ".STORE_SELECTED_VIEW_ID"; //$NON-NLS-1$

    private FilteredTree filteredTree;

    private Button okButton;

    private IViewDescriptor[] viewDescs = new IViewDescriptor[0];

    private IViewRegistry viewReg;

	private IWorkbenchWindow window;

	private Color dimmedForeground;

	private Label descriptionHint;

    public ShowViewDialog(IWorkbenchWindow window, IViewRegistry viewReg) {
        super(window.getShell());
        this.window = window;
        this.viewReg = viewReg;
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
        viewDescs = new IViewDescriptor[0];
        super.cancelPressed();
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(WorkbenchMessages.ShowView_shellTitle);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.SHOW_VIEW_DIALOG);
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
		filteredTree.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));
		
		TreeViewer treeViewer = filteredTree.getViewer();
		Control treeControl = treeViewer.getControl();
		RGB dimmedRGB = blend(treeControl.getForeground().getRGB(), treeControl.getBackground().getRGB(), 60);
		dimmedForeground = new Color(treeControl.getDisplay(), dimmedRGB);
		treeControl.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dimmedForeground.dispose();
			}
		});
		
		treeViewer.setLabelProvider(new ViewLabelProvider(window, dimmedForeground));
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setComparator(new ViewComparator((ViewRegistry) viewReg));
		treeViewer.setInput(viewReg);
		treeViewer.addSelectionChangedListener(this);
		treeViewer.addDoubleClickListener(this);
		treeViewer.addFilter(new CapabilityFilter());
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
		ITreeContentProvider contentProvider = (ITreeContentProvider) tree
				.getContentProvider();
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
            filteredTree.getViewer().setExpandedState(element, !filteredTree.getViewer().getExpandedState(element));
        } else if (viewDescs.length > 0) {
            saveWidgetValues();
            setReturnCode(OK);
            close();
        }
    }

    protected IDialogSettings getDialogSettings() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings section = workbenchSettings
                .getSection(DIALOG_SETTING_SECTION_NAME);
        if (section == null) {
			section = workbenchSettings
                    .addNewSection(DIALOG_SETTING_SECTION_NAME);
		}
        return section;
    }

    public IViewDescriptor[] getSelection() {
        return viewDescs;
    }

    private void layoutTopControl(Control control) {
        GridData spec = new GridData(GridData.FILL_BOTH);
        spec.widthHint = LIST_WIDTH;
        spec.heightHint = LIST_HEIGHT;
        control.setLayoutData(spec);
    }

    protected void restoreWidgetValues() {
		Object selection = null;

        IDialogSettings settings = getDialogSettings();
        String[] expandedCategoryIds = settings
                .getArray(STORE_EXPANDED_CATEGORIES_ID);
		if (expandedCategoryIds != null) {
			ViewRegistry reg = (ViewRegistry) viewReg;
			ArrayList categoriesToExpand = new ArrayList(expandedCategoryIds.length);
			for (int i = 0; i < expandedCategoryIds.length; i++) {
				IViewCategory category = reg.findCategory(expandedCategoryIds[i]);
				if (category != null) {
					categoriesToExpand.add(category);
				}
			}

			if (!categoriesToExpand.isEmpty()) {
				filteredTree.getViewer().setExpandedElements(categoriesToExpand.toArray());
			}

			String selectedViewId = settings.get(STORE_SELECTED_VIEW_ID);
			if (selectedViewId != null) {
				selection = reg.find(selectedViewId);
			}
        }

		if (selection == null)
			selection = filteredTree.getViewer().getTree().getItem(0).getData();

		filteredTree.getViewer().setSelection(new StructuredSelection(selection), true);

    }

    protected void saveWidgetValues() {
        IDialogSettings settings = getDialogSettings();

        Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
        String[] expandedCategoryIds = new String[expandedElements.length];
        for (int i = 0; i < expandedElements.length; ++i) {
			expandedCategoryIds[i] = ((IViewCategory) expandedElements[i]).getId();
		}

        settings.put(STORE_EXPANDED_CATEGORIES_ID, expandedCategoryIds);
        
        String selectedViewId = ""; //$NON-NLS-1$
        if (viewDescs.length > 0) {
            selectedViewId = viewDescs[0].getId();
        }
        settings.put(STORE_SELECTED_VIEW_ID, selectedViewId);
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        updateSelection(event);
        updateButtons();
		descriptionHint.setVisible(viewDescs.length == 1
				&& viewDescs[0].getDescription().length() > 0);
	}

    protected void updateButtons() {
        if (okButton != null) {
            okButton.setEnabled(getSelection().length > 0);
        }
    }

    protected void updateSelection(SelectionChangedEvent event) {
        ArrayList descs = new ArrayList();
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        for (Iterator i = sel.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o instanceof IViewDescriptor) {
                descs.add(o);
			}
		}
		viewDescs = new IViewDescriptor[descs.size()];
		descs.toArray(viewDescs);
    }


	@Override
	protected IDialogSettings getDialogBoundsSettings() {
        return getDialogSettings();
	}
    void handleTreeViewerKeyPressed(KeyEvent event) {

		if (descriptionHint.isVisible() && event.keyCode == SWT.F2
				&& event.stateMask == 0) {
			ITreeSelection selection = filteredTree.getViewer().getStructuredSelection();
			if (selection.size() == 1) {
				Object o = selection.getFirstElement();
				if (o instanceof IViewDescriptor) {
					String description = ((IViewDescriptor) o).getDescription();
					if (description.length() == 0)
						description = WorkbenchMessages.ShowView_noDesc;
					popUp(description);
				}
			}
		}
	}

	private void popUp(final String description) {
		new PopupDialog(filteredTree.getShell(), PopupDialog.HOVER_SHELLSTYLE,
				true, false, false, false, false, null, null) {
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
				GridData gd = new GridData(GridData.BEGINNING
						| GridData.FILL_BOTH);
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
