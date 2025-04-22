package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jgit.api.GitCommand;
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
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.io.NullOutputStream;

import static org.eclipse.jgit.lib.Constants.HEAD;

public class CustomDiffCommand extends GitCommand<List<DiffEntry>> {

	private final Git git;
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

	protected CustomDiffCommand(Git git) {
		super(git.getRepository());
		this.git = git;
	}

	public List<DiffEntry> call() throws GitAPIException {
		final DiffFormatter diffFmt;
		if (out != null && !showNameAndStatusOnly) {
			diffFmt = new DiffFormatter(new BufferedOutputStream(out));
		} else {
			diffFmt = new DiffFormatter(NullOutputStream.INSTANCE);
		}
		diffFmt.setRepository(repo);
		diffFmt.setProgressMonitor(monitor);
		diffFmt.setDetectRenames(true);
		try {
			if (cached) {
				if (oldTree == null) {
					ObjectId head = git.getTreeFromRef(HEAD);
					if (head == null) {
						throw new NoHeadException(JGitText.get().cannotReadTree);
					}
					CanonicalTreeParser p = new CanonicalTreeParser();
					ObjectReader reader = repo.newObjectReader();
					try {
						p.reset(reader
					} finally {
						reader.close();
					}
					oldTree = p;
				}
				newTree = new DirCacheIterator(repo.readDirCache());
			} else {
				if (oldTree == null) {
					oldTree = new DirCacheIterator(repo.readDirCache());
				}
				if (newTree == null) {
					newTree = new FileTreeIterator(repo);
				}
			}

			diffFmt.setPathFilter(pathFilter);

			List<DiffEntry> result = diffFmt.scan(oldTree
			if (showNameAndStatusOnly) {
				return result;
			} else {
				if (contextLines >= 0) {
					diffFmt.setContext(contextLines);
				}
				if (destinationPrefix != null) {
					diffFmt.setNewPrefix(destinationPrefix);
				}
				if (sourcePrefix != null) {
					diffFmt.setOldPrefix(sourcePrefix);
				}
				diffFmt.format(result);
				diffFmt.flush();
				return result;
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			diffFmt.close();
		}
	}

	public CustomDiffCommand setCached(boolean cached) {
		this.cached = cached;
		return this;
	}

	public CustomDiffCommand setPathFilter(TreeFilter pathFilter) {
		this.pathFilter = pathFilter;
		return this;
	}

	public CustomDiffCommand setOldTree(AbstractTreeIterator oldTree) {
		this.oldTree = oldTree;
		return this;
	}

	public CustomDiffCommand setNewTree(AbstractTreeIterator newTree) {
		this.newTree = newTree;
		return this;
	}

	public CustomDiffCommand setShowNameAndStatusOnly(boolean showNameAndStatusOnly) {
		this.showNameAndStatusOnly = showNameAndStatusOnly;
		return this;
	}

	public CustomDiffCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	public CustomDiffCommand setContextLines(int contextLines) {
		this.contextLines = contextLines;
		return this;
	}

	public CustomDiffCommand setSourcePrefix(String sourcePrefix) {
		this.sourcePrefix = sourcePrefix;
		return this;
	}

	public CustomDiffCommand setDestinationPrefix(String destinationPrefix) {
		this.destinationPrefix = destinationPrefix;
		return this;
	}

	public CustomDiffCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}
}
