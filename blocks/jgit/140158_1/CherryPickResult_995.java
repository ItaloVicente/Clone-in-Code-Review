package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.MultipleParentsNotAllowedException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.MissingObjectException;
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
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class CherryPickCommand extends GitCommand<CherryPickResult> {

	private List<Ref> commits = new LinkedList<>();

	private String ourCommitName = null;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	private Integer mainlineParentNumber;

	private boolean noCommit = false;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	protected CherryPickCommand(Repository repo) {
		super(repo);
	}

	@Override
	public CherryPickResult call() throws GitAPIException
			UnmergedPathsException
			WrongRepositoryStateException
		RevCommit newHead = null;
		List<Ref> cherryPickedRefs = new LinkedList<>();
		checkCallable();

		try (RevWalk revWalk = new RevWalk(repo)) {

			Ref headRef = repo.exactRef(Constants.HEAD);
			if (headRef == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);

			newHead = revWalk.parseCommit(headRef.getObjectId());

			for (Ref src : commits) {
				ObjectId srcObjectId = src.getPeeledObjectId();
				if (srcObjectId == null)
					srcObjectId = src.getObjectId();
				RevCommit srcCommit = revWalk.parseCommit(srcObjectId);

				final RevCommit srcParent = getParentCommit(srcCommit

				String ourName = calculateOurName(headRef);
				String cherryPickName = srcCommit.getId().abbreviate(7).name()

				ResolveMerger merger = (ResolveMerger) strategy.newMerger(repo);
				merger.setWorkingTreeIterator(new FileTreeIterator(repo));
				merger.setBase(srcParent.getTree());
				merger.setCommitNames(new String[] { "BASE"
						cherryPickName });
				if (merger.merge(newHead
					if (AnyObjectId.equals(newHead.getTree().getId()
							.getResultTreeId()))
						continue;
					DirCacheCheckout dco = new DirCacheCheckout(repo
							newHead.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.setProgressMonitor(monitor);
					dco.checkout();
					if (!noCommit)
						newHead = new Git(getRepository()).commit()
								.setMessage(srcCommit.getFullMessage())
										+ srcCommit.getShortMessage())
								.setAuthor(srcCommit.getAuthorIdent())
								.setNoVerify(true).call();
					cherryPickedRefs.add(src);
				} else {
					if (merger.failed())
						return new CherryPickResult(merger.getFailingPaths());


					String message = new MergeMessageFormatter()
							.formatWithConflicts(srcCommit.getFullMessage()
									merger.getUnmergedPaths());

					if (!noCommit)
						repo.writeCherryPickHead(srcCommit.getId());
					repo.writeMergeCommitMsg(message);

					return CherryPickResult.CONFLICT;
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
							JGitText.get().exceptionCaughtDuringExecutionOfCherryPickCommand
							e)
		}
		return new CherryPickResult(newHead
	}

	private RevCommit getParentCommit(RevCommit srcCommit
			throws MultipleParentsNotAllowedException
			IOException {
		final RevCommit srcParent;
		if (mainlineParentNumber == null) {
			if (srcCommit.getParentCount() != 1)
				throw new MultipleParentsNotAllowedException(
						MessageFormat.format(
								JGitText.get().canOnlyCherryPickCommitsWithOneParent
								srcCommit.name()
								Integer.valueOf(srcCommit.getParentCount())));
			srcParent = srcCommit.getParent(0);
		} else {
			if (mainlineParentNumber.intValue() > srcCommit.getParentCount())
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().commitDoesNotHaveGivenParent
						mainlineParentNumber));
			srcParent = srcCommit
					.getParent(mainlineParentNumber.intValue() - 1);
		}

		revWalk.parseHeaders(srcParent);
		return srcParent;
	}

	public CherryPickCommand include(Ref commit) {
		checkCallable();
		commits.add(commit);
		return this;
	}

	public CherryPickCommand include(AnyObjectId commit) {
		return include(commit.getName()
	}

	public CherryPickCommand include(String name
		return include(new ObjectIdRef.Unpeeled(Storage.LOOSE
				commit.copy()));
	}

	public CherryPickCommand setOurCommitName(String ourCommitName) {
		this.ourCommitName = ourCommitName;
		return this;
	}

	public CherryPickCommand setReflogPrefix(String prefix) {
		this.reflogPrefix = prefix;
		return this;
	}

	public CherryPickCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public CherryPickCommand setMainlineParentNumber(int mainlineParentNumber) {
		this.mainlineParentNumber = Integer.valueOf(mainlineParentNumber);
		return this;
	}

	public CherryPickCommand setNoCommit(boolean noCommit) {
		this.noCommit = noCommit;
		return this;
	}

	public CherryPickCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	private String calculateOurName(Ref headRef) {
		if (ourCommitName != null)
			return ourCommitName;

		String targetRefName = headRef.getTarget().getName();
		String headName = Repository.shortenRefName(targetRefName);
		return headName;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "CherryPickCommand [repo=" + repo + "
				+ "
				+ "
				+ "
				+ "]";
	}

}
