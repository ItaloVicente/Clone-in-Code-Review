
package org.eclipse.jgit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.jgit.attributes.AttributesHierarchy;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesRule;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.DotFileChangedEvent;
import org.eclipse.jgit.events.DotFileChangedListener;
import org.eclipse.jgit.ignore.FastIgnoreRule;
import org.eclipse.jgit.ignore.IgnoreHierarchy;
import org.eclipse.jgit.ignore.IgnoreNode;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;

public class DotFileTree {




	private final Repository repo;

	private final Map<String

	private volatile long snapshotId;

	private volatile boolean initialized;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(
			true);

	private final AttributesHierarchy attributesHierarchy;

	private final IgnoreHierarchy ignoreHierarchy;

	public DotFileTree(Repository repo) {
		this.repo = repo;
		this.repo.getListenerList().addListener(ConfigChangedListener.class
				new ConfigChangedListener() {
					@Override
					public void onConfigChanged(ConfigChangedEvent event) {
						handleConfigChanged();
					}
				});
		this.repo.getListenerList().addListener(DotFileChangedListener.class
				new DotFileChangedListener() {
					@Override
					public void onDotFileChanged(DotFileChangedEvent event) {
						handleDotFilesChanged(event.getFiles());
					}
				});

		ignoreHierarchy = new IgnoreHierarchy(this);
		attributesHierarchy = new AttributesHierarchy(this);
	}

	public AttributesHierarchy getAttributesHierarchy() {
		return attributesHierarchy;
	}

	public IgnoreHierarchy getIgnoreHierarchy() {
		return ignoreHierarchy;
	}

