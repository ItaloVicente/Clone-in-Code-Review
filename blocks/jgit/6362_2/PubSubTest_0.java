
package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.PubSubConfig;
import org.eclipse.jgit.transport.PubSubConfig.Publisher;
import org.eclipse.jgit.transport.PubSubConfig.Subscriber;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PubSubTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		SubscribeDaemon.GLOBAL_PUBSUB_FILE = ".gitpubsubtest";
		new Git(db).commit().setMessage("initial commit").call();
	}

	@Test
	public void testSubscribeOutput() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.addURI(
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git subscribe origin");
		assertArrayEquals("expected successful subscribe"
				new String[] { MessageFormat.format(
						CLIText.get().didSubscribe
	}

	@Test
	public void testNoUriSubscribeOutput() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git subscribe origin");
		assertArrayEquals("expected failed subscribe"
				new String[] { MessageFormat.format(
						CLIText.get().noRemoteUriSubscribe
	}

	@Test
	public void testConfigWrite() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.addURI(
		remoteConfig.addFetchRefSpec(
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git subscribe origin");
		assertArrayEquals("expected successful subscribe"
				new String[] { MessageFormat.format(
						CLIText.get().didSubscribe
		PubSubConfig config = SubscribeDaemon.getConfig();
		assertEquals(1
		assertTrue(pub != null);
		assertEquals(
		assertEquals(1
		Subscriber sub = pub.getSubscribers().iterator().next();
		assertEquals("android"
		assertEquals("origin"
		assertEquals(1
		RefSpec spec = sub.getSubscribeSpecs().get(0);
	}

	@Test
	public void testUnsubscribeOutput() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.addURI(
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git subscribe origin");
		output = execute("git unsubscribe origin");
		assertArrayEquals("expected successful unsubscribe"
				new String[] { MessageFormat.format(
						CLIText.get().didUnsubscribe
	}

	@Test
	public void testNoUriUnsubscribeOutput() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git unsubscribe origin");
		assertArrayEquals("expected unsuccessful unsubscribe"
				new String[] { MessageFormat.format(
						CLIText.get().noRemoteUriUnsubscribe
	}

	@Test
	public void testNotFoundUnsubscribeOutput() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.addURI(
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git unsubscribe origin");
		assertArrayEquals("expected unsuccessful unsubscribe"
				new String[] { MessageFormat.format(
						CLIText.get().subscriptionDoesNotExist
	}

	@Test
	public void testUnsubscribeConfig() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		remoteConfig.addURI(
		remoteConfig.addFetchRefSpec(
		remoteConfig.update(dbconfig);
		dbconfig.save();

		String[] output = execute("git subscribe origin");
		output = execute("git unsubscribe origin");
		assertArrayEquals("expected successful unsubscribe"
				new String[] { MessageFormat.format(
						CLIText.get().didUnsubscribe
		PubSubConfig config = SubscribeDaemon.getConfig();
		assertEquals(0
	}

	@After
	@Override
	public void tearDown() throws Exception {
		SubscribeDaemon.getConfigFile().delete();
		super.tearDown();
	}
}
