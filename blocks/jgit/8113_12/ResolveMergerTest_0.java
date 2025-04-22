package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.Assert;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Test;

public class RecursiveMergerTest extends RepositoryTestCase {

	@Test
	public void lowLevelBareRepo_EasyCrissCrossMerge() throws Exception {
		FileRepository r = createBareRepository();
		TestRepository tr = new TestRepository<FileRepository>(r);
		BranchBuilder master = tr.branch("master");
		RevCommit m0 = master.commit().add("m"
		RevCommit m1 = master.commit().add("m"
		tr.getRevWalk().parseCommit(m1);

		BranchBuilder side = tr.branch("side");
		RevCommit s1 = side.commit().parent(m0).add("s"
				.create();
		RevCommit s2 = side.commit().parent(m1).add("m"
				.message("s2(merge)").create();

		RevCommit m2 = master.commit().parent(s1).add("s"
				.message("m2(merge)").create();

		RecursiveMerger merger = new RecursiveMerger(r
		Assert.assertEquals(true
		assertEquals("m1"
		assertEquals("s1"
	}

	@Test
	public void lowLevelBareRepo_CrissCrossMerge() throws Exception {
		FileRepository r = createBareRepository();
		TestRepository tr = new TestRepository<FileRepository>(r);
		BranchBuilder master = tr.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0").create();
		RevCommit m1 = master.commit()
				.add("f"
				.create();
		tr.getRevWalk().parseCommit(m1);

		BranchBuilder side = tr.branch("side");
		RevCommit s1 = side.commit().parent(m0)
				.add("f"
				.create();
		RevCommit s2 = side.commit().parent(m1)
				.add("f"
				.message("s2(merge)").create();
		RevCommit m2 = master
				.commit()
				.parent(s1)
				.add("f"
				.message("m2(merge)").create();

		RecursiveMerger merger = new RecursiveMerger(r
		Assert.assertEquals(true
		assertEquals(
				"1-master\n2\n3-res(master)\n4\n5\n6\n7-res(side)\n8\n9-side\n"
				contentAsString(r
	}

	@Test
	public void lowLevelBareRepo_ThreeCommonPredecessors() throws Exception {
		FileRepository r = createBareRepository();
		TestRepository tr = new TestRepository<FileRepository>(r);
		BranchBuilder master = tr.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0").create();
		RevCommit m1 = master.commit()
				.add("f"
				.create();
		BranchBuilder side = tr.branch("side");
		RevCommit s1 = side.commit().parent(m0)
				.add("f"
				.create();
		BranchBuilder other = tr.branch("other");
		RevCommit o1 = other.commit().parent(m0)
				.add("f"
				.create();

		RevCommit m2 = master
				.commit()
				.parent(s1)
				.parent(o1)
				.add("f"
						"1-master\n2\n3-res(master)\n4\n5-other\n6\n7\n8\n9-side\n")
				.message("m2(merge)").create();

		RevCommit s2 = side
				.commit()
				.parent(m1)
				.parent(o1)
				.add("f"
						"1-master\n2\n3\n4\n5-other\n6\n7-res(side)\n8\n9-side\n")
				.message("s2(merge)").create();

		RecursiveMerger merger = new RecursiveMerger(r
		Assert.assertEquals(true
		assertEquals(
				"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side\n"
				contentAsString(r
	}

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

		git.checkout().setCreateBranch(true).setStartPoint(m0).setName("other")
				.call();
		RevCommit o1 = commitNewFile(git

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

	@Test
	public void threeCommonPredecesorsWithContentMerge() throws IOException
			GitAPIException {
		Git git = Git.wrap(db);

		RevCommit m0 = commitNewFile(git
		RevCommit m1 = commitNewFile(git
				"1-master\n2\n3\n4\n5\n6\n7\n8\n9\n");

		git.checkout().setCreateBranch(true).setStartPoint(m0).setName("side")
				.call();
		RevCommit s1 = commitNewFile(git
				"1\n2\n3\n4\n5\n6\n7\n8\n9-side\n");

		git.checkout().setCreateBranch(true).setStartPoint(m0).setName("other")
				.call();
		RevCommit o1 = commitNewFile(git
				"1\n2\n3\n4\n5-other\n6\n7\n8\n9\n");

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
		assertEquals(
				"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side\n"
				read("f"));
	}

	private String contentAsString(Repository r
			throws MissingObjectException
		TreeWalk tw = new TreeWalk(r);
		tw.addTree(treeId);
		tw.setFilter(PathFilter.create(path));
		tw.setRecursive(true);
		if (!tw.next())
			return null;
		AnyObjectId blobId = tw.getObjectId(0);

		StringBuilder result = new StringBuilder();
		BufferedReader br = null;
		ObjectReader or = r.newObjectReader();
		try {
			br = new BufferedReader(new InputStreamReader(or.open(blobId)
					.openStream()));
			String line;
			boolean first = true;
			while ((line = br.readLine()) != null) {
				if (!first)
					result.append('\n');
				result.append(line);
				first = false;
			}
			return result.toString();
		} finally {
			if (br != null)
				br.close();
		}
	}

	private RevCommit commitNewFile(Git git
			GitAPIException
			NoMessageException
			ConcurrentRefUpdateException
		return commitNewFile(git
	}

	private RevCommit commitNewFile(Git git
			throws IOException
			NoHeadException
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
