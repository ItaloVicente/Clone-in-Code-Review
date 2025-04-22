package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.lib.Repository;

public class FileNode extends RepositoryTreeNode<File> {

	public FileNode(RepositoryTreeNode parent, Repository repository, File file) {
		super(parent, RepositoryTreeNodeType.FILE, repository, file);
	}

	@Override
	public IPath getPath() {
		return new Path(getObject().getAbsolutePath());
	}
}
