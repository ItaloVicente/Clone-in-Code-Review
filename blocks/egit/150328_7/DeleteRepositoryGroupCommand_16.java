package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroups;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.swt.widgets.Shell;

public class AddRepositoryGroupCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public static final String COMMAND_ID = "org.eclipse.egit.ui.RepositoriesAddGroup"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoryGroups groupsUtil = RepositoryGroups.getInstance();

		String groupName = getNewGroupName(getActiveShell(event),
				UIText.RepositoriesView_RepoGroup_Add_Title, groupsUtil, ""); //$NON-NLS-1$
		if (groupName != null) {
			UUID groupId = groupsUtil.addGroup(groupName);
			List<File> repoDirs = getSelectedRepositories(event);
			if (!repoDirs.isEmpty()) {
				groupsUtil.addRepositoriesToGroup(groupId, repoDirs);
			}
			getView(event).refresh();
		}
		return null;
	}

	private List<File> getSelectedRepositories(ExecutionEvent event)
			throws ExecutionException {
		return getSelectedNodes(event).stream()
				.filter(node -> node instanceof RepositoryNode)
				.map(e -> e.getRepository().getDirectory())
				.collect(Collectors.toList());
	}

	static String getNewGroupName(Shell shell, String title,
			RepositoryGroups groupsUtil, String initialName) {
		InputDialog inputDialog = new InputDialog(shell, title,
				UIText.RepositoriesView_RepoGroup_EnterName, initialName,
				name -> {
					if (name == null
							|| StringUtils.isEmptyOrNull(name.trim())) {
						return UIText.RepositoriesView_RepoGroup_EmptyNameError;
					}
					if (groupsUtil.groupExists(name.trim())) {
						return UIText.RepositoriesView_RepoGroup_GroupExists;
					}
					return null;
				});

		if (inputDialog.open() == Window.OK) {
			return inputDialog.getValue().trim();
		} else {
			return null;
		}
	}
}
