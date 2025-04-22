package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.CHANGE;
import static org.eclipse.compare.structuremergeviewer.Differencer.RIGHT;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.synchronize.model.TreeBuilder.FileModelFactory;
import org.eclipse.jgit.lib.Repository;

public class GitModelCacheTree extends GitModelTree {

	private final FileModelFactory factory;

	public GitModelCacheTree(GitModelObjectContainer parent, Repository repo,
			IPath fullPath, FileModelFactory factory) {
		super(parent, fullPath, RIGHT | CHANGE);
		this.factory = factory;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != getClass())
			return false;

		GitModelCacheTree objTree = (GitModelCacheTree) obj;
		return path.equals(objTree.path)
				&& factory.isWorkingTree() == objTree.factory.isWorkingTree();
	}

	@Override
	public int hashCode() {
		return path.hashCode() + (factory.isWorkingTree() ? 31 : 41);
	}

	@Override
	public String toString() {
		return "GitModelCacheTree[" + getLocation() + ", isWorkingTree:" + factory.isWorkingTree() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public boolean isWorkingTree() {
		return factory.isWorkingTree();
	}

}
