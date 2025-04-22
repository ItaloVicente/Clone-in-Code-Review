package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.revwalk.filter.SkipRevFilter;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class LogCommand extends GitCommand<Iterable<RevCommit>> {
	private RevWalk walk;

	private boolean startSpecified = false;

	private RevFilter revFilter;

	private final List<PathFilter> pathFilters = new ArrayList<>();

	private int maxCount = -1;

	private int skip = -1;

	protected LogCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
	}

	@Override
	public Iterable<RevCommit> call() throws GitAPIException
		checkCallable();
		if (pathFilters.size() > 0)
			walk.setTreeFilter(AndTreeFilter.create(
					PathFilterGroup.create(pathFilters)
		if (skip > -1 && maxCount > -1)
			walk.setRevFilter(AndRevFilter.create(SkipRevFilter.create(skip)
					MaxCountRevFilter.create(maxCount)));
		else if (skip > -1)
			walk.setRevFilter(SkipRevFilter.create(skip));
		else if (maxCount > -1)
			walk.setRevFilter(MaxCountRevFilter.create(maxCount));
		if (!startSpecified) {
			try {
				ObjectId headId = repo.resolve(Constants.HEAD);
				if (headId == null)
					throw new NoHeadException(
							JGitText.get().noHEADExistsAndNoExplicitStartingRevisionWasSpecified);
				add(headId);
			} catch (IOException e) {
				throw new JGitInternalException(
						JGitText.get().anExceptionOccurredWhileTryingToAddTheIdOfHEAD
						e);
			}
		}

		if (this.revFilter != null) {
			walk.setRevFilter(this.revFilter);
		}

		setCallable(false);
		return walk;
	}

	public LogCommand add(AnyObjectId start) throws MissingObjectException
			IncorrectObjectTypeException {
		return add(true
	}

	public LogCommand not(AnyObjectId start) throws MissingObjectException
			IncorrectObjectTypeException {
		return add(false
	}

	public LogCommand addRange(AnyObjectId since
			throws MissingObjectException
		return not(since).add(until);
	}

	public LogCommand all() throws IOException {
		for (Ref ref : getRepository().getRefDatabase().getRefs()) {
			if(!ref.isPeeled())
				ref = getRepository().getRefDatabase().peel(ref);

			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();
			RevCommit commit = null;
			try {
				commit = walk.parseCommit(objectId);
			} catch (MissingObjectException | IncorrectObjectTypeException e) {
			}
			if (commit != null)
				add(commit);
		}
		return this;
	}

	public LogCommand addPath(String path) {
		checkCallable();
		pathFilters.add(PathFilter.create(path));
		return this;
	}

	public LogCommand setSkip(int skip) {
		checkCallable();
		this.skip = skip;
		return this;
	}

	public LogCommand setMaxCount(int maxCount) {
		checkCallable();
		this.maxCount = maxCount;
		return this;
	}

	private LogCommand add(boolean include
			throws MissingObjectException
			JGitInternalException {
		checkCallable();
		try {
			if (include) {
				walk.markStart(walk.lookupCommit(start));
				startSpecified = true;
			} else
				walk.markUninteresting(walk.lookupCommit(start));
			return this;
		} catch (MissingObjectException | IncorrectObjectTypeException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionOccurredDuringAddingOfOptionToALogCommand
					
		}
	}


	public LogCommand setRevFilter(RevFilter aFilter) {
		checkCallable();
		this.revFilter = aFilter;
		return this;
	}
}
