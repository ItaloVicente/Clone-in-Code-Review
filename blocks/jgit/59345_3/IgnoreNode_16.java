
package org.eclipse.jgit.ignore;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.Debug;
import org.eclipse.jgit.util.DotFileTree;
import org.eclipse.jgit.util.StringUtils;

public class IgnoreHierarchy {

	private final DotFileTree dotFileTree;

	public IgnoreHierarchy(DotFileTree dotFileTree) {
		this.dotFileTree = dotFileTree;
	}


	public boolean isIgnored(String path
			throws IOException {
		final boolean isDirectory = FileMode.TREE.equals(fileMode);

		boolean b = isIgnored0(path
		if (Debug.isDetail())
		return b;
	}

	private boolean isIgnored0(String path
			throws IOException {
		List<String> pathElements = StringUtils.splitPath(path);
		int folderElementCount = (isDirectory ? pathElements.size()
				: pathElements.size() - 1);

		boolean negateFirstMatch = false;

		if (folderElementCount > 0) {
			for (int i = folderElementCount; i > 0; i--) {
				String folderPath = StringUtils.join(pathElements.subList(0
				IgnoreNode node = dotFileTree.getWorkTreeIgnoreNode(folderPath);
				if (node != null) {
					String relativePath = i < pathElements.size()
							? StringUtils.join(pathElements.subList(i
									pathElements.size())
					if (!relativePath.isEmpty()) {
					}
					switch (node.isIgnored(relativePath
							negateFirstMatch)) {
					case IGNORED:
						return true;
					case NOT_IGNORED:
						return false;
					case CHECK_PARENT:
						negateFirstMatch = false;
						break;
					case CHECK_PARENT_NEGATE_FIRST_MATCH:
						negateFirstMatch = true;
						break;
					}
				}
			}
		}

		IgnoreNode[] nodes = new IgnoreNode[] {
				dotFileTree.getWorkTreeIgnoreNode(null)
				dotFileTree.getInfoIgnoreNode()
				dotFileTree.getGlobalIgnoreNode() };
		for (IgnoreNode node : nodes) {
			if (node != null) {
				switch (node.isIgnored(path
				case IGNORED:
					return true;
				case NOT_IGNORED:
					return false;
				case CHECK_PARENT:
					negateFirstMatch = false;
					break;
				case CHECK_PARENT_NEGATE_FIRST_MATCH:
					negateFirstMatch = true;
					break;
				}
			}
		}

		return false;
	}

}
