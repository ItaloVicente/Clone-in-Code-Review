package org.eclipse.ui.dialogs;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;

public final class WorkingSetGroup {

	private WorkingSetConfigurationBlock workingSetBlock;

	public WorkingSetGroup(Composite composite,
			IStructuredSelection currentSelection, String[] workingSetTypes) {
		Group workingSetGroup = new Group(composite, SWT.NONE);
		workingSetGroup.setFont(composite.getFont());
		workingSetGroup
				.setText(WorkbenchMessages.WorkingSetGroup_WorkingSets_group);
		workingSetGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
				false));
		workingSetGroup.setLayout(new GridLayout(1, false));

		workingSetBlock = new WorkingSetConfigurationBlock(workingSetTypes,
				WorkbenchPlugin.getDefault().getDialogSettings());
		workingSetBlock.setWorkingSets(workingSetBlock
				.findApplicableWorkingSets(currentSelection));
		workingSetBlock.createContent(workingSetGroup);
	}

	public IWorkingSet[] getSelectedWorkingSets() {
		return workingSetBlock.getSelectedWorkingSets();
	}
}
