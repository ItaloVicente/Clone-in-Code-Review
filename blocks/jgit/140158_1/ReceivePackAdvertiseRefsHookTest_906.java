
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.junit.Before;
import org.junit.Test;

public class PushProcessTest extends SampleDataRepositoryTestCase {
	private PushProcess process;

	private MockTransport transport;

	private HashSet<RemoteRefUpdate> refUpdates;

	private HashSet<Ref> advertisedRefs;

	private Status connectionUpdateStatus;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		transport = new MockTransport(db
		refUpdates = new HashSet<>();
		advertisedRefs = new HashSet<>();
		connectionUpdateStatus = Status.OK;
	}

	@Test
	public void testUpdateFastForward() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateNonFastForwardUnknownObject() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("0000000000000000000000000000000000000001"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateNonFastForward() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"ac7e7e44c1885efb472ad54a78327d66bfc4ecef"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateNonFastForwardForced() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"ac7e7e44c1885efb472ad54a78327d66bfc4ecef"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateCreateRef() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"ac7e7e44c1885efb472ad54a78327d66bfc4ecef"
				"refs/heads/master"
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateDelete() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateDeleteNonExisting() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"refs/heads/master"
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateUpToDate() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateExpectedRemote() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
						.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateUnexpectedRemote() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
						.fromString("0000000000000000000000000000000000000001"));
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateUnexpectedRemoteVsForce() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
						.fromString("0000000000000000000000000000000000000001"));
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateRejectedByConnection() throws IOException {
		connectionUpdateStatus = Status.REJECTED_OTHER_REASON;
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		testOneUpdateStatus(rru
	}

	@Test
	public void testUpdateMixedCases() throws IOException {
		final RemoteRefUpdate rruOk = new RemoteRefUpdate(db
				"refs/heads/master"
		final Ref refToChange = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		final RemoteRefUpdate rruReject = new RemoteRefUpdate(db
				(String) null
		refUpdates.add(rruOk);
		refUpdates.add(rruReject);
		advertisedRefs.add(refToChange);
		executePush();
		assertEquals(Status.OK
		assertTrue(rruOk.isFastForward());
		assertEquals(Status.NON_EXISTING
	}

	@Test
	public void testTrackingRefUpdateEnabled() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		refUpdates.add(rru);
		advertisedRefs.add(ref);
		final PushResult result = executePush();
		final TrackingRefUpdate tru = result
				.getTrackingRefUpdate("refs/remotes/test/master");
		assertNotNull(tru);
		assertEquals("refs/remotes/test/master"
		assertEquals(Result.NEW
	}

	@Test
	public void testTrackingRefUpdateDisabled() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		refUpdates.add(rru);
		advertisedRefs.add(ref);
		final PushResult result = executePush();
		assertTrue(result.getTrackingRefUpdates().isEmpty());
	}

	@Test
	public void testTrackingRefUpdateOnReject() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"ac7e7e44c1885efb472ad54a78327d66bfc4ecef"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("2c349335b7f797072cf729c4f3bb0914ecb6dec9"));
		final PushResult result = testOneUpdateStatus(rru
				Status.REJECTED_NONFASTFORWARD
		assertTrue(result.getTrackingRefUpdates().isEmpty());
	}

	@Test
	public void testPushResult() throws IOException {
		final RemoteRefUpdate rru = new RemoteRefUpdate(db
				"2c349335b7f797072cf729c4f3bb0914ecb6dec9"
				"refs/heads/master"
		final Ref ref = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				ObjectId.fromString("ac7e7e44c1885efb472ad54a78327d66bfc4ecef"));
		refUpdates.add(rru);
		advertisedRefs.add(ref);
		final PushResult result = executePush();
		assertEquals(1
		assertEquals(1
		assertEquals(1
		assertNotNull(result.getTrackingRefUpdate("refs/remotes/test/master"));
		assertNotNull(result.getAdvertisedRef("refs/heads/master"));
		assertNotNull(result.getRemoteUpdate("refs/heads/master"));
	}

	private PushResult testOneUpdateStatus(final RemoteRefUpdate rru
			final Ref advertisedRef
			Boolean fastForward) throws NotSupportedException
			TransportException {
		refUpdates.add(rru);
		if (advertisedRef != null)
			advertisedRefs.add(advertisedRef);
		final PushResult result = executePush();
		assertEquals(expectedStatus
		if (fastForward != null)
			assertEquals(fastForward
		return result;
	}

	private PushResult executePush() throws NotSupportedException
			TransportException {
		process = new PushProcess(transport
		return process.execute(new TextProgressMonitor());
	}

	private class MockTransport extends Transport {
		MockTransport(Repository local
			super(local
		}

		@Override
		public FetchConnection openFetch() throws NotSupportedException
				TransportException {
			throw new NotSupportedException("mock");
		}

		@Override
		public PushConnection openPush() throws NotSupportedException
				TransportException {
			return new MockPushConnection();
		}

		@Override
		public void close() {
		}
	}

	private class MockPushConnection extends BaseConnection implements
			PushConnection {
		MockPushConnection() {
			final Map<String
			for (Ref r : advertisedRefs)
				refsMap.put(r.getName()
			available(refsMap);
		}

		@Override
		public void close() {
		}

		@Override
		public void push(ProgressMonitor monitor
				Map<String
				throws TransportException {
			push(monitor
		}

		@Override
		public void push(ProgressMonitor monitor
				Map<String
				throws TransportException {
			for (RemoteRefUpdate rru : refsToUpdate.values()) {
				assertEquals(Status.NOT_ATTEMPTED
				rru.setStatus(connectionUpdateStatus);
			}
		}
	}
}
