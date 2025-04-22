package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class LogCommand implements Callable<Iterable<RevCommit>> {
	private final Repository repo;

	private ObjectId start;

	private RevWalk walk;

	protected LogCommand(Git git) {
		repo = git.getRepository();
		walk = new RevWalk(repo);
	}

	private void processOptions() throws IllegalArgumentException
		if (start == null) {
			start = repo.resolve(Constants.HEAD);
			if (start == null)
				throw new IllegalStateException("Cannot resolve "
						+ Constants.HEAD);
		}
	}

	public Iterable<RevCommit> call() throws IllegalStateException
			MissingObjectException
		processOptions();

		walk = new RevWalk(repo);
		walk.markStart(walk.parseCommit(start));
		return walk;
	}

	public LogCommand add(ObjectId start) throws MissingObjectException
			IncorrectObjectTypeException
		return add(true
	}

	public LogCommand not(ObjectId start) throws MissingObjectException
			IncorrectObjectTypeException
		return add(false
	}

	public LogCommand addRange(ObjectId since
			throws MissingObjectException
			IOException {
		return not(since).add(until);
	}

	private LogCommand add(boolean include
			throws MissingObjectException
			IOException {
		if (include)
			walk.markStart(walk.lookupCommit(start));
		else
			walk.markUninteresting(walk.lookupCommit(start));
		return this;
	}
}
