package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.api.MergeCommand.FastForwardMode.Merge;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TagOpt;

public class PullCommand extends TransportCommand<PullCommand


	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private BranchRebaseMode pullRebaseMode = null;

	private String remote;

	private String remoteBranchName;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	private TagOpt tagOption;

	private FastForwardMode fastForwardMode;

	private FetchRecurseSubmodulesMode submoduleRecurseMode = null;

	protected PullCommand(Repository repo) {
		super(repo);
	}

	public PullCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public PullCommand setRebase(boolean useRebase) {
		checkCallable();
		pullRebaseMode = useRebase ? BranchRebaseMode.REBASE
				: BranchRebaseMode.NONE;
		return this;
	}

	public PullCommand setRebase(BranchRebaseMode rebaseMode) {
		checkCallable();
		pullRebaseMode = rebaseMode;
		return this;
	}

	@Override
	public PullResult call() throws GitAPIException
			WrongRepositoryStateException
			InvalidRemoteException
			RefNotFoundException
			org.eclipse.jgit.api.errors.TransportException {
		checkCallable();

		monitor.beginTask(JGitText.get().pullTaskName
		Config repoConfig = repo.getConfig();

		String branchName = null;
		try {
			String fullBranch = repo.getFullBranch();
			if (fullBranch != null
					&& fullBranch.startsWith(Constants.R_HEADS)) {
				branchName = fullBranch.substring(Constants.R_HEADS.length());
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
					e);
		}
		if (remoteBranchName == null && branchName != null) {
			remoteBranchName = repoConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_MERGE);
		}
		if (remoteBranchName == null) {
			remoteBranchName = branchName;
		}
		if (remoteBranchName == null) {
			throw new NoHeadException(
					JGitText.get().cannotCheckoutFromUnbornBranch);
		}

		if (!repo.getRepositoryState().equals(RepositoryState.SAFE))
			throw new WrongRepositoryStateException(MessageFormat.format(
					JGitText.get().cannotPullOnARepoWithState
							.getRepositoryState().name()));

		if (remote == null && branchName != null) {
			remote = repoConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_REMOTE);
		}
		if (remote == null) {
			remote = Constants.DEFAULT_REMOTE_NAME;
		}

		if (pullRebaseMode == null && branchName != null) {
			pullRebaseMode = getRebaseMode(branchName
		}


		String remoteUri;
		FetchResult fetchRes;
		if (isRemote) {
			remoteUri = repoConfig.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION
					ConfigConstants.CONFIG_KEY_URL);
			if (remoteUri == null) {
				String missingKey = ConfigConstants.CONFIG_REMOTE_SECTION + DOT
						+ remote + DOT + ConfigConstants.CONFIG_KEY_URL;
				throw new InvalidConfigurationException(MessageFormat.format(
						JGitText.get().missingConfigurationForKey
			}

			if (monitor.isCancelled())
				throw new CanceledException(MessageFormat.format(
						JGitText.get().operationCanceled
						JGitText.get().pullTaskName));

			FetchCommand fetch = new FetchCommand(repo).setRemote(remote)
					.setProgressMonitor(monitor).setTagOpt(tagOption)
					.setRecurseSubmodules(submoduleRecurseMode);
			configure(fetch);

			fetchRes = fetch.call();
		} else {
			remoteUri = JGitText.get().localRepository;
			fetchRes = null;
		}

		monitor.update(1);

		if (monitor.isCancelled())
			throw new CanceledException(MessageFormat.format(
					JGitText.get().operationCanceled
					JGitText.get().pullTaskName));

		AnyObjectId commitToMerge;
		if (isRemote) {
			Ref r = null;
			if (fetchRes != null) {
				r = fetchRes.getAdvertisedRef(remoteBranchName);
				if (r == null)
					r = fetchRes.getAdvertisedRef(Constants.R_HEADS
							+ remoteBranchName);
			}
			if (r == null) {
				throw new RefNotAdvertisedException(MessageFormat.format(
						JGitText.get().couldNotGetAdvertisedRef
						remoteBranchName));
			} else {
				commitToMerge = r.getObjectId();
			}
		} else {
			try {
				commitToMerge = repo.resolve(remoteBranchName);
				if (commitToMerge == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
			} catch (IOException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
						e);
			}
		}

		String upstreamName = MessageFormat.format(
				JGitText.get().upstreamBranchName
				Repository.shortenRefName(remoteBranchName)

		PullResult result;
		if (pullRebaseMode != BranchRebaseMode.NONE) {
			RebaseCommand rebase = new RebaseCommand(repo);
			RebaseResult rebaseRes = rebase.setUpstream(commitToMerge)
					.setUpstreamName(upstreamName).setProgressMonitor(monitor)
					.setOperation(Operation.BEGIN).setStrategy(strategy)
					.setPreserveMerges(
							pullRebaseMode == BranchRebaseMode.PRESERVE)
					.call();
			result = new PullResult(fetchRes
		} else {
			MergeCommand merge = new MergeCommand(repo);
			MergeResult mergeRes = merge.include(upstreamName
					.setStrategy(strategy).setProgressMonitor(monitor)
					.setFastForward(getFastForwardMode()).call();
			monitor.update(1);
			result = new PullResult(fetchRes
		}
		monitor.endTask();
		return result;
	}

	public PullCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public PullCommand setRemoteBranchName(String remoteBranchName) {
		checkCallable();
		this.remoteBranchName = remoteBranchName;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public String getRemoteBranchName() {
		return remoteBranchName;
	}

	public PullCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public PullCommand setTagOpt(TagOpt tagOpt) {
		checkCallable();
		this.tagOption = tagOpt;
		return this;
	}

	public PullCommand setFastForward(
			@Nullable FastForwardMode fastForwardMode) {
		checkCallable();
		this.fastForwardMode = fastForwardMode;
		return this;
	}

	public PullCommand setRecurseSubmodules(
			@Nullable FetchRecurseSubmodulesMode recurse) {
		this.submoduleRecurseMode = recurse;
		return this;
	}

	public static BranchRebaseMode getRebaseMode(String branchName
			Config config) {
		BranchRebaseMode mode = config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		if (mode == null) {
			mode = config.getEnum(BranchRebaseMode.values()
					ConfigConstants.CONFIG_PULL_SECTION
					ConfigConstants.CONFIG_KEY_REBASE
		}
		return mode;
	}

	private FastForwardMode getFastForwardMode() {
		if (fastForwardMode != null) {
			return fastForwardMode;
		}
		Config config = repo.getConfig();
		Merge ffMode = config.getEnum(Merge.values()
				ConfigConstants.CONFIG_PULL_SECTION
				ConfigConstants.CONFIG_KEY_FF
		return ffMode != null ? FastForwardMode.valueOf(ffMode) : null;
	}
}
