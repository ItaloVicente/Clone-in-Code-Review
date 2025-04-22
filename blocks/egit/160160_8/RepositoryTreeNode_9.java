package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public interface Node {

	Repository getRepository();

	RepositoryTreeNodeType getType();
}
