package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class SkipRevFilter extends RevFilter {

	private int skip;

	private int count;

	public static RevFilter create(int skip) {
		if (skip < 0)
			throw new IllegalArgumentException(
					JGitText.get().skipMustBeNonNegative);
		return new SkipRevFilter(skip);
	}

	private SkipRevFilter(int skip) {
		this.count = 0;
		this.skip = skip;
	}

	@Override
	public boolean include(RevWalk walker
			throws StopWalkException
			IncorrectObjectTypeException
		if (skip > count++)
			return false;
		return true;
	}

	@Override
	public RevFilter clone() {
		return new SkipRevFilter(skip);
	}

}
