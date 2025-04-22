
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;

public final class RevWalkUtils {

	private RevWalkUtils() {
	}

	public static int count(final RevWalk walk
			final RevCommit end) throws MissingObjectException
			IncorrectObjectTypeException
		return find(walk
	}

	public static List<RevCommit> find(final RevWalk walk
			final RevCommit start
			throws MissingObjectException
			IOException {
		walk.reset();
		walk.markStart(start);
		if (end != null)
			walk.markUninteresting(end);

		List<RevCommit> commits = new ArrayList<>();
		for (RevCommit c : walk)
			commits.add(c);
		return commits;
	}

	public static List<Ref> findBranchesReachableFrom(RevCommit commit
			RevWalk revWalk
			throws MissingObjectException
			IOException {

		commit = revWalk.parseCommit(commit.getId());
		revWalk.reset();
		List<Ref> result = new ArrayList<>();


		for (Ref ref : refs) {
			RevObject maybehead = revWalk.parseAny(ref.getObjectId());
			if (!(maybehead instanceof RevCommit))
				continue;
			RevCommit headCommit = (RevCommit) maybehead;

			if (headCommit.getCommitTime() + SKEW < commit.getCommitTime())
				continue;

			if (revWalk.isMergedInto(commit
				result.add(ref);
		}
		return result;
	}

}
