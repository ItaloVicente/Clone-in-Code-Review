package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroups;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class AddRepositoryGroupCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoryGroups groups = new RepositoryGroups();

		String groupName = getNewGroupName(
				getActiveShell(event),
				UIText.RepositoriesView_RepoGroup_Add_Title, groups, ""); //$NON-NLS-1$
		if (groupName != null) {
			UUID groupId = groups.addGroup(groupName);
			List<String> repoDirs = getSelectedRepositories(event);
			if (!repoDirs.isEmpty()) {
				groups.addRepositoriesToGroup(groupId, repoDirs);
			}
			getView(event).refresh();
		}
		return null;
	}

	private List<String> getSelectedRepositories(ExecutionEvent event)
			throws ExecutionException {
		List<String> repoDirs = new ArrayList<>();
		List<RepositoryTreeNode> elements = getSelectedNodes(event);
		for (Object element : elements) {
			if (element instanceof RepositoryNode) {
				File dir = ((RepositoryNode) element).getRepository()
						.getDirectory();
				repoDirs.add(dir.toString());
			}
		}
		return repoDirs;
	}

	static String getNewGroupName(Shell shell, String title,
			RepositoryGroups groups, String initialName) {
		InputDialog inputDialog = new InputDialog(shell, title,
				UIText.RepositoriesView_RepoGroup_EnterName,
				initialName,
				new IInputValidator() {

					@Override
					public String isValid(String name) {
						try {
							if (groups.groupExists(name)) {
								return UIText.RepositoriesView_RepoGroup_GroupExists;
							}
						} catch (IllegalArgumentException e) {
							return UIText.RepositoriesView_RepoGroup_InvalidName;
						}
						return null;
					}

				});

		if (inputDialog.open() == Window.OK) {
			return inputDialog.getValue();
		} else {
			return null;
		}
	}
}
