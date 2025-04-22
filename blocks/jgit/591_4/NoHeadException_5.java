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

	private RevWalk walk;

	private boolean startSpecified = false;

	protected LogCommand(Git git) {
		repo = git.getRepository();
		walk = new RevWalk(repo);
	}

	public Iterable<RevCommit> call() throws NoHeadException
			JGitInternalException {
		if (!startSpecified) {
			try {
				ObjectId headId = repo.resolve(Constants.HEAD);
				if (headId == null)
					throw new NoHeadException(
							"No HEAD exists and no explicit starting revision was specified");
				add(headId);
			} catch (IOException e) {
				throw new JGitInternalException(
						"An exception occured while trying to add the Id of HEAD"
						e);
			}
		}
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
			JGitInternalException {
		return not(since).add(until);
	}

	private LogCommand add(boolean include
			throws MissingObjectException
			JGitInternalException {
		try {
			if (include) {
				walk.markStart(walk.lookupCommit(start));
				startSpecified = true;
			} else
				walk.markUninteresting(walk.lookupCommit(start));
			return this;
		} catch (MissingObjectException e) {
			throw e;
		} catch (IncorrectObjectTypeException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(
					"Exception occured during adding of " + start
							+ " as option to a Log command"
		}
	}
}
