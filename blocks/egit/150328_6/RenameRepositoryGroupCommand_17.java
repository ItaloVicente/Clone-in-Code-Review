package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroupNode;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class DeleteRepositoryGroupConfirmDialog extends TitleAreaDialog {

	private List<RepositoryGroupNode> groupsToDelete;

	public DeleteRepositoryGroupConfirmDialog(Shell parentShell,
			List<RepositoryGroupNode> groupsToDelete) {
		super(parentShell);
		setHelpAvailable(false);
		this.groupsToDelete = groupsToDelete;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		main.setLayout(new GridLayout(1, false));
		TableViewer projectsViewer = new TableViewer(main,
				SWT.BORDER | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(projectsViewer.getControl());
		projectsViewer.setLabelProvider(
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		projectsViewer.setContentProvider(ArrayContentProvider.getInstance());
		projectsViewer.setInput(groupsToDelete);
		setTitle(UIText.RepositoriesView_RepoGroup_Delete_Title);
		setMessage(UIText.RepositoriesView_RepoGroup_Delete_Confirm);
		return main;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID,
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryConfirmButton,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
}
