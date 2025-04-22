package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

public class CheckedTreeSelectionDialog extends SelectionStatusDialog {
    private CheckboxTreeViewer fViewer;

    private ILabelProvider fLabelProvider;

    private ITreeContentProvider fContentProvider;

    private ISelectionStatusValidator fValidator = null;

    private ViewerComparator fComparator;

    private String fEmptyListMessage = WorkbenchMessages.CheckedTreeSelectionDialog_nothing_available; 

    private IStatus fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
            0, "", null); //$NON-NLS-1$

    private List fFilters;

    private Object fInput;

    private boolean fIsEmpty;

    private int fWidth = 60;

    private int fHeight = 18;

    private boolean fContainerMode;

    private Object[] fExpandedElements;

	private int fStyle = SWT.BORDER;

    public CheckedTreeSelectionDialog(Shell parent,
            ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		this(parent, labelProvider, contentProvider, SWT.BORDER);
    }

	public CheckedTreeSelectionDialog(Shell parent, ILabelProvider labelProvider,
			ITreeContentProvider contentProvider, int style) {
		super(parent);
		fLabelProvider = labelProvider;
		fContentProvider = contentProvider;
		setResult(new ArrayList(0));
		setStatusLineAboveButtons(true);
		fContainerMode = false;
		fExpandedElements = null;
		fStyle = style;
	}

    public void setContainerMode(boolean containerMode) {
        fContainerMode = containerMode;
    }

    public void setInitialSelection(Object selection) {
        setInitialSelections(new Object[] { selection });
    }

    public void setEmptyListMessage(String message) {
        fEmptyListMessage = message;
    }

    @Deprecated
	public void setSorter(ViewerSorter sorter) {
        fComparator = sorter;
    }
    
	public void setStyle(int style) {
		fStyle = style;
	}

    public void setComparator(ViewerComparator comparator){
    	fComparator = comparator;
    }

    public void addFilter(ViewerFilter filter) {
        if (fFilters == null) {
			fFilters = new ArrayList(4);
		}
        fFilters.add(filter);
    }

    public void setValidator(ISelectionStatusValidator validator) {
        fValidator = validator;
    }

    public void setInput(Object input) {
        fInput = input;
    }

    public void setExpandedElements(Object[] elements) {
        fExpandedElements = elements;
    }

    public void setSize(int width, int height) {
        fWidth = width;
        fHeight = height;
    }

    protected void updateOKStatus() {
        if (!fIsEmpty) {
            if (fValidator != null) {
                fCurrStatus = fValidator.validate(fViewer.getCheckedElements());
                updateStatus(fCurrStatus);
            } else if (!fCurrStatus.isOK()) {
                fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null);
            }
        } else {
            fCurrStatus = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                    IStatus.OK, fEmptyListMessage, null);
        }
        updateStatus(fCurrStatus);
    }

    @Override
	public int open() {
        fIsEmpty = evaluateIfTreeEmpty(fInput);
        super.open();
        return getReturnCode();
    }

    private void access$superCreate() {
        super.create();
    }

    @Override
	protected void cancelPressed() {
        setResult(null);
        super.cancelPressed();
    }

    @Override
	protected void computeResult() {
        setResult(Arrays.asList(fViewer.getCheckedElements()));
    }

    @Override
	public void create() {
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                access$superCreate();
                fViewer.setCheckedElements(getInitialElementSelections()
                        .toArray());
                if (fExpandedElements != null) {
                    fViewer.setExpandedElements(fExpandedElements);
                }
                updateOKStatus();
            }
        });
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        Label messageLabel = createMessageArea(composite);
        CheckboxTreeViewer treeViewer = createTreeViewer(composite);
        Control buttonComposite = createSelectionButtons(composite);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);
        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());
        if (fIsEmpty) {
            messageLabel.setEnabled(false);
            treeWidget.setEnabled(false);
            buttonComposite.setEnabled(false);
        }
        return composite;
    }

    protected CheckboxTreeViewer createTreeViewer(Composite parent) {
        if (fContainerMode) {
			fViewer = new ContainerCheckedTreeViewer(parent, fStyle);
        } else {
			fViewer = new CheckboxTreeViewer(parent, fStyle);
        }
        fViewer.setContentProvider(fContentProvider);
        fViewer.setLabelProvider(fLabelProvider);
        fViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                updateOKStatus();
            }
        });
        fViewer.setComparator(fComparator);
        if (fFilters != null) {
            for (int i = 0; i != fFilters.size(); i++) {
				fViewer.addFilter((ViewerFilter) fFilters.get(i));
			}
        }
        fViewer.setInput(fInput);
        return fViewer;
    }

    protected CheckboxTreeViewer getTreeViewer() {
        return fViewer;
    }

    protected Composite createSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		buttonComposite.setLayout(layout);
        buttonComposite.setFont(composite.getFont());
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
                | GridData.GRAB_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        buttonComposite.setLayoutData(data);
        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, WorkbenchMessages.CheckedTreeSelectionDialog_select_all,
                false);
        SelectionListener listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                Object[] viewerElements = fContentProvider.getElements(fInput);
                if (fContainerMode) {
					fViewer.setCheckedElements(viewerElements);
				} else {
                    for (int i = 0; i < viewerElements.length; i++) {
						fViewer.setSubtreeChecked(viewerElements[i], true);
					}
                }
                updateOKStatus();
            }
        };
        selectButton.addSelectionListener(listener);
        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, WorkbenchMessages.CheckedTreeSelectionDialog_deselect_all,
                false);
        listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                fViewer.setCheckedElements(new Object[0]);
                updateOKStatus();
            }
        };
        deselectButton.addSelectionListener(listener);
        return buttonComposite;
    }

    private boolean evaluateIfTreeEmpty(Object input) {
        Object[] elements = fContentProvider.getElements(input);
        if (elements.length > 0) {
            if (fFilters != null) {
                for (int i = 0; i < fFilters.size(); i++) {
                    ViewerFilter curr = (ViewerFilter) fFilters.get(i);
                    elements = curr.filter(fViewer, input, elements);
                }
            }
        }
        return elements.length == 0;
    }
}
