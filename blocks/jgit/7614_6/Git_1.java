package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;
import org.eclipse.jgit.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.util.GitDateParser;

public class GarbageCollectCommand extends GitCommand<Properties> {

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
	public Properties call() throws GitAPIException {
		checkCallable();

		GC gc = new GC((FileRepository) repo);
		gc.setProgressMonitor(monitor);
		if (this.expire != null)
			gc.setExpire(expire);

		try {
			gc.gc();
			return toProperties(gc.getStatistics());
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().gcFailed
		} catch (ParseException e) {
			throw new JGitInternalException(JGitText.get().gcFailed
		}
	}

	@SuppressWarnings("boxing")
	private static Properties toProperties(RepoStatistics stats) {
		Properties p = new Properties();
		p.put("numberOfLooseObjects"
		p.put("numberOfLooseRefs"
		p.put("numberOfPackedObjects"
		p.put("numberOfPackedRefs"
		p.put("numberOfPackFiles"
		p.put("sizeOfLooseObjects"
		p.put("sizeOfPackedObjects"
		return p;
	}
}
