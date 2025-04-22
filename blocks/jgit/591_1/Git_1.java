package org.eclipse.jgit.api;

import java.io.IOException;

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

public class CommitCommand {
	private PersonIdent author;

	private PersonIdent committer;

	private String message;

	private Repository repo;

	protected CommitCommand(Git git) {
		repo = git.getRepository();
	}

	public Commit run() throws CorruptObjectException
			IOException {
		ObjectId commitID;
		ObjectId indexTreeId;
		final Commit commit;
		int firstLineEnd;
		ObjectWriter repoWriter = new ObjectWriter(repo);

		processOptions();

		final Ref head = repo.getRef(Constants.HEAD);
		if (head == null || !head.isSymbolic())
			throw new IllegalStateException("Cannot commit on detached HEAD");
		ObjectId parentID = repo.resolve(Constants.HEAD + "^{commit}");

		DirCache index = DirCache.lock(repo);
		try {
			indexTreeId = index.writeTree(repoWriter);

			commit = new Commit(repo);
			commit.setAuthor(author);
			commit.setCommitter(committer);
			commit.setMessage(message);
			if (parentID!=null)
				commit.setParentIds(new ObjectId[] { parentID });
			commit.setTreeId(indexTreeId);
			commitID = repoWriter.writeCommit(commit);
			commit.setCommitId(commitID);

			firstLineEnd = message.indexOf('\n');
			final RefUpdate ru = repo.updateRef(Constants.HEAD);
			ru.setNewObjectId(commitID);
			ru.setRefLogMessage("commit : "
					+ ((firstLineEnd == -1) ? message : message.substring(0
							firstLineEnd))
			ru.forceUpdate();

			return commit;
		} finally {
			index.unlock();
		}
	}

	private void processOptions() throws IllegalArgumentException {
		if (committer == null) {
			committer = new PersonIdent(repo);
		}
		if (author == null) {
			author = committer;
		}
		if (message == null) {
			throw new IllegalArgumentException("commit message not specified");
		}
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

	public PersonIdent getCommitter() {

		return committer;
	}

	public CommitCommand setAuthor(PersonIdent author) {
		this.author = author;
		return this;
	}

	public PersonIdent getAuthor() {
		return author;
	}
}
