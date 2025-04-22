package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.MultipleParentsNotAllowedException;
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

	private List<Ref> cherryPickedRefs = new LinkedList<Ref>();

	protected CherryPickCommand(Repository repo) {
		super(repo);
	}

	public RevCommit call() throws GitAPIException {
		RevCommit newHead = null;
		checkCallable();

		RevWalk revWalk = new RevWalk(repo);
		try {

			Ref headRef = repo.getRef(Constants.HEAD);
			if (headRef == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
			RevCommit headCommit = revWalk.parseCommit(headRef.getObjectId());

			for (Ref src : commits) {
				ObjectId srcObjectId = src.getPeeledObjectId();
				if (srcObjectId == null)
					srcObjectId = src.getObjectId();
				RevCommit srcCommit = revWalk.parseCommit(srcObjectId);

				if (srcCommit.getParentCount() != 1) {
					throw new MultipleParentsNotAllowedException(
							JGitText.get().canOnlyCherryPickCommitsWithOneParent);
				}
				RevCommit srcParent = srcCommit.getParent(0);
				revWalk.parseHeaders(srcParent);

				ResolveMerger merger = (ResolveMerger) MergeStrategy.RESOLVE
						.newMerger(repo);
				merger.setWorkingTreeIterator(new FileTreeIterator(repo));
				merger.setBase(srcParent.getTree());

				if (merger.merge(headCommit
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.checkout();
					newHead = new Git(getRepository()).commit()
							.setMessage(srcCommit.getFullMessage())
							.setAuthor(srcCommit.getAuthorIdent()).call();
					cherryPickedRefs.add(src);
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
			revWalk.release();
		}
		return newHead;
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

	public List<Ref> getCherryPickedRefs() {
		return cherryPickedRefs;
	}
}
