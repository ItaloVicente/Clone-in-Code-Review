package org.eclipse.jgit.pgm;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Before;
import org.junit.Test;

public class ArchiveTest extends CLIRepositoryTestCase {
	private Git git;
	private String emptyTree;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
		emptyTree = db.resolve("HEAD^{tree}").abbreviate(12).name();
	}

	@Test
	public void testEmptyArchive() throws Exception {
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip " + emptyTree
		assertArrayEquals(new String[0]
	}

	@Test
	public void testEmptyTar() throws Exception {
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=tar " + emptyTree
		assertArrayEquals(new String[0]
	}

	@Test
	public void testUnrecognizedFormat() throws Exception {
		String[] expect = new String[] {
				"fatal: Unknown archive format 'nonsense'"
		String[] actual = executeUnchecked(
				"git archive --format=nonsense " + emptyTree);
		assertArrayEquals(expect
	}

	@Test
	public void testArchiveWithFiles() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		writeTrashFile("unrelated"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("populate toplevel").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip HEAD"
		assertArrayEquals(new String[] { "a"
				listZipEntries(result));
	}

	private void commitGreeting() throws Exception {
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		git.commit().setMessage("a commit with a file").call();
	}

	@Test
	public void testDefaultFormatIsTar() throws Exception {
		commitGreeting();
		byte[] result = CLIGitCommand.executeRaw(
				"git archive HEAD"
		assertArrayEquals(new String[] { "greeting" }
				listTarEntries(result));
	}

	private static String shellQuote(String s) {
		return "'" + s.replace("'"
	}

	@Test
	public void testFormatOverridesFilename() throws Exception {
		File archive = new File(db.getWorkTree()
		String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					"--format=zip " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertContainsEntryWithMode(path
		assertIsZip(archive);
	}

	@Test
	public void testUnrecognizedExtensionMeansTar() throws Exception {
		File archive = new File(db.getWorkTree()
		String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertTarContainsEntry(path
		assertIsTar(archive);
	}

	@Test
	public void testNoExtensionMeansTar() throws Exception {
		File archive = new File(db.getWorkTree()
		String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testExtensionMatchIsAnchored() throws Exception {
		File archive = new File(db.getWorkTree()
		String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testZipExtension() throws Exception {
		File archiveWithDot = new File(db.getWorkTree()
		File archiveNoDot = new File(db.getWorkTree()

		commitGreeting();
		execute("git archive " +
			shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
			"HEAD");
		execute("git archive " +
			shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
			"HEAD");
		assertIsZip(archiveWithDot);
		assertIsTar(archiveNoDot);
	}

	@Test
	public void testTarExtension() throws Exception {
		File archive = new File(db.getWorkTree()
		String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testTgzExtensions() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.gz"
			File archiveWithDot = new File(db.getWorkTree()
			File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsGzip(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
	}

	@Test
	public void testTbz2Extension() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.bz2"
			File archiveWithDot = new File(db.getWorkTree()
			File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsBzip2(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
	}

	@Test
	public void testTxzExtension() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.xz"
			File archiveWithDot = new File(db.getWorkTree()
			File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsXz(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
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

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip master"
		String[] expect = { "a"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarWithSubdir() throws Exception {
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

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=tar master"
		String[] expect = { "a"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	private void commitBazAndFooSlashBar() throws Exception {
		writeTrashFile("baz"
		writeTrashFile("foo/bar"
		git.add().addFilepattern("baz").call();
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("sample commit").call();
	}

	@Test
	public void testArchivePrefixOption() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --prefix=x/ --format=zip master"
		String[] expect = { "x/"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarPrefixOption() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --prefix=x/ --format=tar master"
		String[] expect = { "x/"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	private void commitFoo() throws Exception {
		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("boring commit").call();
	}

	@Test
	public void testPrefixDoesNotNormalizeDoubleSlash() throws Exception {
		commitFoo();
		byte[] result = CLIGitCommand.executeRaw(
		String[] expect = { "x/"
		assertArrayEquals(expect
	}

	@Test
	public void testPrefixDoesNotNormalizeDoubleSlashInTar() throws Exception {
		commitFoo();
		byte[] result = CLIGitCommand.executeRaw(
		String[] expect = { "x/"
		assertArrayEquals(expect
	}

	@Test
	public void testPrefixWithoutTrailingSlash() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --prefix=my- --format=zip master"
		String[] expect = { "my-baz"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarPrefixWithoutTrailingSlash() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.executeRaw(
				"git archive --prefix=my- --format=tar master"
		String[] expect = { "my-baz"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testArchiveIncludesSubmoduleDirectory() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("initial commit").call();
		git.submoduleAdd().setURI("./.").setPath("b").call().close();
		git.commit().setMessage("add submodule").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip master"
		String[] expect = { ".gitmodules"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarIncludesSubmoduleDirectory() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("initial commit").call();
		git.submoduleAdd().setURI("./.").setPath("b").call().close();
		git.commit().setMessage("add submodule").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=tar master"
		String[] expect = { ".gitmodules"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testArchivePreservesMode() throws Exception {
		writeTrashFile("plain"
		writeTrashFile("executable"
		writeTrashFile("symlink"
		writeTrashFile("dir/content"
		git.add().addFilepattern("plain").call();
		git.add().addFilepattern("executable").call();
		git.add().addFilepattern("symlink").call();
		git.add().addFilepattern("dir").call();

		DirCache cache = db.lockDirCache();
		cache.getEntry("executable").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.getEntry("symlink").setFileMode(FileMode.SYMLINK);
		cache.write();
		cache.commit();
		cache.unlock();

		git.commit().setMessage("three files with different modes").call();

		byte[] zipData = CLIGitCommand.executeRaw(
				"git archive --format=zip master"
		writeRaw("zip-with-modes.zip"
		assertContainsEntryWithMode("zip-with-modes.zip"
		assertContainsEntryWithMode("zip-with-modes.zip"
		assertContainsEntryWithMode("zip-with-modes.zip"
		assertContainsEntryWithMode("zip-with-modes.zip"
	}

	@Test
	public void testTarPreservesMode() throws Exception {
		writeTrashFile("plain"
		writeTrashFile("executable"
		writeTrashFile("symlink"
		writeTrashFile("dir/content"
		git.add().addFilepattern("plain").call();
		git.add().addFilepattern("executable").call();
		git.add().addFilepattern("symlink").call();
		git.add().addFilepattern("dir").call();

		DirCache cache = db.lockDirCache();
		cache.getEntry("executable").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.getEntry("symlink").setFileMode(FileMode.SYMLINK);
		cache.write();
		cache.commit();
		cache.unlock();

		git.commit().setMessage("three files with different modes").call();

		byte[] archive = CLIGitCommand.executeRaw(
				"git archive --format=tar master"
		writeRaw("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
	}

	@Test
	public void testArchiveWithLongFilename() throws Exception {
		StringBuilder filename = new StringBuilder();
		List<String> l = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			filename.append("1234567890/");
			l.add(filename.toString());
		}
		filename.append("1234567890");
		l.add(filename.toString());
		writeTrashFile(filename.toString()
		git.add().addFilepattern("1234567890").call();
		git.commit().setMessage("file with long name").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip HEAD"
		assertArrayEquals(l.toArray(new String[0])
				listZipEntries(result));
	}

	@Test
	public void testTarWithLongFilename() throws Exception {
		StringBuilder filename = new StringBuilder();
		List<String> l = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			filename.append("1234567890/");
			l.add(filename.toString());
		}
		filename.append("1234567890");
		l.add(filename.toString());
		writeTrashFile(filename.toString()
		git.add().addFilepattern("1234567890").call();
		git.commit().setMessage("file with long name").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=tar HEAD"
		assertArrayEquals(l.toArray(new String[0])
				listTarEntries(result));
	}

	@Test
	public void testArchivePreservesContent() throws Exception {
		String payload = "âThe quick brown fox jumps over the lazy dog!â";
		writeTrashFile("xyzzy"
		git.add().addFilepattern("xyzzy").call();
		git.commit().setMessage("add file with content").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=zip HEAD"
		assertArrayEquals(new String[] { payload }
				zipEntryContent(result
	}

	@Test
	public void testTarPreservesContent() throws Exception {
		String payload = "âThe quick brown fox jumps over the lazy dog!â";
		writeTrashFile("xyzzy"
		git.add().addFilepattern("xyzzy").call();
		git.commit().setMessage("add file with content").call();

		byte[] result = CLIGitCommand.executeRaw(
				"git archive --format=tar HEAD"
		assertArrayEquals(new String[] { payload }
				tarEntryContent(result
	}

	private Process spawnAssumingCommandPresent(String... cmdline) {
		File cwd = db.getWorkTree();
		ProcessBuilder procBuilder = new ProcessBuilder(cmdline)
				.directory(cwd)
				.redirectErrorStream(true);
		Process proc = null;
		try {
			proc = procBuilder.start();
		} catch (IOException e) {
			assumeNoException(e);
		}

		return proc;
	}

	private BufferedReader readFromProcess(Process proc) throws Exception {
		return new BufferedReader(
				new InputStreamReader(proc.getInputStream()
	}

	private void grepForEntry(String name
			throws Exception {
		Process proc = spawnAssumingCommandPresent(cmdline);
		proc.getOutputStream().close();
		BufferedReader reader = readFromProcess(proc);
		try {
			String line;
			while ((line = reader.readLine()) != null)
				if (line.startsWith(mode) && line.endsWith(name))
					return;
			fail("expected entry " + name + " with mode " + mode + " but found none");
		} finally {
			proc.getOutputStream().close();
			proc.destroy();
		}
	}

	private void assertMagic(long offset
		try (BufferedInputStream in = new BufferedInputStream(
				new FileInputStream(file))) {
			if (offset > 0) {
				long skipped = in.skip(offset);
				assertEquals(offset
			}

			byte[] actual = new byte[magicBytes.length];
			in.read(actual);
			assertArrayEquals(magicBytes
		}
	}

	private void assertMagic(byte[] magicBytes
		assertMagic(0
	}

	private void assertIsTar(File file) throws Exception {
		assertMagic(257
	}

	private void assertIsZip(File file) throws Exception {
		assertMagic(new byte[] { 'P'
	}

	private void assertIsGzip(File file) throws Exception {
		assertMagic(new byte[] { 037
	}

	private void assertIsBzip2(File file) throws Exception {
		assertMagic(new byte[] { 'B'
	}

	private void assertIsXz(File file) throws Exception {
		assertMagic(new byte[] { (byte) 0xfd
	}

	private void assertContainsEntryWithMode(String zipFilename
			throws Exception {
		grepForEntry(name
	}

	private void assertTarContainsEntry(String tarfile
			throws Exception {
		grepForEntry(name
	}

	private void writeRaw(String filename
			throws IOException {
		File path = new File(db.getWorkTree()
		try (OutputStream out = new FileOutputStream(path)) {
			out.write(data);
		}
	}

	private static String[] listZipEntries(byte[] zipData) throws IOException {
		List<String> l = new ArrayList<>();
		try (ZipInputStream in = new ZipInputStream(
				new ByteArrayInputStream(zipData))) {
			ZipEntry e;
			while ((e = in.getNextEntry()) != null)
				l.add(e.getName());
		}
		return l.toArray(new String[0]);
	}

	private static Future<Object> writeAsync(OutputStream stream
		ExecutorService executor = Executors.newSingleThreadExecutor();

		return executor.submit(new Callable<Object>() {
			@Override
			public Object call() throws IOException {
				try {
					stream.write(data);
					return null;
				} finally {
					stream.close();
				}
			}
		});
	}

	private String[] listTarEntries(byte[] tarData) throws Exception {
		List<String> l = new ArrayList<>();
		Process proc = spawnAssumingCommandPresent("tar"
		try (BufferedReader reader = readFromProcess(proc)) {
			OutputStream out = proc.getOutputStream();

			Future<?> writing = writeAsync(out

			try {
				String line;
				while ((line = reader.readLine()) != null)
					l.add(line);

				return l.toArray(new String[0]);
			} finally {
				writing.get();
				proc.destroy();
			}
		}
	}

	private static String[] zipEntryContent(byte[] zipData
			throws IOException {
		ZipInputStream in = new ZipInputStream(
				new ByteArrayInputStream(zipData));
		ZipEntry e;
		while ((e = in.getNextEntry()) != null) {
			if (!e.getName().equals(path))
				continue;

			List<String> l = new ArrayList<>();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in
			String line;
			while ((line = reader.readLine()) != null)
				l.add(line);
			return l.toArray(new String[0]);
		}

		return null;
	}

	private String[] tarEntryContent(byte[] tarData
			throws Exception {
		List<String> l = new ArrayList<>();
		Process proc = spawnAssumingCommandPresent("tar"
		try (BufferedReader reader = readFromProcess(proc)) {
			OutputStream out = proc.getOutputStream();
			Future<?> writing = writeAsync(out

			try {
				String line;
				while ((line = reader.readLine()) != null)
					l.add(line);

				return l.toArray(new String[0]);
			} finally {
				writing.get();
				proc.destroy();
			}
		}
	}
}
