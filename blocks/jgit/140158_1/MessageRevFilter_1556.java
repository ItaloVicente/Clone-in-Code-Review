package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class MaxCountRevFilter extends RevFilter {

	private int maxCount;

	private int count;

	public static RevFilter create(int maxCount) {
		if (maxCount < 0)
			throw new IllegalArgumentException(
					JGitText.get().maxCountMustBeNonNegative);
		return new MaxCountRevFilter(maxCount);
	}

	private MaxCountRevFilter(int maxCount) {
		this.count = 0;
		this.maxCount = maxCount;
	}

	@Override
	public boolean include(RevWalk walker
			throws StopWalkException
			IncorrectObjectTypeException
		count++;
		if (count > maxCount)
			throw StopWalkException.INSTANCE;
		return true;
	}

	@Override
	public RevFilter clone() {
		return new MaxCountRevFilter(maxCount);
	}
}
