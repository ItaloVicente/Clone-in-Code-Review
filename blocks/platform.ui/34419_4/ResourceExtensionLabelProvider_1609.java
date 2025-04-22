package org.eclipse.ui.internal.navigator.resources.workbench;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.model.WorkbenchContentProvider;

public class ResourceExtensionContentProvider extends WorkbenchContentProvider {
 
	private static final Object[] NO_CHILDREN = new Object[0];
	private Viewer viewer;
	
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


	@Override
	protected void processDelta(IResourceDelta delta) {		

		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}
		
		
		final Collection<Runnable> runnables = new ArrayList<Runnable>();
		processDelta(delta, runnables);

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
	
	private void processDelta(IResourceDelta delta, Collection<Runnable> runnables) {
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
		if ((changeFlags & (IResourceDelta.OPEN | IResourceDelta.SYNC
				| IResourceDelta.TYPE | IResourceDelta.DESCRIPTION)) != 0) {
			
			runnables.add(getRefreshRunnable(resource.getParent()));
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
		runnables.add(addAndRemove);
	}
	
	private Runnable getRefreshRunnable(final IResource resource) {
		return new Runnable(){
			@Override
			public void run() {
				((StructuredViewer) viewer).refresh(resource);
			}
		};
	}
	
	private void runUpdates(Collection<Runnable> runnables) {
		for (Runnable runnable : runnables) {
			runnable.run();
		}
		
	}
	
}
