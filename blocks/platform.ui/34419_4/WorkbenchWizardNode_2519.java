package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerComparator;

public abstract class WorkbenchWizardListSelectionPage extends
        WorkbenchWizardSelectionPage implements ISelectionChangedListener,
        IDoubleClickListener {

    private static final String DIALOG_SETTING_SECTION_NAME = "WizardListSelectionPage."; //$NON-NLS-1$

    private final static int SIZING_LISTS_HEIGHT = 200;

    private static final String STORE_SELECTED_WIZARD_ID = DIALOG_SETTING_SECTION_NAME
            + "STORE_SELECTED_WIZARD_ID"; //$NON-NLS-1$

    private TableViewer viewer;

    private String message;

    protected WorkbenchWizardListSelectionPage(IWorkbench aWorkbench,
            IStructuredSelection currentSelection,
            AdaptableList wizardElements, String message, String triggerPointId) {
        super(
                "singleWizardSelectionPage", aWorkbench, currentSelection, wizardElements, triggerPointId); //$NON-NLS-1$
        setDescription(WorkbenchMessages.WizardList_description);
        this.message = message;
    }

    @Override
	public void createControl(Composite parent) {

        Font font = parent.getFont();

        Composite outerContainer = new Composite(parent, SWT.NONE);
        outerContainer.setLayout(new GridLayout());
        outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        outerContainer.setFont(font);

        Label messageLabel = new Label(outerContainer, SWT.NONE);
        messageLabel.setText(message);
        messageLabel.setFont(font);

        createViewer(outerContainer);
        layoutTopControl(viewer.getControl());

        restoreWidgetValues();

        setControl(outerContainer);
    }

    private void createViewer(Composite parent) {
        Table table = new Table(parent, SWT.BORDER);
        table.setFont(parent.getFont());

        viewer = new TableViewer(table);
        viewer.setContentProvider(new WizardContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setComparator(new WorkbenchViewerComparator());
        viewer.addSelectionChangedListener(this);
        viewer.addDoubleClickListener(this);
        viewer.setInput(wizardElements);
    }

    protected abstract IWizardNode createWizardNode(
            WorkbenchWizardElement element);

    @Override
	public void doubleClick(DoubleClickEvent event) {
        selectionChanged(new SelectionChangedEvent(event.getViewer(), event
                .getViewer().getSelection()));
        getContainer().showPage(getNextPage());
    }

    private void layoutTopControl(Control control) {
        GridData data = new GridData(GridData.FILL_BOTH);

        int availableRows = DialogUtil.availableRows(control.getParent());

        if (availableRows > 50) {
            data.heightHint = SIZING_LISTS_HEIGHT;
        } else {
            data.heightHint = availableRows * 3;
        }

        control.setLayoutData(data);

    }

    private void restoreWidgetValues() {

        IDialogSettings settings = getDialogSettings();
        if (settings == null) {
			return;
		}

        String wizardId = settings.get(STORE_SELECTED_WIZARD_ID);
        WorkbenchWizardElement wizard = findWizard(wizardId);
        if (wizard == null) {
			return;
		}

        StructuredSelection selection = new StructuredSelection(wizard);
        viewer.setSelection(selection);
    }

    public void saveWidgetValues() {
        IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
        if (sel.size() > 0) {
            WorkbenchWizardElement selectedWizard = (WorkbenchWizardElement) sel
                    .getFirstElement();
            getDialogSettings().put(STORE_SELECTED_WIZARD_ID,
                    selectedWizard.getId());
        }
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        setErrorMessage(null);
        IStructuredSelection selection = (IStructuredSelection) event
                .getSelection();
        WorkbenchWizardElement currentWizardSelection = (WorkbenchWizardElement) selection
                .getFirstElement();
        if (currentWizardSelection == null) {
            setMessage(null);
            setSelectedNode(null);
            return;
        }

        setSelectedNode(createWizardNode(currentWizardSelection));
        setMessage(currentWizardSelection.getDescription());
    }
}
