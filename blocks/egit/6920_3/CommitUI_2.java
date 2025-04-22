package org.eclipse.egit.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveableFilter;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.ide.ResourceUtil;

class SaveFilter implements ISaveableFilter {
	private final IResource[] roots;

	public SaveFilter(IResource[] roots) {
		this.roots = roots;
	}

	public boolean select(Saveable saveable,
			IWorkbenchPart[] containingParts) {
		if (isDescendantOfRoots(saveable)) {
			return true;
		}
		for (int i = 0; i < containingParts.length; i++) {
			IWorkbenchPart workbenchPart = containingParts[i];
			if (workbenchPart instanceof IEditorPart) {
				IEditorPart editorPart = (IEditorPart) workbenchPart;
				if (isEditingDescendantOf(editorPart)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isDescendantOfRoots(Saveable saveable) {
		ResourceMapping mapping = ResourceUtil.getResourceMapping(saveable);
		if (mapping != null) {
			try {
				ResourceTraversal[] traversals = mapping.getTraversals(
						ResourceMappingContext.LOCAL_CONTEXT, null);
				for (int i = 0; i < traversals.length; i++) {
					ResourceTraversal traversal = traversals[i];
					IResource[] resources = traversal.getResources();
					for (int j = 0; j < resources.length; j++) {
						IResource resource = resources[j];
						if (isDescendantOfRoots(resource)) {
							return true;
						}
					}
				}
			} catch (CoreException e) {
				Activator
						.logError(
								NLS
										.bind(
												"An internal error occurred while determining the resources for {0}", saveable.getName()), e); //$NON-NLS-1$
			}
		} else {
			IFile file = ResourceUtil.getFile(saveable);
			if (file != null) {
				return isDescendantOfRoots(file);
			}
		}
		return false;
	}

	private boolean isDescendantOfRoots(IResource resource) {
		for (int l = 0; l < roots.length; l++) {
			IResource root = roots[l];
			if (root.getFullPath().isPrefixOf(resource.getFullPath())) {
				return true;
			}
		}
		return false;
	}

	private boolean isEditingDescendantOf(IEditorPart part) {
		IFile file = ResourceUtil.getFile(part.getEditorInput());
		if (file != null) {
			return isDescendantOfRoots(file);
		}
		return false;
	}

}
