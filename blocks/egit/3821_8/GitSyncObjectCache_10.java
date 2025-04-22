package org.eclipse.egit.core.synchronize;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;

class GitSyncCache {

	private final Map<File, GitSyncObjectCache> cache;

	public static GitSyncCache getAllData(GitSynchronizeDataSet gsds,
			IProgressMonitor monitor) {
		GitSyncCache cache = new GitSyncCache();
		for (GitSynchronizeData gsd : gsds) {
			Repository repo = gsd.getRepository();
			GitSyncObjectCache repoCache = cache.put(repo);

			loadDataFromGit(gsd, repoCache);
			monitor.worked(1);
		}
		monitor.done();

		return cache;
	}

	private static void loadDataFromGit(GitSynchronizeData gsd,
			GitSyncObjectCache repoCache) {
		Repository repo = gsd.getRepository();
		TreeWalk tw = new TreeWalk(repo);
		if (gsd.getPathFilter() != null)
			tw.setFilter(gsd.getPathFilter());

		try {
			if (gsd.shouldIncludeLocal()) {
				tw.addTree(new FileTreeIterator(repo));
				tw.setFilter(new NotIgnoredFilter(0));
			} else if (gsd.getSrcRevCommit() != null)
				tw.addTree(gsd.getSrcRevCommit().getTree());
			else
				tw.addTree(new EmptyTreeIterator());

			if (gsd.getDstRevCommit() != null)
				tw.addTree(gsd.getDstRevCommit().getTree());
			else
				tw.addTree(new EmptyTreeIterator());

			List<DiffEntry> diffEntrys = DiffEntry.scan(tw, true);
			tw.release();

			for (DiffEntry diffEntry : diffEntrys)
				repoCache.addMember(diffEntry);
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

	private GitSyncCache() {
		cache = new HashMap<File, GitSyncObjectCache>();
	}

	public GitSyncObjectCache get(Repository repo) {
		return cache.get(repo.getDirectory());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<File, GitSyncObjectCache> entry : cache.entrySet())
			builder.append(entry.getKey().getPath())
					.append(": ").append(entry.getValue()); //$NON-NLS-1$

		return builder.toString();
	}

	private GitSyncObjectCache put(Repository repo) {
		GitSyncObjectCache objectCache = new GitSyncObjectCache();
		cache.put(repo.getDirectory(), objectCache);

		return objectCache;
	}

}
