package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Test;

public class PatchIdDiffFormatterTest extends RepositoryTestCase {

	@Test
	public void testDiff() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		folder.mkdir();
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder

		PatchIdDiffFormatter df = new PatchIdDiffFormatter();
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
		FileTreeIterator newTree = new FileTreeIterator(db);
		df.format(oldTree
		df.flush();

		assertEquals("AnyObjectId[1ff64e0f9333e9b81967c3e8d7a81362b14d5441]"
				df.getCalulatedPatchId().toString());
	}

	@Test
	public void testSameDiff() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		folder.mkdir();
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder

		PatchIdDiffFormatter df = new PatchIdDiffFormatter();
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
		FileTreeIterator newTree = new FileTreeIterator(db);
		df.format(oldTree
		df.flush();

		assertEquals("AnyObjectId[08fca5ac531383eb1da8bf6b6f7cf44411281407]"
				df.getCalulatedPatchId().toString());

		write(new File(folder
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder

		df = new PatchIdDiffFormatter();
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		oldTree = new DirCacheIterator(db.readDirCache());
		newTree = new FileTreeIterator(db);
		df.format(oldTree
		df.flush();

		assertEquals("AnyObjectId[08fca5ac531383eb1da8bf6b6f7cf44411281407]"
				df.getCalulatedPatchId().toString());
	}

}
