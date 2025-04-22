package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class RmCommand extends GitCommand<DirCache> {

	private Collection<String> filepatterns;

	private boolean cached = false;

	public RmCommand(Repository repo) {
		super(repo);
		filepatterns = new LinkedList<>();
	}

	public RmCommand addFilepattern(String filepattern) {
		checkCallable();
		filepatterns.add(filepattern);
		return this;
	}

	public RmCommand setCached(boolean cached) {
		checkCallable();
		this.cached = cached;
		return this;
	}

	@Override
	public DirCache call() throws GitAPIException
			NoFilepatternException {

		if (filepatterns.isEmpty())
			throw new NoFilepatternException(JGitText.get().atLeastOnePatternIsRequired);
		checkCallable();
		DirCache dc = null;

		List<String> actuallyDeletedFiles = new ArrayList<>();
		try (TreeWalk tw = new TreeWalk(repo)) {
			dc = repo.lockDirCache();
			DirCacheBuilder builder = dc.builder();
			tw.setRecursive(true);
			tw.setFilter(PathFilterGroup.createFromStrings(filepatterns));
			tw.addTree(new DirCacheBuildIterator(builder));

			while (tw.next()) {
				if (!cached) {
					final FileMode mode = tw.getFileMode(0);
					if (mode.getObjectType() == Constants.OBJ_BLOB) {
						String relativePath = tw.getPathString();
						final File path = new File(repo.getWorkTree()
								relativePath);
						if (delete(path)) {
							actuallyDeletedFiles.add(relativePath);
						}
					}
				}
			}
			builder.commit();
			setCallable(false);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfRmCommand
		} finally {
			try {
				if (dc != null) {
					dc.unlock();
				}
			} finally {
				if (!actuallyDeletedFiles.isEmpty()) {
					repo.fireEvent(new WorkingTreeModifiedEvent(null
							actuallyDeletedFiles));
				}
			}
		}

		return dc;
	}

	private boolean delete(File p) {
		boolean deleted = false;
		while (p != null && !p.equals(repo.getWorkTree()) && p.delete()) {
			deleted = true;
			p = p.getParentFile();
		}
		return deleted;
	}

}
