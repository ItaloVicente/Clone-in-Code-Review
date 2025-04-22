package org.eclipse.jgit.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Test;

public class RemoteAddCommandTest extends AbstractRemoteCommandTest {

	@Test
	public void testAdd() throws Exception {
		Repository remoteRepository = createWorkRepository();
		URIish uri = new URIish(
				remoteRepository.getDirectory().toURI().toURL());

		RemoteAddCommand cmd = Git.wrap(db).remoteAdd();
		cmd.setName(REMOTE_NAME);
		cmd.setUri(uri);
		RemoteConfig remote = cmd.call();

		assertEquals(REMOTE_NAME
		assertArrayEquals(new URIish[] { uri }
		assertEquals(1
		assertEquals(
				remote.getFetchRefSpecs().get(0).toString());

		assertRemoteConfigEquals(remote
				new RemoteConfig(db.getConfig()
	}

}
