
package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.Attribute;
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

public class EndOfLineRepositoryTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private TreeWalk walk;

	private File dotGitattributes;

	private File crlfTxt;

	private File lfTxt;


	@Test
	public void testDefaultSetup() throws Exception {
		setupGit(null
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_forceEOL() throws Exception {
		String origFlag = System.getProperty("jgit.forceEOL"
		System.setProperty("jgit.forceEOL"
		try {
			setupGit(AutoCRLF.FALSE
					"* text=auto");
			checkGit("a\r\nb"
		} finally {
			System.setProperty("jgit.forceEOL"
		}
	}

	@Test
	public void test_ConfigAuto_true() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_input() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigEOL_lf() throws Exception {
		setupGit(null
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigEOL_crlf() throws Exception {
		setupGit(null
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigEOL_native_windows() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			setupGit(null
			checkGit("a\nb"
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
			checkGit("a\nb"
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native() throws Exception {
		setupGit(AutoCRLF.FALSE
				null);
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native_forceEOL()
			throws Exception {
		String origFlag = System.getProperty("jgit.forceEOL"
		System.setProperty("jgit.forceEOL"
		try {
			setupGit(AutoCRLF.FALSE
					null);
			checkGit("a\r\nb"
		} finally {
			System.setProperty("jgit.forceEOL"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_true_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_input_ConfigEOL_lf() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.FALSE
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_lf() throws Exception {
		setupGit(AutoCRLF.INPUT
		checkGit("a\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.TRUE
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.FALSE
				null);
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_crlf() throws Exception {
		setupGit(AutoCRLF.INPUT
				null);
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf_InfoEOL_crlf()
			throws Exception {
		setupGit(AutoCRLF.TRUE
				"*.txt eol=crlf"
		checkGit("a\r\nb"
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf_InfoEOL_lf()
			throws Exception {
		setupGit(AutoCRLF.FALSE
				"*.txt eol=lf"
		checkGit("a\nb"
	}

	@Test
	public void test_GlobalEOL_lf_RootEOL_crlf() throws Exception {
		setupGit(null
		checkGit("a\r\nb"
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_lf() throws Exception {
		setupGit(null
				"*.txt eol=lf");
		checkGit("a\r\nb"
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_unspec()
			throws Exception {
		setupGit(null
				"*.txt text !eol");
		checkGit("a\r\nb"
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_unspec_RootEOL_crlf()
			throws Exception {
		setupGit(null
				"*.txt text eol=crlf");
		checkGit("a\nb"
	}

	@Test
	public void testBinary1() throws Exception {
		setupGit(AutoCRLF.TRUE
				"*.txt binary"
		checkGit("a\r\nb"
	}

	@Test
	public void testBinary2() throws Exception {
		setupGit(AutoCRLF.TRUE
				"*.txt binary");
		checkGit("a\r\nb"
	}

	private void setupGit(AutoCRLF autoCRLF
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
			dotGitattributes = createFile(git
					workDirRootAttributesContent);
		} else {
			dotGitattributes = null;
		}

		crlfTxt = createFile(git

		lfTxt = createFile(git


		for (File f : new File[] { dotGitattributes
			if (f == null)
				continue;
			f.delete();
			Assert.assertFalse(f.exists());
		}

		git.reset().setMode(ResetType.HARD).call();
	}

	private File createFile(Git git
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
		fsTick(f);
		Assert.assertTrue(f.exists());
		return f;
	}

	private void checkGit(String expectedCRLFTxtContent
			String expectedLFTxtContent
					throws Exception {
		walk = beginWalk();
		if (dotGitattributes != null)
			assertIteration(F
		assertIteration(F
				expectedCRLFTxtContent);
		assertIteration(F
				expectedLFTxtContent);
		endWalk();
	}

	private TreeWalk beginWalk() {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}

	private void assertIteration(FileMode type
			String expectedAttrs
					throws IOException {
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
		if (expectedContent != null) {
			assertEquals(expectedContent
					new String(
							IO.readFully(new File(db.getWorkTree()
 pathName))
					StandardCharsets.UTF_8));
		}
		if (D.equals(type))
			walk.enterSubtree();
	}

}
