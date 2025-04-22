
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.RefTranslator;
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
		rc.addFetchRefSpec(
				new RefSpec("refs/heads/master:refs/remotes/origin/master"));
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
	public void testRefSetup() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		r.setUpRefs();
		Set<String> keys = db.getRefDatabase()
				.getRefs("refs/pubsub/origin/").keySet();
		assertEquals(13
		assertNotNull(db.getRef("refs/pubsub/origin/heads/master"));
		assertNotNull(db.getRef("refs/pubsub/origin/tags/A"));
		assertNotNull(db.getRef("refs/pubsub/origin/tags/B"));
	}

	@Test
	public void testRefRemove() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		r.setUpRefs();
		List<RefSpec> newSpecs = new ArrayList<RefSpec>();
		r.setSubscribeSpecs(newSpecs);
		r.setUpRefs();
		assertNull(db.getRef("refs/pubsub/origin/heads/master"));
		assertNotNull(db.getRef("refs/pubsub/origin/tags/A"));
		assertNotNull(db.getRef("refs/pubsub/origin/tags/B"));
	}

	@Test
	public void testRefFilter() throws Exception {
		SubscribedRepository r = new SubscribedRepository(publisherConfig
				.getSubscriber("origin"
		r.setUpRefs();
		Set<String> refs = r.getRemoteRefs().keySet();
		assertEquals(13
		assertTrue(refs.contains("refs/heads/master"));
		assertTrue(refs.contains("refs/tags/A"));

		Set<String> pubsubRefs = r.getPubSubRefs().keySet();
		assertEquals(13
		assertTrue(pubsubRefs.contains("refs/heads/master"));
		assertTrue(pubsubRefs.contains("refs/tags/A"));
	}

	@Test
	public void testRefTranslate() throws Exception {
		FileBasedConfig fc = db.getConfig();
		fc.load();
		RemoteConfig rc = new RemoteConfig(fc

		assertEquals("refs/pubsub/self/heads/master"
				RefTranslator.getPubSubRefFromTracking(
						rc
		assertEquals("refs/pubsub/self/heads/master"
				RefTranslator.getPubSubRefFromRemote(
						"self"
		assertEquals("refs/remotes/origin/master"
				RefTranslator.getTrackingRefFromPubSub(
						rc
		assertEquals("refs/heads/master"
				RefTranslator.getRemoteRefFromTracking(
						rc
		assertEquals("refs/remotes/origin/master"
				RefTranslator.getTrackingRefFromRemote(
						rc
		assertEquals("refs/heads/master"
				RefTranslator.getRemoteRefFromTracking(
						rc
		assertEquals("refs/heads/master"
				RefTranslator.getRemoteRefFromPubSub(
						"self"
	}
}
