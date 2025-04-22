package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.operations.DeleteResourcesOperationUI;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.repository.tree.FileNode;

public class DeleteFileCommand extends RepositoriesViewCommandHandler<FileNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IResource> resources = new ArrayList<IResource>();
		Collection<IPath> paths = getSelectedFileAndFolderPaths(event);

		for (IPath path : paths) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = getResource(path, root);
			resources.add(resource);
		}
		RepositoriesView view = getView(event);
		DeleteResourcesOperationUI operation = new DeleteResourcesOperationUI(resources, view.getSite());
		operation.run();

		view.refresh();
		return null;
	}

	private IResource getResource(IPath path, IWorkspaceRoot root) {
		IResource resource = root.getFileForLocation(path);
		if (resource == null || !resource.exists())
			resource = root.getContainerForLocation(path);
		if (resource == null || !resource.exists())
			resource = root.getFile(path);
		return resource;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		enableWorkingDirCommand(evaluationContext);
	}

}
