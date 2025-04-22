package org.eclipse.egit.ui.internal.operations;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.mapping.ISynchronizationScopeManager;
import org.eclipse.team.ui.synchronize.ModelOperation;
import org.eclipse.ui.IWorkbenchPart;

public class GitScopeOperation extends ModelOperation {

	public GitScopeOperation(IWorkbenchPart part,
			ISynchronizationScopeManager manager) {
		super(part, manager);
	}

	@Override
	protected void execute(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
	}

	List<IResource> getRelevantResources() {
		List<IResource> resourcesInScope = new ArrayList<IResource>();
		ResourceTraversal[] traversals = getScope().getTraversals();
		for (ResourceTraversal resourceTraversal : traversals)
			resourcesInScope.addAll(Arrays.asList(resourceTraversal
					.getResources()));
		return resourcesInScope;
	}

	@Override
	protected boolean promptForInputChange(String requestPreviewMessage,
			IProgressMonitor monitor) {
		List<IResource> relevantResources = getRelevantResources();
		boolean showPrompt = false;

		for (IResource resource : relevantResources)
			showPrompt |= hasChanged(resource);

		if(showPrompt)
				return super.promptForInputChange(requestPreviewMessage, monitor);

		return false;
	}

	private boolean hasChanged(IResource resource) {
		boolean hasChanged = false;
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		try {
			Repository repository = mapping.getRepository();
			Status repoStatus = new Git(repository).status().call();
			String path = resource.getFullPath().removeFirstSegments(1)
					.toOSString();
			hasChanged |= repoStatus.getAdded().contains(path);
			hasChanged |= repoStatus.getChanged().contains(path);
			hasChanged |= repoStatus.getModified().contains(path);
			hasChanged |= repoStatus.getRemoved().contains(path);
			hasChanged |= repoStatus.getUntracked().contains(path);
		} catch (NoWorkTreeException e) {
			Activator.logError(UIText.GitScopeOperation_couldNotDetermineState,
					e);
		} catch (IOException e) {
			Activator.logError(UIText.GitScopeOperation_couldNotDetermineState,
					e);
		}
		return hasChanged;
	}

}
