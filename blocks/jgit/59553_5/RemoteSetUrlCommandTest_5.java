package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.jgit.transport.RemoteConfig;
import org.junit.Test;

public class RemoteListCommandTest extends AbstractRemoteCommandTest {

	@Test
	public void testList() throws Exception {
		RemoteConfig remoteConfig = setupRemote();

		List<RemoteConfig> remotes = Git.wrap(db).remoteList().call();

		assertEquals(1
		assertRemoteConfigEquals(remoteConfig
	}

}
