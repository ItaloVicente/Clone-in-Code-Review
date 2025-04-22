package org.eclipse.jgit.diff;

import static org.eclipse.jgit.diff.DiffEntry.DEV_NULL;
import static org.eclipse.jgit.util.FileUtils.delete;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class DiffEntryTest extends RepositoryTestCase {

	@Test
	public void shouldListAddedFileInInitialCommit() throws Exception {
		writeTrashFile("a.txt"
		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c = git.commit().setMessage("initial commit").call();

		TreeWalk walk = new TreeWalk(db);
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

	@Test
	public void shouldListAddedFileBetweenTwoCommits() throws Exception {
		Git git = new Git(db);
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		RevCommit c2 = git.commit().setMessage("second commit").call();

		TreeWalk walk = new TreeWalk(db);
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

	@Test
	public void shouldListModificationBetweenTwoCommits() throws Exception {
		Git git = new Git(db);
		File file = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		write(file
		RevCommit c2 = git.commit().setAll(true).setMessage("second commit")
				.call();

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> result = DiffEntry.scan(walk);

		assertThat(result
		assertThat(Integer.valueOf(result.size())

		DiffEntry entry = result.get(0);
		assertThat(entry.getChangeType()
		assertThat(entry.getNewPath()
	}

	@Test
	public void shouldListDeletationBetweenTwoCommits() throws Exception {
		Git git = new Git(db);
		File file = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		delete(file);
		RevCommit c2 = git.commit().setAll(true).setMessage("delete a.txt")
				.call();

		TreeWalk walk = new TreeWalk(db);
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

	@Test
	public void shouldListModificationInDirWithoutModifiedTrees()
			throws Exception {
		Git git = new Git(db);
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

		TreeWalk walk = new TreeWalk(db);
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

	@Test
	public void shouldListModificationInDirWithModifiedTrees() throws Exception {
		Git git = new Git(db);
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

		TreeWalk walk = new TreeWalk(db);
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

	@Test
	public void shouldListChangesInWorkingTree() throws Exception {
		writeTrashFile("a.txt"
		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c = git.commit().setMessage("initial commit").call();
		writeTrashFile("b.txt"

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c.getTree());
		walk.addTree(new FileTreeIterator(db));
		List<DiffEntry> result = DiffEntry.scan(walk

		assertThat(Integer.valueOf(result.size())
		DiffEntry entry = result.get(0);

		assertThat(entry.getChangeType()
		assertThat(entry.getNewPath()
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIAEWhenTreeWalkHaveLessThenTwoTrees()
			throws Exception {

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(new EmptyTreeIterator());
		DiffEntry.scan(walk);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIAEWhenTreeWalkHaveMoreThenTwooTrees()
			throws Exception {

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(new EmptyTreeIterator());
		walk.addTree(new EmptyTreeIterator());
		walk.addTree(new EmptyTreeIterator());
		DiffEntry.scan(walk);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrownIAEWhenScanShouldIncludeTreesAndWalkIsRecursive()
			throws Exception {

		TreeWalk walk = new TreeWalk(db);
		walk.addTree(new EmptyTreeIterator());
		walk.addTree(new EmptyTreeIterator());
		walk.setRecursive(true);
		DiffEntry.scan(walk
	}

}
