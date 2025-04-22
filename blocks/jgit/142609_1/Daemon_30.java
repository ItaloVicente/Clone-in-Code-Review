package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public interface ReachabilityChecker {

	Optional<RevCommit> areAllReachable(Collection<RevCommit> targets
			Collection<RevCommit> starters)
			throws MissingObjectException
			IOException;
}
