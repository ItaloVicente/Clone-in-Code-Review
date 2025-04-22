package org.eclipse.egit.core.resource;

import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.FileTreeEntry;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.lib.TreeEntry;

public class GitFolder extends GitContainer implements IFolder {

	private final Tree tree;

	public GitFolder(IContainer parent, Tree tree) {
		super(parent, tree);

		this.tree = tree;
	}

	public void create(boolean force, boolean local, IProgressMonitor monitor)
			throws CoreException {
	}

	public void create(int updateFlags, boolean local, IProgressMonitor monitor)
			throws CoreException {
	}

	public void createLink(IPath localLocation, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void createLink(URI location, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void delete(boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	public IFile getFile(String name) {
		TreeEntry member = findBlobMember(tree, name);
		if (member != null && member instanceof FileTreeEntry)
			return new GitFile(parent, (FileTreeEntry) member);

		return null;
	}

	public IFolder getFolder(String name) {
		return super.getFolder(name);
	}

	public void move(IPath destination, boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	@Override
	public int getType() {
		return IResource.FOLDER;
	}

}
