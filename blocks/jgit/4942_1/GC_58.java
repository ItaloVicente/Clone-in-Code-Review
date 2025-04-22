
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
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

		List<RevCommit> commits = new ArrayList<RevCommit>();
		for (RevCommit c : walk)
			commits.add(c);
		return commits;
	}

	public static List<Ref> findBranchesReachableFrom(RevCommit commit
			RevWalk revWalk
			throws MissingObjectException
			IOException {

		List<Ref> result = new ArrayList<Ref>();
		revWalk.markStart(Arrays.asList(commit.getParents()));
		ObjectIdSubclassMap<ObjectId> cutOff = new ObjectIdSubclassMap<ObjectId>();


		for (Ref ref : refs) {
			RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());

			if (headCommit.getCommitTime() + SKEW < commit.getCommitTime())
				continue;

			revWalk.resetRetain();
			revWalk.markStart(headCommit);
			RevCommit current;
			Ref found = null;
			while ((current = revWalk.next()) != null) {
				if (AnyObjectId.equals(current
					found = ref;
					break;
				}
				if (cutOff.contains(current))
					break;
				maybeCutOff.add(current.toObjectId());
			}
			if (found != null)
				result.add(ref);
			else
				for (ObjectId id : maybeCutOff)
					cutOff.addIfAbsent(id);

		}
		return result;
	}

}
