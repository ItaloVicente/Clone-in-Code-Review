package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.EOL;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
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
	public static String smallContents[] = {
			generateTestData(3
			generateTestData(3
			generateTestData(3

	@DataPoint
	public static String hugeContents[] = {
			generateTestData(1000000
			generateTestData(1000000
			generateTestData(1000000

	static String generateTestData(int size
			boolean withLF) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0 && i % lineSize == 0) {
				if (withCRLF && withLF) {
					if (i % 2 == 0)
						sb.append("\r\n");
					else
						sb.append("\n");
				} else if (withCRLF) {
					sb.append("\r\n");
				} else if (withLF) {
					sb.append("\n");
				}
			}
			sb.append("A");
		}
		return sb.toString();
	}

	public EolRepositoryTest(String[] testContent) {
		CONTENT_CRLF = testContent[0];
		CONTENT_LF = testContent[1];
		CONTENT_MIXED = testContent[2];
	}

	protected String CONTENT_CRLF;

	protected String CONTENT_LF;

	protected String CONTENT_MIXED;

	private TreeWalk walk;

	private File dotGitattributes;

	private File fileCRLF;

	private File fileLF;

	private File fileMixed;

	private static class ActualEntry {
		private String attrs;

		private String file;

		private String index;
	}

	private ActualEntry entryCRLF = new ActualEntry();

	private ActualEntry entryLF = new ActualEntry();

	private ActualEntry entryMixed = new ActualEntry();

	private DirCache dc;

	@Test
	public void testDefaultSetup() throws Exception {
		setupGitAndDoHardReset(null
		collectRepositoryState();
		assertEquals("text=auto"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	public void checkEntryContent(ActualEntry entry
			String indexContent) {
		assertEquals(fileContent
		assertEquals(indexContent
	}

	@Test
	public void test_ConfigAutoCRLF_false() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("text=auto"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_true() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
		collectRepositoryState();
		assertEquals("text=auto"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_input() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT
		collectRepositoryState();
		assertEquals("text=auto"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(null
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigEOL_crlf() throws Exception {
		setupGitAndDoHardReset(null
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigEOL_native_windows() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			setupGitAndDoHardReset(null
			collectRepositoryState();
			assertEquals("text"
			checkEntryContent(entryCRLF
			checkEntryContent(entryLF
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigEOL_native_xnix() throws Exception {
		String origLineSeparator = System.getProperty("line.separator"
		System.setProperty("line.separator"
		try {
			setupGitAndDoHardReset(null
			collectRepositoryState();
			assertEquals("text"
			checkEntryContent(entryCRLF
			checkEntryContent(entryLF
		} finally {
			System.setProperty("line.separator"
		}
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_true_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_input_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
		collectRepositoryState();
		assertEquals("eol=lf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("eol=lf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT
		collectRepositoryState();
		assertEquals("eol=lf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf_InfoEOL_crlf()
			throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf_InfoEOL_lf()
			throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE
		collectRepositoryState();
		assertEquals("eol=lf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_GlobalEOL_lf_RootEOL_crlf() throws Exception {
		setupGitAndDoHardReset(null
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_lf() throws Exception {
		setupGitAndDoHardReset(null
		collectRepositoryState();
		assertEquals("eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_unspec()
			throws Exception {
		setupGitAndDoHardReset(null
				"*.txt text !eol");
		collectRepositoryState();
		assertEquals("eol=crlf text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_unspec_RootEOL_crlf()
			throws Exception {
		setupGitAndDoHardReset(null
				"*.txt text eol=crlf");
		collectRepositoryState();
		assertEquals("text"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void testBinary1() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
				"*.txt eol=crlf");
		collectRepositoryState();
		assertEquals("binary -diff -merge -text eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	@Test
	public void testBinary2() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE
				"*.txt binary");
		collectRepositoryState();
		assertEquals("binary -diff -merge -text eol=crlf"
		checkEntryContent(entryCRLF
		checkEntryContent(entryLF
		checkEntryContent(entryMixed
	}

	private void setupGitAndDoHardReset(AutoCRLF autoCRLF
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

		fileCRLF = createAndAddFile(git

		fileLF = createAndAddFile(git

		fileMixed = createAndAddFile(git

		git.commit().setMessage("add files").call();

		for (File f : new File[] { dotGitattributes
			if (f == null)
				continue;
			f.delete();
			Assert.assertFalse(f.exists());
		}

		git.reset().setMode(ResetType.HARD).call();
		fsTick(db.getIndexFile());
		git.add().addFilepattern(".").call();

		dc = db.readDirCache();

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

	private void collectRepositoryState() throws Exception {
		walk = beginWalk();
		if (dotGitattributes != null)
			collectEntryContentAndAttributes(F
		collectEntryContentAndAttributes(F
		collectEntryContentAndAttributes(F
		collectEntryContentAndAttributes(F
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

	private void collectEntryContentAndAttributes(FileMode type
			ActualEntry e) throws IOException {
		assertTrue("walk has entry"

		assertEquals(pathName
		assertEquals(type

		if (e != null) {
			e.attrs = "";
			for (Attribute a : walk.getAttributes().getAll()) {
				e.attrs += " " + a.toString();
			}
			e.attrs = e.attrs.trim();
			e.file = new String(
					IO.readFully(new File(db.getWorkTree()
			DirCacheEntry dce = dc.getEntry(pathName);
			ObjectLoader open = walk.getObjectReader().open(dce.getObjectId());
			e.index = new String(open.getBytes());
		}

		if (D.equals(type))
			walk.enterSubtree();
	}
}
