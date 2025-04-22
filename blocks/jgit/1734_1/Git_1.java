package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidCherryPickCommit;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class CherryPickCommand extends GitCommand<RevCommit> {
	private List<Ref> commits = new LinkedList<Ref>();

	protected CherryPickCommand(Repository repo) {
		super(repo);
	}

	public RevCommit call() throws GitAPIException {
		checkCallable();

		RevWalk revWalk = null;
		try {
			revWalk = new RevWalk(repo);

			Ref headRef = repo.getRef(Constants.HEAD);
			if (headRef == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
			RevCommit headCommit = revWalk.lookupCommit(headRef.getObjectId());

			for (Ref src : commits) {
				ObjectId srcObjectId = src.getPeeledObjectId();
				if (srcObjectId == null)
					srcObjectId = src.getObjectId();
				RevCommit srcCommit = revWalk.parseCommit(srcObjectId);

				if (srcCommit.getParentCount() != 1) {
					throw new InvalidCherryPickCommit(
							JGitText.get().canOnlyCherryPickCommitsWithOneParent);
				}
				RevCommit srcParent = revWalk.parseCommit(srcCommit
						.getParent(0));

				ResolveMerger merger = (ResolveMerger) MergeStrategy.RESOLVE
						.newMerger(repo);
				merger.setWorkingTreeIt(new FileTreeIterator(repo));
				merger.setBase(srcParent.getTree());

				if (merger.merge(headCommit
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.checkout();
					RevCommit newHead = new Git(getRepository()).commit()
							.setMessage(srcCommit.getFullMessage())
							.setAuthor(srcCommit.getAuthorIdent()).call();
					return newHead;
				} else {
					return null;
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
							JGitText.get().exceptionCaughtDuringExecutionOfCherryPickCommand
							e)
		} finally {
			if (revWalk != null)
				revWalk.release();
		}
		return null;

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

}
