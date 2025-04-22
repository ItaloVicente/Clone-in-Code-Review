package org.eclipse.jgit.lfs.internal;

import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.CleanFilter;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lfs.SmudgeFilter;
import org.eclipse.jgit.lfs.errors.LfsConfigInvalidException;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LfsConnectionFactoryTest extends RepositoryTestCase {

	private static final String SMUDGE_NAME = org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
			+ Constants.ATTR_FILTER_DRIVER_PREFIX
			+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_SMUDGE;

	private static final String CLEAN_NAME = org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
			+ Constants.ATTR_FILTER_DRIVER_PREFIX
			+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_CLEAN;




	private Git git;

	@BeforeClass
	public static void installLfs() {
		FilterCommandRegistry.register(SMUDGE_NAME
		FilterCommandRegistry.register(CLEAN_NAME
	}

	@AfterClass
	public static void removeLfs() {
		FilterCommandRegistry.unregister(SMUDGE_NAME);
		FilterCommandRegistry.unregister(CLEAN_NAME);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void lfsUrlFromRemoteUrlWithDotGit() throws Exception {
	}

	@Test
	public void lfsUrlFromRemoteUrlWithoutDotGit() throws Exception {
	}

	@Test
	public void lfsUrlFromLocalConfig() throws Exception {

		StoredConfig cfg = ((Repository) db).getConfig();
		cfg.setString(ConfigConstants.CONFIG_SECTION_LFS
				null
				ConfigConstants.CONFIG_KEY_URL
		cfg.save();

	}

	@Test
	public void lfsUrlFromOriginConfig() throws Exception {

		StoredConfig cfg = ((Repository) db).getConfig();
		cfg.setString(ConfigConstants.CONFIG_SECTION_LFS
				org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME
				ConfigConstants.CONFIG_KEY_URL
		cfg.save();

	}

	@Test
	public void lfsUrlNotConfigured() throws Exception {
		assertThrows(LfsConfigInvalidException.class
				() -> LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromWorkingDir()
			throws Exception {
		writeLfsConfig();
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromIndex()
			throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_lfsurl_lfsconfigFromHEAD()
			throws Exception {
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
	public void checkGetLfsConnection_remote_lfsconfigFromWorkingDir()
			throws Exception {
		addRemoteUrl(ORIGIN_URL);
		writeLfsConfig(LFS_SERVER_URL1
		checkLfsUrl(LFS_SERVER_URL1);
	}

	@Test
	public void checkGetLfsConnection_ConfigFilePrecedence_lfsconfigFromWorkingDir()
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

	private void addRemoteUrl(String remotUrl) throws Exception {
		RemoteAddCommand add = git.remoteAdd();
		add.setUri(new URIish(remotUrl));
		add.setName(org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME);
		add.call();
	}

	private String getStackTrace(Exception actualException) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		actualException.printStackTrace(pw);
		return sw.toString();
	}

	private void writeLfsConfig() throws IOException {
		writeLfsConfig(LFS_SERVER_URL1
	}

	private void writeLfsConfig(String lfsUrl
			throws IOException {
		writeLfsConfig(lfsUrl
	}

	private void writeLfsConfig(String lfsUrl
			String subsection
		StringBuilder config = new StringBuilder();
		config.append("[");
		config.append(section);
		if (subsection != null) {
			config.append(" \"");
			config.append(subsection);
			config.append("\"");
		}
		config.append("]\n");
		config.append("    ");
		config.append(name);
		config.append(" = ");
		config.append(lfsUrl);
		writeTrashFile(Constants.DOT_LFS_CONFIG
	}

	private void writeInvalidLfsConfig() throws IOException {
		writeTrashFile(Constants.DOT_LFS_CONFIG
				"{lfs]\n    url = " + LFS_SERVER_URL1);
	}

	private void checkLfsUrl(String lfsUrl) throws IOException {
		HttpConnection lfsServerConn;
		lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST

		assertEquals(lfsUrl + Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
	}
}
