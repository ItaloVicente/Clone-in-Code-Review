package org.eclipse.jgit.indexdiff;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

	private static final String TESTTARGET = "äéü.txt";

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
		File testDir = createTempDirectory("IndeDiffWithSymlink");
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(
				this.getClass().getPackage().getName().replace('.'
						+ FILEREPO + Constants.DOT_GIT_EXT + ".zip");
		assertNotNull("Test repo zip not found"
		try {
			unzip(in
		} finally {
			in.close();
		}
		cloneWithCGit(testDir
				new File(testDir
		testRepoDir = new File(new File(testDir
	}

	private void unzip(InputStream stream
		try (InputStream buffered = new BufferedInputStream(stream);
				ZipInputStream zip = new ZipInputStream(buffered)) {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				File file = new File(directory
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					file.getParentFile().mkdirs();
					try (OutputStream out = new FileOutputStream(file)) {
						copy(zip
					}
				}
			}
		}
	}

	private void copy(InputStream from
		byte[] buffer = new byte[4096];
		int n;
		while ((n = from.read(buffer)) > 0) {
			to.write(buffer
		}
	}

	private void cloneWithCGit(File directory
		String[] cmd = { "git"
		Process process = Runtime.getRuntime().exec(cmd
		readStream(process.getInputStream());
		String stdErr = readStream(process.getErrorStream());
		process.waitFor();
		int exitCode = process.exitValue();
		if (exitCode != 0) {
			throw new IOException("cgit returned " + exitCode + '\n' + stdErr);
		}
	}

	private String readStream(InputStream stream) throws IOException {
		try (BufferedReader stdOut = new BufferedReader(
				new InputStreamReader(stream))) {
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = stdOut.readLine()) != null) {
				out.append(line);
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
			assertNotNull("Cannot get raw path"
			assertArrayEquals("Expected an NFC link target"
			assertTrue("Could not delete symlink"
			Files.createSymbolicLink(symLink.toPath()
			linkTarget = Files.readSymbolicLink(symLink.toPath());
			assertEquals("Unexpected link target"
					linkTarget.toString());
			raw = rawPath(linkTarget);
			assertNotNull("Cannot get raw path"
			assertArrayEquals("Expected an NFD link target"
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
