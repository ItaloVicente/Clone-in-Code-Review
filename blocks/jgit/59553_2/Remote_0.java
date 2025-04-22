package org.eclipse.jgit.pgm;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class RemoteTest extends CLIRepositoryTestCase {

	private StoredConfig config;

	private RemoteConfig remote;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Repository remoteRepository = createWorkRepository();

		config = db.getConfig();
		remote = new RemoteConfig(config
		remote.addFetchRefSpec(
		URIish uri = new URIish(
				remoteRepository.getDirectory().toURI().toURL());
		remote.addURI(uri);
		remote.update(config);
		config.save();

		Git.wrap(remoteRepository).commit().setMessage("initial commit").call();
	}

	@Test
	public void testList() throws Exception {
		assertArrayEquals(new String[] { remote.getName()
				execute("git remote"));
	}

	@Test
	public void testVerboseList() throws Exception {
		assertArrayEquals(
				new String[] {
						String.format("%s\t%s (fetch)"
								remote.getURIs().get(0))
						String.format("%s\t%s (push)"
								remote.getURIs().get(0))
						"" }
				execute("git remote -v"));
	}

	@Test
	public void testAdd() throws Exception {
		assertArrayEquals(new String[] { "" }

		List<RemoteConfig> remotes = RemoteConfig.getAllRemoteConfigs(config);
		assertEquals(2
		assertEquals("second"
		assertEquals("test"
	}

	@Test
	public void testRemove() throws Exception {
		assertArrayEquals(new String[] { "" }
				execute("git remote remove test"));

		assertTrue(RemoteConfig.getAllRemoteConfigs(config).isEmpty());
	}

	@Test
	public void testSetUrl() throws Exception {
		assertArrayEquals(new String[] { "" }

		RemoteConfig result = new RemoteConfig(config
		assertEquals("test"
				result.getURIs().toArray());
		assertTrue(result.getPushURIs().isEmpty());
	}

	@Test
	public void testSetUrlPush() throws Exception {
		assertArrayEquals(new String[] { "" }

		RemoteConfig result = new RemoteConfig(config
		assertEquals("test"
		assertEquals(remote.getURIs()
				result.getPushURIs().toArray());
	}

	@Test
	public void testUpdate() throws Exception {
		assertArrayEquals(new String[] {
				"From " + remote.getURIs().get(0).toString()
				" * [new branch]      master     -> test/master"
				execute("git remote update test"));
	}

}
