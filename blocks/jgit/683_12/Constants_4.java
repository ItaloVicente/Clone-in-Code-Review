package org.eclipse.jgit.ignore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;

public class SimpleIgnoreCache {

	private HashMap<String

	private Repository repository;

	private String basePath;

	public SimpleIgnoreCache(Repository repository) {
		ignoreMap = new HashMap<String
		this.repository = repository;
		this.basePath = repository.getDirectory().getAbsolutePath();
	}

	public void initialize() throws IOException {
		TreeWalk tw = new TreeWalk(repository);
		tw.reset();
		tw.addTree(new FileTreeIterator(repository.getDirectory()
		tw.setRecursive(true);


		HashSet<FileTreeIterator> toAdd = new HashSet<FileTreeIterator>();
		while (tw.next()) {
			FileTreeIterator t = tw.getTree(0
			if (t.hasGitIgnore()) {
				toAdd.add(t);
			}
		}
		for (FileTreeIterator t : toAdd) {
			addNodeFromTree(t);
		}

		readRulesAtBase();
	}

	private void readRulesAtBase() {
		String path =  new File(basePath
		File f = new File(path);
		IgnoreNode n = new IgnoreNode(f.getParentFile());

		path = new File(repository.getDirectory()
		f = new File(path);
		if (f.canRead())
			n.addSecondarySource(f);

		ignoreMap.put(""
	}

	protected void addNodeFromTree(FileTreeIterator t) {
		IgnoreNode n = ignoreMap.get(t.getDirectory().getAbsolutePath().replaceFirst(basePath
		long time = t.getGitIgnoreLastModified();
		if (n != null) {
			if (n.getLastModified() == time)
				return;
		}
		n = addIgnoreNode(t.getDirectory());
		n.setLastModified(time);
	}

	protected IgnoreNode addIgnoreNode(File dir) {
		String relativeDir = dir.getAbsolutePath().replaceFirst(basePath
		IgnoreNode n = ignoreMap.get(relativeDir);
		if (n != null) {
			n.clear();
		} else {
			n = new IgnoreNode(dir);
			ignoreMap.put(relativeDir
		}
		return n;
	}

	public boolean isIgnored(String file) throws IOException{
		String currentPriority = file;

		boolean ignored = false;
		while (currentPriority.length() > 1) {
			currentPriority = getParent(currentPriority);
			IgnoreNode n = ignoreMap.get(currentPriority);

			if (n != null) {
				ignored = n.isIgnored(basePath + file);

				if (n.wasMatched())
					return ignored;
			}
		}

		return false;
	}

	private String getParent(String filePath) {
		int lastSlash = filePath.lastIndexOf(File.separator);
		if (filePath.length() > 0 && lastSlash != -1)
			return filePath.substring(0
		else
			return "";
	}

	public IgnoreNode getRules(String base) {
		return ignoreMap.get(base);
	}

	public boolean isEmpty() {
		return ignoreMap.isEmpty();
	}

	public void clear() {
		ignoreMap.clear();
		basePath = "";
	}
}
