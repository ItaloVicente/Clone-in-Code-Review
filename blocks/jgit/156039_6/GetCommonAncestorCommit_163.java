package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class GetCommit {

	private final Git git;
	private final String commitId;

	public GetCommit(final Git git
		this.git = checkNotNull("git"
		this.commitId = checkNotEmpty("commitId"
	}

	public RevCommit execute() {
		final Repository repository = git.getRepository();

		try (final RevWalk revWalk = new RevWalk(repository)) {
			final ObjectId id = repository.resolve(this.commitId);
			return id != null ? revWalk.parseCommit(id) : null;
		} catch (Exception e) {
			throw new GitException("Error when trying to get commit"
		}
	}
}
