package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.decorators.IProblemDecoratable;

public class StagingFolderEntry implements IAdaptable, IProblemDecoratable {
	private final IPath repoLocation;
	private final IPath repoRelativePath;
	private final IPath nodePath;

	private StagingFolderEntry parent;

	public StagingFolderEntry(IPath repoLocation, IPath repoRelativePath,
			IPath nodePath) {
		this.repoLocation = repoLocation;
		this.repoRelativePath = repoRelativePath;
		this.nodePath = nodePath;
	}

	public IContainer getContainer() {
		return ResourceUtil.getContainerForLocation(getLocation());
	}

	public int getProblemSeverity() {
		IContainer container = getContainer();
		if (container == null)
			return SEVERITY_NONE;

		try {
			return container.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_ONE);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IResource.class || adapter == IContainer.class)
			return getContainer();
		else if (adapter == IPath.class)
			return getLocation();
		return null;
	}

	public IPath getPath() {
		return repoRelativePath;
	}

	public IPath getLocation() {
		return repoLocation.append(repoRelativePath);
	}

	public IPath getParentPath() {
		return repoRelativePath.removeLastSegments(nodePath.segmentCount());
	}

	public IPath getNodePath() {
		return nodePath;
	}

	public StagingFolderEntry getParent() {
		return parent;
	}

	public void setParent(StagingFolderEntry parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StagingFolderEntry)
			return ((StagingFolderEntry) obj).getLocation().equals(getLocation());
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return getLocation().hashCode();
	}

}
