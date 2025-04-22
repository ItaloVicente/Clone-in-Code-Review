package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;
import org.eclipse.jgit.lib.Repository;

public abstract class SubmoduleCommand<V> extends
		RepositoriesViewCommandHandler<V> {

	protected Map<Repository, List<String>> getSubmodules(
			final List<RepositoryTreeNode<?>> nodes) {
		final Map<Repository, List<String>> repoPaths = new HashMap<Repository, List<String>>();
		for (RepositoryTreeNode<?> node : nodes) {
			if (node.getType() == RepositoryTreeNodeType.REPO) {
				Repository parent = node.getParent().getRepository();
				String path = Repository.stripWorkDir(parent.getWorkTree(),
						node.getRepository().getWorkTree());
				List<String> paths = repoPaths.get(parent);
				if (paths == null) {
					paths = new ArrayList<String>();
					repoPaths.put(parent, paths);
				}
				paths.add(path);
			}
		}
		for (RepositoryTreeNode<?> node : nodes)
			if (node.getType() == RepositoryTreeNodeType.SUBMODULES)
				repoPaths.put(node.getParent().getRepository(), null);
		return repoPaths;
	}

}
