package org.eclipse.egit.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.revwalk.RevCommit;

public class CommitUtil {

	private CommitUtil() {
	}

	public static List<RevCommit> sortCommits(Collection<RevCommit> commits) {
		Map<RevCommit, RevCommit> parentToChild = new HashMap<RevCommit, RevCommit>();
		RevCommit firstCommit = null;
		for (RevCommit commit : commits) {
			RevCommit parentCommit = commit.getParent(0);
			parentToChild.put(parentCommit, commit);
			if (!commits.contains(parentCommit))
				firstCommit = commit;
		}

		List<RevCommit> sortedCommits = new ArrayList<RevCommit>();
		sortedCommits.add(firstCommit);
		RevCommit parentCommit = firstCommit;
		for (;;) {
			RevCommit childCommit = parentToChild.get(parentCommit);
			if (childCommit == null)
				break;
			sortedCommits.add(childCommit);
			parentCommit = childCommit;
		}

		return sortedCommits;
	}
}
