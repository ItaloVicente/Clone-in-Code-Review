package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class DecoratorTableTreeView extends DecoratorTestPart {
	
	TableTreeViewer viewer;

	public DecoratorTableTreeView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableTreeViewer(parent);

		viewer.setLabelProvider(getLabelProvider());

		viewer.setContentProvider(new TestTreeContentProvider());
		viewer.setInput(this);

		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL
				| GridData.FILL_BOTH);
		
		viewer.getControl().setLayoutData(data);

	}

	@Override
	public void setFocus() {

	}

}
