package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;
import org.eclipse.jgit.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.util.GitDateParser;

public class GarbageCollectCommand extends GitCommand<GarbageCollectResult> {

	private ProgressMonitor monitor;

	private Date expire;

	protected GarbageCollectCommand(Repository repo) {
		super(repo);
		if (!(repo instanceof FileRepository))
			throw new UnsupportedOperationException(MessageFormat.format(
					JGitText.get().unsupportedGC
	}

	public GarbageCollectCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public GarbageCollectCommand setExpire(Date expire) {
		this.expire = expire;
		return this;
	}

	@Override
	public GarbageCollectResult call() throws GitAPIException {
		checkCallable();

		GC gc = new GC((FileRepository) repo);
		gc.setProgressMonitor(monitor);
		if (this.expire != null)
			gc.setExpire(expire);

		RepoStatistics preStats = null;
		RepoStatistics postStats = null;
		try {
			preStats = gc.getStatistics();
			gc.gc();
			postStats = gc.getStatistics();
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().gcFailed
		}
		return new GarbageCollectResult(preStats
	}
}
