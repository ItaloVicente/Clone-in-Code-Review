package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.MoveException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class MvCommand extends GitCommand<DirCache> {

	private String source;

	private String destination;

	MvCommand(Repository repo) {
		super(repo);
	}

	public DirCache call() throws GitAPIException {
		if (source == null || destination == null)
			throw new NoFilepatternException(
					JGitText.get().sourceDestinationMustBeProvided);
		try {
			Git git = new Git(getRepository());
			File srcFile = new File(getRepository().getWorkTree()
			if (!isTracked(srcFile)) {
				throw new MoveException(
						JGitText.get().sourceIsNotUnderVersionControl);
			}
			File dstFile = new File(getRepository().getWorkTree()
			srcFile.renameTo(dstFile);
			git.add().addFilepattern(destination).call();
			return git.rm().addFilepattern(source).call();
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
							JGitText.get().exceptionCaughtDuringExecutionOfMvCommand
							e)
		}
	}

	public MvCommand setSource(String src) {
		this.source = src;
		return this;
	}

	public MvCommand setDestination(String dst) {
		this.destination = dst;
		return this;
	}

	private boolean isTracked(File file) throws IOException {
		ObjectId objectId = repo.resolve(Constants.HEAD);
		RevTree tree;
		if (objectId != null)
			tree = new RevWalk(repo).parseTree(objectId);
		else
			tree = null;

		TreeWalk treeWalk = new TreeWalk(repo);
		treeWalk.setRecursive(true);
		if (tree != null)
			treeWalk.addTree(tree);
		else
			treeWalk.addTree(new EmptyTreeIterator());
		treeWalk.addTree(new DirCacheIterator(repo.readDirCache()));
		treeWalk.setFilter(PathFilterGroup.createFromStrings(Collections
				.singleton(Repository.stripWorkDir(repo.getWorkTree()
		return treeWalk.next();
	}
}
