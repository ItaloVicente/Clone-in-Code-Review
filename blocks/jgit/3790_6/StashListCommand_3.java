package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FileUtils;

public class StashCreateCommand extends GitCommand<RevCommit> {
	private PersonIdent author;

	private PersonIdent committer;

	private String message;

	protected StashCreateCommand(Repository repo) {
		super(repo);
	}

	private RevCommit checkoutCurrentHead(DirCache dc) throws IOException
			NoHeadException
		ObjectId headTree = repo.resolve(Constants.HEAD + "^{tree}");

		if (headTree == null)
			throw new NoHeadException(
					JGitText.get().cannotRebaseWithoutCurrentHead);

		try {
			DirCacheCheckout dco = new DirCacheCheckout(repo
			dco.setFailOnConflict(false);
			boolean needsDeleteFiles = dco.checkout();
			if (needsDeleteFiles) {
				List<String> fileList = dco.getToBeDeleted();
				for (String filePath : fileList) {
					File fileToDelete = new File(repo.getWorkTree()
					if (fileToDelete.exists())
						FileUtils.delete(fileToDelete
								| FileUtils.RETRY);
				}
			}
		} finally {
			dc.unlock();
		}
		RevWalk rw = new RevWalk(repo);
		RevCommit commit = rw.parseCommit(repo.resolve(Constants.HEAD));
		rw.release();
		return commit;
	}

	public RevCommit call() throws Exception {
		DirCache index = repo.lockDirCache();
		ObjectInserter obi = repo.newObjectInserter();

		ObjectId headCommitId = repo.resolve(Constants.HEAD + "^{commit}");

		CommitBuilder secondParent = new CommitBuilder();
		secondParent.setCommitter(committer);
		secondParent.setAuthor(author);
		secondParent.setMessage("commitMessage");
		secondParent.setParentId(headCommitId);
		secondParent.setTreeId(index.writeTree(repo.newObjectInserter()));
		ObjectId secondParentCommitId = obi.insert(secondParent);
		obi.flush();

		List<AnyObjectId> stashParentSet = new ArrayList<AnyObjectId>();
		stashParentSet.add(headCommitId);
		stashParentSet.add(secondParentCommitId);

		CommitBuilder stashObject = new CommitBuilder();
		stashObject.setCommitter(committer);
		stashObject.setAuthor(author);
		stashObject.setMessage("commitMessage");
		stashObject.setParentIds(stashParentSet);
		stashObject.setTreeId(index.writeTree(repo.newObjectInserter()));
		ObjectId stashHead = obi.insert(stashObject);
		obi.flush();

		RefUpdate ru = repo.updateRef(Constants.R_STASH);
		ru.setNewObjectId(stashHead);
		if (repo.resolve("refs/stash") != null) {
			ru.setExpectedOldObjectId(repo.resolve("refs/stash"));
		} else {
			ru.setExpectedOldObjectId(ObjectId.zeroId());
		}
		ru.forceUpdate();

		checkoutCurrentHead(index);
		return null;
	}

	public StashCreateCommand setMessage(String message) {
		checkCallable();
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public StashCreateCommand setCommitter(PersonIdent committer) {
		checkCallable();
		this.committer = committer;
		return this;
	}

	public StashCreateCommand setCommitter(String name
		checkCallable();
		return setCommitter(new PersonIdent(name
	}

	public PersonIdent getCommitter() {
		return committer;
	}

	public StashCreateCommand setAuthor(PersonIdent author) {
		checkCallable();
		this.author = author;
		return this;
	}

	public StashCreateCommand setAuthor(String name
		checkCallable();
		return setAuthor(new PersonIdent(name
	}

	public PersonIdent getAuthor() {
		return author;
	}
}
