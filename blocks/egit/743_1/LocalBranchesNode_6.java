package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;

import org.eclipse.jgit.lib.Repository;

public class FolderNode extends RepositoryTreeNode<File> {

	public FolderNode(RepositoryTreeNode parent, Repository repository,
			File directory) {
		super(parent, RepositoryTreeNodeType.FOLDER, repository, directory);
	}

}
