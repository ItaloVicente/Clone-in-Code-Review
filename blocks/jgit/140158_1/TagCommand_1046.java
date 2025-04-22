package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class SubmoduleUpdateCommand extends
		TransportCommand<SubmoduleUpdateCommand

	private ProgressMonitor monitor;

	private final Collection<String> paths;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	private CloneCommand.Callback callback;

	private FetchCommand.Callback fetchCallback;

	private boolean fetch = false;

	public SubmoduleUpdateCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<>();
	}

	public SubmoduleUpdateCommand setProgressMonitor(
			final ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public SubmoduleUpdateCommand setFetch(boolean fetch) {
		this.fetch = fetch;
		return this;
	}

	public SubmoduleUpdateCommand addPath(String path) {
		paths.add(path);
		return this;
	}

	private Repository getOrCloneSubmodule(SubmoduleWalk generator
			throws IOException
		Repository repository = generator.getRepository();
		if (repository == null) {
			if (callback != null) {
				callback.cloningSubmodule(generator.getPath());
			}
			CloneCommand clone = Git.cloneRepository();
			configure(clone);
			clone.setURI(url);
			clone.setDirectory(generator.getDirectory());
			clone.setGitDir(
					new File(new File(repo.getDirectory()
							generator.getPath()));
			if (monitor != null) {
				clone.setProgressMonitor(monitor);
			}
			repository = clone.call().getRepository();
		} else if (this.fetch) {
			if (fetchCallback != null) {
				fetchCallback.fetchingSubmodule(generator.getPath());
			}
			FetchCommand fetchCommand = Git.wrap(repository).fetch();
			if (monitor != null) {
				fetchCommand.setProgressMonitor(monitor);
			}
			configure(fetchCommand);
			fetchCommand.call();
		}
		return repository;
	}

	@Override
	public Collection<String> call() throws InvalidConfigurationException
			NoHeadException
			CheckoutConflictException
			WrongRepositoryStateException
			RefNotFoundException
		checkCallable();

		try (SubmoduleWalk generator = SubmoduleWalk.forIndex(repo)) {
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			List<String> updated = new ArrayList<>();
			while (generator.next()) {
				if (generator.getModulesPath() == null)
					continue;
				String url = generator.getConfigUrl();
				if (url == null)
					continue;

				try (Repository submoduleRepo = getOrCloneSubmodule(generator
						url); RevWalk walk = new RevWalk(submoduleRepo)) {
					RevCommit commit = walk
							.parseCommit(generator.getObjectId());

					String update = generator.getConfigUpdate();
					if (null == update) {
                                            DirCacheCheckout co = new DirCacheCheckout(
                                                    submoduleRepo
                                                    commit.getTree());
                                            co.setFailOnConflict(true);
                                            co.setProgressMonitor(monitor);
                                            co.checkout();
                                            RefUpdate refUpdate = submoduleRepo.updateRef(
                                                    Constants.HEAD
                                            refUpdate.setNewObjectId(commit);
                                            refUpdate.forceUpdate();
                                            if (callback != null) {
                                                callback.checkingOut(commit
                                                        generator.getPath());
                                            }
                                        } else switch (update) {
                                        case ConfigConstants.CONFIG_KEY_MERGE:
                                            MergeCommand merge = new MergeCommand(submoduleRepo);
                                            merge.include(commit);
                                            merge.setProgressMonitor(monitor);
                                            merge.setStrategy(strategy);
                                            merge.call();
                                            break;
                                        case ConfigConstants.CONFIG_KEY_REBASE:
                                            RebaseCommand rebase = new RebaseCommand(submoduleRepo);
                                            rebase.setUpstream(commit);
                                            rebase.setProgressMonitor(monitor);
                                            rebase.setStrategy(strategy);
                                            rebase.call();
                                            break;
                                        default:
                                            DirCacheCheckout co = new DirCacheCheckout(
                                                    submoduleRepo
                                                    commit.getTree());
                                            co.setFailOnConflict(true);
                                            co.setProgressMonitor(monitor);
                                            co.checkout();
                                            RefUpdate refUpdate = submoduleRepo.updateRef(
                                                    Constants.HEAD
                                            refUpdate.setNewObjectId(commit);
                                            refUpdate.forceUpdate();
                                            if (callback != null) {
                                                callback.checkingOut(commit
                                                        generator.getPath());
                                            }
                                            break;
                                    }
				}
				updated.add(generator.getPath());
			}
			return updated;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage()
		}
	}

	public SubmoduleUpdateCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public SubmoduleUpdateCommand setCallback(CloneCommand.Callback callback) {
		this.callback = callback;
		return this;
	}

	public SubmoduleUpdateCommand setFetchCallback(
			FetchCommand.Callback callback) {
		this.fetchCallback = callback;
		return this;
	}
}
