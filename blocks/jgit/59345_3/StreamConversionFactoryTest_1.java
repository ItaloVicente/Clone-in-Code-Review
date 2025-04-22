
package org.eclipse.jgit.api;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.attributes.AttributeSet;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.EOL;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.IO;
import org.junit.Assert;
import org.junit.Test;

public class EndOfLineRepositoryTest extends RepositoryTestCase {

	private final HashSet<File> repoFiles = new HashSet<File>();

	@Test
	public void testDefaultSetup() throws Exception {
		Git git = setupGit(null
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false() throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_forceEOL() throws Exception {
		String origFlag = System.getProperty("jgit.forceEOL"
		System.setProperty("jgit.forceEOL"
		try {
			Git git = setupGit(AutoCRLF.FALSE
					"* text=auto");
			textFileContentIs(git
		} finally {
			System.setProperty("jgit.forceEOL"
		}
	}

	@Test
	public void test_ConfigAuto_true() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_input() throws Exception {
		Git git = setupGit(AutoCRLF.INPUT
		textFileContentIs(git
	}

	@Test
	public void test_ConfigEOL_lf() throws Exception {
		Git git = setupGit(null
		textFileContentIs(git
	}

	@Test
	public void test_ConfigEOL_crlf() throws Exception {
		Git git = setupGit(null
		textFileContentIs(git
	}

	@Test
	public void test_ConfigEOL_native_windows() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			Git git = setupGit(null
			textFileContentIs(git
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigEOL_native_xnix() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			Git git = setupGit(null
			textFileContentIs(git
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native() throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
				null);
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native_forceEOL()
			throws Exception {
		String origFlag = System.getProperty("jgit.forceEOL"
		System.setProperty("jgit.forceEOL"
		try {
			Git git = setupGit(AutoCRLF.FALSE
					null);
			textFileContentIs(git
		} finally {
			System.setProperty("jgit.forceEOL"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_true_ConfigEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_input_ConfigEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.INPUT
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_lf() throws Exception {
		Git git = setupGit(AutoCRLF.INPUT
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_crlf() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf() throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
				null);
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_crlf() throws Exception {
		Git git = setupGit(AutoCRLF.INPUT
				null);
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf_InfoEOL_crlf()
			throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
				"*.txt eol=crlf"
		textFileContentIs(git
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf_InfoEOL_lf()
			throws Exception {
		Git git = setupGit(AutoCRLF.FALSE
				"*.txt eol=lf"
		textFileContentIs(git
	}

	@Test
	public void test_GlobalEOL_crlf_RootEOL_lf() throws Exception {
		Git git = setupGit(null
		textFileContentIs(git
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_lf() throws Exception {
		Git git = setupGit(null
				"*.txt eol=lf");
		textFileContentIs(git
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_unspec()
			throws Exception {
		Git git = setupGit(null
				"*.txt text !eol");
		AttributeSet expectedAttrs = new AttributeSet(
				new Attribute("text"
		AttributeSet actualAttrs = git.getRepository().getAttributesHierarchy()
				.getAttributes("lf.txt"
		Assert.assertEquals(expectedAttrs
		textFileContentIs(git
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_unspec_RootEOL_crlf()
			throws Exception {
		Git git = setupGit(null
				"*.txt text eol=crlf");
		AttributeSet expectedAttrs = new AttributeSet(
				new Attribute("text"
		AttributeSet actualAttrs = git.getRepository().getAttributesHierarchy()
				.getAttributes("lf.txt"
		Assert.assertEquals(expectedAttrs
		textFileContentIs(git
	}

	@Test
	public void testBinary1() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
				"*.txt binary"
		textFileContentIs(git
	}

	@Test
	public void testBinary2() throws Exception {
		Git git = setupGit(AutoCRLF.TRUE
				"*.txt binary");
		textFileContentIs(git
	}

	private Git setupGit(AutoCRLF autoCRLF
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
		if(globalAttributesContent!=null){
			File f = new File(db.getDirectory()
			write(f
			config.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_ATTRIBUTESFILE

		}
		if(infoAttributesContent!=null){
			File f = new File(db.getDirectory()
			write(f
		}
		config.save();

		if (workDirRootAttributesContent != null) {
			createFile(git
					workDirRootAttributesContent);
		}

		createFile(git

		createFile(git

		for (File f : repoFiles) {
			f.delete();
			Assert.assertFalse(f.exists());
		}

		git.reset().setMode(ResetType.HARD).call();

		return git;
	}

	private void createFile(Git git
			String content)
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
		repoFiles.add(f);
	}

	private void textFileContentIs(Git git
			String expectedCRLFTxtContent)
					throws Exception {
		Assert.assertEquals(expectedLFTxtContent
				new String(IO.readFully(
						new File(git.getRepository().getWorkTree()
				StandardCharsets.UTF_8));

		Assert.assertEquals(expectedCRLFTxtContent
				new File(git.getRepository().getWorkTree()
				StandardCharsets.UTF_8));
	}

}
