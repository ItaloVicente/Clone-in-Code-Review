
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
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
		FileBasedConfig fc = db.getConfig();
		fc.load();
		RemoteConfig rc = new RemoteConfig(fc
		rc.addURI(new URIish(db.getWorkTree().getAbsolutePath()));
		rc.update(fc);
		fc.save();
		new Git(db).fetch().setRemote("self").call();

		String directory = db.getDirectory().getAbsolutePath();
		fc = db.getConfig();
		fc.load();
		rc = new RemoteConfig(fc
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
		r.setUpRefs();
		assertTrue(null != db.getRef("refs/pubsub/origin/heads/master"));
		assertTrue(null != db.getRef("refs/pubsub/origin/tags/A"));
		assertTrue(null != db.getRef("refs/pubsub/origin/tags/B"));
	}

	@Test
	public void testRefFilter() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		r.setUpRefs();
		Map<String
		assertTrue(refs.containsKey("refs/heads/master"));
		assertTrue(refs.containsKey("refs/tags/A"));

		Map<String
		assertTrue(pubsubRefs.containsKey("refs/heads/master"));
		assertTrue(pubsubRefs.containsKey("refs/tags/A"));
	}

	@Test
	public void testRefTranslate() throws Exception {
		assertEquals("refs/pubsub/origin/heads/master"
				SubscribedRepository.getPubSubRefFromRemote(
						"origin"
		assertEquals("refs/pubsub/origin/heads/master"
				SubscribedRepository.getPubSubRefFromLocal(
						"origin"
		assertEquals("refs/remotes/origin/master"
				SubscribedRepository.getRemoteRefFromPubSub(
						"origin"
		assertEquals("refs/heads/master"
				SubscribedRepository.getLocalRefFromRemote(
						"origin"
		assertEquals("refs/remotes/origin/master"
				SubscribedRepository.getRemoteRefFromLocal(
						"origin"
		assertEquals("refs/heads/master"
				SubscribedRepository.getLocalRefFromRemote(
						"origin"
		assertEquals("refs/heads/master"
				SubscribedRepository.getLocalRefFromPubSub(
						"origin"
	}
}
