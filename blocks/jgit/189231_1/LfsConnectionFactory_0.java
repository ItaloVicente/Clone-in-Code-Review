package org.eclipse.jgit.lfs.internal;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;
import org.junit.Before;
import org.junit.Test;

public class LfsConnectionFactoryTest extends RepositoryTestCase {



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

	private void writeLfsConfig() throws IOException {
		writeTrashFile(Constants.DOT_LFS_CONFIG
				"[lfs]\n    url = " + LFS_SERVER_URL);
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
		HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST

		assertEquals(LFS_SERVER_URL + Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
	}


	@Test
	public void checkGetLfsConnection_Index() throws Exception {
		writeLfsConfig();
		git.add().addFilepattern(Constants.DOT_LFS_CONFIG).call();
		deleteTrashFile(Constants.DOT_LFS_CONFIG);
		HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST

		assertEquals(LFS_SERVER_URL + Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
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

			HttpConnection lfsServerConn = LfsConnectionFactory
					.getLfsConnection(bareRepoDb
							Protocol.OPERATION_DOWNLOAD);

			assertEquals(LFS_SERVER_URL + Protocol.OBJECTS_LFS_ENDPOINT
					lfsServerConn.getURL().toString());
		}
	}
}
