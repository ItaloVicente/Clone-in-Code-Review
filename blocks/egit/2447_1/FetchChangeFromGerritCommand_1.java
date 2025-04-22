package org.eclipse.egit.ui.internal.commands.shared;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractSharedCommandHandler extends AbstractHandler {
	protected Repository getRepository(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection.isEmpty())
			return null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Repository result = null;
			for (Object element : ssel.toList()) {
				Repository elementRepository = null;
				if (element instanceof RepositoryTreeNode) {
					elementRepository = ((RepositoryTreeNode) element)
							.getRepository();
				} else if (element instanceof IResource) {
					IResource resource = (IResource) element;
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resource.getProject());
					if (mapping != null)
						elementRepository = mapping.getRepository();
				} else if (element instanceof IAdaptable) {
					IResource adapted = (IResource) ((IAdaptable) element)
							.getAdapter(IResource.class);
					if (adapted != null) {
						RepositoryMapping mapping = RepositoryMapping
								.getMapping(adapted.getProject());
						if (mapping != null)
							elementRepository = mapping.getRepository();
					}
				}
				if (elementRepository == null)
					continue;
				if (result != null && !elementRepository.equals(result))
					return null;
				if (result == null)
					result = elementRepository;
			}
			return result;
		}
		if (selection instanceof TextSelection) {
		}
		return null;
	}
}
