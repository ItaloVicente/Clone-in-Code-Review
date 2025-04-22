package org.eclipse.egit.core;

import java.io.IOException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class RevUtils {

	private RevUtils() {
	}

	public static RevCommit getCommonAncestor(Repository repo,
			AnyObjectId commit1, AnyObjectId commit2) throws IOException {
		RevWalk rw = new RevWalk(repo);
		rw.setRevFilter(RevFilter.MERGE_BASE);

		RevCommit srcRev = rw.parseCommit(commit1);
		RevCommit dstRev = rw.parseCommit(commit2);

		rw.markStart(dstRev);
		rw.markStart(srcRev);

		RevCommit result;
		result = rw.next();

		return result != null ? result : null;
	}

}
