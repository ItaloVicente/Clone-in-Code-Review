
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class NotRevFilter extends RevFilter {
	public static RevFilter create(RevFilter a) {
		return new NotRevFilter(a);
	}

	private final RevFilter a;

	private NotRevFilter(RevFilter one) {
		a = one;
	}

	@Override
	public RevFilter negate() {
		return a;
	}

	@Override
	public boolean include(RevWalk walker
			throws MissingObjectException
			IOException {
		return !a.include(walker
	}

	@Override
	public boolean requiresCommitBody() {
		return a.requiresCommitBody();
	}

	@Override
	public RevFilter clone() {
		return new NotRevFilter(a.clone());
	}

	@Override
	public String toString() {
	}
}
