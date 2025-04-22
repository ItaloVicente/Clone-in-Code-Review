
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.transport.PublisherSession.SessionGenerator;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Test;

public class PublisherTest extends SampleDataRepositoryTestCase {
	ObjectId OBJECT_ID1 = ObjectId.fromString(
			"ac7e7e44c1885efb472ad54a78327d66bfc4ecef");

	ObjectId OBJECT_ID2 = ObjectId.fromString(
			"2c349335b7f797072cf729c4f3bb0914ecb6dec9");

	ObjectId OBJECT_HEADS_A = ObjectId.fromString(
			"6db9c2ebf75590eef973081736730a9ea169a0c4");

	String refName = "refs/heads/foobar";

	PublisherBuffer buffer;

	PublisherPackFactory packFactory;

	Publisher publisher;

	volatile CountDownLatch connectLatch;

	private void setUpPublisher(int expectedClients) {
		connectLatch = new CountDownLatch(2 * expectedClients);
		buffer = new PublisherBuffer(64 * 1024);
		packFactory = new PublisherPackFactory(buffer);
		packFactory.setSliceSize(1024);
		packFactory.setSliceMemoryThreshold(3);
		publisher = new Publisher(new PublisherReverseResolver()
			new RepositoryResolver<PublisherClient>() {
			public Repository open(PublisherClient req
					throws RepositoryNotFoundException
					ServiceNotAuthorizedException
					ServiceMayNotContinueException {
				if (!name.equals("testrepository"))
					fail("Invalid open database request");
				return db;
			}
		}
			private AtomicInteger sid = new AtomicInteger();

			public String generate() {
				return "fixed-session-id-" + sid.incrementAndGet();
			}
		}) {
			@Override
			public synchronized PublisherSession connectClient(
					PublisherClient c) {
				PublisherSession s = super.connectClient(c);
				connectLatch.countDown();
				return s;
			}
		};
		buffer.startGC();
	}

