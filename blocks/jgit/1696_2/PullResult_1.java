package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;

public class PullCommand extends GitCommand<PullResult> {
	private int timeout = 0;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	protected PullCommand(Repository repo) {
		super(repo);
	}

	public PullCommand setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public PullCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public PullResult call() throws WrongRepositoryStateException
			InvalidConfigurationException
			InvalidRemoteException
		checkCallable();

		monitor.beginTask(JGitText.get().pullTaskName

		String branchName;
		try {
			if (!repo.getFullBranch().startsWith(Constants.R_HEADS)) {
				throw new DetachedHeadException();
			}
			branchName = repo.getBranch();
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
					e);
		}

		if (!repo.getRepositoryState().equals(RepositoryState.SAFE))
			throw new WrongRepositoryStateException(MessageFormat.format(
					JGitText.get().cannotPullOnARepoWithState
							.getRepositoryState().name()));

		Config repoConfig = repo.getConfig();
		final String remote = repoConfig.getString("branch"
				"remote");
		if (remote == null) {
			String missingKey = "branch" + "." + branchName + "." + "remote";
			throw new InvalidConfigurationException(MessageFormat.format(
					JGitText.get().missingConfigurationForKey
		}

		String remoteBranchName = repoConfig.getString("branch"
				"merge");
		if (remoteBranchName == null) {
			remoteBranchName = repoConfig.getString("branch"
					"rebase");
			if (remoteBranchName != null) {
				throw new JGitInternalException(
						"Pull with rebase is not yet supported");
			}
		}
		if (remoteBranchName == null) {
			String missingKey = "branch" + "." + branchName + "." + "merge";
			throw new InvalidConfigurationException(MessageFormat.format(
					JGitText.get().missingConfigurationForKey
		}

		if (monitor.isCancelled())
			throw new CanceledException(MessageFormat.format(
					JGitText.get().operationCanceled
					JGitText.get().pullTaskName));

		FetchCommand fetch = new FetchCommand(repo);
		fetch.setRemote(remote);
		if (monitor != null)
			fetch.setProgressMonitor(monitor);
		fetch.setTimeout(this.timeout);

		FetchResult fetchRes = fetch.call();

		monitor.update(1);

		String remoteTrackingBranch = null;
		for (TrackingRefUpdate update : fetchRes.getTrackingRefUpdates()) {
			if (update.getRemoteName().equals(remoteBranchName)) {
				remoteTrackingBranch = update.getLocalName();
				break;
			}
		}

		if (remoteTrackingBranch == null) {
			remoteTrackingBranch = Constants.R_REMOTES + remote + '/'
					+ branchName;
		}

		Ref mergeBranch = null;
		try {
			mergeBranch = repo.getRef(remoteTrackingBranch);
			if (mergeBranch == null) {
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().branchNotFound

			}
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().branchNotFound
		}

		if (monitor.isCancelled())
			throw new CanceledException(MessageFormat.format(
					JGitText.get().operationCanceled
					JGitText.get().pullTaskName));

		MergeCommand merge = new MergeCommand(repo);
		merge.include(mergeBranch);
		MergeResult mergeRes;
		try {
			mergeRes = merge.call();
			monitor.update(1);
		} catch (NoHeadException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConcurrentRefUpdateException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (CheckoutConflictException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (InvalidMergeHeadsException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (WrongRepositoryStateException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (NoMessageException e) {
			throw new JGitInternalException(e.getMessage()
		}
		monitor.endTask();
		return new PullResult(fetchRes
	}
}
