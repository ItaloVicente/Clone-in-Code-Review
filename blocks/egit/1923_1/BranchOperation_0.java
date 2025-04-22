package org.eclipse.egit.core;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class IteratorService {

	public static WorkingTreeIterator createInitialIterator(
			Repository repository) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = findContainer(root, repository.getWorkTree());
		if (container != null)
			return new ContainerTreeIterator(container);
		return new AdaptableFileTreeIterator(repository.getWorkTree(), root);
	}

	public static IContainer findContainer(IWorkspaceRoot root, File file) {
		if (!file.isDirectory())
			throw new IllegalArgumentException(
					"file " + file.getAbsolutePath() + " is no directory"); //$NON-NLS-1$//$NON-NLS-2$
		final IContainer[] containers = root.findContainersForLocationURI(file
				.toURI());
		for (IContainer container : containers)
			if (container.isAccessible() && isProjectSharedWithGit(container))
				return container;
		return null;
	}

	private static boolean isProjectSharedWithGit(IContainer container) {
		return RepositoryMapping.getMapping(container) != null;
	}

}
