package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class NestedProjectsContentProvider implements ITreeContentProvider {

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public Object[] getElements(Object inputElement) {
		return null;
	}

	public Object[] getChildren(Object parentElement) {
		if (! (parentElement instanceof IContainer)) {
			return null;
		}
		IContainer container = (IContainer)parentElement;
		List<IProject> nestedProjects = new ArrayList<IProject>();
		try {
			List<IResource> children = Arrays.asList(container.members());
			for (IResource child : children) {
				if (child instanceof IFolder) {
					if (NestedProjectManager.isShownAsProject((IFolder)child)) { 
						for (IProject project : container.getWorkspace().getRoot().getProjects()) {
							if (project.getLocation().equals(child.getLocation()) && project.isOpen()) {
								nestedProjects.add(project);
							}
						}
					}
				}
			}
			return nestedProjects.toArray(new IProject[nestedProjects.size()]);
		} catch (CoreException ex) {
			return null;
		}
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return element instanceof IContainer;
	}

}
