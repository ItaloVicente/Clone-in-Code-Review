package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class AddCommandTest extends RepositoryTestCase {

	public void testAddNonExisitingSingleFile() throws IOException {
		Git git = new Git(db);

		try {
			git.add("a.txt").call();
			fail("Expected IllegalStateException");
		} catch (IllegalStateException e) {
		}

	}

	public void testAddExisitingSingleFile() throws IOException {
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);

		DirCache dc = git.add("a.txt").call();

		assertEquals(1
		assertEquals("a.txt"
		assertNotNull(dc.getEntry(0).getObjectId());
		assertEquals(file.lastModified()
		assertEquals(file.length()
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(0
	}

	public void testAddExisitingSingleFileInSubDir() throws IOException {
		new File(db.getWorkDir()
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);

		DirCache dc = git.add("sub/a.txt").call();

		assertEquals(1
		assertEquals("sub/a.txt"
		assertNotNull(dc.getEntry(0).getObjectId());
		assertEquals(file.lastModified()
		assertEquals(file.length()
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(0
	}

	public void testAddExisitingSingleFileTwice() throws IOException {
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add("a.txt").call();

		ObjectId id1 = dc.getEntry(0).getObjectId();

		writer = new PrintWriter(file);
		writer.print("other content");
		writer.close();

		dc = git.add("a.txt").call();

		assertEquals(1
		assertEquals("a.txt"
		assertNotSame(id1
		assertEquals(0
	}

	public void testAddExisitingSingleFileTwiceWithCommit() throws Exception {
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add("a.txt").call();

		ObjectId id1 = dc.getEntry(0).getObjectId();

		git.commit().setMessage("commit a.txt").call();

		writer = new PrintWriter(file);
		writer.print("other content");
		writer.close();

		dc = git.add("a.txt").call();

		assertEquals(1
		assertEquals("a.txt"
		assertNotSame(id1
		assertEquals(0
	}

	public void testAddRemovedFile() throws Exception {
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add("a.txt").call();

		ObjectId id1 = dc.getEntry(0).getObjectId();
		file.delete();

		dc = git.add("a.txt").call();

		assertEquals(1
		assertEquals("a.txt"
		assertEquals(id1
		assertEquals(0
	}

	public void testAddRemovedCommittedFile() throws Exception {
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add("a.txt").call();

		git.commit().setMessage("commit a.txt").call();

		ObjectId id1 = dc.getEntry(0).getObjectId();
		file.delete();

		dc = git.add("a.txt").call();

		assertEquals(1
		assertEquals("a.txt"
		assertEquals(id1
		assertEquals(0
	}

	public void testAddWithConflicts() throws Exception {

		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		File file2 = new File(db.getWorkDir()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();

		ObjectWriter ow = new ObjectWriter(db);
		DirCache dc = DirCache.lock(db);
		DirCacheBuilder builder = dc.builder();

		addEntryToBuilder("b.txt"
		addEntryToBuilder("a.txt"

		writer = new PrintWriter(file);
		writer.print("other content");
		writer.close();
		addEntryToBuilder("a.txt"

		writer = new PrintWriter(file);
		writer.print("our content");
		writer.close();
		ObjectId id1 = addEntryToBuilder("a.txt"
				.getObjectId();

		builder.commit();

		assertEquals(4


		Git git = new Git(db);
		dc = git.add("a.txt").call();

		assertEquals(2
		assertEquals("a.txt"
		assertEquals(id1
		assertEquals(0
		assertEquals(0
	}

	private DirCacheEntry addEntryToBuilder(String path
			ObjectWriter ow
			throws IOException {
		ObjectId id = ow.writeBlob(file);
		DirCacheEntry entry = new DirCacheEntry(path
		entry.setObjectId(id);
		entry.setFileMode(FileMode.REGULAR_FILE);
		entry.setLastModified(file.lastModified());
		entry.setLength((int) file.length());

		builder.add(entry);
		return entry;
	}

}
