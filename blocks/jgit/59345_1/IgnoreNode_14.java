
package org.eclipse.jgit.ignore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.AbstractDotFileManager;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.Debug;
import org.eclipse.jgit.util.StringUtils;

public class IgnoreManager extends AbstractDotFileManager<IgnoreNode> {

	public IgnoreManager(File root
		super(root
		if (Debug.isInfo())
	}

	public void initFromRepository(final Repository repository) {
		setGlobalFileLocator(new IFileLocator() {
			@Override
			public File locateFile() throws IOException {
				String path = repository.getConfig().get(CoreConfig.KEY)
						.getExcludesFile();
				if (path == null)
					return null;
				FS fs = repository.getFS();
					return fs.resolve(fs.userHome()
				}
				return fs.resolve(null
			}
		});
		setInfoFileLocator(new IFileLocator() {
			@Override
			public File locateFile() throws IOException {
				FS fs = repository.getFS();
				return fs.resolve(repository.getDirectory()
						Constants.INFO_EXCLUDE);
			}
		});
	}

	@Override
	protected IgnoreNode loadNode(File f) throws IOException {
		if (Debug.isInfo())
		IgnoreNode node = new IgnoreNode();
		try (FileInputStream in = new FileInputStream(f)) {
			node.parse(in);
		}
		if (Debug.isInfo()) {
			for (FastIgnoreRule rule : node.getRules()) {
			}
		}
		return node;
	}

	@Override
	protected void clearSnapshots() {
	}

	public boolean isIgnored(String path
			throws IOException {
		final boolean isDirectory = FileMode.TREE.equals(fileMode);

		boolean b = isIgnored0(path
		if (Debug.isInfo())
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
				IgnoreNode node = lookupWorkTreeNode(folderPath);
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

		IgnoreNode[] nodes = new IgnoreNode[] { lookupRootNode()
				lookupInfoNode()
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