	public void reset() {
		lock.writeLock().lock();
		try {
			initialized = false;
			snapshotId++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public long getSnapshotId() {
		return snapshotId;
	}

	public AttributesNode getGlobalAttributesNode() throws IOException {
		return getAttributesNode(PATH_GLOBAL_ATTRIBUTES);
	}

	public IgnoreNode getGlobalIgnoreNode() throws IOException {
		return getIgnoreNode(PATH_GLOBAL_EXCLUDES);
	}

	public AttributesNode getInfoAttributesNode() throws IOException {
		return getAttributesNode(PATH_INFO_ATTRIBUTES);
	}

	public IgnoreNode getInfoIgnoreNode() throws IOException {
		return getIgnoreNode(PATH_INFO_EXCLUDES);
	}

	public AttributesNode getWorkTreeAttributesNode(String folderPath)
			throws IOException {
		if (folderPath == null)
			folderPath = folderPath.substring(0
		String filePath = folderPath.isEmpty() ? Constants.DOT_GIT_ATTRIBUTES
		return getAttributesNode(filePath);
	}

	public IgnoreNode getWorkTreeIgnoreNode(String folderPath)
			throws IOException {
		if (folderPath == null)
			folderPath = folderPath.substring(0
		String filePath = folderPath.isEmpty() ? Constants.DOT_GIT_IGNORE
		return getIgnoreNode(filePath);
	}

	private AttributesNode getAttributesNode(String path)
			throws IOException {
		ensureInitialized();

		return (AttributesNode) nodeCache.get(path);
	}

	private IgnoreNode getIgnoreNode(String path) throws IOException {
		ensureInitialized();

		return (IgnoreNode) nodeCache.get(path);
	}

	private void ensureInitialized() throws IOException {
		if (initialized || lock.writeLock().isHeldByCurrentThread())
			return;

		lock.writeLock().lock();
		try {
			if (initialized)
				return;

			if (repo.isBare())
				return;

			nodeCache.clear();
			final FS fs = repo.getFS();

			putFile(DotFileType.ATTRIBUTES
					locateGlobalAttributes(repo));
			putFile(DotFileType.IGNORE
					locateGlobalExcludes(repo));
			putFile(DotFileType.ATTRIBUTES
					locateInfoAttributes(repo));
			putFile(DotFileType.IGNORE
					locateInfoExcludes(repo));

			final String basePath = repo.getWorkTree().getAbsolutePath()
					+ File.separator;
			Files.walkFileTree(repo.getWorkTree().toPath()
					new FileVisitor<Path>() {
						@Override
						public FileVisitResult preVisitDirectory(Path dirPath
								BasicFileAttributes attrs) throws IOException {
							if (dirPath.endsWith(Constants.DOT_GIT))
								return FileVisitResult.SKIP_SUBTREE;
							File dir = dirPath.toFile();
							File f = new File(dir
									Constants.DOT_GIT_ATTRIBUTES);
							if (f.exists()) {
								putFile(DotFileType.ATTRIBUTES
										basePath
										f.getAbsolutePath())
							}
							f = new File(dir
							if (f.exists()) {
								putFile(DotFileType.IGNORE
										basePath
										f.getAbsolutePath())
							}
							String path = fs
									.relativize(basePath
									.replace(File.separatorChar
							if (ignoreHierarchy.isIgnored(path
									FileMode.TREE)) {
								return FileVisitResult.SKIP_SUBTREE;
							}
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(Path file
								BasicFileAttributes attrs) throws IOException {
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file
								IOException exc) throws IOException {
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult postVisitDirectory(Path dir
								IOException exc) throws IOException {
							return FileVisitResult.CONTINUE;
						}
					});
		}	finally{
			initialized = true;
			lock.writeLock().unlock();
		}
	}

	private void handleConfigChanged() {
		lock.writeLock().lock();
		try {
			putFile(DotFileType.ATTRIBUTES
					locateGlobalAttributes(repo));
			putFile(DotFileType.IGNORE
					locateGlobalExcludes(repo));
			putFile(DotFileType.ATTRIBUTES
					locateInfoAttributes(repo));
			putFile(DotFileType.IGNORE
					locateInfoExcludes(repo));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	private void handleDotFilesChanged(Collection<File> changedFiles) {
		FS fs=repo.getFS();
		String basePath = repo.getWorkTree().getAbsolutePath()
				+ File.separator;

		lock.writeLock().lock();
		try {
			for (File f : changedFiles) {
				if (f == null)
					continue;
				String path = fs.relativize(basePath
				switch (f.getName()) {
				case Constants.DOT_GIT_ATTRIBUTES:
					putFile(DotFileType.ATTRIBUTES
					break;
				case Constants.DOT_GIT_IGNORE:
					putFile(DotFileType.IGNORE
					break;
				}
			}
			snapshotId++;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	private void putFile(DotFileType type
			throws IOException {
		path = path.replace(File.separatorChar
		if (f == null || !f.exists()) {
			if (Debug.isInfo())
			nodeCache.remove(path);
			return;
		}

		if (Debug.isInfo())

		switch (type) {
		case ATTRIBUTES:
			nodeCache.put(path
			break;
		case IGNORE:
			nodeCache.put(path
			break;
		}
	}

	private static AttributesNode createAttributesNode(FS fs
			throws IOException {
		if (f == null || !fs.exists(f))
			return null;
		if (Debug.isInfo())
		AttributesNode node = new AttributesNode();
		try (FileInputStream in = new FileInputStream(f)) {
			node.parse(in);
		}
		if (Debug.isInfo()) {
			for (AttributesRule rule : node.getRules()) {
			}
		}
		return node;
	}

	private static IgnoreNode createIgnoreNode(FS fs
			throws IOException {
		if (f == null || !fs.exists(f))
			return null;
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

	private static File locateGlobalAttributes(Repository repo) {
		FS fs = repo.getFS();
		String fp = repo.getConfig().get(CoreConfig.KEY).getAttributesFile();
		if (fp != null) {
				return fs.resolve(fs.userHome()
			} else {
				return fs.resolve(null
			}
		}
		return null;
	}

	private static File locateGlobalExcludes(Repository repo) {
		FS fs = repo.getFS();
		String fp = repo.getConfig().get(CoreConfig.KEY).getExcludesFile();
		if (fp != null) {
				return fs.resolve(fs.userHome()
			} else {
				return fs.resolve(null
			}
		}
		return null;
	}

	private static File locateInfoAttributes(Repository repo) {
		FS fs = repo.getFS();
		return fs.resolve(repo.getDirectory()
	}

	private static File locateInfoExcludes(Repository repo) {
		FS fs = repo.getFS();
		return fs.resolve(repo.getDirectory()
	}

	private static enum DotFileType {
		ATTRIBUTES
	}
}
