package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitIndex;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.WorkDirCheckout;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class MergeCommand extends GitCommand<MergeResult> {

	private MergeStrategy mergeStrategy = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE;

	private Ref commit;

	protected MergeCommand(Repository repo) {
		super(repo);
	}

	public MergeResult call() throws NoHeadException
			ConcurrentRefUpdateException {
		checkCallable();

		try {
			Ref head = repo.getRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(
						"Commit on repo without HEAD currently not supported");
			StringBuilder refLogMessage = new StringBuilder("merge ");
			refLogMessage.append(commit.getName() + ": ");

			RevWalk revWalk = new RevWalk(repo);
			RevCommit headCommit = revWalk.lookupCommit(head.getObjectId());
			RevCommit srcCommit = revWalk.lookupCommit(commit.getLeaf()
					.getObjectId());
			if (revWalk.isMergedInto(srcCommit
				return new MergeResult(headCommit
						MergeResult.MergeStatus.ALREADY_UP_TO_DATE
						mergeStrategy);
			}

			Commit newHeadCommit;
			MergeResult.MergeStatus mergeStatus = null;
			if (revWalk.isMergedInto(headCommit
				newHeadCommit = repo.mapCommit(srcCommit);
				refLogMessage.append("Fast forward");
				mergeStatus = MergeResult.MergeStatus.FAST_FORWARD;

			} else {
				return new MergeResult(null
						MergeResult.MergeStatus.NOT_SUPPORTED
						"only already-up-to-date and fast forward merges are available");

			}
			checkoutNewHead(revWalk

			return updateHead(refLogMessage

		} catch (IOException e) {
			throw new JGitInternalException(
					"Exception caught during execution of merge command"
		}
	}

	private void checkoutNewHead(RevWalk revWalk
			Commit newHeadCommit) throws IOException {
		GitIndex index = repo.getIndex();

		File workDir = repo.getWorkDir();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(repo
					workDir
					newHeadCommit.getTree());
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (CheckoutConflictException e) {
				throw new JGitInternalException(
						"Couldn't check out because of conflicts"
			}
			index.write();
		}
	}

	private MergeResult updateHead(StringBuilder refLogMessage
			Commit newHeadCommit
			throws IOException
		RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
		refUpdate.setNewObjectId(newHeadCommit.getCommitId());
		refUpdate.setRefLogMessage(refLogMessage.toString()
		Result rc = refUpdate.update();
		switch (rc) {
		case NEW:
		case FAST_FORWARD:
			setCallable(false);
			return new MergeResult(newHeadCommit.getCommitId()
					mergeStrategy);
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					"Could lock HEAD during commit"
		default:
			throw new JGitInternalException("Updating the ref "
					+ Constants.HEAD + " to "
					+ newHeadCommit.getCommitId().toString()
					+ " failed. ReturnCode from RefUpdate.update() was " + rc);
		}
	}

	public MergeCommand setStrategy(MergeStrategy mergeStrategy) {
		checkCallable();
		this.mergeStrategy = mergeStrategy;
		return this;
	}

	public MergeCommand setCommit(Ref commit) {
		checkCallable();
		this.commit = commit;
		return this;
	}

}
