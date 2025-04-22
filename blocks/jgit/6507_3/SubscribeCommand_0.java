
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.junit.Before;
import org.junit.Test;

public class SubscribeTest extends SampleDataRepositoryTestCase {

	PubSubConfig.Publisher publisherConfig;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		String directory = db.getDirectory().getAbsolutePath();
		FileBasedConfig fc = db.getConfig();
		fc.load();
		RemoteConfig rc = new RemoteConfig(fc
		rc.addFetchRefSpec(new RefSpec("refs/heads/master"));
		rc.update(fc);
		fc.save();
		PubSubConfig.Subscriber subscribeConfig = new PubSubConfig.Subscriber(
				publisherConfig
		publisherConfig.addSubscriber(subscribeConfig);
	}

	@Test
	public void testSetup() throws Exception {
		Collection<PubSubConfig.Subscriber> subscribers = publisherConfig
				.getSubscribers();
		assertEquals(1
		PubSubConfig.Subscriber s1 = subscribers.iterator().next();
		assertEquals("origin"
		assertEquals(publisherConfig
		assertEquals(db.getDirectory().getAbsolutePath()
		assertEquals("testrepository"
	}

	@Test
	public void testSync() throws Exception {
		Subscriber s = new Subscriber(publisherConfig.getUri());
		Map<String
		assertTrue(cmds.containsKey("testrepository"));
		assertEquals(2

		List<SubscribeCommand> expected = new ArrayList<SubscribeCommand>();
		expected.add(
				new SubscribeCommand(Command.SUBSCRIBE
		expected.add(new SubscribeCommand(Command.SUBSCRIBE
		expected.removeAll(cmds.get("testrepository"));
		assertEquals(0
	}

	@Test
	public void testResync() throws Exception {
		Subscriber s = new Subscriber(publisherConfig.getUri());

		s.sync(publisherConfig);

		String directory = db.getDirectory().getAbsolutePath();
		FileBasedConfig fc = db.getConfig();
		fc.load();
		RemoteConfig rc = new RemoteConfig(fc
		rc.addFetchRefSpec(new RefSpec("refs/heads/branch"));
		rc.update(fc);
		fc.save();
		PubSubConfig.Subscriber subscribeConfig = new PubSubConfig.Subscriber(
				publisherConfig
		publisherConfig.addSubscriber(subscribeConfig);

		Map<String

		assertTrue(after.containsKey("testrepository"));
		assertEquals(2
		List<SubscribeCommand> expected = new ArrayList<SubscribeCommand>();
		expected.add(new SubscribeCommand(Command.UNSUBSCRIBE
		expected.add(
				new SubscribeCommand(Command.SUBSCRIBE
		expected.removeAll(after.get("testrepository"));
		assertEquals(0
	}

	@Test
	public void testRefSetup() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		assertTrue(null != db.getRef(SubscribedRepository.PUBSUB_REF_PREFIX
				+ "origin/heads/master"));
		assertTrue(null != db.getRef(
				SubscribedRepository.PUBSUB_REF_PREFIX + "origin/tags/A"));
		assertTrue(null != db.getRef(
				SubscribedRepository.PUBSUB_REF_PREFIX + "origin/tags/B"));

		Map<String
		assertTrue(pubsubRefs.containsKey(SubscribedRepository.PUBSUB_REF_PREFIX
				+ "origin/heads/master"));
		assertTrue(pubsubRefs.containsKey(SubscribedRepository.PUBSUB_REF_PREFIX
				+ "origin/tags/A"));
	}

	@Test
	public void testRefFilter() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		Map<String
		assertTrue(refs.containsKey("refs/heads/master"));
		assertFalse(refs.containsKey("refs/heads/foo"));
	}
}
