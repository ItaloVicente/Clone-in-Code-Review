package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ListSelectionDialog extends SelectionDialog {
    private Object inputElement;

    private ILabelProvider labelProvider;

    private IStructuredContentProvider contentProvider;

    CheckboxTableViewer listViewer;

    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;

    public ListSelectionDialog(Shell parentShell, Object input,
            IStructuredContentProvider contentProvider,
            ILabelProvider labelProvider, String message) {
        super(parentShell);
        setTitle(WorkbenchMessages.ListSelection_title);
        inputElement = input;
        this.contentProvider = contentProvider;
        this.labelProvider = labelProvider;
        if (message != null) {
			setMessage(message);
		} else {
			setMessage(WorkbenchMessages.ListSelection_message);
		} 
    }

    private void addSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        buttonComposite.setLayout(layout);
        buttonComposite.setLayoutData(new GridData(SWT.END, SWT.TOP, true, false));

        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, SELECT_ALL_TITLE, false);

        SelectionListener listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(true);
            }
        };
        selectButton.addSelectionListener(listener);

        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE, false);

        listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(false);
            }
        };
        deselectButton.addSelectionListener(listener);
    }

    private void checkInitialSelections() {
        Iterator itemsToCheck = getInitialElementSelections().iterator();

        while (itemsToCheck.hasNext()) {
			listViewer.setChecked(itemsToCheck.next(), true);
		}
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.LIST_SELECTION_DIALOG);
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        
        initializeDialogUnits(composite);
        
        createMessageArea(composite);

        listViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        listViewer.getTable().setLayoutData(data);

        listViewer.setLabelProvider(labelProvider);
        listViewer.setContentProvider(contentProvider);

        addSelectionButtons(composite);

        initializeViewer();

        if (!getInitialElementSelections().isEmpty()) {
			checkInitialSelections();
		}

        Dialog.applyDialogFont(composite);
        
        return composite;
    }

    protected CheckboxTableViewer getViewer() {
        return listViewer;
    }

    private void initializeViewer() {
        listViewer.setInput(inputElement);
    }

    @Override
	protected void okPressed() {

        Object[] children = contentProvider.getElements(inputElement);

        if (children != null) {
            ArrayList list = new ArrayList();
            for (int i = 0; i < children.length; ++i) {
                Object element = children[i];
                if (listViewer.getChecked(element)) {
					list.add(element);
				}
            }
            setResult(list);
        }

        super.okPressed();
    }
}
