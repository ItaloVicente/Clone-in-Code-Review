package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class SubmoduleUpdateCommand extends GitCommand<Collection<String>> {

	private ProgressMonitor monitor;

	private CredentialsProvider credentialsProvider;

	private final Collection<String> paths;

	public SubmoduleUpdateCommand(final Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleUpdateCommand setProgressMonitor(
			final ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public SubmoduleUpdateCommand setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	public SubmoduleUpdateCommand addPath(final String path) {
		paths.add(path);
		return this;
	}

	public Collection<String> call() throws JGitInternalException {
		checkCallable();

		try {
			SubmoduleWalk generator = SubmoduleWalk.forIndex(repo);
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			List<String> updated = new ArrayList<String>();
			while (generator.next()) {
				if (generator.getModulesPath() == null)
					continue;
				String url = generator.getConfigUrl();
				if (url == null)
					continue;

				Repository submoduleRepo = generator.getRepository();
				if (submoduleRepo == null) {
					CloneCommand clone = Git.cloneRepository();
					clone.setURI(url);
					clone.setDirectory(generator.getDirectory());
					if (monitor != null)
						clone.setProgressMonitor(monitor);
					if (credentialsProvider != null)
						clone.setCredentialsProvider(credentialsProvider);
					submoduleRepo = clone.call().getRepository();
				}

				RevWalk walk = new RevWalk(submoduleRepo);
				RevCommit commit = walk.parseCommit(generator.getObjectId());

				String update = generator.getConfigUpdate();
				if (ConfigConstants.CONFIG_KEY_MERGE.equals(update)) {
					MergeCommand merge = new MergeCommand(submoduleRepo);
					merge.include(commit);
					merge.call();
				} else if (ConfigConstants.CONFIG_KEY_REBASE.equals(update)) {
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
