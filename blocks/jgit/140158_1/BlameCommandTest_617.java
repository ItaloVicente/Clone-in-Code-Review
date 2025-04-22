package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.beans.Statement;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	@Override
	@After
	public void tearDown() {
		ArchiveCommand.unregisterFormat(format.SUFFIXES.get(0));
	}

	@Test
	public void archiveHeadAllFiles() throws IOException
		try (Git git = new Git(db)) {
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
	}

	@Test
	public void archiveHeadSpecificPath() throws IOException
		try (Git git = new Git(db)) {
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
	}

	@Test
	public void archiveByIdSpecificFile() throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("file_1.txt"
			git.add().addFilepattern("file_1.txt").call();
			RevCommit first = git.commit().setMessage("create file").call();

			writeTrashFile("file_1.txt"
			String expectedFilePath = "some_directory/file_2.txt";
			writeTrashFile(expectedFilePath
			git.add().addFilepattern(".").call();
			git.commit().setMessage("updated file").call();

			Map<String
			Integer opt = Integer.valueOf(42);
			options.put("foo"
			MockOutputStream out = new MockOutputStream();
			git.archive().setOutputStream(out)
					.setFormat(format.SUFFIXES.get(0))
					.setFormatOptions(options)
					.setTree(first)
					.setPaths("file_1.txt").call();

			assertEquals(opt.intValue()
			assertEquals(UNEXPECTED_ARCHIVE_SIZE
			assertEquals(UNEXPECTED_FILE_CONTENTS
		}
	}

	@Test
	public void archiveByDirectoryPath() throws GitAPIException
		try (Git git = new Git(db)) {
			writeTrashFile("file_0.txt"
			git.add().addFilepattern("file_0.txt").call();
			git.commit().setMessage("commit_1").call();

			writeTrashFile("file_0.txt"
			String expectedFilePath1 = "some_directory/file_1.txt";
			writeTrashFile(expectedFilePath1
			String expectedFilePath2 = "some_directory/file_2.txt";
			writeTrashFile(expectedFilePath2
		        String expectedFilePath3 = "some_directory/nested_directory/file_3.txt";
			writeTrashFile(expectedFilePath3
			git.add().addFilepattern(".").call();
			git.commit().setMessage("commit_2").call();
			git.archive().setOutputStream(new MockOutputStream())
					.setFormat(format.SUFFIXES.get(0))
					.setTree(git.getRepository().resolve("HEAD"))
					.setPaths("some_directory/").call();

			assertEquals(UNEXPECTED_ARCHIVE_SIZE
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertEquals(UNEXPECTED_FILE_CONTENTS
			assertNull(UNEXPECTED_TREE_CONTENTS
			assertNull(UNEXPECTED_TREE_CONTENTS
		}
	}

	private static class MockFormat
			implements ArchiveCommand.Format<MockOutputStream> {

		private Map<String

		private int size() {
			return entries.size();
		}

		private String getByPath(String path) {
			return entries.get(path);
		}

		private final List<String> SUFFIXES = Collections
				.unmodifiableList(Arrays.asList(".mck"));

		@Override
		public MockOutputStream createArchiveOutputStream(OutputStream s)
				throws IOException {
			return createArchiveOutputStream(s
					Collections.<String
		}

		@Override
		public MockOutputStream createArchiveOutputStream(OutputStream s
				Map<String
			for (Map.Entry<String
				try {
					String methodName = "set"
							+ StringUtils.capitalize(p.getKey());
					new Statement(s
							.execute();
				} catch (Exception e) {
					throw new IOException("cannot set option: " + p.getKey()
				}
			}
			return new MockOutputStream();
		}

		@Override
		public void putEntry(MockOutputStream out
			String content = mode != FileMode.TREE
					? new String(loader.getBytes()
					: null;
			entries.put(path
		}

		@Override
		public Iterable<String> suffixes() {
			return SUFFIXES;
		}
	}

	public static class MockOutputStream extends OutputStream {

		private int foo;

		public void setFoo(int foo) {
			this.foo = foo;
		}

		public int getFoo() {
			return foo;
		}

		@Override
		public void write(int b) throws IOException {
		}
	}
}
