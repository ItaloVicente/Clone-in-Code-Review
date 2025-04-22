package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.EgitUiEditorUtils;
import org.eclipse.egit.ui.internal.repository.tree.FileNode;

public class OpenInEditorCommand extends
		RepositoriesViewCommandHandler<FileNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FileNode node = getSelectedNodes(event).get(0);
		File file = node.getObject();
		EgitUiEditorUtils.openEditor(file, getView(event).getSite().getPage());
		return null;
	}
}
