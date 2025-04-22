package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CommitCommand extends GitCommand<RevCommit> {
	private PersonIdent author;

	private PersonIdent committer;

	private String message;

	protected CommitCommand(Repository repo) {
		super(repo);
	}

	public RevCommit call() throws NoHeadException
			UnmergedPathException
			JGitInternalException {
		checkCallable();
		processOptions();

		try {
			Ref head = repo.getRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(
						"Commit on repo without HEAD currently not supported");

			ObjectId parentID = repo.resolve(Constants.HEAD + "^{commit}");

			DirCache index = DirCache.lock(repo);
			try {
				ObjectWriter repoWriter = new ObjectWriter(repo);

				ObjectId indexTreeId = index.writeTree(repoWriter);

				Commit commit = new Commit(repo);
				commit.setCommitter(committer);
				commit.setAuthor(author);
				commit.setMessage(message);
				if (parentID != null)
					commit.setParentIds(new ObjectId[] { parentID });
				commit.setTreeId(indexTreeId);
				ObjectId commitId = repoWriter.writeCommit(commit);

				RevCommit revCommit = new RevWalk(repo).parseCommit(commitId);
				RefUpdate ru = repo.updateRef(Constants.HEAD);
				ru.setNewObjectId(commitId);
				ru.setRefLogMessage("commit : " + revCommit.getShortMessage()
						false);

				ru.setExpectedOldObjectId(parentID);
				Result rc = ru.update();
				switch (rc) {
				case NEW:
				case FAST_FORWARD:
					setCallable(false);
					return revCommit;
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(
							"Could lock HEAD during commit"
				default:
					throw new JGitInternalException(
							"Updating the ref "
									+ Constants.HEAD
									+ " to "
									+ commitId.toString()
									+ " failed. ReturnCode from RefUpdate.update() was "
									+ rc);
				}
			} finally {
				index.unlock();
			}
		} catch (UnmergedPathException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(
					"Exception caught during execution of commit command"
		}
	}

	private void processOptions() throws NoMessageException {
		if (message == null)
			throw new NoMessageException("commit message not specified");
		if (committer == null)
			committer = new PersonIdent(repo);
		if (author == null)
			author = committer;
	}

	public CommitCommand setMessage(String message) {
		checkCallable();
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public CommitCommand setCommitter(PersonIdent committer) {
		checkCallable();
		this.committer = committer;
		return this;
	}

	public CommitCommand setCommitter(String name
		checkCallable();
		return setCommitter(new PersonIdent(name
	}

	public PersonIdent getCommitter() {
		return committer;
	}

	public CommitCommand setAuthor(PersonIdent author) {
		checkCallable();
		this.author = author;
		return this;
	}

	public CommitCommand setAuthor(String name
		checkCallable();
		return setAuthor(new PersonIdent(name
	}

	public PersonIdent getAuthor() {
		return author;
	}
}
