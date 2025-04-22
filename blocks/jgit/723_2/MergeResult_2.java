package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.WorkDirCheckout;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class MergeCommand extends GitCommand<MergeResult> {

	private MergeStrategy mergeStrategy = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE;

	private Map<String

	protected MergeCommand(Repository repo) {
		super(repo);
	}

	public MergeResult call() throws NoHeadException
			ConcurrentRefUpdateException
		checkCallable();

		try {
			Ref head = repo.getRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(
						"Commit on repo without HEAD currently not supported");
			StringBuilder refLogMessage = new StringBuilder("merge ");

			RevWalk revWalk = new RevWalk(repo);
			RevCommit headCommit = revWalk.lookupCommit(head.getObjectId());

			RevCommit newHeadCommit = null;
			MergeStatus mergeStatus = MergeResult.MergeStatus.ALREADY_UP_TO_DATE;
			boolean firstEntry=true;
			for (Map.Entry<String
				if (!firstEntry)
					refLogMessage.append("
				firstEntry = false;
				refLogMessage.append(commitEntry.getKey());
				RevCommit srcCommit = revWalk.lookupCommit(commitEntry.getValue());
				if (revWalk.isMergedInto(srcCommit
				} else if (revWalk.isMergedInto(headCommit
					if (newHeadCommit == null
							|| revWalk.isMergedInto(newHeadCommit
						newHeadCommit = srcCommit;
					mergeStatus = MergeResult.MergeStatus.FAST_FORWARD;
				} else {
					mergeStatus = MergeResult.MergeStatus.NOT_SUPPORTED;
					break;
				}
			}

			if (mergeStatus==MergeStatus.ALREADY_UP_TO_DATE)
				return new MergeResult(headCommit
			else if (mergeStatus==MergeStatus.FAST_FORWARD) {
				refLogMessage.append(": Fast forward");
				checkoutNewHead(revWalk

				return updateHead(refLogMessage
			} else
				return new MergeResult(null
						MergeResult.MergeStatus.NOT_SUPPORTED
				"only already-up-to-date and fast forward merges are available");
		} catch (IOException e) {
			throw new JGitInternalException(
					"Exception caught during execution of merge command"
		}
	}

	private void checkoutNewHead(RevWalk revWalk
			RevCommit newHeadCommit) throws IOException
		GitIndex index = repo.getIndex();

		File workDir = repo.getWorkDir();
		if (workDir != null) {
			WorkDirCheckout workDirCheckout = new WorkDirCheckout(repo
					workDir
					newHeadCommit.asCommit(revWalk).getTree());
			workDirCheckout.setFailOnConflict(true);
			try {
				workDirCheckout.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
				throw new CheckoutConflictException(
						"Couldn't check out because of conflicts"
						workDirCheckout.getConflicts()
			}
			index.write();
		}
	}

	private MergeResult updateHead(StringBuilder refLogMessage
			ObjectId newHeadId
			throws IOException
		RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
		refUpdate.setNewObjectId(newHeadId);
		refUpdate.setRefLogMessage(refLogMessage.toString()
		refUpdate.setExpectedOldObjectId(oldHeadID);
		Result rc = refUpdate.update();
		switch (rc) {
		case NEW:
		case FAST_FORWARD:
			setCallable(false);
			return new MergeResult(newHeadId
					mergeStrategy);
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					"Couldn't lock HEAD during merge"
		default:
			throw new JGitInternalException("Updating the ref "
					+ Constants.HEAD + " to "
					+ newHeadId.toString()
					+ " failed. ReturnCode from RefUpdate.update() was " + rc);
		}
	}

	public MergeCommand setStrategy(MergeStrategy mergeStrategy) {
		checkCallable();
		this.mergeStrategy = mergeStrategy;
		return this;
	}

	public MergeCommand include(Ref commit) {
		return include(commit.getName()
	}

	public MergeCommand include(AnyObjectId commit) {
		return include(commit.getName()
	}

	public MergeCommand include(String name
		checkCallable();
		this.commits.put(name
		return this;
	}
}
