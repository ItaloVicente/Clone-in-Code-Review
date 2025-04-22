package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.decorators.IProblemDecoratable;
import org.eclipse.jgit.annotations.NonNull;

public class StagingFolderEntry implements IAdaptable, IProblemDecoratable {
	private final IPath repoLocation;
	private final IPath repoRelativePath;
	private final IPath nodePath;
	private final IContainer container;

	private StagingFolderEntry parent;
	private Object[] children;


	public StagingFolderEntry(IPath repoLocation, IPath repoRelativePath,
			IPath nodePath) {
		this.repoLocation = repoLocation;
		this.repoRelativePath = repoRelativePath;
		this.nodePath = nodePath;
		this.container = ResourceUtil.getContainerForLocation(getLocation(),
				false);
	}

	public IContainer getContainer() {
		return container;
	}

	@Override
	public int getProblemSeverity() {
		if (container == null)
			return SEVERITY_NONE;

		try {
			return container.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

	@Override
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

	@SuppressWarnings("null")
	@NonNull
	public IPath getLocation() {
		return repoLocation.append(repoRelativePath);
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

	public Object[] getChildren() {
		return children;
	}

	public void setChildren(Object[] children) {
		this.children = children;
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

	@Override
	public String toString() {
		return "StagingFolderEntry[" + repoRelativePath + "]"; //$NON-NLS-1$//$NON-NLS-2$
	}
}
