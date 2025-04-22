package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.io.NullOutputStream;

public class DiffCommand extends GitCommand<List<DiffEntry>> {
	private AbstractTreeIterator oldTree;

	private AbstractTreeIterator newTree;

	private boolean cached;

	private TreeFilter pathFilter = TreeFilter.ALL;

	private boolean showNameAndStatusOnly;

	private OutputStream out;

	private int contextLines = -1;

	private String sourcePrefix;

	private String destinationPrefix;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	protected DiffCommand(Repository repo) {
		super(repo);
	}

	private DiffFormatter getDiffFormatter() {
		return out != null && !showNameAndStatusOnly
				? new DiffFormatter(new BufferedOutputStream(out))
				: new DiffFormatter(NullOutputStream.INSTANCE);
	}

	@Override
	public List<DiffEntry> call() throws GitAPIException {
		try (DiffFormatter diffFmt = getDiffFormatter()) {
			diffFmt.setRepository(repo);
			diffFmt.setProgressMonitor(monitor);
			if (cached) {
				if (oldTree == null) {
					if (head == null)
						throw new NoHeadException(JGitText.get().cannotReadTree);
					CanonicalTreeParser p = new CanonicalTreeParser();
					try (ObjectReader reader = repo.newObjectReader()) {
						p.reset(reader
					}
					oldTree = p;
				}
				newTree = new DirCacheIterator(repo.readDirCache());
			} else {
				if (oldTree == null)
					oldTree = new DirCacheIterator(repo.readDirCache());
				if (newTree == null)
					newTree = new FileTreeIterator(repo);
			}

			diffFmt.setPathFilter(pathFilter);

			List<DiffEntry> result = diffFmt.scan(oldTree
			if (showNameAndStatusOnly)
				return result;
			else {
				if (contextLines >= 0)
					diffFmt.setContext(contextLines);
				if (destinationPrefix != null)
					diffFmt.setNewPrefix(destinationPrefix);
				if (sourcePrefix != null)
					diffFmt.setOldPrefix(sourcePrefix);
				diffFmt.format(result);
				diffFmt.flush();
				return result;
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
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
		this.showNameAndStatusOnly = showNameAndStatusOnly;
		return this;
	}

	public DiffCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	public DiffCommand setContextLines(int contextLines) {
		this.contextLines = contextLines;
		return this;
	}

	public DiffCommand setSourcePrefix(String sourcePrefix) {
		this.sourcePrefix = sourcePrefix;
		return this;
	}

	public DiffCommand setDestinationPrefix(String destinationPrefix) {
		this.destinationPrefix = destinationPrefix;
		return this;
	}

	public DiffCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}
}
