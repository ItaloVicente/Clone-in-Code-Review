package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;

import org.eclipse.jgit.lib.Repository;

public class FileNode extends RepositoryTreeNode<File> {

	public FileNode(RepositoryTreeNode parent, Repository repository, File file) {
		super(parent, RepositoryTreeNodeType.FILE, repository, file);
	}

}
