
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_MergeBase")
class MergeBase extends TextBuiltin {
	@Option(name = "--all"
	private boolean all;

	@Argument(index = 0
	void commit_0(final RevCommit c) {
		commits.add(c);
	}

	@Argument(index = 1
	private List<RevCommit> commits = new ArrayList<>();

	@Override
	protected void run() {
		try {
			for (RevCommit c : commits) {
				argWalk.markStart(c);
			}
			argWalk.setRevFilter(RevFilter.MERGE_BASE);
			int max = all ? Integer.MAX_VALUE : 1;
			while (max-- > 0) {
				final RevCommit b = argWalk.next();
				if (b == null) {
					break;
				}
				outw.println(b.getId().name());
			}
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
