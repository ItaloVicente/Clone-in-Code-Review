package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class RenameRemoteCommandTest extends RepositoryTestCase {

	private static final String REMOTE_NAME = Constants.DEFAULT_REMOTE_NAME;


	private Git git;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		final StoredConfig config = db.getConfig();
		final RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addFetchRefSpec(getFetchRefSpec(REMOTE_NAME));
		remoteConfig.update(config);
		config.save();
	}

	@Test
	public void renameRemoteNonExistantRemote() throws Exception {
		final String nonExistantRemoteName = "bad";

		try {
			git.remoteRename().setOldName(nonExistantRemoteName)
					.setNewName(nonExistantRemoteName).call();
			fail("Rename Remote with non-existant remote name should fail");
		} catch (RefNotFoundException e) {
		}
	}

	@Test
	public void renameRemoteAlreadyExistsRemote() throws Exception {
		try {
			git.remoteRename().setOldName(REMOTE_NAME).setNewName(REMOTE_NAME)
					.call();
			fail("Rename Remote with existing ref name should fail");
		} catch (RefAlreadyExistsException e) {
		}
	}

	@Test
	public void renameRemoteSuccess() throws Exception {
		final String newRemoteName = "newName";

		final RemoteConfig newRemote = git.remoteRename()
				.setOldName(REMOTE_NAME).setNewName(newRemoteName).call();

		assertNotNull(newRemote);

		final StoredConfig config = db.getConfig();

		assertTrue(!config.getNames(ConfigConstants.CONFIG_REMOTE_SECTION
				newRemoteName).isEmpty());
		assertTrue(config.getNames(ConfigConstants.CONFIG_REMOTE_SECTION
				REMOTE_NAME).isEmpty());

		final List<RefSpec> fetchRefs = newRemote.getFetchRefSpecs();
		assertTrue(fetchRefs.size() == 1);
		assertEquals(fetchRefs.get(0)

	}

	private RefSpec getFetchRefSpec(String refName) {
		return new RefSpec("+" + Constants.R_HEADS + "*:" + Constants.R_REMOTES
				+ refName + RefSpec.WILDCARD_SUFFIX);
	}
}
