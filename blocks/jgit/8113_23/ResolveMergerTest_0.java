package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoMergeBaseException;
import org.eclipse.jgit.errors.NoMergeBaseException.MergeBaseFailureReason;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
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

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyWorktree(worktreeState
		modifyIndex(indexState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			boolean expectSuccess = true;
			if (!(indexState == IndexState.Bare
					|| indexState == IndexState.Missing
					|| indexState == IndexState.SameAsHead || indexState == IndexState.SameAsOther))
				expectSuccess = false;

			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			assertEquals("m1"
					contentAsString(db
			assertEquals("s1"
					contentAsString(db
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
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

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			boolean expectSuccess = true;
			if (!(indexState == IndexState.Bare
					|| indexState == IndexState.Missing || indexState == IndexState.SameAsHead))
				expectSuccess = false;
			else if (worktreeState == WorktreeState.DifferentFromHeadAndOther
					|| worktreeState == WorktreeState.SameAsOther)
				expectSuccess = false;
			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (!expectSuccess)
				return;
			assertEquals(
					"1-master\n2\n3-res(master)\n4\n5\n6\n7-res(side)\n8\n9-side"
					contentAsString(db
			if (indexState != IndexState.Bare)
				assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				assertEquals(
						"1-master\n2\n3-res(master)\n4\n5\n6\n7-res(side)\n8\n9-side\n"
						read("f"));
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
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

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			assertFalse(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (indexState == IndexState.SameAsHead
					&& worktreeState == WorktreeState.SameAsHead) {
				assertEquals(
						"[f
								+ "[f
								+ "[f
						indexState(RepositoryTestCase.CONTENT));
				assertEquals(
						"1-master\n2\n3\n4\n5\n6\n<<<<<<< OURS\n7-conflict\n=======\n7-res(side)\n>>>>>>> THEIRS\n8\n9-side\n"
						read("f"));
			}
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
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

		Git git = Git.wrap(db);
		git.checkout().setName("master").call();
		modifyWorktree(worktreeState
		modifyIndex(indexState

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
				worktreeState == WorktreeState.Bare);
		if (worktreeState != WorktreeState.Bare)
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
		try {
			boolean expectSuccess = true;
			if (!(indexState == IndexState.Bare
					|| indexState == IndexState.Missing || indexState == IndexState.SameAsHead))
				expectSuccess = false;
			else if (worktreeState == WorktreeState.DifferentFromHeadAndOther
					|| worktreeState == WorktreeState.SameAsOther)
				expectSuccess = false;

			assertEquals(Boolean.valueOf(expectSuccess)
					Boolean.valueOf(merger.merge(new RevCommit[] { m2
			assertEquals(MergeStrategy.RECURSIVE
			if (!expectSuccess)
				return;
			assertEquals(
					"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side"
					contentAsString(db
			if (indexState != IndexState.Bare)
				assertEquals(
						"[f
						indexState(RepositoryTestCase.CONTENT));
			if (worktreeState != WorktreeState.Bare
					&& worktreeState != WorktreeState.Missing)
				assertEquals(
						"1-master\n2\n3-res(master)\n4\n5-other\n6\n7-res(side)\n8\n9-side\n"
						read("f"));
		} catch (NoMergeBaseException e) {
			assertEquals(MergeStrategy.RESOLVE
			assertEquals(e.getReason()
					MergeBaseFailureReason.MULTIPLE_MERGE_BASES_NOT_SUPPORTED);
		}
	}

	void modifyIndex(IndexState indexState
			throws Exception {
		RevBlob blob;
		switch (indexState) {
		case Missing:
			setIndex(null
			break;
		case SameAsHead:
			setIndex(contentId(Constants.HEAD
			break;
		case SameAsOther:
			setIndex(contentId(other
			break;
		case SameAsWorkTree:
			blob = db_t.blob(read(path));
			setIndex(blob
			break;
		case DifferentFromHeadAndOtherAndWorktree:
			blob = db_t.blob(Integer.toString(counter++));
			setIndex(blob
			break;
		case Bare:
			File file = new File(db.getDirectory()
			if (!file.exists())
				return;
			db.close();
			file.delete();
			db = new FileRepository(db.getDirectory());
			db_t = new TestRepository<FileRepository>(db);
			break;
		}
	}

	private void setIndex(final ObjectId id
			throws MissingObjectException
		DirCache lockedDircache;
		DirCacheEditor dcedit;

		lockedDircache = db.lockDirCache();
		dcedit = lockedDircache.editor();
		try {
			if (id != null) {
				final ObjectLoader contLoader = db.newObjectReader().open(id);
				dcedit.add(new DirCacheEditor.PathEdit(path) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(FileMode.REGULAR_FILE);
						ent.setLength(contLoader.getSize());
						ent.setObjectId(id);
					}
				});
			} else
				dcedit.add(new DirCacheEditor.DeletePath(path));
		} finally {
			dcedit.commit();
		}
	}

	private ObjectId contentId(String revName
		RevCommit headCommit = db_t.getRevWalk().parseCommit(
				db.resolve(revName));
		db_t.parseBody(headCommit);
		return db_t.get(headCommit.getTree()
	}

	void modifyWorktree(WorktreeState worktreeState
			throws Exception {
		FileOutputStream fos = null;
		ObjectId bloblId;

		try {
			switch (worktreeState) {
			case Missing:
				new File(db.getWorkTree()
				break;
			case DifferentFromHeadAndOther:
				write(new File(db.getWorkTree()
						Integer.toString(counter++));
				break;
			case SameAsHead:
				bloblId = contentId(Constants.HEAD
				fos = new FileOutputStream(new File(db.getWorkTree()
				db.newObjectReader().open(bloblId).copyTo(fos);
				break;
			case SameAsOther:
				bloblId = contentId(other
				fos = new FileOutputStream(new File(db.getWorkTree()
				db.newObjectReader().open(bloblId).copyTo(fos);
				break;
			case Bare:
				if (db.isBare())
					return;
				File workTreeFile = db.getWorkTree();
				db.getConfig().setBoolean("core"
				db.getDirectory().renameTo(new File(workTreeFile
				db = new FileRepository(new File(workTreeFile
				db_t = new TestRepository<FileRepository>(db);
			}
		} finally {
			if (fos != null)
				fos.close();
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
		if (worktreeState != WorktreeState.DifferentFromHeadAndOther
				&& indexState == IndexState.SameAsWorkTree)
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
