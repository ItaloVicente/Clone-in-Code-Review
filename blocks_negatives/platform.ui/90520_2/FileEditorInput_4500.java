/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

/**
 * Tree content provider for resource objects that can be adapted to the
 * interface {@link org.eclipse.ui.model.IWorkbenchAdapter IWorkbenchAdapter}.
 * This provider will listen for resource changes within the workspace and
 * update the viewer as necessary.
 * <p>
 * This class may be instantiated, or subclassed by clients.
 * </p>
 */
public class WorkbenchContentProvider extends BaseWorkbenchContentProvider
		implements IResourceChangeListener {
	private Viewer viewer;

	/**
	 * Creates the resource content provider.
	 */
	public WorkbenchContentProvider() {
		super();
	}

	@Override
	public void dispose() {
		if (viewer != null) {
			IWorkspace workspace = null;
			Object obj = viewer.getInput();
			if (obj instanceof IWorkspace) {
				workspace = (IWorkspace) obj;
			} else if (obj instanceof IContainer) {
				workspace = ((IContainer) obj).getWorkspace();
			}
			if (workspace != null) {
				workspace.removeResourceChangeListener(this);
			}
		}

		super.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);

		this.viewer = viewer;
		IWorkspace oldWorkspace = null;
		IWorkspace newWorkspace = null;

		if (oldInput instanceof IWorkspace) {
			oldWorkspace = (IWorkspace) oldInput;
		} else if (oldInput instanceof IContainer) {
			oldWorkspace = ((IContainer) oldInput).getWorkspace();
		}

		if (newInput instanceof IWorkspace) {
			newWorkspace = (IWorkspace) newInput;
		} else if (newInput instanceof IContainer) {
			newWorkspace = ((IContainer) newInput).getWorkspace();
		}

		if (oldWorkspace != newWorkspace) {
			if (oldWorkspace != null) {
				oldWorkspace.removeResourceChangeListener(this);
			}
			if (newWorkspace != null) {
				newWorkspace.addResourceChangeListener(this,
						IResourceChangeEvent.POST_CHANGE);
			}
		}
	}

	@Override
	public final void resourceChanged(final IResourceChangeEvent event) {

		processDelta(event.getDelta());

	}

	/**
	 * Process the resource delta.
	 *
	 * @param delta
	 */
	protected void processDelta(IResourceDelta delta) {

		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}


		final Collection runnables = new ArrayList();
		processDelta(delta, runnables);

		if (runnables.isEmpty()) {
			return;
		}

		if (ctrl.getDisplay().getThread() == Thread.currentThread()) {
			runUpdates(runnables);
		} else {
			ctrl.getDisplay().asyncExec(() -> {
				Control ctrl1 = viewer.getControl();
				if (ctrl1 == null || ctrl1.isDisposed()) {
					return;
				}

				runUpdates(runnables);
			});
		}

	}

	/**
	 * Run all of the runnables that are the widget updates
	 * @param runnables
	 */
	private void runUpdates(Collection runnables) {
		Iterator runnableIterator = runnables.iterator();
		while(runnableIterator.hasNext()){
			((Runnable)runnableIterator.next()).run();
		}

	}

	/**
	 * Process a resource delta. Add any runnables
	 */
	private void processDelta(IResourceDelta delta, Collection runnables) {
		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}

		final IResource resource = delta.getResource();

		IResourceDelta[] affectedChildren = delta
				.getAffectedChildren(IResourceDelta.CHANGED);
		for (int i = 0; i < affectedChildren.length; i++) {
			if ((affectedChildren[i].getFlags() & IResourceDelta.TYPE) != 0) {
				runnables.add(getRefreshRunnable(resource));
				return;
			}
		}

		int changeFlags = delta.getFlags();
		if ((changeFlags & IResourceDelta.OPEN) != 0) {
			if (resource.isAccessible())  {
				runnables.add(getUpdateRunnable(resource));
			} else {
				runnables.add(getRefreshRunnable(resource));
				return;
			}
		}
		if ((changeFlags & (IResourceDelta.SYNC
				| IResourceDelta.TYPE | IResourceDelta.DESCRIPTION)) != 0) {
			runnables.add(getUpdateRunnable(resource));
		}
		if ((changeFlags & IResourceDelta.REPLACED) != 0) {
			runnables.add(getRefreshRunnable(resource));
			return;
		}

		for (int i = 0; i < affectedChildren.length; i++) {
			processDelta(affectedChildren[i], runnables);
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

		Runnable addAndRemove = () -> {
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
		};
		runnables.add(addAndRemove);
	}
	/**
	 * Return a runnable for refreshing a resource.
	 * @param resource
	 * @return Runnable
	 */
	private Runnable getRefreshRunnable(final IResource resource) {
		return () -> ((StructuredViewer) viewer).refresh(resource);
	}

		/**
		 * Return a runnable for refreshing a resource.
		 * @param resource
		 * @return Runnable
		 */
		private Runnable getUpdateRunnable(final IResource resource) {
			return () -> ((StructuredViewer) viewer).update(resource, null);
		}
}
