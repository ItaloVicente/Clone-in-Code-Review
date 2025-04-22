package org.eclipse.jgit.api;

import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.transport.RemoteConfig;
import org.junit.Test;

public class RemoteDeleteCommandTest extends AbstractRemoteCommandTest {

	@Test
	public void testDelete() throws Exception {
		RemoteConfig remoteConfig = setupRemote();

		RemoteRemoveCommand cmd = Git.wrap(db).remoteRemove();
		cmd.setRemoteName(REMOTE_NAME);
		RemoteConfig remote = cmd.call();

		assertRemoteConfigEquals(remoteConfig
		assertTrue(RemoteConfig.getAllRemoteConfigs(db.getConfig()).isEmpty());
	}

}
