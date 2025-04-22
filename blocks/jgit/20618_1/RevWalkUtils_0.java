
package org.eclipse.jgit.revwalk;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.junit.Test;

public class RevWalkUtilsReachableTest extends RevWalkTestCase {

	@Test
	public void oneCommit() throws Exception {
		RevCommit a = commit();
		Ref branchA = branch("a"

		assertContains(a
	}

	@Test
	public void twoCommits() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		branch("a"
		Ref branchB = branch("b"

		assertContains(b
	}

	@Test
	public void multipleBranches() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		branch("a"
		Ref branchB = branch("b"
		Ref branchB2 = branch("b2"

		assertContains(b
	}

	@Test
	public void withMerge() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit();
		RevCommit c = commit(a
		Ref branchA = branch("a"
		Ref branchB = branch("b"
		Ref branchC = branch("c"

		assertContains(a
		assertContains(b
	}

	private Ref branch(final String name
		return Git.wrap(db).branchCreate().setName(name)
				.setStartPoint(dst.name()).call();
	}

	private void assertContains(RevCommit commit
		Collection<Ref> allRefs = db.getAllRefs().values();
		Collection<Ref> sortedRefs = RefComparator.sort(allRefs);
		RevCommit parsedCommit = rw.parseCommit(commit);
		List<Ref> actual = RevWalkUtils.findBranchesReachableFrom(parsedCommit
				rw
		assertEquals(refsThatShouldContainCommit
	}

}
