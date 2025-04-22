package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsGarbageCollector;
import org.eclipse.jgit.internal.storage.dfs.DfsRepository;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.internal.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.pack.PackConfig;

public class GarbageCollectCommand extends GitCommand<Properties> {
	public static final int DEFAULT_GC_AGGRESSIVE_DEPTH = 250;

	public static final int DEFAULT_GC_AGGRESSIVE_WINDOW = 250;

	private ProgressMonitor monitor;

	private Date expire;

	private PackConfig pconfig;

	protected GarbageCollectCommand(Repository repo) {
		super(repo);
		pconfig = new PackConfig(repo);
	}

	public GarbageCollectCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public GarbageCollectCommand setExpire(Date expire) {
		this.expire = expire;
		return this;
	}

	public GarbageCollectCommand setAggressive(boolean aggressive) {
		if (aggressive) {
			StoredConfig repoConfig = repo.getConfig();
			pconfig.setDeltaSearchWindowSize(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_WINDOW
					DEFAULT_GC_AGGRESSIVE_WINDOW));
			pconfig.setMaxDeltaDepth(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_DEPTH
					DEFAULT_GC_AGGRESSIVE_DEPTH));
			pconfig.setReuseObjects(false);
		} else
			pconfig = new PackConfig(repo);
		return this;
	}

	public GarbageCollectCommand setPreserveOldPacks(boolean preserveOldPacks) {
		if (pconfig == null)
			pconfig = new PackConfig(repo);

		pconfig.setPreserveOldPacks(preserveOldPacks);
		return this;
	}

	public GarbageCollectCommand setPrunePreserved(boolean prunePreserved) {
		if (pconfig == null)
			pconfig = new PackConfig(repo);

		pconfig.setPrunePreserved(prunePreserved);
		return this;
	}

	@Override
	public Properties call() throws GitAPIException {
		checkCallable();

		try {
			if (repo instanceof FileRepository) {
				GC gc = new GC((FileRepository) repo);
				gc.setPackConfig(pconfig);
				gc.setProgressMonitor(monitor);
				if (this.expire != null)
					gc.setExpire(expire);

				try {
					gc.gc();
					return toProperties(gc.getStatistics());
				} catch (ParseException e) {
					throw new JGitInternalException(JGitText.get().gcFailed
				}
			} else if (repo instanceof DfsRepository) {
				DfsGarbageCollector gc =
					new DfsGarbageCollector((DfsRepository) repo);
				gc.setPackConfig(pconfig);
				gc.pack(monitor);
				return new Properties();
			} else {
				throw new UnsupportedOperationException(MessageFormat.format(
						JGitText.get().unsupportedGC
						repo.getClass().toString()));
			}
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().gcFailed
		}
	}

	public Properties getStatistics() throws GitAPIException {
		try {
			if (repo instanceof FileRepository) {
				GC gc = new GC((FileRepository) repo);
				return toProperties(gc.getStatistics());
			} else {
				return new Properties();
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().couldNotGetRepoStatistics
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
