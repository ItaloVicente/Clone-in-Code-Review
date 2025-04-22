package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ArchiveCommandTest extends RepositoryTestCase {

	private static final String UNEXPECTED_ARCHIVE_SIZE  = "Unexpected archive size";
	private static final String UNEXPECTED_FILE_CONTENTS = "Unexpected file contents";
	private static final String UNEXPECTED_TREE_CONTENTS = "Unexpected tree contents";

	private MockFormat format = null;

	@Before
	public void setup() {
		format = new MockFormat();
		ArchiveCommand.registerFormat(format.SUFFIXES.get(0)
	}

	@After
	public void tearDown() {
		ArchiveCommand.unregisterFormat(format.SUFFIXES.get(0));
	}

	@Test
	public void archiveHeadAllFiles() throws IOException
		Git git = new Git(db);
		writeTrashFile("file_1.txt"
		git.add().addFilepattern("file_1.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt"
		writeTrashFile("file_2.txt"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();

		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0))
				.setTree(git.getRepository().resolve("HEAD")).call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE
		assertEquals(UNEXPECTED_FILE_CONTENTS
		assertEquals(UNEXPECTED_FILE_CONTENTS
	}

	@Test
	public void archiveHeadSpecificPath() throws IOException
		Git git = new Git(db);
		writeTrashFile("file_1.txt"
		git.add().addFilepattern("file_1.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt"
		String expectedFilePath = "some_directory/file_2.txt";
		writeTrashFile(expectedFilePath
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();

		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0))
				.setTree(git.getRepository().resolve("HEAD"))
				.setPaths(expectedFilePath).call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE
		assertEquals(UNEXPECTED_FILE_CONTENTS
		assertNull(UNEXPECTED_TREE_CONTENTS
	}

	@Test
	public void archiveByIdSpecificFile() throws IOException
		Git git = new Git(db);
		writeTrashFile("file_1.txt"
		git.add().addFilepattern("file_1.txt").call();
		RevCommit first = git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt"
		String expectedFilePath = "some_directory/file_2.txt";
		writeTrashFile(expectedFilePath
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();

		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0)).setTree(first)
				.setPaths("file_1.txt").call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE
		assertEquals(UNEXPECTED_FILE_CONTENTS
	}

	@Test
	public void archiveByDirectoryPath() throws GitAPIException
		Git git = new Git(db);
		writeTrashFile("file_0.txt"
		git.add().addFilepattern("file_0.txt").call();
		git.commit().setMessage("commit_1").call();

		writeTrashFile("file_0.txt"
		String expectedFilePath1 = "some_directory/file_1.txt";
		writeTrashFile(expectedFilePath1
		String expectedFilePath2 = "some_directory/file_2.txt";
		writeTrashFile(expectedFilePath2
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit_2").call();
		git.archive().setOutputStream(new MockOutputStream())
				.setFormat(format.SUFFIXES.get(0))
				.setTree(git.getRepository().resolve("HEAD"))
				.setPaths("some_directory/").call();

		assertEquals(UNEXPECTED_ARCHIVE_SIZE
		assertEquals(UNEXPECTED_FILE_CONTENTS
		assertEquals(UNEXPECTED_FILE_CONTENTS
        assertNull(UNEXPECTED_TREE_CONTENTS

	}

	private class MockFormat implements ArchiveCommand.Format<MockOutputStream> {

		private Map<String

		private int size() {
			return entries.size();
		}

		private String getByPath(String path) {
			return entries.get(path);
		}

		private final List<String> SUFFIXES = Collections
				.unmodifiableList(Arrays.asList(".mck"));

		public MockOutputStream createArchiveOutputStream(OutputStream s)
				throws IOException {
			return new MockOutputStream();
		}

		public void putEntry(MockOutputStream out
			String content = mode != FileMode.TREE ? new String(loader.getBytes()) : null;
			entries.put(path
		}

		public Iterable<String> suffixes() {
			return SUFFIXES;
		}
	}

	protected class MockOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
		}
	}
}
