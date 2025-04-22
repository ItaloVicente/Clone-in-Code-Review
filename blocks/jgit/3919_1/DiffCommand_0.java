package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class DiffCommandTest extends RepositoryTestCase {
	@Test
	public void testDiffModified() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		folder.mkdir();
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder

		List<DiffEntry> entries = git.diff().call();
		assertEquals(1
		assertEquals(ChangeType.MODIFY
				.getChangeType());
		assertEquals("folder/folder.txt"
				.getOldPath());
		assertEquals("folder/folder.txt"
				.getNewPath());
	}

	@Test
	public void testDiffCached() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		folder.mkdir();
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder
		git.add().addFilepattern(".").call();

		List<DiffEntry> entries = git.diff().setCached(true).call();
		assertEquals(1
		assertEquals(ChangeType.ADD
				.getChangeType());
		assertEquals("/dev/null"
				.getOldPath());
		assertEquals("folder/folder.txt"
				.getNewPath());
	}
}
