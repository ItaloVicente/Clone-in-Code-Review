package org.eclipse.ui.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractElementListSelectionDialog extends SelectionStatusDialog {

    private ILabelProvider fRenderer;

    private boolean fIgnoreCase = true;

    private boolean fIsMultipleSelection = false;

    private boolean fMatchEmptyString = true;

    private boolean fAllowDuplicates = true;

    private Label fMessage;

    protected FilteredList fFilteredList;

    private Text fFilterText;

    private ISelectionStatusValidator fValidator;

    private String fFilter = null;

    private String fEmptyListMessage = ""; //$NON-NLS-1$

    private String fEmptySelectionMessage = ""; //$NON-NLS-1$

    private int fWidth = 60;

    private int fHeight = 18;

    private Object[] fSelection = new Object[0];

    protected AbstractElementListSelectionDialog(Shell parent,
            ILabelProvider renderer) {
        super(parent);
        fRenderer = renderer;
    }

    protected void handleDefaultSelected() {
        if (validateCurrentSelection()) {
			buttonPressed(IDialogConstants.OK_ID);
		}
    }

    public void setIgnoreCase(boolean ignoreCase) {
        fIgnoreCase = ignoreCase;
    }

    public boolean isCaseIgnored() {
        return fIgnoreCase;
    }

    public void setMatchEmptyString(boolean matchEmptyString) {
        fMatchEmptyString = matchEmptyString;
    }

    public void setMultipleSelection(boolean multipleSelection) {
        fIsMultipleSelection = multipleSelection;
    }

    public void setAllowDuplicates(boolean allowDuplicates) {
        fAllowDuplicates = allowDuplicates;
    }

    public void setSize(int width, int height) {
        fWidth = width;
        fHeight = height;
    }

    public void setEmptyListMessage(String message) {
        fEmptyListMessage = message;
    }

    public void setEmptySelectionMessage(String message) {
        fEmptySelectionMessage = message;
    }

    public void setValidator(ISelectionStatusValidator validator) {
        fValidator = validator;
    }

    protected void setListElements(Object[] elements) {
        Assert.isNotNull(fFilteredList);
        fFilteredList.setElements(elements);
		handleElementsChanged();
    }

	protected void handleElementsChanged() {
		boolean enabled = !fFilteredList.isEmpty();

		if (fMessage != null && !fMessage.isDisposed())
			fMessage.setEnabled(enabled);

		fFilteredList.setEnabled(enabled);
		updateOkState();
	}

    public void setFilter(String filter) {
        if (fFilterText == null) {
			fFilter = filter;
		} else {
			fFilterText.setText(filter);
		}
    }

    public String getFilter() {
        if (fFilteredList == null) {
			return fFilter;
		}
		return fFilteredList.getFilter();
    }

    protected int[] getSelectionIndices() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelectionIndices();
    }

    protected int getSelectionIndex() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelectionIndex();
    }

    protected void setSelection(Object[] selection) {
        Assert.isNotNull(fFilteredList);
        fFilteredList.setSelection(selection);
    }

    protected Object[] getSelectedElements() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelection();
    }

    public Object[] getFoldedElements(int index) {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getFoldedElements(index);
    }

    @Override
	protected Label createMessageArea(Composite composite) {
        Label label = super.createMessageArea(composite);

        GridData data = new GridData();
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        label.setLayoutData(data);

        fMessage = label;

        return label;
    }

    protected void handleSelectionChanged() {
        validateCurrentSelection();
    }

    protected boolean validateCurrentSelection() {
        Assert.isNotNull(fFilteredList);

        IStatus status;
        Object[] elements = getSelectedElements();

        if (elements.length > 0) {
            if (fValidator != null) {
                status = fValidator.validate(elements);
            } else {
                status = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null);
            }
        } else {
            if (fFilteredList.isEmpty()) {
                status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                        IStatus.ERROR, fEmptyListMessage, null);
            } else {
                status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                        IStatus.ERROR, fEmptySelectionMessage, null);
            }
        }

        updateStatus(status);

        return status.isOK();
    }

    @Override
	protected void cancelPressed() {
        setResult(null);
        super.cancelPressed();
    }

    protected FilteredList createFilteredList(Composite parent) {
        int flags = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
                | (fIsMultipleSelection ? SWT.MULTI : SWT.SINGLE);

        FilteredList list = new FilteredList(parent, flags, fRenderer,
                fIgnoreCase, fAllowDuplicates, fMatchEmptyString);

        GridData data = new GridData();
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        list.setLayoutData(data);
        list.setFont(parent.getFont());
        list.setFilter((fFilter == null ? "" : fFilter)); //$NON-NLS-1$		

        list.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                handleDefaultSelected();
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                handleWidgetSelected();
            }
        });

        fFilteredList = list;

        return list;
    }

    private void handleWidgetSelected() {
        Object[] newSelection = fFilteredList.getSelection();

        if (newSelection.length != fSelection.length) {
            fSelection = newSelection;
            handleSelectionChanged();
        } else {
            for (int i = 0; i != newSelection.length; i++) {
                if (!newSelection[i].equals(fSelection[i])) {
                    fSelection = newSelection;
                    handleSelectionChanged();
                    break;
                }
            }
        }
    }

    protected Text createFilterText(Composite parent) {
        Text text = new Text(parent, SWT.BORDER);

        GridData data = new GridData();
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        text.setLayoutData(data);
        text.setFont(parent.getFont());

        text.setText((fFilter == null ? "" : fFilter)); //$NON-NLS-1$

        Listener listener = new Listener() {
            @Override
			public void handleEvent(Event e) {
                fFilteredList.setFilter(fFilterText.getText());
            }
        };
        text.addListener(SWT.Modify, listener);

        text.addKeyListener(new KeyListener() {
            @Override
			public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.ARROW_DOWN) {
					fFilteredList.setFocus();
				}
            }

            @Override
			public void keyReleased(KeyEvent e) {
            }
        });

        fFilterText = text;

        return text;
    }

    @Override
	public int open() {
        super.open();
        return getReturnCode();
    }

    private void access$superCreate() {
        super.create();
    }

    @Override
	public void create() {

        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                access$superCreate();

                Assert.isNotNull(fFilteredList);

                if (fFilteredList.isEmpty()) {
                    handleEmptyList();
                } else {
                    validateCurrentSelection();
                    fFilterText.selectAll();
                    fFilterText.setFocus();
                }
            }
        });

    }

    protected void handleEmptyList() {
		fMessage.setEnabled(false);
		fFilterText.setEnabled(false);
		fFilteredList.setEnabled(false);
        updateOkState();
    }

    protected void updateOkState() {
        Button okButton = getOkButton();
        if (okButton != null) {
			okButton.setEnabled(getSelectedElements().length != 0);
		}
    }
    
    protected ISelectionStatusValidator getValidator() {
        return fValidator;
    }
}
