package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class VirtualTableView extends ViewPart {

	TableViewer viewer;

	int itemCount = 10000;

	public VirtualTableView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {

		viewer = new TableViewer(parent, SWT.VIRTUAL);
		viewer.setContentProvider(getContentProvider());
		viewer.setInput(this);
		viewer.setItemCount(itemCount);

		Composite buttonComposite = new Composite(parent, SWT.NONE);
		buttonComposite.setLayout(new GridLayout());

		Button resetInput = new Button(buttonComposite, SWT.PUSH);
		resetInput.setText("Reset input");
		resetInput.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				resetInput();
			}
		});

		Button delete = new Button(buttonComposite, SWT.PUSH);
		delete.setText("Delete selection");
		delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] selection = ((IStructuredSelection) viewer
						.getSelection()).toArray();
				doRemove(selection);
			}
		});

	}

	final protected void doRemove(Object[] selection) {
		viewer.remove(selection);
	}

	protected IContentProvider getContentProvider() {
		return new IStructuredContentProvider() {
			@Override
			public void dispose() {

			}

			@Override
			public Object[] getElements(Object inputElement) {
				String[] elements = new String[itemCount];
				for (int i = 0; i < itemCount; i++) {
					elements[i] = "Element " + String.valueOf(i);
				}
				return elements;
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {

			}
		};
	}

	@Override
	public void setFocus() {
		viewer.getTable().setFocus();

	}

	protected void resetInput() {
		viewer.setInput(this);
	}

}
