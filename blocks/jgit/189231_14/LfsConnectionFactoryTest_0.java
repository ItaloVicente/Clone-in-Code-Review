package org.eclipse.jgit.lfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.internal.LfsConnectionFactory;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LfsConfigGitTest extends RepositoryTestCase {

	private static final String SMUDGE_NAME = org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
			+ Constants.ATTR_FILTER_DRIVER_PREFIX
			+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_SMUDGE;


	private static final String EXPECTED_SERVER_URL1 = LFS_SERVER_URI1
			+ Protocol.OBJECTS_LFS_ENDPOINT;


	private static final String EXPECTED_SERVER_URL2 = LFS_SERVER_URI2
			+ Protocol.OBJECTS_LFS_ENDPOINT;


	private static final String EXPECTED_SERVER_URL3 = LFS_SERVER_URI3
			+ Protocol.OBJECTS_LFS_ENDPOINT;

			+ "oid sha256:6ce9fab52ee9a6c4c097def4e049c6acdeba44c99d26e83ba80adec1473c9b2d\n"
			+ "size 253952\n";

			+ "oid sha256:a4b711cd989863ae2038758a62672138347abbbae4076a7ad3a545fda7d08f82\n"
			+ "size 67072\n";

	private static List<String> checkoutURLs = new ArrayList<>();

	static class SmudgeFilterMock extends FilterCommand {
		public SmudgeFilterMock(Repository db
				OutputStream out) throws IOException {
			super(in
			HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(db
					HttpSupport.METHOD_POST
			checkoutURLs.add(lfsServerConn.getURL().toString());
		}

		@Override
		public int run() throws IOException {
			in.transferTo(out);
			return -1;
		}
	}

	@BeforeClass
	public static void installLfs() {
		FilterCommandRegistry.register(SMUDGE_NAME
	}

	@AfterClass
	public static void removeLfs() {
		FilterCommandRegistry.unregister(SMUDGE_NAME);
	}

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();

		fileBefore = null;
		fileAfter = null;
		configFile = null;
		gitAttributesFile = null;
	}

	File fileBefore;

	File fileAfter;

	File configFile;

	File gitAttributesFile;

	private void createLfsFiles(String lfsPointer) throws Exception {

		String fileNameBefore = ".aaa.txt";
		fileBefore = writeTrashFile(fileNameBefore
		git.add().addFilepattern(fileNameBefore).call();

		String fileNameAfter = "zzz.txt";
		fileAfter = writeTrashFile(fileNameAfter
		git.add().addFilepattern(fileNameAfter).call();

		git.commit().setMessage("Commit LFS Pointer files").call();
	}


	private String addLfsConfigFiles(String lfsServerUrl) throws Exception {
		String lfsConfig1 = createLfsConfig(lfsServerUrl);
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		if (gitAttributesFile == null) {
			gitAttributesFile = writeTrashFile(".gitattributes"
				"*.txt filter=lfs");
		} else {
			gitAttributesFile = writeTrashFile(".gitattributes"
					"*.txt filter=lfs\n");
		}

		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("Commit config files").call();
		return lfsConfig1;
	}

	private String createLfsConfig(String lfsServerUrl) throws IOException {
		String lfsConfig1 = "[lfs]\n    url = " + lfsServerUrl;
		configFile = writeTrashFile(Constants.DOT_LFS_CONFIG
		return lfsConfig1;
	}

	@Test
	public void checkoutLfsObjects_reset() throws Exception {
		createLfsFiles(FAKE_LFS_POINTER1);
		String lfsConfig1 = addLfsConfigFiles(LFS_SERVER_URI1);

		assertTrue(configFile.delete());
		assertTrue(fileBefore.delete());
		assertTrue(fileAfter.delete());

		assertTrue(gitAttributesFile.delete());

		createLfsConfig(LFS_SERVER_URI3);

		checkoutURLs.clear();
		git.reset().setMode(ResetType.HARD).call();

		checkFile(configFile
		checkFile(fileBefore
		checkFile(fileAfter

		assertEquals(2
		assertEquals(EXPECTED_SERVER_URL3
		assertEquals(EXPECTED_SERVER_URL1
	}

	@Test
	public void checkoutLfsObjects_BranchSwitch() throws Exception {
		git.checkout().setCreateBranch(true).setName("URL1").call();

		createLfsFiles(FAKE_LFS_POINTER1);
		String lfsConfig1 = addLfsConfigFiles(LFS_SERVER_URI1);

		git.checkout().setCreateBranch(true).setName("URL2").call();

		createLfsFiles(FAKE_LFS_POINTER2);
		String lfsConfig2 = addLfsConfigFiles(LFS_SERVER_URI2);

		checkFile(configFile
		checkFile(fileBefore
		checkFile(fileAfter

		checkoutURLs.clear();
		git.checkout().setName("URL1").call();

		checkFile(configFile
		checkFile(fileBefore
		checkFile(fileAfter

		assertEquals(2
		assertEquals(EXPECTED_SERVER_URL2
		assertEquals(EXPECTED_SERVER_URL1

		checkoutURLs.clear();
		git.checkout().setName("URL2").call();

		checkFile(configFile
		checkFile(fileBefore
		checkFile(fileAfter

		assertEquals(2
		assertEquals(EXPECTED_SERVER_URL1
		assertEquals(EXPECTED_SERVER_URL2
	}

	@Test
	public void checkoutLfsObjects_BranchSwitch_ModifiedLocal()
			throws Exception {

		git.checkout().setCreateBranch(true).setName("URL1").call();

		createLfsFiles(FAKE_LFS_POINTER1);
		addLfsConfigFiles(LFS_SERVER_URI1);

		git.checkout().setCreateBranch(true).setName("URL2").call();

		createLfsFiles(FAKE_LFS_POINTER2);
		addLfsConfigFiles(LFS_SERVER_URI1);

		assertTrue(configFile.delete());
		String lfsConfig3 = createLfsConfig(LFS_SERVER_URI3);

		checkFile(configFile
		checkFile(fileBefore
		checkFile(fileAfter

		checkoutURLs.clear();
		git.checkout().setName("URL1").call();

		checkFile(fileBefore
		checkFile(fileAfter
		checkFile(configFile

		assertEquals(2

		assertEquals(EXPECTED_SERVER_URL3
		assertEquals(EXPECTED_SERVER_URL3

		checkoutURLs.clear();
		git.checkout().setName("URL2").call();

		checkFile(fileBefore
		checkFile(fileAfter
		checkFile(configFile

		assertEquals(2
		assertEquals(EXPECTED_SERVER_URL3
		assertEquals(EXPECTED_SERVER_URL3
	}
}