	@Test
	public void testSubscribe() throws Exception {
		setUpPublisher(1);
		PublisherClientTest pc = new PublisherClientTest(0) {
			@Override
			protected void writeSubscribePacket(PacketLineOut pckLineOut)
					throws IOException {
				pckLineOut.writeString("restart " + state.getKey());
				pckLineOut.end();
				pckLineOut.writeString("repository testrepository");
				pckLineOut.writeString("want " + refName);
				pckLineOut.writeString(
						"have " + OBJECT_ID1.getName() + " " + refName);
				pckLineOut.end();
				pckLineOut.writeString("done");
			}
		};

		connectLatch.await();

		assertEquals(pc.getPublisherState().getKey()

		Map<String
		assertTrue(cmds.containsKey("testrepository"));
		List<SubscribeCommand> subCmds = cmds.get("testrepository");
		assertEquals(1
		SubscribeCommand subCmd = subCmds.get(0);
		assertEquals(Command.SUBSCRIBE
		assertEquals(refName

		Map<String
		assertTrue(s.containsKey("testrepository"));
		Map<String
		assertEquals(1
		assertTrue(specs.containsKey(refName));
		assertEquals(OBJECT_ID1
	}

	@Test
	public void testSimplePackStream() throws Exception {
		runPackStreamTest(1
	}

	@Test
	public void testLargePackStream() throws Exception {
		runPackStreamTest(1
	}

	@Test
	public void testManyClientsPackStream() throws Exception {
		runPackStreamTest(2000
	}

	@Test
	public void testMultiplePacks() throws Exception {
		runPackStreamTest(1
	}

	@Test
	public void testEverythingPackStream() throws Exception {
		runPackStreamTest(25
	}

	@Test
	public void testInitialUpdate() throws Exception {
		setUpPublisher(1);
		PublisherClientTest c = new PublisherClientTest(0) {
			@Override
			protected void writeSubscribePacket(PacketLineOut pckLineOut)
					throws IOException {
				pckLineOut.writeString("restart " + state.getKey());
				pckLineOut.end();
				pckLineOut.writeString("repository testrepository");
				pckLineOut.writeString("want refs/heads/a");
				pckLineOut.writeString("have " +
						ObjectId.zeroId().getName() + " refs/heads/a");
				pckLineOut.end();
				pckLineOut.writeString("done");
			}
		};

		connectLatch.await();

		Thread.sleep(2000);

		byte out[] = c.getByteOutputStream().toByteArray();
		InputStream rawIn = new ByteArrayInputStream(out);
		PacketLineIn in = new PacketLineIn(rawIn);
		String line;
		String parts[];
		line = in.readString();
		parts = line.split(" "
		assertEquals("restart-token"
		assertEquals(c.getPublisherState().getKey()
		line = in.readString();
		parts = line.split(" "
		assertEquals("heartbeat-interval"
		assertEquals(PacketLineIn.END
		int updatesLeft = 1;
		while (updatesLeft > 0) {
			line = in.readString();
			parts = line.split(" "
			if (parts[0].equals("heartbeat"))
				continue;
			assertEquals("update"
			assertEquals("testrepository"
			while ((line = in.readString()) != PacketLineIn.END) {
				parts = line.split(" "
				assertEquals(ObjectId.zeroId().name()
				assertEquals(OBJECT_HEADS_A.name()
				assertEquals("refs/heads/a"
			}
			updatesLeft--;
		}
	}

	private void runPackStreamTest(int clients
			throws Exception {
		setUpPublisher(clients);
		List<ReceiveCommand> refUpdates = Collections.nCopies(update_size
				new ReceiveCommand(ObjectId.zeroId()

		List<PublisherSession> states = new ArrayList<
				PublisherSession>();
		List<PublisherClientTest> testClients = new ArrayList<
				PublisherTest.PublisherClientTest>();

		for (int i = 0; i < clients; i++) {
			PublisherClientTest t = new PublisherClientTest(i * 5) {
				@Override
				protected void writeSubscribePacket(PacketLineOut pckLineOut)
						throws IOException {
					pckLineOut.writeString("restart " + state.getKey());
					pckLineOut.end();
					pckLineOut.writeString("repository testrepository");
					pckLineOut.writeString("want " + refName);
					pckLineOut.writeString(
							"have " + OBJECT_ID1.getName() + " " + refName);
					pckLineOut.end();
					pckLineOut.writeString("done");
				}
			};
			testClients.add(t);
			states.add(t.getPublisherState());
		}

		connectLatch.await();

		for (int i = 0; i < updates; i++) {
			Thread.sleep(500);
			publisher.onPush(db
		}

		Thread.sleep(2000);

		for (PublisherClientTest c : testClients) {
			try {
				byte out[] = c.getByteOutputStream().toByteArray();
				InputStream rawIn = new ByteArrayInputStream(out);
				PacketLineIn in = new PacketLineIn(rawIn);
				String line;
				String parts[];
				line = in.readString();
				parts = line.split(" "
				assertEquals("restart-token"
				assertEquals(c.getPublisherState().getKey()
				line = in.readString();
				parts = line.split(" "
				assertEquals("heartbeat-interval"
				assertEquals(PacketLineIn.END
				int updatesLeft = updates;
				while (updatesLeft > 0) {
					line = in.readString();
					if (line.startsWith("heartbeat "))
						continue;
					assertEquals("update testrepository"

					ReceivePack rp = new ReceivePack(db);
					rp.setExpectDataAfterPackFooter(true);
					rp.setAllowCreates(true);
					rp.setBiDirectionalPipe(false);
					rp.receive(rawIn
					rp.unlockPack();
					rp.close();

					line = in.readString();
					parts = line.split(" "
					assertEquals("sequence"
					updatesLeft--;
				}
			} catch (Exception e) {
				e.initCause(new Throwable(c.getPublisherState().getKey()));
				throw e;
			}
		}
	}

	abstract class PublisherClientTest extends PublisherClient {
		Thread subscribeThread;

		PublisherSession state;

		ByteArrayOutputStream output;

		public PublisherClientTest(final int delayWrite) {
			super(publisher);
			state = publisher.connectClient(this);
			state.disconnect();

			ByteArrayOutputStream fillIn = new ByteArrayOutputStream();
			PacketLineOut pckLineOut = new PacketLineOut(fillIn);
			try {
				writeSubscribePacket(pckLineOut);
				pckLineOut.flush();
			} catch (IOException e) {
				fail(e.toString());
			}
			byte buf[] = fillIn.toByteArray();

			final InputStream myIn = new ByteArrayInputStream(buf);
			output = new ByteArrayOutputStream() {
				@Override
				public synchronized void write(byte[] b
					try {
						Thread.sleep(delayWrite);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					super.write(b
				}
			};
			final PublisherClient me = this;
			subscribeThread = new Thread() {
				@Override
				public void run() {
					try {
						me.subscribe(myIn
					} catch (IOException e) {
						fail(e.toString());
					}
				}
			};

			subscribeThread.start();
		}

		@Override
		public synchronized void close() {
		}

		protected abstract void writeSubscribePacket(PacketLineOut pckLineOut)
				throws IOException;

		public Thread getSubscribeThread() {
			return subscribeThread;
		}

		public PublisherSession getPublisherState() {
			return state;
		}

		public ByteArrayOutputStream getByteOutputStream() {
			return output;
		}
	}
}
