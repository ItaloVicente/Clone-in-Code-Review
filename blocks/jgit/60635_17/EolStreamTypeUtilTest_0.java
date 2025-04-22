package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.EOL;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.IO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class EolRepositoryTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	@DataPoint
	public static String smallContents[] = { "a\r\nb"

	@DataPoint
	public static String hugeContents[] = { generateTestData("\r\n")
			generateTestData("\n") };

	static String generateTestData(String eol) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 1024 * 1024; i++) {
			if (i % 17 == 0) {
				sb.append(eol);
			} else {
				sb.append("A");
			}
		}
		return sb.toString();
	}

	public EolRepositoryTest(String[] testContent) {
		CONTENT_CRLF = testContent[0];
		CONTENT_LF = testContent[1];
	}

	protected String CONTENT_CRLF;

	protected String CONTENT_LF;

	private TreeWalk walk;

	private File dotGitattributes;

	private File file1;

	private File file2;

	@Test
	public void testDefaultSetup() throws Exception {
		setupGit(null
		checkAllContentsAndAttributes("text=auto"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_false() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("text=auto"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_true() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkAllContentsAndAttributes("text=auto"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_input() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkAllContentsAndAttributes("text=auto"
				CONTENT_LF
	}

	@Test
	public void test_ConfigEOL_lf() throws Exception {
		setupGit(null
		checkAllContentsAndAttributes("text"
				CONTENT_LF
	}

	@Test
	public void test_ConfigEOL_crlf() throws Exception {
		setupGit(null
		checkAllContentsAndAttributes("text"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigEOL_native_windows() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			setupGit(null
			checkAllContentsAndAttributes("text"
					CONTENT_LF
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigEOL_native_xnix() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			setupGit(null
			checkAllContentsAndAttributes("text"
					CONTENT_LF
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("text"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("text"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_true_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkAllContentsAndAttributes("text"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_input_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkAllContentsAndAttributes("text"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkAllContentsAndAttributes("eol=lf"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("eol=lf"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkAllContentsAndAttributes("eol=lf"
				CONTENT_LF
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf_InfoEOL_crlf()
			throws Exception {
		setupGit(AutoCRLF.TRUE
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf_InfoEOL_lf()
			throws Exception {
		setupGit(AutoCRLF.FALSE
		checkAllContentsAndAttributes("eol=lf"
				CONTENT_LF
	}

	@Test
	public void test_GlobalEOL_lf_RootEOL_crlf() throws Exception {
		setupGit(null
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_lf() throws Exception {
		setupGit(null
		checkAllContentsAndAttributes("eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_unspec()
			throws Exception {
		setupGit(null
				"*.txt text !eol");
		checkAllContentsAndAttributes("eol=crlf text"
				CONTENT_CRLF
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_unspec_RootEOL_crlf()
			throws Exception {
		setupGit(null
				"*.txt text eol=crlf");
		checkAllContentsAndAttributes("text"
				CONTENT_LF
	}

	@Test
	public void testBinary1() throws Exception {
		setupGit(AutoCRLF.TRUE
				"*.txt eol=crlf");
		checkAllContentsAndAttributes("binary -diff -merge -text eol=crlf"
				CONTENT_CRLF
	}

	@Test
	public void testBinary2() throws Exception {
		setupGit(AutoCRLF.TRUE
				"*.txt binary");
		checkAllContentsAndAttributes("binary -diff -merge -text eol=crlf"
				CONTENT_CRLF
	}

	protected void setupGit(AutoCRLF autoCRLF
			String globalAttributesContent
			String workDirRootAttributesContent) throws Exception {
		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();
		if (autoCRLF != null) {
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
		}
		if (eol != null) {
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_EOL
		}
		if (globalAttributesContent != null) {
			File f = new File(db.getDirectory()
			write(f
			config.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_ATTRIBUTESFILE
					f.getAbsolutePath());

		}
		if (infoAttributesContent != null) {
			File f = new File(db.getDirectory()
			write(f
		}
		config.save();

		if (workDirRootAttributesContent != null) {
			dotGitattributes = createAndAddFile(git
					Constants.DOT_GIT_ATTRIBUTES
		} else {
			dotGitattributes = null;
		}

		file1 = createAndAddFile(git

		file2 = createAndAddFile(git

		git.commit().setMessage("add files").call();

		for (File f : new File[] { dotGitattributes
			if (f == null)
				continue;
			f.delete();
			Assert.assertFalse(f.exists());
		}

		git.reset().setMode(ResetType.HARD).call();
	}

	private File createAndAddFile(Git git
			throws Exception {
		File f;
		int pos = path.lastIndexOf('/');
		if (pos < 0) {
			f = writeTrashFile(path
		} else {
			f = writeTrashFile(path.substring(0
					content);
		}
		git.add().addFilepattern(path).call();
		Assert.assertTrue(f.exists());
		return f;
	}

	protected void checkAllContentsAndAttributes(String expectedAttributes
			String expectedFile1Content
			String expectedFile2Content
					throws Exception {
		walk = beginWalk();
		if (dotGitattributes != null)
			checkEntryContentAndAttributes(F
					null);
		checkEntryContentAndAttributes(F
				expectedAttributes
				expectedIndex1Content);
		checkEntryContentAndAttributes(F
				expectedAttributes
				expectedIndex2Content);
		endWalk();
	}

	private TreeWalk beginWalk() throws Exception {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		newWalk.addTree(new DirCacheIterator(db.readDirCache()));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}

	private void checkEntryContentAndAttributes(FileMode type
			String expectedAttrs
			String expectedIndexContent) throws IOException {
		assertTrue("walk has entry"

		assertEquals(pathName
		assertEquals(type
		if (expectedAttrs != null) {
			String s = "";
			for (Attribute a : walk.getAttributes().getAll()) {
				s += " " + a.toString();
			}
			s = s.trim();
			assertEquals(expectedAttrs
		} else if (!pathName.equals(".gitattributes")) {
			String s = "";
			for (Attribute a : walk.getAttributes().getAll()) {
				s += " " + a.toString();
			}
			s = s.trim();
			System.out
					.println(new Exception().getStackTrace()[2].getMethodName()
							+ ": " + s);
		}
		if (expectedFileContent != null) {
			String actualFileContent = new String(
					IO.readFully(new File(db.getWorkTree()
			assertEquals(expectedFileContent
		}
		if (expectedIndexContent != null) {
			String actualIndexContent = new String(walk.getObjectReader()
					.open(walk.getObjectId(1)).getBytes());
			assertEquals(actualIndexContent
		}
		if (D.equals(type))
			walk.enterSubtree();
	}
}
