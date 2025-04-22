package org.eclipse.ui.examples.jobs.views;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
public class LazyTreeView extends ViewPart {
	protected TreeViewer viewer;
	protected Button serializeButton, batchButton;

	public void createPartControl(Composite top) {
		Composite parent = new Composite(top, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		parent.setLayoutData(data);
		serializeButton = new Button(parent, SWT.CHECK | SWT.FLAT);
		serializeButton.setText("Serialize fetch jobs"); //$NON-NLS-1$
		serializeButton.setSelection(SlowElementAdapter.isSerializeFetching());
		serializeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SlowElementAdapter.setSerializeFetching(serializeButton.getSelection());
			}
		});
		serializeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		batchButton = new Button(parent, SWT.CHECK | SWT.FLAT);
		batchButton.setText("Batch returned children"); //$NON-NLS-1$
		serializeButton.setSelection(SlowElementAdapter.isBatchFetchedChildren());
		batchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SlowElementAdapter.setBatchFetchedChildren(batchButton.getSelection());
			}
		});
		batchButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new DeferredContentProvider());
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setInput(new SlowElement("root")); //$NON-NLS-1$
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
	}
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
