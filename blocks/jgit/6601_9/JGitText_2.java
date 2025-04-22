
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.junit.Before;
import org.junit.Test;

public class SubscribeProcessTest extends SampleDataRepositoryTestCase {
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
	public void testSync() throws Exception {
		SubscribeProcess s = new SubscribeProcess(
				publisherConfig.getUri()
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
		SubscribeProcess s = new SubscribeProcess(
				publisherConfig.getUri()

		s.sync(publisherConfig);

		String directory = db.getDirectory().getAbsolutePath();
		FileBasedConfig fc = db.getConfig();
		fc.load();
		RemoteConfig rc = new RemoteConfig(fc
		rc.addFetchRefSpec(
				new RefSpec("refs/heads/branch:refs/remotes/origin/branch"));
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
}
