package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ElementTreeSelectionDialog extends SelectionStatusDialog {

    private TreeViewer fViewer;

	private IBaseLabelProvider fLabelProvider;

    private ITreeContentProvider fContentProvider;

    private ISelectionStatusValidator fValidator = null;

    private ViewerComparator fComparator;

    private boolean fAllowMultiple = true;

    private boolean fDoubleClickSelects = true;

    private String fEmptyListMessage = WorkbenchMessages.ElementTreeSelectionDialog_nothing_available;

    private IStatus fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
            IStatus.OK, "", null); //$NON-NLS-1$

    private List fFilters;

    private Object fInput;

    private boolean fIsEmpty;

    private int fWidth = 60;

    private int fHeight = 18;

    public ElementTreeSelectionDialog(Shell parent,
            ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		this(parent, (IBaseLabelProvider) labelProvider, contentProvider);
	}

	public ElementTreeSelectionDialog(Shell parent, IBaseLabelProvider labelProvider,
			ITreeContentProvider contentProvider) {
        super(parent);

        fLabelProvider = labelProvider;
        fContentProvider = contentProvider;

        setResult(new ArrayList(0));
        setStatusLineAboveButtons(true);
    }

    public void setInitialSelection(Object selection) {
        setInitialSelections(new Object[] { selection });
    }

    public void setEmptyListMessage(String message) {
        fEmptyListMessage = message;
    }

    public void setAllowMultiple(boolean allowMultiple) {
        fAllowMultiple = allowMultiple;
    }

    public void setDoubleClickSelects(boolean doubleClickSelects) {
        fDoubleClickSelects = doubleClickSelects;
    }

    @Deprecated
	public void setSorter(ViewerSorter sorter) {
        fComparator = sorter;
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

    public void setSize(int width, int height) {
        fWidth = width;
        fHeight = height;
    }

    protected void updateOKStatus() {
        if (!fIsEmpty) {
            if (fValidator != null) {
                fCurrStatus = fValidator.validate(getResult());
                updateStatus(fCurrStatus);
            } else {
                fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null);
            }
        } else {
            fCurrStatus = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                    IStatus.ERROR, fEmptyListMessage, null);
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
        setResult(((IStructuredSelection) fViewer.getSelection()).toList());
    }

    @Override
	public void create() {
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                access$superCreate();
                fViewer.setSelection(new StructuredSelection(
                        getInitialElementSelections()), true);
                updateOKStatus();
            }
        });
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        Label messageLabel = createMessageArea(composite);
        TreeViewer treeViewer = createTreeViewer(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);

        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        if (fIsEmpty) {
            messageLabel.setEnabled(false);
            treeWidget.setEnabled(false);
        }

        return composite;
    }

    protected TreeViewer createTreeViewer(Composite parent) {
        int style = SWT.BORDER | (fAllowMultiple ? SWT.MULTI : SWT.SINGLE);

        fViewer = doCreateTreeViewer(parent, style);
        fViewer.setContentProvider(fContentProvider);
        fViewer.setLabelProvider(fLabelProvider);
        fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                access$setResult(((IStructuredSelection) event.getSelection())
                        .toList());
                updateOKStatus();
            }
        });

        fViewer.setComparator(fComparator);
        if (fFilters != null) {
            for (int i = 0; i != fFilters.size(); i++) {
				fViewer.addFilter((ViewerFilter) fFilters.get(i));
			}
        }

        if (fDoubleClickSelects) {
            Tree tree = fViewer.getTree();
            tree.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetDefaultSelected(SelectionEvent e) {
                    updateOKStatus();
                    if (fCurrStatus.isOK()) {
						access$superButtonPressed(IDialogConstants.OK_ID);
					}
                }
            });
        }
        fViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                updateOKStatus();

                if (!(fDoubleClickSelects && fCurrStatus.isOK())) {
                    ISelection selection = event.getSelection();
                    if (selection instanceof IStructuredSelection) {
                        Object item = ((IStructuredSelection) selection)
                                .getFirstElement();
                        if (fViewer.getExpandedState(item)) {
							fViewer.collapseToLevel(item, 1);
						} else {
							fViewer.expandToLevel(item, 1);
						}
                    }
                }
            }
        });

        fViewer.setInput(fInput);

        return fViewer;
    }

	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		return new TreeViewer(new Tree(parent, style));
	}

    protected TreeViewer getTreeViewer() {
        return fViewer;
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

    protected void access$superButtonPressed(int id) {
        super.buttonPressed(id);
    }

    protected void access$setResult(List result) {
        super.setResult(result);
    }

    @Override
	protected void handleShellCloseEvent() {
        super.handleShellCloseEvent();

        if (getReturnCode() == CANCEL) {
			setResult(null);
		}
    }

}
