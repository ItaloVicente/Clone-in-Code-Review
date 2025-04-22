
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.junit.Test;

public class RevWalkResetTest extends RevWalkTestCase {

	@Test
	public void testRevFilterReceivesParsedCommits() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		final AtomicBoolean filterRan = new AtomicBoolean();
		RevFilter testFilter = new RevFilter() {

			@Override
			public boolean include(RevWalk walker
					throws StopWalkException
					IncorrectObjectTypeException
				assertNotNull("commit is parsed"
				filterRan.set(true);
				return true;
			}

			@Override
			public RevFilter clone() {
				return this;
			}

			@Override
			public boolean requiresCommitBody() {
				return true;
			}
		};

		filterRan.set(false);
		rw.setRevFilter(testFilter);
		markStart(c);
		rw.markUninteresting(b);
		for (RevCommit cmit = rw.next(); cmit != null; cmit = rw.next()) {
		}
		assertTrue("filter ran"

		filterRan.set(false);
		rw.reset();
		markStart(c);
		for (RevCommit cmit = rw.next(); cmit != null; cmit = rw.next()) {
			cmit.disposeBody();
		}
		assertTrue("filter ran"

		filterRan.set(false);
		rw.reset();
		markStart(c);
		for (RevCommit cmit = rw.next(); cmit != null; cmit = rw.next()) {
		}
		assertTrue("filter ran"

	}
}
