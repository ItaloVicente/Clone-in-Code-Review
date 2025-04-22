
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class TransportTest extends SampleDataRepositoryTestCase {
	private RemoteConfig remoteConfig;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		final Config config = db.getConfig();
		remoteConfig = new RemoteConfig(config
	}

	@Test
	public void testFindRemoteRefUpdatesNoWildcardNoTracking()
			throws IOException {
		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(Collections.nCopies(1
					new RefSpec("refs/heads/master:refs/heads/x")));
		}

		assertEquals(1
		final RemoteRefUpdate rru = result.iterator().next();
		assertNull(rru.getExpectedOldObjectId());
		assertFalse(rru.isForceUpdate());
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
		assertEquals("refs/heads/x"
	}

	@Test
	public void testFindRemoteRefUpdatesNoWildcardNoDestination()
			throws IOException {
		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(
					Collections.nCopies(1
		}

		assertEquals(1
		final RemoteRefUpdate rru = result.iterator().next();
		assertNull(rru.getExpectedOldObjectId());
		assertTrue(rru.isForceUpdate());
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
		assertEquals("refs/heads/master"
	}

	@Test
	public void testFindRemoteRefUpdatesWildcardNoTracking() throws IOException {
		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(Collections.nCopies(1
		}

		assertEquals(12
		boolean foundA = false;
		boolean foundB = false;
		for (RemoteRefUpdate rru : result) {
			if ("refs/heads/a".equals(rru.getSrcRef())
					&& "refs/heads/test/a".equals(rru.getRemoteName()))
				foundA = true;
			if ("refs/heads/b".equals(rru.getSrcRef())
					&& "refs/heads/test/b".equals(rru.getRemoteName()))
				foundB = true;
		}
		assertTrue(foundA);
		assertTrue(foundB);
	}

	@Test
	public void testFindRemoteRefUpdatesTwoRefSpecs() throws IOException {
		final RefSpec specA = new RefSpec("+refs/heads/a:refs/heads/b");
		final RefSpec specC = new RefSpec("+refs/heads/c:refs/heads/d");
		final Collection<RefSpec> specs = Arrays.asList(specA

		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(specs);
		}

		assertEquals(2
		boolean foundA = false;
		boolean foundC = false;
		for (RemoteRefUpdate rru : result) {
			if ("refs/heads/a".equals(rru.getSrcRef())
					&& "refs/heads/b".equals(rru.getRemoteName()))
				foundA = true;
			if ("refs/heads/c".equals(rru.getSrcRef())
					&& "refs/heads/d".equals(rru.getRemoteName()))
				foundC = true;
		}
		assertTrue(foundA);
		assertTrue(foundC);
	}

	@Test
	public void testFindRemoteRefUpdatesTrackingRef() throws IOException {
		remoteConfig.addFetchRefSpec(new RefSpec(

		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(Collections.nCopies(1
					new RefSpec("+refs/heads/a:refs/heads/a")));
		}

		assertEquals(1
		final TrackingRefUpdate tru = result.iterator().next()
				.getTrackingRefUpdate();
		assertEquals("refs/remotes/test/a"
		assertEquals("refs/heads/a"
		assertEquals(db.resolve("refs/heads/a")
		assertEquals(ObjectId.zeroId()
	}

	@Test
	public void testFindRemoteRefUpdatesWithLeases() throws IOException {
		final RefSpec specA = new RefSpec("+refs/heads/a:refs/heads/b");
		final RefSpec specC = new RefSpec("+refs/heads/c:refs/heads/d");
		final Collection<RefSpec> specs = Arrays.asList(specA
		final Map<String
		leases.put("refs/heads/b"
				new RefLeaseSpec("refs/heads/b"

		Collection<RemoteRefUpdate> result;
		try (Transport transport = Transport.open(db
			result = transport.findRemoteRefUpdatesFor(specs
		}

		assertEquals(2
		boolean foundA = false;
		boolean foundC = false;
		for (RemoteRefUpdate rru : result) {
			if ("refs/heads/a".equals(rru.getSrcRef())
					&& "refs/heads/b".equals(rru.getRemoteName())) {
				foundA = true;
				assertEquals(db.exactRef("refs/heads/c").getObjectId()
						rru.getExpectedOldObjectId());
			}
			if ("refs/heads/c".equals(rru.getSrcRef())
					&& "refs/heads/d".equals(rru.getRemoteName())) {
				foundC = true;
				assertNull(rru.getExpectedOldObjectId());
			}
		}
		assertTrue(foundA);
		assertTrue(foundC);
	}

	@Test
	public void testLocalTransportWithRelativePath() throws Exception {
		Repository other = createWorkRepository();
		String otherDir = other.getWorkTree().getName();

		RemoteConfig config = new RemoteConfig(db.getConfig()
		config.addURI(new URIish("../" + otherDir));

		Transport.open(db
	}

	@Test
	public void testLocalTransportFetchWithoutLocalRepository()
			throws Exception {
		try (Transport transport = Transport.open(uri)) {
			try (FetchConnection fetchConnection = transport.openFetch()) {
				Ref head = fetchConnection.getRef(Constants.HEAD);
				assertNotNull(head);
			}
		}
	}

	@Test
	public void testSpi() {
		List<TransportProtocol> protocols = Transport.getTransportProtocols();
		assertNotNull(protocols);
		assertFalse(protocols.isEmpty());
		TransportProtocol found = null;
		for (TransportProtocol protocol : protocols)
			if (protocol.getSchemes().contains(SpiTransport.SCHEME)) {
				found = protocol;
				break;
			}
		assertEquals(SpiTransport.PROTO
	}
}
