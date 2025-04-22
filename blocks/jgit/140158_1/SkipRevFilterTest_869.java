
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

	@Test
	public void withCommitLoadedByDifferentRevWalk() throws Exception {
		RevCommit a = commit();
		Ref branchA = branch("a"

		try (RevWalk walk = new RevWalk(db)) {
			RevCommit parsedCommit = walk.parseCommit(a.getId());
			assertContains(parsedCommit
		}
	}

	private Ref branch(String name
		return Git.wrap(db).branchCreate().setName(name)
				.setStartPoint(dst.name()).call();
	}

	private void assertContains(RevCommit commit
		Collection<Ref> allRefs = db.getRefDatabase().getRefs();
		Collection<Ref> sortedRefs = RefComparator.sort(allRefs);
		List<Ref> actual = RevWalkUtils.findBranchesReachableFrom(commit
				rw
		assertEquals(refsThatShouldContainCommit
	}

}
