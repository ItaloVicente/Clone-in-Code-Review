package org.eclipse.jgit.diff;

import static org.eclipse.jgit.diff.DiffEntry.DEV_NULL;
import static org.eclipse.jgit.util.FileUtils.delete;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class DiffEntryTest extends RepositoryTestCase {

	@Test
	public void shouldListAddedFileInInitialCommit() throws Exception {
		writeTrashFile("a.txt"
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			git.add().addFilepattern("a.txt").call();
			RevCommit c = git.commit().setMessage("initial commit").call();

			walk.addTree(new EmptyTreeIterator());
			walk.addTree(c.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
			assertThat(entry.getOldPath()
		}
	}

	@Test
	public void shouldListAddedFileBetweenTwoCommits() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			RevCommit c2 = git.commit().setMessage("second commit").call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
			assertThat(entry.getOldPath()
		}
	}

	@Test
	public void shouldListModificationBetweenTwoCommits() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File file = writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			write(file
			RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
	}

	@Test
	public void shouldListDeletionBetweenTwoCommits() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File file = writeTrashFile("a.txt"
			git.add().addFilepattern("a.txt").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			delete(file);
			RevCommit c2 = git.commit().setAll(true).setMessage("delete a.txt")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getOldPath()
			assertThat(entry.getNewPath()
			assertThat(entry.getChangeType()
		}
	}

	@Test
	public void shouldListModificationInDirWithoutModifiedTrees()
			throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File tree = new File(new File(db.getWorkTree()
			FileUtils.mkdirs(tree);
			File file = new File(tree
			FileUtils.createNewFile(file);
			write(file
			git.add().addFilepattern("a").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			write(file
			RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			walk.setRecursive(true);
			List<DiffEntry> result = DiffEntry.scan(walk);

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
	}

	@Test
	public void shouldListModificationInDirWithModifiedTrees() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			File tree = new File(new File(db.getWorkTree()
			FileUtils.mkdirs(tree);
			File file = new File(tree
			FileUtils.createNewFile(file);
			write(file
			git.add().addFilepattern("a").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			write(file
			RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
					.call();

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk

			assertThat(result
			assertThat(Integer.valueOf(result.size())

			DiffEntry entry = result.get(0);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()

			entry = result.get(1);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()

			entry = result.get(2);
			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
	}

	@Test
	public void shouldListChangesInWorkingTree() throws Exception {
		writeTrashFile("a.txt"
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			git.add().addFilepattern("a.txt").call();
			RevCommit c = git.commit().setMessage("initial commit").call();
			writeTrashFile("b.txt"

			walk.addTree(c.getTree());
			walk.addTree(new FileTreeIterator(db));
			List<DiffEntry> result = DiffEntry.scan(walk

			assertThat(Integer.valueOf(result.size())
			DiffEntry entry = result.get(0);

			assertThat(entry.getChangeType()
			assertThat(entry.getNewPath()
		}
	}

	@Test
	public void shouldMarkEntriesWhenGivenMarkTreeFilter() throws Exception {
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			FileUtils.mkdir(new File(db.getWorkTree()
			writeTrashFile("a.txt"
			writeTrashFile("b/1.txt"
			writeTrashFile("b/2.txt"
			writeTrashFile("c.txt"
			git.add().addFilepattern("a.txt").addFilepattern("b")
					.addFilepattern("c.txt").call();
			RevCommit c2 = git.commit().setMessage("second commit").call();
			TreeFilter filterA = PathFilterGroup.createFromStrings("a.txt");
			TreeFilter filterB = PathFilterGroup.createFromStrings("b");
			TreeFilter filterB2 = PathFilterGroup.createFromStrings("b/2.txt");

			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> result = DiffEntry.scan(walk
					filterA

			assertThat(result
			assertEquals(5

			DiffEntry entryA = result.get(0);
			DiffEntry entryB = result.get(1);
			DiffEntry entryB1 = result.get(2);
			DiffEntry entryB2 = result.get(3);
			DiffEntry entryC = result.get(4);

			assertThat(entryA.getNewPath()
			assertTrue(entryA.isMarked(0));
			assertFalse(entryA.isMarked(1));
			assertFalse(entryA.isMarked(2));
			assertEquals(1

			assertThat(entryB.getNewPath()
			assertFalse(entryB.isMarked(0));
			assertTrue(entryB.isMarked(1));
			assertTrue(entryB.isMarked(2));
			assertEquals(6

			assertThat(entryB1.getNewPath()
			assertFalse(entryB1.isMarked(0));
			assertTrue(entryB1.isMarked(1));
			assertFalse(entryB1.isMarked(2));
			assertEquals(2

			assertThat(entryB2.getNewPath()
			assertFalse(entryB2.isMarked(0));
			assertTrue(entryB2.isMarked(1));
			assertTrue(entryB2.isMarked(2));
			assertEquals(6

			assertThat(entryC.getNewPath()
			assertFalse(entryC.isMarked(0));
			assertFalse(entryC.isMarked(1));
			assertFalse(entryC.isMarked(2));
			assertEquals(0
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIAEWhenTreeWalkHasLessThanTwoTrees()
			throws Exception {

		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new EmptyTreeIterator());
			DiffEntry.scan(walk);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIAEWhenTreeWalkHasMoreThanTwoTrees()
			throws Exception {

		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(new EmptyTreeIterator());
			DiffEntry.scan(walk);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIAEWhenScanShouldIncludeTreesAndWalkIsRecursive()
			throws Exception {

		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(new EmptyTreeIterator());
			walk.setRecursive(true);
			DiffEntry.scan(walk
		}
	}

	@Test
	public void shouldReportFileModeChange() throws Exception {
		writeTrashFile("a.txt"
		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			git.add().addFilepattern("a.txt").call();
			RevCommit c1 = git.commit().setMessage("initial commit").call();
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			walk.addTree(c1.getTree());
			walk.setRecursive(true);
			assertTrue(walk.next());

			editor.add(new PathEdit("a.txt") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.EXECUTABLE_FILE);
					ent.setObjectId(walk.getObjectId(0));
				}
			});
			assertTrue(editor.commit());
			RevCommit c2 = git.commit().setMessage("second commit").call();
			walk.reset();
			walk.addTree(c1.getTree());
			walk.addTree(c2.getTree());
			List<DiffEntry> diffs = DiffEntry.scan(walk
			assertEquals(1
			DiffEntry diff = diffs.get(0);
			assertEquals(ChangeType.MODIFY
			assertEquals(diff.getOldId()
			assertEquals("a.txt"
			assertEquals(diff.getOldPath()
			assertEquals(FileMode.EXECUTABLE_FILE
			assertEquals(FileMode.REGULAR_FILE
		}
	}
}
