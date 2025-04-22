package org.eclipse.egit.ui.internal.synchronize.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.jgit.lib.Repository;

class TreeBuilder {

	interface FileModelFactory {
		GitModelBlob createFileModel(GitModelObjectContainer parent,
				Repository repo, Change change, IPath fullPath);

		boolean isWorkingTree();
	}

	interface TreeModelFactory {
		GitModelTree createTreeModel(GitModelObjectContainer parent,
				IPath fullPath, int kind);
	}

	public static GitModelObject[] build(final GitModelObjectContainer root,
			final Repository repo, final Map<String, Change> changes,
			final FileModelFactory fileFactory,
			final TreeModelFactory treeFactory) {

		if (changes == null || changes.isEmpty())
			return new GitModelObject[] {};

		final IPath rootPath = new Path(repo.getWorkTree()
				.getAbsolutePath());
		final List<GitModelObject> rootChildren = new ArrayList<>();

		final Map<IPath, Node> nodes = new HashMap<>();

		for (Map.Entry<String, Change> entry : changes.entrySet()) {
			String repoRelativePath = entry.getKey();
			Change change = entry.getValue();

			GitModelObjectContainer parent = root;
			List<GitModelObject> children = rootChildren;
			IPath path = rootPath;

			String[] segments = repoRelativePath.split("/"); //$NON-NLS-1$

			for (int i = 0; i < segments.length; i++) {
				path = path.append(segments[i]);

				boolean fileNode = (i == segments.length - 1);
				if (!fileNode) {
					Node node = nodes.get(path);
					if (node == null) {
						GitModelTree tree = treeFactory.createTreeModel(parent,
								path, change.getKind());
						node = new Node(tree);
						nodes.put(path, node);
						children.add(tree);
					}
					parent = node.tree;
					children = node.children;
				} else {
					GitModelBlob file = fileFactory.createFileModel(parent,
							repo, change, path);
					children.add(file);
				}
			}
		}

		for (Node object : nodes.values()) {
			GitModelTree tree = object.tree;
			tree.setChildren(object.children);
		}

		return rootChildren.toArray(new GitModelObject[rootChildren.size()]);
	}

	private static class Node {
		private final GitModelTree tree;

		private final List<GitModelObject> children = new ArrayList<>();

		public Node(GitModelTree tree) {
			this.tree = tree;
		}
	}
}
