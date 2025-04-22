package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public class IndexDiffFilterTest extends RepositoryTestCase {
	private RevCommit commit;

	public void setUp() throws Exception {
		super.setUp();

		Git git = new Git(db);
		writeTrashFile("folder/file"
		git.add().addFilepattern("folder/file").call();
		commit = git.commit().setMessage("commit").call();

		deleteTrashFile("folder/file");
		deleteTrashFile("folder");
		writeTrashFile("folder"
	}

	public void testRecursiveTreeWalk() throws Exception {
		TreeWalk treeWalk = new TreeWalk(db);
		treeWalk.setRecursive(true);
		treeWalk.addTree(commit.getTree());
		treeWalk.addTree(new DirCacheIterator(db.readDirCache()));
		treeWalk.addTree(new FileTreeIterator(db));
		treeWalk.setFilter(new IndexDiffFilter(1
		assertTrue(treeWalk.next());
		assertEquals("folder"
		assertTrue(treeWalk.next());
		assertEquals("folder/file"
		assertFalse(treeWalk.next());
	}

	public void testNonRecursiveTreeWalk() throws Exception {
		TreeWalk treeWalk = new TreeWalk(db);
		treeWalk.setRecursive(false);
		treeWalk.addTree(commit.getTree());
		treeWalk.addTree(new DirCacheIterator(db.readDirCache()));
		treeWalk.addTree(new FileTreeIterator(db));
		treeWalk.setFilter(new IndexDiffFilter(1
		assertTrue(treeWalk.next());
		assertEquals("folder"
		assertTrue(treeWalk.next());
		assertEquals("folder"
		assertTrue(treeWalk.isSubtree());
		treeWalk.enterSubtree();
		assertTrue(treeWalk.next());
		assertEquals("folder/file"
		assertFalse(treeWalk.next());
	}
}
