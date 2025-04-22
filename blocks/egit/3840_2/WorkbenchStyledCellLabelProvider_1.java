package org.eclipse.egit.ui.internal;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class WorkbenchStyledCellLabelProvider extends
		DelegatingStyledCellLabelProvider implements ILabelProvider {

	private final WorkbenchLabelProvider wrapped;

	public WorkbenchStyledCellLabelProvider() {
		super(new WorkbenchLabelProvider());
		wrapped = (WorkbenchLabelProvider) getStyledStringProvider();
	}

	public String getText(Object element) {
		return wrapped.getText(element);
	}
}
