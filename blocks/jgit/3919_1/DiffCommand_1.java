package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class DiffCommand extends GitCommand<List<DiffEntry>> {
	private AbstractTreeIterator oldTree;

	private AbstractTreeIterator newTree;

	private boolean cached;

	private TreeFilter pathFilter = TreeFilter.ALL;

	private boolean showNameAndStatusOnly = true;

	protected DiffCommand(Repository repo) {
		super(repo);
	}

	public List<DiffEntry> call() throws GitAPIException
		final DiffFormatter diffFmt = new DiffFormatter(null);
		diffFmt.setRepository(repo);
		try {
			if (cached) {
				if (oldTree == null) {
					ObjectId head = repo.resolve(HEAD + "^{tree}");
					if (head == null)
						throw new NoHeadException(JGitText.get().cannotReadTree);
					CanonicalTreeParser p = new CanonicalTreeParser();
					ObjectReader reader = repo.newObjectReader();
					try {
						p.reset(reader
					} finally {
						reader.release();
					}
					oldTree = p;
				}
				newTree = new DirCacheIterator(repo.readDirCache());
			} else if (oldTree == null) {
				oldTree = new DirCacheIterator(repo.readDirCache());
				newTree = new FileTreeIterator(repo);
			} else if (newTree == null)
				newTree = new FileTreeIterator(repo);

			diffFmt.setPathFilter(pathFilter);

			if (showNameAndStatusOnly) {
				return diffFmt.scan(oldTree
			} else {
				throw new UnsupportedOperationException();
			}
		} finally {
			diffFmt.release();
		}
	}

	public DiffCommand setCached(boolean cached) {
		this.cached = cached;
		return this;
	}

	public DiffCommand setPathFilter(TreeFilter pathFilter) {
		this.pathFilter = pathFilter;
		return this;
	}

	public DiffCommand setOldTree(AbstractTreeIterator oldTree) {
		this.oldTree = oldTree;
		return this;
	}

	public DiffCommand setNewTree(AbstractTreeIterator newTree) {
		this.newTree = newTree;
		return this;
	}

	public DiffCommand setShowNameAndStatusOnly(boolean showNameAndStatusOnly) {
		if (!showNameAndStatusOnly)
			throw new UnsupportedOperationException();
		this.showNameAndStatusOnly = showNameAndStatusOnly;
		return this;
	}
}
