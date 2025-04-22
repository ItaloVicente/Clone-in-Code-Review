package org.eclipse.jgit.indexdiff;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class IndexDiffWithSymlinkTest extends LocalDiskRepositoryTestCase {

	private static final String FILEREPO = "filerepo";

	private static final String TESTFOLDER = "testfolder";

	private static final String TESTTARGET = "Ã¤Ã©Ã¼.txt";

	private static final String TESTLINK = "aeu.txt";

	{ -61

	{ 97

	private File testRepoDir;

	@Override
	@Before
	public void setUp() throws Exception {
		assumeTrue(SystemReader.getInstance().isMacOS()
				&& FS.DETECTED.supportsSymlinks());
		super.setUp();
		File testDir = createTempDirectory(this.getClass().getSimpleName());
		try (InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(
				this.getClass().getPackage().getName().replace('.'
								+ FILEREPO + ".txt")) {
			assertNotNull("Test repo file not found"
			testRepoDir = restoreGitRepo(in
		}
	}

	private File restoreGitRepo(InputStream in
			throws Exception {
		File exportedTestRepo = new File(testDir
		copy(in
		File restoreScript = new File(testDir
		try (OutputStream out = new BufferedOutputStream(
				new FileOutputStream(restoreScript));
				Writer writer = new OutputStreamWriter(out
			writer.write("echo `which git` 1>&2\n");
			writer.write("echo `git --version` 1>&2\n");
			writer.write("git init " + name + " && \\\n");
			writer.write("cd ./" + name + " && \\\n");
			writer.write("git fast-import < ../" + name + ".txt && \\\n");
			writer.write("git checkout -f\n");
		}
		String[] cmd = { "/bin/sh"
		int exitCode;
		String stdErr;
		ProcessBuilder builder = new ProcessBuilder(cmd);
		builder.environment().put("HOME"
				FS.DETECTED.userHome().getAbsolutePath());
		builder.directory(testDir);
		Process process = builder.start();
		try (InputStream stdOutStream = process.getInputStream();
				InputStream stdErrStream = process.getErrorStream();
				OutputStream stdInStream = process.getOutputStream()) {
			readStream(stdOutStream);
			stdErr = readStream(stdErrStream);
			process.waitFor();
			exitCode = process.exitValue();
		}
		if (exitCode != 0) {
			fail("cgit repo restore returned " + exitCode + '\n' + stdErr);
		}
		return new File(new File(testDir
	}

	private void copy(InputStream from
		try (OutputStream out = new FileOutputStream(to)) {
			byte[] buffer = new byte[4096];
			int n;
			while ((n = from.read(buffer)) > 0) {
				out.write(buffer
			}
		}
	}

	private String readStream(InputStream stream) throws IOException {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(stream
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				out.append(line).append('\n');
			}
			return out.toString();
		}
	}

	@Test
	public void testSymlinkWithEncodingDifference() throws Exception {
		try (Repository testRepo = FileRepositoryBuilder.create(testRepoDir)) {
			File workingTree = testRepo.getWorkTree();
			File symLink = new File(new File(workingTree
					TESTLINK);
			Path linkTarget = Files.readSymbolicLink(symLink.toPath());
			assertEquals("Unexpected link target"
					linkTarget.toString());
			byte[] raw = rawPath(linkTarget);
			if (raw != null) {
				assertArrayEquals("Expected an NFC link target"
			}
			assertTrue("Could not delete symlink"
			Files.createSymbolicLink(symLink.toPath()
			linkTarget = Files.readSymbolicLink(symLink.toPath());
			assertEquals("Unexpected link target"
					linkTarget.toString());
			raw = rawPath(linkTarget);
			if (raw != null) {
				assertArrayEquals("Expected an NFD link target"
			}
			WorkingTreeIterator iterator = new FileTreeIterator(testRepo);
			IndexDiff diff = new IndexDiff(testRepo
			diff.setFilter(PathFilterGroup.createFromStrings(
					Collections.singleton(TESTFOLDER + '/' + TESTLINK)));
			diff.diff();
		}
	}

	private byte[] rawPath(Path p) {
		try {
			Method method = p.getClass().getDeclaredMethod("asByteArray");
			if (method != null) {
				method.setAccessible(true);
				return (byte[]) method.invoke(p);
			}
		} catch (NoSuchMethodException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
		}
		return null;
	}
}
