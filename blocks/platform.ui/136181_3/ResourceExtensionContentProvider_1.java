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
import org.eclipse.core.runtime.IPath;
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
		Set<IResource> localDirty = null;
		synchronized (this.dirty) {
			localDirty = new HashSet<>(dirty);
			this.dirty.removeAll(localDirty);
		}
		removeFromCache(localDirty);
		modifiedSeveritySinceLastRun.addAll(localDirty);

		try {
			for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE)) {
				IResource resource = marker.getResource();
				if (resource != null && (neverRan || localDirty.contains(resource))) {
					int severity = marker.getAttribute(IMarker.SEVERITY, -1);
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
			final IContainer currentContainer = resource instanceof IContainer ? (IContainer) resource
					: resource.getParent();
			if (currentContainer == null) {
				continue;
			}
			IPath currentLocation = currentContainer.getLocation();
			if (currentLocation == null) {
				continue;
			}
			dirtyLeafContainers
					.removeIf(leafContainer -> {
						IPath leafLocation = leafContainer.getLocation();
						return leafLocation != null && leafLocation.isPrefixOf(currentLocation);
					});
			if (dirtyLeafContainers.stream().noneMatch(
					leafContainer -> {
						IPath leafLocation = leafContainer.getLocation();
						return leafLocation != null && currentLocation.isPrefixOf(leafLocation);
					})) {
				dirtyLeafContainers.add(currentContainer);
			}
			if (resource.getType() == IResource.FILE) {
				cache.remove(resource);
				modifiedSeveritySinceLastRun.add(resource);
			}
			IContainer container = currentContainer;
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
		int severity = -1;
		for (IResource child : children) {
			Integer cachedSeverity = cache.get(child);
			if (cachedSeverity != null && cachedSeverity.intValue() > severity) {
				severity = cachedSeverity.intValue();
				if (severity >= IMarker.SEVERITY_ERROR) {
					return severity;
				}
			}
		}
		;
		return severity;
	}

	private void propagateSeverityToCache(IResource resource, int severity) {
		while (resource != null) {
			Integer cachedSeverity = cache.get(resource);
			if (cachedSeverity == null || cachedSeverity.intValue() < severity) {
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
		synchronized (dirty) {
			this.dirty.addAll(dirty);
		}
	}

	public Collection<IResource> getResourcesWithModifiedSeverity() {
		return Collections.unmodifiableSet(modifiedSeveritySinceLastRun);
	}

	public Integer getMaxSeverityIncludingNestedProjects(IResource resource) {
		return cache.getOrDefault(resource, -1).intValue();
	}

	public boolean isDirty() {
		return neverRan || !this.dirty.isEmpty();
	}
}
