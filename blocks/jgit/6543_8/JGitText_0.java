
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubscribeConnectionTest extends SampleDataRepositoryTestCase {
	class TestTransport extends Transport implements PackTransport {
		protected TestTransport(URIish uri) {
			super(uri);
		}

		@Override
		public FetchConnection openFetch()
				throws NotSupportedException
			return null;
		}

		@Override
		public PushConnection openPush()
				throws NotSupportedException
			return null;
		}

		@Override
		public SubscribeConnection openSubscribe()
				throws NotSupportedException
			BasePackSubscribeConnection c = new BasePackSubscribeConnection(
					this) {
				@Override
				public void doSubscribeAdvertisment(
						Subscriber s) throws IOException {
				}
				
				@Override
				public void doSubscribe(Subscriber s
						String
						List<SubscribeCommand>> subscribeCommands
						ProgressMonitor monitor)
						throws InterruptedException
						IOException {
					init(new ByteArrayInputStream(publisherOut.toByteArray())
							testOut);
					super.doSubscribe(s
				}
			};
			return c;
		}

		@Override
		public void close() {
		}
	}

	Subscriber subscriber;

	TransportProtocol newProtocol;

	ByteArrayOutputStream testOut;

	ByteArrayInputStream testIn;

	PacketLineIn testLineIn;

	ByteArrayOutputStream progressOut;

	ByteArrayInputStream progressIn;

	ByteArrayOutputStream publisherOut;

	PacketLineOut publisherLineOut;

	ProgressMonitor progressMonitor;

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

		publisherOut = new ByteArrayOutputStream();
		publisherLineOut = new PacketLineOut(publisherOut);
		testOut = new ByteArrayOutputStream();
		progressOut = new ByteArrayOutputStream();
		progressMonitor = new TextProgressMonitor(new OutputStreamWriter(
				progressOut));

		final TestTransport transport = new TestTransport(publisherConfig
				.getUri());

		newProtocol = new TransportProtocol() {
			@Override
			public String getName() {
				return "http-test";
			}

			@Override
			public boolean canHandle(
					URIish uri
				return true;
			}

			@Override
			public Transport open(URIish uri)
					throws NotSupportedException
				return transport;
			}

			@Override
			public Transport open(
					URIish uri
					throws NotSupportedException
				return transport;
			}
		};

		Transport.register(newProtocol);
		subscriber = new Subscriber(publisherConfig.getUri());
		subscriber.setTimeout(1000);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		Transport.unregister(newProtocol);
		super.tearDown();
	}

	@Test
	public void testBadRestart() throws Exception {
		subscriber.setRestartToken("badtoken");
		subscriber.setLastPackNumber("0");
		publisherLineOut.writeString("reconnect");
		try {
			executeSubscribe();
		} catch (TransportException e) {
			assertEquals("Invalid restart token"
		}

		assertEquals("restart badtoken"
		assertEquals("last-pack 0"
		assertEquals(PacketLineIn.END
		assertEquals("repository testrepository"
		assertEquals("want refs/heads/master"
		String line;
		while ((line = testLineIn.readString()) != PacketLineIn.END) {
			assertTrue(line.startsWith("have "));
		}
		assertEquals("done"
	}

	@Test
	public void testCleanStart() throws Exception {
		publisherLineOut.writeString("restart-token server-token");
		publisherLineOut.writeString("heartbeat-interval 10");
		publisherLineOut.end();
		writeHeartbeat();
		try {
			executeSubscribe();
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
		}
		assertEquals("server-token"
		assertTrue(null == subscriber.getLastPackNumber());
	}

	@Test
	public void testChangeToken() throws Exception {
		publisherLineOut.writeString("restart-token server-token");
		publisherLineOut.writeString("heartbeat-interval 10");
		publisherLineOut.end();
		writeHeartbeat();
		publisherLineOut.writeString("change-restart-token new-server-token");
		try {
			executeSubscribe();
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
		}
		assertEquals("new-server-token"
		assertTrue(null == subscriber.getLastPackNumber());
	}

	@Test
	public void testSingleUpdate() throws Exception {
		publisherLineOut.writeString("restart-token server-token");
		publisherLineOut.writeString("heartbeat-interval 10");
		publisherLineOut.end();
		writeHeartbeat();
		ObjectId id = db.getRef("refs/heads/master").getLeaf().getObjectId();
		writeUpdate(ObjectId.zeroId()

		try {
			executeSubscribe();
		} catch (InterruptedIOException e) {
		}
		String tagId = db.getRef("refs/pubsub/origin/tags/pubsubtest")
				.getLeaf().getObjectId().name();
		assertEquals(id.name()
		assertEquals("1234"
	}

	@Test
	public void testMultiUpdate() throws Exception {
		publisherLineOut.writeString("restart-token server-token");
		publisherLineOut.writeString("heartbeat-interval 10");
		publisherLineOut.end();
		ObjectId id1 = db.getRef("refs/heads/master").getLeaf().getObjectId();
		writeUpdate(ObjectId.zeroId()

		writeHeartbeat();
		writeHeartbeat();

		ObjectId id2 = db.getRef("refs/heads/b").getLeaf().getObjectId();
		writeUpdate(ObjectId.zeroId()

		try {
			executeSubscribe();
		} catch (InterruptedIOException e) {
		}
		String pubsubId1 = db.getRef("refs/pubsub/origin/heads/pubsub1")
				.getLeaf().getObjectId().name();
		assertEquals(id1.name()
		String pubsubId2 = db.getRef("refs/pubsub/origin/heads/pubsub2")
				.getLeaf().getObjectId().name();
		assertEquals(id2.name()
		assertEquals("5678"
	}

	@Test
	public void testBadUpdate() throws Exception {
		publisherLineOut.writeString("restart-token server-token");
		publisherLineOut.writeString("heartbeat-interval 10");
		publisherLineOut.end();
		writeHeartbeat();
		ObjectId id = db.getRef("refs/heads/master").getLeaf().getObjectId();
		writeUpdate(ObjectId.zeroId()

		try {
			executeSubscribe();
			fail("Should have failed creating refs/heads/master");
		} catch (TransportException e) {
		} catch (InterruptedIOException e) {
		}
	}

	private void writeHeartbeat() throws IOException {
		publisherLineOut.writeString("heartbeat");
	}

	private void writeUpdate(
			ObjectId from
			throws IOException {
		publisherLineOut.writeString("update testrepository");
		publisherLineOut.writeString(
				from.name() + " " + to.name() + " " + refName);
		publisherLineOut.end();
		PackWriter pw = new PackWriter(db);
		pw.setThin(true);
		pw.preparePack(NullProgressMonitor.INSTANCE
				new HashSet<ObjectId>(Collections.nCopies(1
				new HashSet<ObjectId>(Collections.nCopies(1
		pw.writePack(NullProgressMonitor.INSTANCE
				publisherOut);
		pw.release();
		publisherLineOut.writeString("sequence " + sequence);
	}

	private void executeSubscribe() throws Exception {
		try {
			subscriber.subscribe(
					subscriber.sync(publisherConfig)
		} catch (EOFException e) {
		} finally {
			testIn = new ByteArrayInputStream(testOut.toByteArray());
			testLineIn = new PacketLineIn(testIn);
			progressIn = new ByteArrayInputStream(progressOut.toByteArray());
		}
	}
}
