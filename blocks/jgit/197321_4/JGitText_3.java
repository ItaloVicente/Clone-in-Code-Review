package org.eclipse.jgit.api;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RestoreCommand extends GitCommand<DirCache> {

	private Collection<String> filepatterns;

	private	List<String> actuallyRestoredFiles = new ArrayList<>();

	private boolean cached = false;

	private boolean addAll = false;
	public RestoreCommand(Repository repo) {
		super(repo);
		filepatterns = new LinkedList<>();
	}

	public RestoreCommand addFilepattern(String filepattern) {
		checkCallable();
		filepatterns.add(filepattern);
		return this;
	}

	public RestoreCommand setCached(boolean cached) {
		checkCallable();
		this.cached = cached;
		return this;
	}

	@Override
	public DirCache call() throws GitAPIException
			NoFilepatternException {

		if (filepatterns.isEmpty()) {
			throw new NoFilepatternException(
					JGitText.get().atLeastOnePatternIsRequired);
		}

		checkCallable();
		return restore();
	}

	private DirCache restore() throws GitAPIException {
		DirCache dc = null;

		try (TreeWalk tw = new TreeWalk(repo)) {
			dc = repo.readDirCache();

			DirCacheBuilder builder = dc.builder();
			tw.setRecursive(true);
			if (!addAll) {
				tw.setFilter(PathFilterGroup.createFromStrings(filepatterns));
			}
			tw.addTree(new DirCacheBuildIterator(builder));

			while (tw.next()) {
				final FileMode mode = tw.getFileMode(0);
				if (mode.getObjectType() == Constants.OBJ_BLOB) {
					String relativePath = tw.getPathString();
					if (cached) {
						if (restoreFromCache(relativePath)) {
							actuallyRestoredFiles.add(relativePath);
						}
					} else if (restoreFromIndex(relativePath)) {
							actuallyRestoredFiles.add(relativePath);
					}
				}
			}
			setCallable(false);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get()
							.exceptionCaughtDuringExecutionOfRestoreCommand
					e);
		} finally {
			if (!actuallyRestoredFiles.isEmpty()) {
				repo.fireEvent(new WorkingTreeModifiedEvent(null
						actuallyRestoredFiles));
			}
		}

		return dc;
	}

	private boolean restoreFromIndex(String relativePath)
			throws GitAPIException {
		try (Git git = new Git(repo)) {
			File p = new File(repo.getWorkTree()
					relativePath);
			CheckoutCommand checkout = git.checkout();
			boolean restored = false;
			while (p != null && !p.equals(repo.getWorkTree()) && p.isFile()) {
				restored = true;
				checkout.addPath(relativePath);
				p = p.getParentFile();
			}
			checkout.call();

			return restored;
		}
	}

	private boolean restoreFromCache(String relativePath)
			throws GitAPIException {
		try (Git git = new Git(repo)) {
			File p = new File(repo.getWorkTree()
					relativePath);
			ResetCommand reset = git.reset();
			boolean restored = false;
			while (p != null && !p.equals(repo.getWorkTree()) && p.isFile()) {
				restored = true;
				reset.addPath(relativePath);
				p = p.getParentFile();
			}
			reset.call();

			return restored;
		}
	}

}
