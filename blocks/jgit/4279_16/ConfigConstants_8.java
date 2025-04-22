package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleGenerator;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleUpdateCommand extends GitCommand<Collection<String>> {

	private static final String CONFIG_VALUE_REBASE = "rebase";

	private static final String CONFIG_VALUE_MERGE = "merge";

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private CredentialsProvider credentialsProvider;

	private final Collection<String> paths;

	public SubmoduleUpdateCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleUpdateCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public SubmoduleUpdateCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	public SubmoduleUpdateCommand addPath(String path) {
		checkCallable();
		paths.add(path);
		return this;
	}

	public Collection<String> call() throws JGitInternalException {
		checkCallable();

		TreeFilter filter = null;
		if (!paths.isEmpty())
			filter = PathFilterGroup.createFromStrings(paths);
		try {
			SubmoduleGenerator generator = new SubmoduleGenerator(repo
			List<String> updated = new ArrayList<String>();
			while (generator.next()) {
				if (generator.getRepository() != null)
					continue;
				String url = generator.getConfigUrl();
				if (url == null)
					continue;

				CloneCommand clone = Git.cloneRepository();
				clone.setProgressMonitor(monitor);
				clone.setCredentialsProvider(credentialsProvider);
				clone.setURI(url);
				clone.setDirectory(generator.getDirectory());
				Repository submoduleRepo = clone.call().getRepository();

				RevWalk walk = new RevWalk(submoduleRepo);
				RevCommit commit = walk.parseCommit(generator.getObjectId());

				String update = generator.getConfigUpdate();
				if (CONFIG_VALUE_MERGE.equals(update)) {
					MergeCommand merge = new MergeCommand(submoduleRepo);
					merge.include(commit);
					merge.call();
				} else if (CONFIG_VALUE_REBASE.equals(update)) {
					RebaseCommand rebase = new RebaseCommand(submoduleRepo);
					rebase.setUpstream(commit);
					rebase.call();
				} else {
					DirCacheCheckout co = new DirCacheCheckout(submoduleRepo
							submoduleRepo.lockDirCache()
					co.setFailOnConflict(true);
					co.checkout();
					RefUpdate refUpdate = submoduleRepo.updateRef(
							Constants.HEAD
					refUpdate.setNewObjectId(commit);
					refUpdate.forceUpdate();
				}
				updated.add(generator.getPath());
			}
			return updated;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (GitAPIException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
