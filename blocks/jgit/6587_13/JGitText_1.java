
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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

	PublisherMemoryPool buffer;

	PublisherPackFactory packFactory;

	Publisher publisher;

	CountDownLatch connectLatch;

	private void setUpPublisher(int expectedClients) {
		connectLatch = new CountDownLatch(expectedClients);
		buffer = new PublisherMemoryPool(64 * 1024);
		packFactory = new PublisherPackFactory(buffer);
		packFactory.setSliceSize(1024);
		packFactory.setSliceMemoryThreshold(3);
		publisher = new Publisher(new PublisherReverseResolver()
			packFactory
			private AtomicInteger sid = new AtomicInteger();

			public String generate() {
				return "fixed-session-id-" + sid.incrementAndGet();
			}
		}) {
			@Override
			public synchronized PublisherSession newSession() {
				PublisherSession s = super.newSession();
				connectLatch.countDown();
				return s;
			}
		};
	}

	@Test
	public void testSubscribe() throws Exception {
		setUpPublisher(1);
		PublisherClientTest pc = new PublisherClientTest(0
			@Override
			protected void writeSubscribePacket(PacketLineOut pckLineOut)
					throws IOException {
				pckLineOut.writeString("subscribe");
				pckLineOut.end();
				pckLineOut.writeString("repository testrepository");
				pckLineOut.writeString("want " + refName);
				pckLineOut.writeString(
						"have " + OBJECT_ID1.getName() + " " + refName);
				pckLineOut.end();
				pckLineOut.writeString("done");
			}

			@Override
			public Repository openRepository(String name)
					throws RepositoryNotFoundException
					ServiceMayNotContinueException
					ServiceNotAuthorizedException
				if (!name.equals("testrepository"))
					fail("Invalid open database request");
				return db;
			}
		};

		connectLatch.await();

		assertEquals(pc.getSession().getKey()

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
		PublisherClientTest c = new PublisherClientTest(0
			@Override
			protected void writeSubscribePacket(PacketLineOut pckLineOut)
					throws IOException {
				pckLineOut.writeString("subscribe");
				pckLineOut.end();
				pckLineOut.writeString("repository testrepository");
				pckLineOut.writeString("want refs/heads/a");
				pckLineOut.writeString("have " +
						ObjectId.zeroId().getName() + " refs/heads/a");
				pckLineOut.end();
				pckLineOut.writeString("done");
			}

			@Override
			public Repository openRepository(String name)
					throws RepositoryNotFoundException
					ServiceMayNotContinueException
					ServiceNotAuthorizedException
				if (!name.equals("testrepository"))
					fail("Invalid open database request");
				return db;
			}
		};

		connectLatch.await();

		InputStream rawIn = new BufferedInputStream(c
				.getPipedInputStream()
		PacketLineIn in = new PacketLineIn(rawIn);
		String line;
		String parts[];
		line = in.readString();
		assertEquals("ACK"
		line = in.readString();
		parts = line.split(" "
		assertEquals("restart-token"
		assertEquals(c.getSession().getKey()
		line = in.readString();
		parts = line.split(" "
		assertEquals("heartbeat-interval"
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

		int space = update_size * 100;

		for (int i = 0; i < clients; i++) {
			PublisherClientTest t = new PublisherClientTest(i * 5
				@Override
				protected void writeSubscribePacket(PacketLineOut pckLineOut)
						throws IOException {
					pckLineOut.writeString("subscribe");
					pckLineOut.end();
					pckLineOut.writeString("repository testrepository");
					pckLineOut.writeString("want " + refName);
					pckLineOut.writeString(
							"have " + OBJECT_ID1.getName() + " " + refName);
					pckLineOut.end();
					pckLineOut.writeString("done");
				}

				@Override
				public Repository openRepository(String name)
						throws RepositoryNotFoundException
						ServiceMayNotContinueException
						ServiceNotAuthorizedException
						ServiceNotEnabledException {
					if (!name.equals("testrepository"))
						fail("Invalid open database request");
					return db;
				}
			};
			testClients.add(t);
			states.add(t.getSession());
		}

		connectLatch.await();

		for (int i = 0; i < updates; i++) {
			Thread.sleep(500);
			publisher.onPush(db
		}

		for (PublisherClientTest c : testClients) {
			try {
				InputStream rawIn = new BufferedInputStream(c
						.getPipedInputStream()
				PacketLineIn in = new PacketLineIn(rawIn);
				String line;
				String parts[];
				line = in.readString();
				assertEquals("ACK"
				line = in.readString();
				parts = line.split(" "
				assertEquals("restart-token"
				assertEquals(c.getSession().getKey()
				line = in.readString();
				parts = line.split(" "
				assertEquals("heartbeat-interval"
				int updatesLeft = updates;
				while (updatesLeft > 0) {
					line = in.readString();
					if (line.startsWith("heartbeat"))
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
					assertEquals("pack-id"
					updatesLeft--;
				}
			} catch (Exception e) {
				throw new Exception(c.getSession().getKey()
			} finally {
				c.close();
				c.checkExceptions();
			}
		}
	}

	abstract class PublisherClientTest extends PublisherClient {
		Thread subscribeThread;

		PipedOutputStream output;

		PipedInputStream input;

		List<Exception> exceptions;

		public PublisherClientTest(final int delayWrite
				throws ServiceNotAuthorizedException
				ServiceNotEnabledException {
			super(publisher);

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
			output = new PipedOutputStream() {
				@Override
				public synchronized void write(byte[] b
						throws IOException {
					try {
						Thread.sleep(delayWrite);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					super.write(b
				}
			};
			input = new PipedInputStream(space + 1024);
			try {
				output.connect(input);
			} catch (IOException e1) {
			}

			exceptions = new ArrayList<Exception>();
			final PublisherClient me = this;
			subscribeThread = new Thread() {
				@Override
				public void run() {
					try {
						me.subscribe(myIn
					} catch (EOFException e) {
					} catch (Exception e) {
						exceptions.add(e);
					}
				}
			};

			subscribeThread.start();
		}

		public void checkExceptions() throws Exception {
			for (Exception e : exceptions)
				throw e;
		}

		@Override
		public synchronized void close() {
			super.close();
			try {
				if (output != null) {
					output.close();
					input.close();
				}
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}

		protected abstract void writeSubscribePacket(PacketLineOut pckLineOut)
				throws IOException;

		public Thread getSubscribeThread() {
			return subscribeThread;
		}

		public PipedInputStream getPipedInputStream() {
			return input;
		}
	}
}
