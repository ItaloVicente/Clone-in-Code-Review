package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.MultipleParentsNotAllowedException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeMessageFormatter;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class RevertCommand extends GitCommand<RevCommit> {
	private List<Ref> commits = new LinkedList<>();

	private String ourCommitName = null;

	private List<Ref> revertedRefs = new LinkedList<>();

	private MergeResult failingResult;

	private List<String> unmergedPaths;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	protected RevertCommand(Repository repo) {
		super(repo);
	}

	@Override
	public RevCommit call() throws NoMessageException
			ConcurrentRefUpdateException
			GitAPIException {
		RevCommit newHead = null;
		checkCallable();

		try (RevWalk revWalk = new RevWalk(repo)) {

			Ref headRef = repo.exactRef(Constants.HEAD);
			if (headRef == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
			RevCommit headCommit = revWalk.parseCommit(headRef.getObjectId());

			newHead = headCommit;

			for (Ref src : commits) {
				ObjectId srcObjectId = src.getPeeledObjectId();
				if (srcObjectId == null)
					srcObjectId = src.getObjectId();
				RevCommit srcCommit = revWalk.parseCommit(srcObjectId);

				if (srcCommit.getParentCount() != 1)
					throw new MultipleParentsNotAllowedException(
							MessageFormat.format(
									JGitText.get().canOnlyRevertCommitsWithOneParent
									srcCommit.name()
									Integer.valueOf(srcCommit.getParentCount())));

				RevCommit srcParent = srcCommit.getParent(0);
				revWalk.parseHeaders(srcParent);

				String ourName = calculateOurName(headRef);
				String revertName = srcCommit.getId().abbreviate(7).name()

				ResolveMerger merger = (ResolveMerger) strategy.newMerger(repo);
				merger.setWorkingTreeIterator(new FileTreeIterator(repo));
				merger.setBase(srcCommit.getTree());
				merger.setCommitNames(new String[] {
						"BASE"

				if (merger.merge(headCommit
					if (AnyObjectId.equals(headCommit.getTree().getId()
							.getResultTreeId()))
						continue;
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.setProgressMonitor(monitor);
					dco.checkout();
					try (Git git = new Git(getRepository())) {
						newHead = git.commit().setMessage(newMessage)
								.call();
					}
					revertedRefs.add(src);
					headCommit = newHead;
				} else {
					unmergedPaths = merger.getUnmergedPaths();
					Map<String
							.getFailingPaths();
					if (failingPaths != null)
						failingResult = new MergeResult(null
								merger.getBaseCommitId()
								new ObjectId[] { headCommit.getId()
										srcParent.getId() }
								MergeStatus.FAILED
								merger.getMergeResults()
					else
						failingResult = new MergeResult(null
								merger.getBaseCommitId()
								new ObjectId[] { headCommit.getId()
										srcParent.getId() }
								MergeStatus.CONFLICTING
								merger.getMergeResults()
					if (!merger.failed() && !unmergedPaths.isEmpty()) {
						String message = new MergeMessageFormatter()
						.formatWithConflicts(newMessage
								merger.getUnmergedPaths());
						repo.writeRevertHead(srcCommit.getId());
						repo.writeMergeCommitMsg(message);
					}
					return null;
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
									JGitText.get().exceptionCaughtDuringExecutionOfRevertCommand
							e)
		}
		return newHead;
	}

	public RevertCommand include(Ref commit) {
		checkCallable();
		commits.add(commit);
		return this;
	}

	public RevertCommand include(AnyObjectId commit) {
		return include(commit.getName()
	}

	public RevertCommand include(String name
		return include(new ObjectIdRef.Unpeeled(Storage.LOOSE
				commit.copy()));
	}

	public RevertCommand setOurCommitName(String ourCommitName) {
		this.ourCommitName = ourCommitName;
		return this;
	}

	private String calculateOurName(Ref headRef) {
		if (ourCommitName != null)
			return ourCommitName;

		String targetRefName = headRef.getTarget().getName();
		String headName = Repository.shortenRefName(targetRefName);
		return headName;
	}

	public List<Ref> getRevertedRefs() {
		return revertedRefs;
	}

	public MergeResult getFailingResult() {
		return failingResult;
	}

	public List<String> getUnmergedPaths() {
		return unmergedPaths;
	}

	public RevertCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public RevertCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}
}
