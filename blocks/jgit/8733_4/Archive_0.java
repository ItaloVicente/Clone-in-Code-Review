package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.pgm.CLIGitCommand;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ArchiveTest extends CLIRepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
	}

	@Ignore("Some versions of java.util.zip refuse to write an empty ZIP")
	@Test
	public void testEmptyArchive() throws Exception {
				"git archive 4b825dc642cb"
		assertArrayEquals(new String[0]
	}

	@Test
	public void testArchiveWithFiles() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		writeTrashFile("unrelated"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("populate toplevel").call();

				"git archive HEAD"
		assertArrayEquals(new String[] { "a"
				listZipEntries(result));
	}

	@Test
	public void testArchiveWithSubdir() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b.c"
		writeTrashFile("b0c"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("b.c").call();
		git.add().addFilepattern("b0c").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("populate toplevel").call();
		writeTrashFile("b/b"
		writeTrashFile("b/a"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("add subdir").call();

				"git archive master"
		String[] expect = { "a"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testArchivePreservesContent() throws Exception {
		final String payload = "“The quick brown fox jumps over the lazy dog!”";
		writeTrashFile("xyzzy"
		git.add().addFilepattern("xyzzy").call();
		git.commit().setMessage("add file with content").call();

				"git archive HEAD"
		assertArrayEquals(new String[] { payload }
				zipEntryContent(result
	}

	private static String[] listZipEntries(byte[] zipData) throws IOException {
		final List<String> l = new ArrayList<String>();
				new ByteArrayInputStream(zipData));

		ZipEntry e;
		while ((e = in.getNextEntry()) != null)
			l.add(e.getName());
		in.close();
		return l.toArray(new String[l.size()]);
	}

	private static String[] zipEntryContent(byte[] zipData
			throws IOException {
				new ByteArrayInputStream(zipData));
		ZipEntry e;
		while ((e = in.getNextEntry()) != null) {
			if (!e.getName().equals(path))
				continue;

			final List<String> l = new ArrayList<String>();
					new InputStreamReader(in
			String line;
			while ((line = reader.readLine()) != null)
				l.add(line);
			return l.toArray(new String[l.size()]);
		}

		return null;
	}
}
