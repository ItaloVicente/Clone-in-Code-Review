package org.eclipse.jgit.ignore;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;

public class SimpleIgnoreCache {

	private HashMap<String

	private Repository repository;

	private URI rootFileURI;

	public SimpleIgnoreCache(Repository repository) {
		ignoreMap = new HashMap<String
		this.repository = repository;
		this.rootFileURI = repository.getWorkDir().toURI();
	}

	public void initialize() throws IOException {
		TreeWalk tw = new TreeWalk(repository);
		tw.reset();
		tw.addTree(new FileTreeIterator(repository.getWorkDir()
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
		String path =  new File(repository.getWorkDir()
		File f = new File(path);
		IgnoreNode n = new IgnoreNode(f.getParentFile());

		path = new File(repository.getWorkDir()
		f = new File(path);
		if (f.canRead())
			n.addSecondarySource(f);

		ignoreMap.put(""
	}

	protected void addNodeFromTree(FileTreeIterator t) {
		IgnoreNode n = ignoreMap.get(relativize(t.getDirectory()));
		long time = t.getGitIgnoreLastModified();
		if (n != null) {
			if (n.getLastModified() == time)
				return;
		}
		n = addIgnoreNode(t.getDirectory());
		n.setLastModified(time);
	}

	protected IgnoreNode addIgnoreNode(File dir) {
		String relativeDir = relativize(dir);
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
		String target = rootFileURI.getPath() + file;
		while (currentPriority.length() > 1) {
			currentPriority = getParent(currentPriority);
			IgnoreNode n = ignoreMap.get(currentPriority);

			if (n != null) {
				ignored = n.isIgnored(target);

				if (n.wasMatched()) {
					if (ignored)
						return ignored;
					else
						target = getParent(target);
				}
			}
		}

		return false;
	}

	private String getParent(String filePath) {
		int lastSlash = filePath.lastIndexOf("/");
		if (filePath.length() > 0 && lastSlash != -1)
			return filePath.substring(0
		else
			return "";
	}

	public IgnoreNode getRules(String relativePath) {
		return ignoreMap.get(relativePath);
	}

	public boolean isEmpty() {
		return ignoreMap.isEmpty();
	}

	public void clear() {
		ignoreMap.clear();
	}

	private String relativize(File directory) {
		String retVal = rootFileURI.relativize(directory.toURI()).getPath();
		if (retVal.endsWith("/"))
			retVal = retVal.substring(0
		return retVal;
	}

}
