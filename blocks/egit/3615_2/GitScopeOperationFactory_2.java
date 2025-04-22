package org.eclipse.egit.ui.internal.operations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.mapping.ISynchronizationScopeManager;
import org.eclipse.team.ui.synchronize.ModelOperation;
import org.eclipse.ui.IWorkbenchPart;

public class GitScopeOperation extends ModelOperation {

	public /*package*/GitScopeOperation(IWorkbenchPart part,
			ISynchronizationScopeManager manager) {
		super(part, manager);
	}

	@Override
	protected void execute(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
	}

		List<IResource> resourcesInScope = new ArrayList<IResource>();
		ResourceTraversal[] traversals = getScope().getTraversals();
		for (ResourceTraversal resourceTraversal : traversals) {
			resourcesInScope.addAll(Arrays.asList(resourceTraversal
					.getResources()));
		}
		return resourcesInScope;
	}

}
