package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class RecursiveMergerTest extends RepositoryTestCase {

	@Test
	public void crissCrossMerge() throws IOException
		Git git = Git.wrap(db);

		RevCommit m0 = commitNewFile(git
		RevCommit m1 = commitNewFile(git

		git.checkout().setCreateBranch(true).setStartPoint(m0).setName("side")
				.call();
		RevCommit s1 = commitNewFile(git

		MergeResult res_s2 = git.merge().include(m1)
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(MergeStatus.MERGED

		git.checkout().setName("master").call();

		MergeResult res_m2 = git.merge().include(s1)
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(MergeStatus.MERGED

		MergeResult res_m3 = git.merge().include(res_s2.getNewHead())
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(MergeStatus.MERGED
	}

	@Test
	public void threeCommonPredecesors() throws IOException
		Git git = Git.wrap(db);

		RevCommit m0 = commitNewFile(git
		RevCommit m1 = commitNewFile(git

		git.checkout().setCreateBranch(true).setStartPoint(m0).setName("side")
				.call();
		RevCommit s1 = commitNewFile(git

		git.checkout().setCreateBranch(true).setStartPoint(m0)
				.setName("another").call();
		RevCommit a1 = commitNewFile(git

		git.checkout().setName("master").call();
		MergeResult res_m2 = mergeMultiple(git
		assertEquals(MergeStatus.MERGED

		git.checkout().setName("side").call();
		MergeResult res_s2 = mergeMultiple(git
		assertEquals(MergeStatus.MERGED

		git.checkout().setName("master").call();

		MergeResult res_s3 = git.merge().include(res_s2.getNewHead())
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(MergeStatus.MERGED
	}

	private RevCommit commitNewFile(Git git
			GitAPIException
			NoMessageException
			ConcurrentRefUpdateException
		writeTrashFile(name
		git.add().addFilepattern(name).call();
		RevCommit m0 = git.commit().setMessage(name).call();
		return m0;
	}

	private MergeResult mergeMultiple(Git git
			throws GitAPIException {
		MergeResult result = null;
		for (RevCommit c : commits) {
			result = git.merge().include(c)
					.setStrategy(MergeStrategy.RECURSIVE).call();
		}
		return result;
	}

}
