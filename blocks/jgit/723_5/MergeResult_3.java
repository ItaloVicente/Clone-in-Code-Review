package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.WorkDirCheckout;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class MergeCommand extends GitCommand<MergeResult> {

	private MergeStrategy mergeStrategy = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE;

	private List<Ref> commits = new LinkedList<Ref>();

	protected MergeCommand(Repository repo) {
		super(repo);
	}

	public MergeResult call() throws NoHeadException
			ConcurrentRefUpdateException
			InvalidMergeHeadsException {
		checkCallable();

		if (commits.size() != 1)
			throw new InvalidMergeHeadsException(
					commits.isEmpty() ? JGitText.get().noMergeHeadSpecified
							: MessageFormat.format(
									JGitText.get().mergeStrategyDoesNotSupportHeads
									mergeStrategy.getName()

		try {
			Ref head = repo.getRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
			StringBuilder refLogMessage = new StringBuilder("merge ");

			RevWalk revWalk = new RevWalk(repo);
			RevCommit headCommit = revWalk.lookupCommit(head.getObjectId());

			Ref ref = commits.get(0);

			refLogMessage.append(ref.getName());

			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();

			RevCommit srcCommit = revWalk.lookupCommit(objectId);
			if (revWalk.isMergedInto(srcCommit
				setCallable(false);
				return new MergeResult(headCommit
						MergeStatus.ALREADY_UP_TO_DATE
			} else if (revWalk.isMergedInto(headCommit
				refLogMessage.append(": " + MergeStatus.FAST_FORWARD);
				checkoutNewHead(revWalk
				updateHead(refLogMessage
				setCallable(false);
				return new MergeResult(srcCommit
						mergeStrategy);
			} else {
				return new MergeResult(
						headCommit
						MergeResult.MergeStatus.NOT_SUPPORTED
						mergeStrategy
						JGitText.get().onlyAlreadyUpToDateAndFastForwardMergesAreAvailable);
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
							JGitText.get().exceptionCaughtDuringExecutionOfMergeCommand
							e));
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
						JGitText.get().couldNotCheckOutBecauseOfConflicts
						workDirCheckout.getConflicts()
			}
			index.write();
		}
	}

	private void updateHead(StringBuilder refLogMessage
			ObjectId newHeadId
			ConcurrentRefUpdateException {
		RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
		refUpdate.setNewObjectId(newHeadId);
		refUpdate.setRefLogMessage(refLogMessage.toString()
		refUpdate.setExpectedOldObjectId(oldHeadID);
		Result rc = refUpdate.update();
		switch (rc) {
		case NEW:
		case FAST_FORWARD:
			return;
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					JGitText.get().couldNotLockHEAD
		default:
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().updatingRefFailed
					newHeadId.toString()
		}
	}

	public MergeCommand setStrategy(MergeStrategy mergeStrategy) {
		checkCallable();
		this.mergeStrategy = mergeStrategy;
		return this;
	}

	public MergeCommand include(Ref commit) {
		checkCallable();
		commits.add(commit);
		return this;
	}

	public MergeCommand include(AnyObjectId commit) {
		return include(commit.getName()
	}

	public MergeCommand include(String name
		return include(new ObjectIdRef.Unpeeled(Storage.LOOSE
				commit.copy()));
	}
}
