/*******************************************************************************
 * Copyright (c) 2003, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 * Mickael Istria (Red Hat Inc.) Bug 264404 - Problem decorators
 *******************************************************************************/
package org.eclipse.ui.internal.navigator.resources.workbench;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.navigator.resources.nested.PathComparator;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.model.WorkbenchContentProvider;

/**
 * @since 3.2
 */
public class ResourceExtensionContentProvider extends WorkbenchContentProvider {

	private static final Object[] NO_CHILDREN = new Object[0];
	private Viewer viewer;

	/**
	 *
	 */
	public ResourceExtensionContentProvider() {
		super();
	}

	@Override
	public Object[] getElements(Object element) {
		return super.getChildren(element);
	}

	@Override
	public Object[] getChildren(Object element) {
		if(element instanceof IResource)
			return super.getChildren(element);
		return NO_CHILDREN;
	}

	@Override
	public boolean hasChildren(Object element) {
		try {
			if (element instanceof IContainer) {
				IContainer c = (IContainer) element;
				if (!c.isAccessible())
					return false;
				return c.members().length > 0;
			}
		} catch (CoreException ex) {
			WorkbenchNavigatorPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, 0, ex.getMessage(), ex));
			return false;
		}

		return super.hasChildren(element);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		this.viewer = viewer;
	}


	/**
	 * Process the resource delta.
	 *
	 * @param delta
	 */
	@Override
	protected void processDelta(IResourceDelta delta) {

		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}

		final Collection<Runnable> runnables = new ArrayList<Runnable>();
		final SortedSet<IResource> resourcesToRefresh = new TreeSet<IResource>(new Comparator<IResource>() {
			private PathComparator pathComparator = new PathComparator();
			@Override
			public int compare(IResource arg0, IResource arg1) {
				return pathComparator.compare(arg0.getFullPath(), arg1.getFullPath());
			}
		});
		processDelta(delta, runnables, resourcesToRefresh);

		IResource currentTopLevelResource = null;
		for (IResource resource : resourcesToRefresh) {
			if (resource == null) {
				continue;
			}
			if (currentTopLevelResource == null
					|| !currentTopLevelResource.getFullPath().isPrefixOf(resource.getFullPath())) {
				currentTopLevelResource = resource;
				runnables.add(getRefreshRunnable(resource));
			}
		}

		if (runnables.isEmpty()) {
			return;
		}

		if (ctrl.getDisplay().getThread() == Thread.currentThread()) {
			runUpdates(runnables);
		} else {
			ctrl.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					Control ctrl = viewer.getControl();
					if (ctrl == null || ctrl.isDisposed()) {
						return;
					}

					runUpdates(runnables);
				}
			});
		}

	}

	/**
	 * Process a resource delta. Add runnables for addAndRemove and
	 * resourceToUpdate.
	 */
	private void processDelta(IResourceDelta delta, Collection<Runnable> addAndRemoveRunnables,
			Set<IResource> toRefresh) {
		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}

		final IResource resource = delta.getResource();

		IResourceDelta[] affectedChildren = delta
				.getAffectedChildren(IResourceDelta.CHANGED);
		for (IResourceDelta element : affectedChildren) {
			if ((element.getFlags() & IResourceDelta.TYPE) != 0) {
				toRefresh.add(resource);
				return;
			}
		}

		int changeFlags = delta.getFlags();
		if ((changeFlags & (IResourceDelta.OPEN | IResourceDelta.SYNC
				| IResourceDelta.TYPE | IResourceDelta.DESCRIPTION)) != 0) {
			/* support the Closed Projects filter;
			 * when a project is closed, it may need to be removed from the view.
			 */
			IContainer parent = resource.getParent();
			if (parent != null) {
				toRefresh.add(parent);
			}
		}
		if ((changeFlags & IResourceDelta.REPLACED) != 0) {
			toRefresh.add(resource);
			return;
		}
		if ((changeFlags & IResourceDelta.MARKERS) != 0) {
			IProject project = resource.getProject();
			if (project != null) {
				toRefresh.add(project);
				return;
			}
		}


		for (IResourceDelta element : affectedChildren) {
			processDelta(element, addAndRemoveRunnables, toRefresh);
		}


		IResourceDelta[] addedChildren = delta
				.getAffectedChildren(IResourceDelta.ADDED);
		IResourceDelta[] removedChildren = delta
				.getAffectedChildren(IResourceDelta.REMOVED);

		if (addedChildren.length == 0 && removedChildren.length == 0) {
			return;
		}

		final Object[] addedObjects;
		final Object[] removedObjects;

		int numMovedFrom = 0;
		int numMovedTo = 0;
		if (addedChildren.length > 0) {
			addedObjects = new Object[addedChildren.length];
			for (int i = 0; i < addedChildren.length; i++) {
				addedObjects[i] = addedChildren[i].getResource();
				if ((addedChildren[i].getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					++numMovedFrom;
				}
			}
		} else {
			addedObjects = new Object[0];
		}

		if (removedChildren.length > 0) {
			removedObjects = new Object[removedChildren.length];
			for (int i = 0; i < removedChildren.length; i++) {
				removedObjects[i] = removedChildren[i].getResource();
				if ((removedChildren[i].getFlags() & IResourceDelta.MOVED_TO) != 0) {
					++numMovedTo;
				}
			}
		} else {
			removedObjects = new Object[0];
		}
		final boolean hasRename = numMovedFrom > 0 && numMovedTo > 0;

		Runnable addAndRemove = new Runnable(){
			@Override
			public void run() {
				if (viewer instanceof AbstractTreeViewer) {
					AbstractTreeViewer treeViewer = (AbstractTreeViewer) viewer;
					if (hasRename) {
						treeViewer.getControl().setRedraw(false);
					}
					try {
						if (addedObjects.length > 0) {
							treeViewer.add(resource, addedObjects);
						}
						if (removedObjects.length > 0) {
							treeViewer.remove(removedObjects);
						}
					}
					finally {
						if (hasRename) {
							treeViewer.getControl().setRedraw(true);
						}
					}
				} else {
					((StructuredViewer) viewer).refresh(resource);
				}
			}
		};
		addAndRemoveRunnables.add(addAndRemove);
	}

	/**
	 * Return a runnable for refreshing a resource.
	 * @param resource
	 * @return Runnable
	 */
	private Runnable getRefreshRunnable(final IResource resource) {
		return new Runnable(){
			@Override
			public void run() {
				((StructuredViewer) viewer).refresh(resource);
			}
		};
	}

	/**
	 * Run all of the runnables that are the widget updates
	 * @param runnables
	 */
	private void runUpdates(Collection<Runnable> runnables) {
		for (Runnable runnable : runnables) {
			runnable.run();
		}

	}

}
