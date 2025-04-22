package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.junit.Test;

public class RevWalkResetTest extends RevWalkTestCase {

	@Test
	public void testResetWalkProducesParsedCommits() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		RevFilter testFilter = new RevFilter() {

			@Override
			public boolean include(RevWalk walker
					throws StopWalkException
					IncorrectObjectTypeException
				assertNotNull(cmit.getRawBuffer());
				return true;
			}

			@Override
			public RevFilter clone() {
				return this;
			}

		};

		rw.setRevFilter(testFilter);
		markStart(c);
		rw.markUninteresting(b);
		while (rw.next() != null) {
		}

		rw.reset();
		markStart(c);
		while (rw.next() != null) {
		}

	}
}
