package org.eclipse.ui.internal.navigator.resources.nested;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;

public class NestedProjectsProblemsModel {
	private boolean neverRan = true;
	private final Set<IResource> dirty = new HashSet<>();
	private final Map<IResource, Integer> cache = new HashMap<>();
	private final Set<IResource> modifiedSeveritySinceLastRun = new HashSet<>();
	public void refreshModel() {
		modifiedSeveritySinceLastRun.clear();
		Set<IResource> currentDirty = null;
		if (dirty != null) {
			currentDirty = new HashSet<>();
			currentDirty.addAll(this.dirty);
			this.dirty.removeAll(currentDirty);
			removeFromCache(currentDirty);
			modifiedSeveritySinceLastRun.addAll(currentDirty);
		}

		try {
			for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE)) {
				IResource resource = marker.getResource();
				if (neverRan || currentDirty.contains(resource)) {
					int severity = marker.getAttribute(IMarker.SEVERITY, 0);
					if (severity >= 0) {
						propagateSeverityToCache(resource, severity);
					}
				}
			}
			neverRan = false;
		} catch (CoreException e) {
			WorkbenchNavigatorPlugin.log(e.getMessage(),
					new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
			throw new RuntimeException(e);
		} catch (CancellationException e) {
		}
	}

	private void removeFromCache(Set<IResource> toRemove) {
		Set<IContainer> dirtyLeafContainers = new HashSet<>();
		for (IResource resource : toRemove) {
			final IContainer initialContainer = resource instanceof IContainer ? (IContainer) resource
					: resource.getParent();
			dirtyLeafContainers
					.removeIf(leafContainer -> leafContainer.getLocation().isPrefixOf(initialContainer.getLocation()));
			if (dirtyLeafContainers.stream().noneMatch(
					leafContainer -> initialContainer.getLocation().isPrefixOf(leafContainer.getLocation()))) {
				dirtyLeafContainers.add(initialContainer);
			}
			if (resource.getType() == IResource.FILE) {
				cache.remove(resource);
				modifiedSeveritySinceLastRun.add(resource);
			}
			IContainer container = initialContainer;
			while (container != null && cache.containsKey(container)) {
				cache.remove(container);
				modifiedSeveritySinceLastRun.add(container);
				container = getParentInView(container);
			}
		}
		dirtyLeafContainers.forEach(leafContainer -> {
			IContainer container = leafContainer;
			while (container != null) {
				int severity = getMaxChildrenSeverityInCache(container);
				if (severity >= 0) {
					propagateSeverityToCache(container, severity);
				}
				container = getParentInView(container);
			}
		});
	}

	private int getMaxChildrenSeverityInCache(IContainer container) {
		if (!container.isAccessible()) {
			return -1;
		}
		Set<IResource> children = new HashSet<>();
		try {
			children.addAll(Arrays.asList(container.members()));
		} catch (CoreException ex) {
			WorkbenchNavigatorPlugin.log("Cannot access members", //$NON-NLS-1$
					WorkbenchNavigatorPlugin.createErrorStatus(ex.getMessage(), ex));
		}
		children.addAll(Arrays.asList(NestedProjectManager.getInstance().getDirectChildrenProjects(container)));
		int[] severity = new int[] { -1 };
		children.forEach(child -> {
			if (cache.containsKey(child) && cache.get(child).intValue() > severity[0]) {
				severity[0] = cache.get(child).intValue();
			}
		});
		return severity[0];
	}

	private void propagateSeverityToCache(IResource resource, int severity) {
		while (resource != null) {
			if (!cache.containsKey(resource) || cache.get(resource).intValue() < severity) {
				cache.put(resource, Integer.valueOf(severity));
				modifiedSeveritySinceLastRun.add(resource);
				resource = getParentInView(resource);
			} else {
				resource = null;
			}
		}
	}

	private IContainer getParentInView(IResource resource) {
		if (resource.getType() == IResource.PROJECT) {
			return NestedProjectManager.getInstance().getMostDirectOpenContainer((IProject) resource);
		}
		return resource.getParent();
	}

	public void markDirty(Set<IResource> dirty) {
		this.dirty.addAll(dirty);
	}

	public Collection<IResource> getResourcesWithModifiedSeverity() {
		return Collections.unmodifiableSet(modifiedSeveritySinceLastRun);
	}

	public int getMaxSeverityIncludingNestedProjects(IResource resource) {
		return cache.getOrDefault(resource, 0).intValue();
	}

	public boolean isDirty() {
		return neverRan || !this.dirty.isEmpty();
	}
}
