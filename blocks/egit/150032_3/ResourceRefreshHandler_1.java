package org.eclipse.egit.core.internal;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.job.RuleUtil;
import org.eclipse.egit.core.internal.trace.GitTraceLocation;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.events.WorkingTreeModifiedListener;
import org.eclipse.jgit.lib.Repository;

public class ResourceRefreshHandler implements WorkingTreeModifiedListener {

	@Override
	public void onWorkingTreeModified(WorkingTreeModifiedEvent event) {
		if (event.isEmpty()) {
			return;
		}
		Repository repo = event.getRepository();
		if (repo == null || repo.isBare()) {
			return; // Should never occur
		}
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Triggered refresh for repo: " + repo); //$NON-NLS-1$
		}
		try {
			refreshRepository(event);
		} catch (OperationCanceledException oe) {
			return;
		} catch (CoreException e) {
			Activator.error(CoreText.Activator_refreshFailed, e);
		}
	}

	private void refreshRepository(WorkingTreeModifiedEvent event)
			throws CoreException {
		if (event.isEmpty()) {
			return; // Should actually not occur
		}
		File workTree = event.getRepository().getWorkTree().getAbsoluteFile();
		Map<IPath, IProject> roots = getProjectLocations(workTree);
		if (roots.isEmpty()) {
			return;
		}
		IPath wt = new Path(workTree.getPath());
		Map<IResource, Boolean> toRefresh = computeResources(
				event.getModified(), event.getDeleted(), wt, roots);
		if (toRefresh.isEmpty()) {
			return;
		}
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Refreshing repository " + workTree + ' ' //$NON-NLS-1$
							+ toRefresh.size());
		}
		for (Map.Entry<IResource, Boolean> entry : toRefresh.entrySet()) {
			entry.getKey()
					.refreshLocal(entry.getValue().booleanValue()
							? IResource.DEPTH_INFINITE
							: IResource.DEPTH_ONE, null);
		}
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Refreshed repository " + workTree + ' ' //$NON-NLS-1$
							+ toRefresh.size());
		}
	}

	private Map<IPath, IProject> getProjectLocations(File workTree) {
		IProject[] projects = RuleUtil.getProjects(workTree);
		if (projects == null) {
			return Collections.emptyMap();
		}
		Map<IPath, IProject> result = new HashMap<>();
		for (IProject project : projects) {
			if (project.isAccessible()) {
				IPath path = project.getLocation();
				if (path != null) {
					IPath projectFilePath = path.append(
							IProjectDescription.DESCRIPTION_FILE_NAME);
					if (projectFilePath.toFile().exists()) {
						result.put(path, project);
					}
				}
			}
		}
		return result;
	}

	private Map<IResource, Boolean> computeResources(
			Collection<String> modified, Collection<String> deleted,
			IPath workTree, Map<IPath, IProject> roots) {
		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Calculating refresh for repository " + workTree + ' ' //$NON-NLS-1$
							+ modified.size() + ' ' + deleted.size());
		}
		Set<IPath> fullRefreshes = new HashSet<>();
		Map<IPath, IFile> handled = new HashMap<>();
		Map<IResource, Boolean> result = new HashMap<>();
		Stream.concat(modified.stream(), deleted.stream()).forEach(path -> {
			IPath filePath = "/".equals(path) ? workTree //$NON-NLS-1$
					: workTree.append(path);
			IProject project = roots.get(filePath);
			if (project != null) {
				handled.put(filePath, null);
				result.put(project, Boolean.FALSE);
				return;
			}
			if (fullRefreshes.stream()
					.anyMatch(full -> full.isPrefixOf(filePath))
					|| !roots.keySet().stream()
							.anyMatch(root -> root.isPrefixOf(filePath))) {
				return;
			}
			IPath containerPath;
			boolean isFile;
			if (path.endsWith("/")) { //$NON-NLS-1$
				isFile = false;
				containerPath = filePath.removeTrailingSeparator();
			} else {
				isFile = true;
				containerPath = filePath.removeLastSegments(1);
			}
			if (!handled.containsKey(containerPath)) {
				if (!isFile && containerPath != null) {
					IContainer container = ResourceUtil
							.getContainerForLocation(containerPath, false);
					if (container != null) {
						IFile file = handled.get(containerPath);
						handled.put(containerPath, null);
						if (file != null) {
							result.remove(file);
						}
						result.put(container, Boolean.FALSE);
					}
				} else if (isFile) {
					String lastPart = filePath.lastSegment();
					while (containerPath != null
							&& workTree.isPrefixOf(containerPath)) {
						IContainer container = ResourceUtil
								.getContainerForLocation(containerPath,
										false);
						if (container == null) {
							lastPart = containerPath.lastSegment();
							containerPath = containerPath
									.removeLastSegments(1);
							isFile = false;
							continue;
						}
						if (container.getType() == IResource.ROOT) {
							containerPath = containerPath.append(lastPart);
							fullRefreshes.add(containerPath);
							handled.put(containerPath, null);
						} else if (isFile) {
							IFile file = container
									.getFile(new Path(lastPart));
							handled.put(containerPath, file);
							result.put(file, Boolean.FALSE);
						} else {
							container = container
									.getFolder(new Path(lastPart));
							containerPath = containerPath.append(lastPart);
							fullRefreshes.add(containerPath);
							handled.put(containerPath, null);
							result.put(container, Boolean.TRUE);
						}
						break;
					}
				}
			} else {
				IFile file = handled.get(containerPath);
				if (file != null) {
					handled.put(containerPath, null);
					result.remove(file);
					result.put(file.getParent(), Boolean.FALSE);
				}
			}
		});

		if (GitTraceLocation.REFRESH.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REFRESH.getLocation(),
					"Calculated refresh for repository " + workTree); //$NON-NLS-1$
		}
		return result;
	}
}
