package org.eclipse.jgit.lfs.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lfs.errors.LfsConfigInvalidException;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;
import org.junit.Before;
import org.junit.Test;

import static org.eclipse.jgit.lib.Constants.MASTER;

public class LfsConnectionFactoryTest extends RepositoryTestCase {




	private final static String SECOND_BRANCH_NAME = "SecondBranchi";

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();

		git.remoteAdd().setName("origin").setUri(new URIish(ORIGIN_URL)).call();
	}

	private String getStackTrace(Exception actualException) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		actualException.printStackTrace(pw);
		return sw.toString();
	}

	private void writeLfsConfig() throws IOException {
		writeLfsConfig(LFS_SERVER_URL1);
	}

	private void writeLfsConfig(String LfsUrl) throws IOException {
		writeTrashFile(Constants.DOT_LFS_CONFIG
				"[lfs]\n    url = " + LfsUrl);
	}

	private void writeInvalidLfsConfig() throws IOException {
		writeTrashFile(Constants.DOT_LFS_CONFIG
				"{lfs]\n    url = " + LFS_SERVER_URL1);
	}

	private void checkLfsUrl(String LfsUrl) throws IOException {
		HttpConnection lfsServerConn;
		lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST

		assertEquals(LfsUrl + Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
	}

	@Test
	public void checkGetLfsConnection_NoLfsConfig() throws Exception {
		HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST
		assertEquals(
				ORIGIN_URL + Protocol.INFO_LFS_ENDPOINT
						+ Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
	}

	@Test
	public void checkGetLfsConnection_WorkingDir() throws Exception {
		writeLfsConfig();
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_Index() throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_HEAD() throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit LFS Config").call();

		File directory = createTempDirectory("testBareRepo");
		try (Repository bareRepoDb = Git.cloneRepository()
				.setDirectory(directory)
				.setURI(db.getDirectory().toURI().toString()).setBare(true)
				.call().getRepository()) {

			checkLfsUrl(LFS_SERVER_URL1);
		}
	}

	@Test
	public void checkGetLfsConnection_BranchSwitch() throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit LFS Config").call();
		System.out.println(db.getBranch());

		git.branchCreate().setName(SECOND_BRANCH_NAME).call();
		git.checkout().setName(SECOND_BRANCH_NAME).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		writeLfsConfig(LFS_SERVER_URL2);
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit changed LFS Config to second branch")
				.call();

		checkLfsUrl(LFS_SERVER_URL2);

		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		checkLfsUrl(LFS_SERVER_URL2);

		git.checkout().setName(MASTER).call();
		checkLfsUrl(LFS_SERVER_URL1);

		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_ConfigFilePrecedence()
			throws Exception {
		writeLfsConfig();
		checkLfsUrl(LFS_SERVER_URL1);

		StoredConfig config = git.getRepository().getConfig();
		config.setString(ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_URL
		config.save();

		checkLfsUrl(LFS_SERVER_URL2);
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_WorkingDir()
			throws Exception {
		writeInvalidLfsConfig();
		LfsConfigInvalidException actualException = assertThrows(
				LfsConfigInvalidException.class
			LfsConnectionFactory.getLfsConnection(db
					Protocol.OPERATION_DOWNLOAD);
		});
		assertTrue(getStackTrace(actualException)
				.contains("Invalid line in config file"));
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_Index()
			throws Exception {
		writeInvalidLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		LfsConfigInvalidException actualException = assertThrows(
				LfsConfigInvalidException.class
			LfsConnectionFactory.getLfsConnection(db
					Protocol.OPERATION_DOWNLOAD);
		});
		assertTrue(getStackTrace(actualException)
				.contains("Invalid line in config file"));
	}

	@Test
	public void checkGetLfsConnection_InvalidLfsConfig_HEAD() throws Exception {
		writeInvalidLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		git.commit().setMessage("Commit LFS Config").call();

		File directory = createTempDirectory("testBareRepo");
		try (Repository bareRepoDb = Git.cloneRepository()
				.setDirectory(directory)
				.setURI(db.getDirectory().toURI().toString()).setBare(true)
				.call().getRepository()) {
			LfsConfigInvalidException actualException = assertThrows(
					LfsConfigInvalidException.class
					() -> {
						LfsConnectionFactory.getLfsConnection(db
								HttpSupport.METHOD_POST
								Protocol.OPERATION_DOWNLOAD);
					});
			assertTrue(getStackTrace(actualException)
					.contains("Invalid line in config file"));
		}
	}
}
