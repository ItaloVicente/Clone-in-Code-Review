package org.eclipse.jgit.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Test;

public class RemoteSetUrlCommandTest extends AbstractRemoteCommandTest {

	@Test
	public void testSetUrl() throws Exception {
		setupRemote();

		RemoteSetUrlCommand cmd = Git.wrap(db).remoteSetUrl();
		cmd.setName(REMOTE_NAME);
		cmd.setUri(newUri);
		RemoteConfig remote = cmd.call();

		assertEquals(REMOTE_NAME
		assertArrayEquals(new URIish[] { newUri }

		assertRemoteConfigEquals(remote
				new RemoteConfig(db.getConfig()
	}

	@Test
	public void testSetPushUrl() throws Exception {
		RemoteConfig remoteConfig = setupRemote();

		RemoteSetUrlCommand cmd = Git.wrap(db).remoteSetUrl();
		cmd.setName(REMOTE_NAME);
		cmd.setUri(newUri);
		cmd.setPush(true);
		RemoteConfig remote = cmd.call();

		assertEquals(REMOTE_NAME
		assertEquals(remoteConfig.getURIs()
		assertArrayEquals(new URIish[] { newUri }
				remote.getPushURIs().toArray());

		assertRemoteConfigEquals(remote
				new RemoteConfig(db.getConfig()
	}

}
