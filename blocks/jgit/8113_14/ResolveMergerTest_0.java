package org.eclipse.jgit.merge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class RecursiveMergerTest extends RepositoryTestCase {
	static int counter = 0;

	@DataPoints
	public static MergeStrategy[] strategiesUnderTest = new MergeStrategy[] {
			MergeStrategy.RECURSIVE

	public enum IndexState {
		Bare
	}

	@DataPoints
	public static IndexState[] indexStates = IndexState.values();

	public enum WorktreeState {
		Bare
	}

	@DataPoints
	public static WorktreeState[] worktreeStates = WorktreeState.values();

	private TestRepository<FileRepository> db_t;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		db_t = new TestRepository<FileRepository>(db);
	}

	@Theory
	public void crissCrossMerge(MergeStrategy strategy
			WorktreeState worktreeState) throws Exception {
		if (!validateStates(indexState
			return;
		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("m"
		RevCommit m1 = master.commit().add("m"
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0).add("s"
				.create();
		RevCommit s2 = side.commit().parent(m1).add("m"
				.message("s2(merge)").create();
		RevCommit m2 = master.commit().parent(s1).add("s"
				.message("m2(merge)").create();

		modifyIndex(indexState
		modifyWorktree(worktreeState

		Merger merger = strategy.newMerger(db);
		try {
			Assert.assertTrue(merger.merge(new RevCommit[] { s2
			Assert.assertEquals(MergeStrategy.RECURSIVE
			Assert.assertEquals("m1"
					contentAsString(db
			Assert.assertEquals("s1"
					contentAsString(db
			if (indexState != IndexState.Bare)
				Assert.assertEquals(
						"[m
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare) {
				Assert.assertEquals("m1"
				Assert.assertEquals("s1"
			}
		} catch (IOException ex) {
			Assert.assertEquals(strategy
		}
	}

	@Theory
	public void crissCrossMerge_mergeable(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0").create();
		RevCommit m1 = master.commit()
				.add("f"
				.create();
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
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

		modifyIndex(indexState
		modifyWorktree(worktreeState

		Merger merger = strategy.newMerger(db);
		try {
			Assert.assertTrue(merger.merge(new RevCommit[] { s2
			Assert.assertEquals(MergeStrategy.RECURSIVE
			Assert.assertEquals(
					"1-master\n2\n3-res(master)\n4\n5\n6\n7-res(side)\n8\n9-side"
					contentAsString(db
			if (indexState != IndexState.Bare)
				Assert.assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				Assert.assertEquals(
						"1-master\n2\n3-res(master)\n4\n5\n6\n7-res(side)\n8\n9-side\n"
						read("f"));
		} catch (IOException ex) {
			Assert.assertEquals(MergeStrategy.RESOLVE
		}
	}

	@Theory
	public void crissCrossMerge_nonmergeable(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0").create();
		RevCommit m1 = master.commit()
				.add("f"
				.create();
		db_t.getRevWalk().parseCommit(m1);

		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0)
				.add("f"
				.create();
		RevCommit s2 = side.commit().parent(m1)
				.add("f"
				.message("s2(merge)").create();
		RevCommit m2 = master.commit().parent(s1)
				.add("f"
				.message("m2(merge)").create();

		modifyIndex(indexState
		modifyWorktree(worktreeState

		Merger merger = strategy.newMerger(db);
		try {
			Assert.assertFalse(merger.merge(new RevCommit[] { s2
			Assert.assertEquals(MergeStrategy.RECURSIVE
			if (indexState != IndexState.Bare)
				Assert.assertEquals(
						"[f
								+ "[f
								+ "[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				Assert.assertEquals(
						"1-master\n2\n3\n4\n5\n6\n<<<<<<< OURS\n7-res(side)\n=======\n7-conflict\n>>>>>>> THEIRS\n8\n9-side\n"
						read("f"));
		} catch (IOException ex) {
			Assert.assertEquals(MergeStrategy.RESOLVE
		}
	}

	@Theory
	public void crissCrossMerge_ThreeCommonPredecessors(MergeStrategy strategy
			IndexState indexState
			throws Exception {
		if (!validateStates(indexState
			return;

		BranchBuilder master = db_t.branch("master");
		RevCommit m0 = master.commit().add("f"
				.message("m0").create();
		RevCommit m1 = master.commit()
				.add("f"
				.create();
		BranchBuilder side = db_t.branch("side");
		RevCommit s1 = side.commit().parent(m0)
				.add("f"
				.create();
		BranchBuilder other = db_t.branch("other");
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

		modifyIndex(indexState
		modifyWorktree(worktreeState

		Merger merger = strategy.newMerger(db);
		try {
			Assert.assertTrue(merger.merge(new RevCommit[] { s2
			Assert.assertEquals(MergeStrategy.RECURSIVE
			Assert.assertEquals(
					"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side"
					contentAsString(db
			if (indexState != IndexState.Bare)
				Assert.assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				Assert.assertEquals(
						"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side\n"
						read("f"));
		} catch (IOException ex) {
			Assert.assertEquals(MergeStrategy.RESOLVE
		}
	}

	void modifyIndex(IndexState indexState
		switch (indexState) {
		case Missing:
			db_t.commit().rm(path);
			break;
		case DifferentFromHead:
			db_t.commit().add(path
			break;
		case SameAsHead:
			RevCommit headCommit = db_t.getRevWalk().parseCommit(
					db.resolve("HEAD"));
			db_t.parseBody(headCommit);
			db_t.commit().add(path
					(RevBlob) (db_t.get(headCommit.getTree()
			break;
		case Bare:
			new File(db.getDirectory()
			break;
		}
	}

	void modifyWorktree(WorktreeState worktreeState
			String branchName) throws Exception {
		new Git(db).reset().setMode(ResetType.HARD).setRef(branchName).call();

		switch (worktreeState) {
		case Missing:
			new File(db.getWorkTree()
			break;
		case DifferentFromIndexAndHead:
			write(new File(db.getWorkTree()
			break;
		case SameAsHeadDifferentFromIndex:
			RevCommit headCommit = db_t.getRevWalk().parseCommit(
					db.resolve("HEAD"));
			db_t.parseBody(headCommit);
			ObjectId bloblId = db_t.get(headCommit.getTree()
			db.newObjectReader()
					.open(bloblId)
					.copyTo(new FileOutputStream(new File(db.getWorkTree()
							path)));
			break;
		case SameAsIndex:
			DirCache dc = DirCache.read(db);
			bloblId = dc.getEntry(path).getObjectId();
			db.newObjectReader()
					.open(bloblId)
					.copyTo(new FileOutputStream(new File(db_t.getRepository()
							.getWorkTree()
			break;
		case Bare:
			db.getConfig().setBoolean("core"
			db = new FileRepository(db.getDirectory());
		}
	}

	private boolean validateStates(IndexState indexState
			WorktreeState worktreeState) {
		if (worktreeState == WorktreeState.Bare
				&& indexState != IndexState.Bare)
			return false;
		if (worktreeState != WorktreeState.Bare
				&& indexState == IndexState.Bare)
			return false;
		if (worktreeState == WorktreeState.SameAsIndex
				&& indexState == IndexState.Missing)
			return false;
		if (worktreeState == WorktreeState.SameAsHeadDifferentFromIndex
				&& indexState == IndexState.SameAsHead)
			return false;
		return true;
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
}
