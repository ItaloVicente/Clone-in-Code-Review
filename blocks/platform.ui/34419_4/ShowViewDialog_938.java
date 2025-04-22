
package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ITriggerPoint;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.activities.ws.ActivityMessages;
import org.eclipse.ui.internal.activities.ws.ActivityViewerFilter;
import org.eclipse.ui.internal.activities.ws.WorkbenchTriggerPoints;
import org.eclipse.ui.model.PerspectiveLabelProvider;

public class SelectPerspectiveDialog extends Dialog implements
        ISelectionChangedListener {

    final private static int LIST_HEIGHT = 300;

    final private static int LIST_WIDTH = 300;

    private TableViewer list;

    private Button okButton;

    private IPerspectiveDescriptor perspDesc;

    private IPerspectiveRegistry perspReg;

    private ActivityViewerFilter activityViewerFilter = new ActivityViewerFilter();

    private Button showAllButton;

    public SelectPerspectiveDialog(Shell parentShell,
            IPerspectiveRegistry perspReg) {
        super(parentShell);
        this.perspReg = perspReg;
		setShellStyle(getShellStyle() | SWT.SHEET);
    }

    @Override
	protected void cancelPressed() {
        perspDesc = null;
        super.cancelPressed();
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(WorkbenchMessages.SelectPerspective_shellTitle);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.SELECT_PERSPECTIVE_DIALOG);
    }

    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
        updateButtons();
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.setFont(parent.getFont());

        createViewer(composite);
        layoutTopControl(list.getControl());
        if (needsShowAllButton()) {
            createShowAllButton(composite);
        }
        return composite;
    }

    private boolean needsShowAllButton() {
        return activityViewerFilter.getHasEncounteredFilteredItem();
    }

    private void createShowAllButton(Composite parent) {
        showAllButton = new Button(parent, SWT.CHECK);
        showAllButton
                .setText(ActivityMessages.Perspective_showAll);
        showAllButton.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(SelectionEvent e) {
                if (showAllButton.getSelection()) {
                    list.resetFilters();
                } else {
                    list.addFilter(activityViewerFilter);
                }
            }
        });

    }

    private void createViewer(Composite parent) {
        list = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER);
        list.getTable().setFont(parent.getFont());
		list.setLabelProvider(new PerspectiveLabelProvider());
        list.setContentProvider(new PerspContentProvider());
        list.addFilter(activityViewerFilter);
        list.setComparator(new ViewerComparator());
        list.setInput(perspReg);
        list.addSelectionChangedListener(this);
        list.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                handleDoubleClickEvent();
            }
        });
    }

    public IPerspectiveDescriptor getSelection() {
        return perspDesc;
    }

    protected void handleDoubleClickEvent() {
        okPressed();
    }

    private void layoutTopControl(Control control) {
        GridData spec = new GridData(GridData.FILL_BOTH);
        spec.widthHint = LIST_WIDTH;
        spec.heightHint = LIST_HEIGHT;
        control.setLayoutData(spec);
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        updateSelection(event);
        updateButtons();
    }

    protected void updateButtons() {
        okButton.setEnabled(getSelection() != null);
    }

    protected void updateSelection(SelectionChangedEvent event) {
        perspDesc = null;
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        if (!sel.isEmpty()) {
            Object obj = sel.getFirstElement();
            if (obj instanceof IPerspectiveDescriptor) {
				perspDesc = (IPerspectiveDescriptor) obj;
			}
        }
    }

    @Override
	protected void okPressed() {
        ITriggerPoint triggerPoint = PlatformUI.getWorkbench()
                .getActivitySupport().getTriggerPointManager().getTriggerPoint(
                        WorkbenchTriggerPoints.OPEN_PERSPECITVE_DIALOG);
        if (WorkbenchActivityHelper.allowUseOf(triggerPoint, getSelection())) {
			super.okPressed();
		}
    }
    
    @Override
	protected boolean isResizable() {
    	return true;
    }
}
