package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CommitCommand implements Callable<RevCommit> {
	public static final String CANNOT_COMMIT_WITHOUT_A_HEAD = "Cannot commit without a HEAD";

	public static final String COMMIT_MESSAGE_NOT_SPECIFIED = "commit message not specified";

	private PersonIdent author;

	private PersonIdent committer;

	private String message;

	private final Repository repo;

	protected CommitCommand(Git git) {
		repo = git.getRepository();
	}

	public RevCommit call() throws CorruptObjectException
			UnmergedPathException
		processOptions();

		final Ref head = repo.getRef(Constants.HEAD);
		if (head == null)
			throw new IllegalStateException(CANNOT_COMMIT_WITHOUT_A_HEAD);
		final ObjectId parentID = repo.resolve(Constants.HEAD + "^{commit}");

		final DirCache index = DirCache.lock(repo);
		try {
			final ObjectWriter repoWriter = new ObjectWriter(repo);

			final ObjectId indexTreeId = index.writeTree(repoWriter);
			final Commit commit = new Commit(repo);
			final Date currentDate = new Date();
			final PersonIdent currentCommitter = committer == null ? new PersonIdent(
					repo)
					: new PersonIdent(committer
			commit.setCommitter(currentCommitter);
			commit.setAuthor(author == null ? currentCommitter
					: new PersonIdent(author
			commit.setMessage(message);
			if (parentID != null)
				commit.setParentIds(new ObjectId[] { parentID });
			commit.setTreeId(indexTreeId);
			final ObjectId commitId = repoWriter.writeCommit(commit);
			commit.setCommitId(commitId);

			final RevCommit revCommit = new RevWalk(repo).parseCommit(commitId);
			final RefUpdate ru = repo.updateRef(Constants.HEAD);
			ru.setNewObjectId(commitId);
			ru.setRefLogMessage("commit : " + revCommit.getShortMessage()
					false);

			ru.setExpectedOldObjectId(parentID);
			switch (ru.update()) {
			case NEW:
			case FAST_FORWARD:
				return revCommit;
			default:
				throw new IllegalStateException("Updating the ref "
						+ Constants.HEAD + " to " + commitId.toString()
						+ " failed");
			}
		} finally {
			index.unlock();
		}
	}

	private void processOptions() throws IllegalArgumentException {
		if (message == null)
			throw new IllegalArgumentException(COMMIT_MESSAGE_NOT_SPECIFIED);
	}

	public CommitCommand setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public CommitCommand setCommitter(PersonIdent committer) {
		this.committer = committer;
		return this;
	}

	public CommitCommand setCommitter(String name
		this.committer = new PersonIdent(name
		return this;
	}

	public PersonIdent getCommitter() {
		return committer;
	}

	public CommitCommand setAuthor(PersonIdent author) {
		this.author = author;
		return this;
	}

	public CommitCommand setAuthor(String name
		this.author = new PersonIdent(name
		return this;
	}

	public PersonIdent getAuthor() {
		return author;
	}
}
