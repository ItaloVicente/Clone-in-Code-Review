package org.eclipse.egit.ui.internal.staging;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.ui.internal.decorators.IProblemDecoratable;

public class StagingFolderEntry implements IAdaptable, IProblemDecoratable {
	private File file;
	private IPath path;
	private StagingFolderEntry parent;

	public StagingFolderEntry(File file) {
		super();
		this.file = file;
		path = new Path(file.getAbsolutePath());
	}

	public IContainer getContainer() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer resource = root.getContainerForLocation(path);
		return resource;
	}

	public int getProblemSeverity() {
		IContainer container = getContainer();
		if (container == null)
			return SEVERITY_NONE;

		try {
			return container.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ONE);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IResource.class || adapter == IContainer.class) {
			return getContainer();
		}
		else if (adapter == IPath.class) {
			return path;
		}
		return null;
	}

	public IPath getPath() {
		return path;
	}

	public File getFile() {
		return file;
	}

	public StagingFolderEntry getParent() {
		return parent;
	}

	public void setParent(StagingFolderEntry parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StagingFolderEntry) {
			return ((StagingFolderEntry)obj).getFile().equals(file);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return file.getAbsolutePath().hashCode();
	}

}
