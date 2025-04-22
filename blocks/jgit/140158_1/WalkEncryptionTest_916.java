package org.eclipse.jgit.transport;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.theInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.storage.dfs.DfsGarbageCollector;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.UploadPack.RequestPolicy;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UploadPackTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private URIish uri;

	private TestProtocol<Object> testProtocol;

	private Object ctx = new Object();

	private InMemoryRepository server;

	private InMemoryRepository client;

	private TestRepository<InMemoryRepository> remote;

	@Before
	public void setUp() throws Exception {
		server = newRepo("server");
		client = newRepo("client");

		remote = new TestRepository<>(server);
	}

	@After
	public void tearDown() {
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	private void generateBitmaps(InMemoryRepository repo) throws Exception {
		new DfsGarbageCollector(repo).pack(null);
		repo.scanForRepoChanges();
	}

	private static TestProtocol<Object> generateReachableCommitUploadPackProtocol() {
		return new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						return up;
					}
				}
	}

	@Test
	public void testFetchParentOfShallowCommit() throws Exception {
		RevCommit commit0 = remote.commit().message("0").create();
		RevCommit commit1 = remote.commit().message("1").parent(commit0).create();
		RevCommit tip = remote.commit().message("2").parent(commit1).create();
		remote.update("master"

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						up.getRevWalk().assumeShallow(
								Collections.singleton(commit1.getId()));
						return up;
					}
				}
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(commit0.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit0.name())));
			assertTrue(client.getObjectDatabase().has(commit0.toObjectId()));
		}
	}

	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		remote.commit(remote.tree(remote.file("foo"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchReachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
			assertTrue(client.getObjectDatabase().has(blob.toObjectId()));
		}
	}

	@Test
	public void testFetchReachableBlobWithoutBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchWithBlobNoneFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob1 = remote2.blob("foobar");
			RevBlob blob2 = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(0);
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertTrue(client.getObjectDatabase().has(tree.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob1.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
			}
		}
	}

	@Test
	public void testFetchExplicitBlobWithFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob1 = remote2.blob("foobar");
			RevBlob blob2 = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"
			remote2.update("a_blob"

			server2.getConfig().setBoolean("uploadpack"
					true);

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(0);
				tn.fetch(NullProgressMonitor.INSTANCE
						new RefSpec(commit.name())
				assertTrue(client.getObjectDatabase().has(tree.toObjectId()));
				assertTrue(client.getObjectDatabase().has(blob1.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
			}
		}
	}

	@Test
	public void testFetchWithBlobLimitFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob longBlob = remote2.blob("foobar");
			RevBlob shortBlob = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(5);
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertFalse(
						client.getObjectDatabase().has(longBlob.toObjectId()));
				assertTrue(
						client.getObjectDatabase().has(shortBlob.toObjectId()));
			}
		}
	}

	@Test
	public void testFetchExplicitBlobWithFilterAndBitmaps() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob1 = remote2.blob("foobar");
			RevBlob blob2 = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"
			remote2.update("a_blob"

			server2.getConfig().setBoolean("uploadpack"
					true);

			new DfsGarbageCollector(server2).pack(null);
			server2.scanForRepoChanges();

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(0);
				tn.fetch(NullProgressMonitor.INSTANCE
						new RefSpec(commit.name())
				assertTrue(client.getObjectDatabase().has(blob1.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
			}
		}
	}

	@Test
	public void testFetchWithBlobLimitFilterAndBitmaps() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob longBlob = remote2.blob("foobar");
			RevBlob shortBlob = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			new DfsGarbageCollector(server2).pack(null);
			server2.scanForRepoChanges();

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(5);
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertFalse(
						client.getObjectDatabase().has(longBlob.toObjectId()));
				assertTrue(
						client.getObjectDatabase().has(shortBlob.toObjectId()));
			}
		}
	}

	@Test
	public void testFetchWithNonSupportingServer() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob = remote2.blob("foo");
			RevTree tree = remote2.tree(remote2.file("1"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					false);

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(0);

				thrown.expect(TransportException.class);
				thrown.expectMessage(
						"filter requires server to advertise that capability");

				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
			}
		}
	}

	private ByteArrayInputStream uploadPackV2Setup(RequestPolicy requestPolicy
			RefFilter refFilter
			throws Exception {

		ByteArrayInputStream send = linesAsInputStream(inputLines);

		server.getConfig().setString("protocol"
		UploadPack up = new UploadPack(server);
		if (requestPolicy != null)
			up.setRequestPolicy(requestPolicy);
		if (refFilter != null)
			up.setRefFilter(refFilter);
		up.setExtraParameters(Sets.of("version=2"));
		if (hook != null) {
			up.setProtocolV2Hook(hook);
		}

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		return new ByteArrayInputStream(recv.toByteArray());
	}

	private static ByteArrayInputStream linesAsInputStream(String... inputLines)
			throws IOException {
		try (ByteArrayOutputStream send = new ByteArrayOutputStream()) {
			PacketLineOut pckOut = new PacketLineOut(send);
			for (String line : inputLines) {
				if (line == PacketLineIn.END) {
					pckOut.end();
				} else if (line == PacketLineIn.DELIM) {
					pckOut.writeDelim();
				} else {
					pckOut.writeString(line);
				}
			}
			return new ByteArrayInputStream(send.toByteArray());
		}
	}

	private ByteArrayInputStream uploadPackV2(RequestPolicy requestPolicy
			RefFilter refFilter
			throws Exception {
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(requestPolicy
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		while (pckIn.readString() != PacketLineIn.END) {
		}
		return recvStream;
	}

	private ByteArrayInputStream uploadPackV2(String... inputLines) throws Exception {
		return uploadPackV2(null
	}

	private static class TestV2Hook implements ProtocolV2Hook {
		private CapabilitiesV2Request capabilitiesRequest;

		private LsRefsV2Request lsRefsRequest;

		private FetchV2Request fetchRequest;

		@Override
		public void onCapabilities(CapabilitiesV2Request req) {
			capabilitiesRequest = req;
		}

		@Override
		public void onLsRefs(LsRefsV2Request req) {
			lsRefsRequest = req;
		}

		@Override
		public void onFetch(FetchV2Request req) {
			fetchRequest = req;
		}
	}

	@Test
	public void testV2Capabilities() throws Exception {
		TestV2Hook hook = new TestV2Hook();
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(hook.capabilitiesRequest
		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
						pckIn.readString())
				hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesAllowFilter() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
						pckIn.readString())
				hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesRefInWant() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
						pckIn.readString())
				hasItems("ls-refs"
						"server-option"));
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesRefInWantNotAdvertisedIfUnallowed() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
						pckIn.readString())
				hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesRefInWantNotAdvertisedIfAdvertisingForbidden() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
				uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
						pckIn.readString())
				hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2EmptyRequest() throws Exception {
		ByteArrayInputStream recvStream = uploadPackV2(PacketLineIn.END);
		assertEquals(0
	}

	@Test
	public void testV2LsRefs() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		TestV2Hook hook = new TestV2Hook();
		ByteArrayInputStream recvStream = uploadPackV2(null
				"command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(hook.lsRefsRequest
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsSymrefs() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsPeel() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(
			pckIn.readString()
			is(tag.toObjectId().getName() + " refs/tags/tag peeled:"
				+ tip.toObjectId().getName()));
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsMultipleCommands() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			"command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(
			pckIn.readString()
			is(tag.toObjectId().getName() + " refs/tags/tag peeled:"
				+ tip.toObjectId().getName()));
		assertTrue(pckIn.readString() == PacketLineIn.END);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsRefPrefix() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		remote.update("other"
		remote.update("yetAnother"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			PacketLineIn.DELIM
			"ref-prefix refs/heads/maste"
			"ref-prefix refs/heads/other"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsRefPrefixNoSlash() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		remote.update("other"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			PacketLineIn.DELIM
			"ref-prefix refs/heads/maste"
			"ref-prefix r"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsUnrecognizedArgument() throws Exception {
		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=ls-refs\n"
			PacketLineIn.DELIM
			"invalid-argument\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsServerOptions() throws Exception {
		String[] lines = { "command=ls-refs\n"
				"server-option=one\n"
				PacketLineIn.DELIM
				PacketLineIn.END };

		TestV2Hook testHook = new TestV2Hook();
		uploadPackV2Setup(null

		LsRefsV2Request req = testHook.lsRefsRequest;
		assertEquals(2
		assertThat(req.getServerOptions()
	}

	private ReceivedPackStatistics parsePack(ByteArrayInputStream recvStream) throws Exception {
		return parsePack(recvStream
	}

	private ReceivedPackStatistics parsePack(ByteArrayInputStream recvStream
			throws Exception {
		SideBandInputStream sb = new SideBandInputStream(
				recvStream
				new StringWriter()
		PackParser pp = client.newObjectInserter().newPackParser(sb);
		pp.parse(NullProgressMonitor.INSTANCE);

		assertEquals(-1

		return pp.getReceivedPackStatistics();
	}

	@Test
	public void testV2FetchRequestPolicyAdvertised() throws Exception {
		RevCommit advertized = remote.commit().message("x").create();
		RevCommit unadvertized = remote.commit().message("y").create();
		remote.update("branch1"

		uploadPackV2(
			RequestPolicy.ADVERTISED
			null
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + advertized.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unadvertized.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.ADVERTISED
			null
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unadvertized.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyReachableCommit() throws Exception {
		RevCommit reachable = remote.commit().message("x").create();
		RevCommit advertized = remote.commit().message("x").parent(reachable).create();
		RevCommit unreachable = remote.commit().message("y").create();
		remote.update("branch1"

		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT
			null
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + reachable.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT
			null
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyTip() throws Exception {
		RevCommit parentOfTip = remote.commit().message("x").create();
		RevCommit tip = remote.commit().message("y").parent(parentOfTip).create();
		remote.update("secret"

		uploadPackV2(
			RequestPolicy.TIP
			new RejectAllRefFilter()
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + tip.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + parentOfTip.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.TIP
			new RejectAllRefFilter()
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + parentOfTip.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyReachableCommitTip() throws Exception {
		RevCommit parentOfTip = remote.commit().message("x").create();
		RevCommit tip = remote.commit().message("y").parent(parentOfTip).create();
		RevCommit unreachable = remote.commit().message("y").create();
		remote.update("secret"

		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT_TIP
			new RejectAllRefFilter()
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + parentOfTip.name() + "\n"
			PacketLineIn.END);

		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT_TIP
			new RejectAllRefFilter()
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchRequestPolicyAny() throws Exception {
		RevCommit unreachable = remote.commit().message("y").create();

		uploadPackV2(
			RequestPolicy.ANY
			null
			null
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + unreachable.name() + "\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchServerDoesNotStopNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}

	@Test
	public void testV2FetchServerStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"have " + barParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"ACK " + fooParent.toObjectId().getName()
				"ACK " + barParent.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.getObjectDatabase().has(fooParent.toObjectId()));
		assertTrue(client.getObjectDatabase().has(fooChild.toObjectId()));
		assertFalse(client.getObjectDatabase().has(barParent.toObjectId()));
		assertTrue(client.getObjectDatabase().has(barChild.toObjectId()));
	}

	@Test
	public void testV2FetchClientStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.getObjectDatabase().has(fooParent.toObjectId()));
		assertTrue(client.getObjectDatabase().has(fooChild.toObjectId()));
		assertTrue(client.getObjectDatabase().has(barParent.toObjectId()));
		assertTrue(client.getObjectDatabase().has(barChild.toObjectId()));
	}

	@Test
	public void testV2FetchThinPack() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"have " + parent.toObjectId().getName() + "\n"
			"thin-pack\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()

		thrown.expect(IOException.class);
		thrown.expectMessage("pack has unresolved deltas");
		parsePack(recvStream);
	}

	@Test
	public void testV2FetchNoProgress() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		remote.update("branch1"

		StringWriter sw = new StringWriter();
		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream
		assertFalse(sw.toString().isEmpty());

		sw = new StringWriter();
		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"no-progress\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream
		assertTrue(sw.toString().isEmpty());
	}

	@Test
	public void testV2FetchIncludeTag() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		RevTag tag = remote.tag("tag"
		remote.update("branch1"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.getObjectDatabase().has(tag.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"include-tag\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(tag.toObjectId()));
	}

	@Test
	public void testV2FetchOfsDelta() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		ReceivedPackStatistics stats = parsePack(recvStream);
		assertTrue(stats.getNumOfsDelta() == 0);

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"ofs-delta\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		stats = parsePack(recvStream);
		assertTrue(stats.getNumOfsDelta() != 0);
	}

	@Test
	public void testV2FetchShallow() throws Exception {
		RevCommit commonParent = remote.commit().message("parent").create();
		RevCommit fooChild = remote.commit().message("x").parent(commonParent).create();
		RevCommit barChild = remote.commit().message("y").parent(commonParent).create();
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooChild.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(barChild.toObjectId()));
		assertFalse(client.getObjectDatabase().has(commonParent.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooChild.toObjectId().getName() + "\n"
			"shallow " + fooChild.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(commonParent.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenAndDone() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"deepen 1\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(child.toObjectId()));
		assertFalse(client.getObjectDatabase().has(parent.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(parent.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenWithoutDone() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"deepen 1\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}

	@Test
	public void testV2FetchShallowSince() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit beyondBoundary = remote.commit()
			.committer(new PersonIdent(person
		RevCommit boundary = remote.commit().parent(beyondBoundary)
			.committer(new PersonIdent(person
		RevCommit tooOld = remote.commit()
			.committer(new PersonIdent(person
		RevCommit merge = remote.commit().parent(boundary).parent(tooOld)
			.committer(new PersonIdent(person

		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"shallow " + boundary.toObjectId().getName() + "\n"
			"deepen-since 1510000\n"
			"want " + merge.toObjectId().getName() + "\n"
			"have " + boundary.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(tooOld.toObjectId()));

		assertFalse(client.getObjectDatabase().has(boundary.toObjectId()));

		assertTrue(client.getObjectDatabase().has(beyondBoundary.toObjectId()));
		assertTrue(client.getObjectDatabase().has(merge.toObjectId()));
	}

	@Test
	public void testV2FetchShallowSince_excludedParentWithMultipleChildren() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit base = remote.commit()
			.committer(new PersonIdent(person
		RevCommit child1 = remote.commit().parent(base)
			.committer(new PersonIdent(person
		RevCommit child2 = remote.commit().parent(base)
			.committer(new PersonIdent(person

		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-since 1510000\n"
			"want " + child1.toObjectId().getName() + "\n"
			"want " + child2.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"shallow " + child1.toObjectId().getName()
				"shallow " + child2.toObjectId().getName()));

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(base.toObjectId()));
		assertTrue(client.getObjectDatabase().has(child1.toObjectId()));
		assertTrue(client.getObjectDatabase().has(child2.toObjectId()));
	}

	@Test
	public void testV2FetchShallowSince_noCommitsSelected() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit tooOld = remote.commit()
			.committer(new PersonIdent(person

		remote.update("branch1"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-since 1510000\n"
			"want " + tooOld.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchDeepenNot() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit side = remote.commit().message("side").parent(one).create();
		RevCommit merge = remote.commit().message("merge")
			.parent(three).parent(side).create();

		remote.update("branch1"
		remote.update("side"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"shallow " + three.toObjectId().getName() + "\n"
			"deepen-not side\n"
			"want " + merge.toObjectId().getName() + "\n"
			"have " + three.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"shallow " + merge.toObjectId().getName()
				"shallow " + two.toObjectId().getName()));

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(side.toObjectId()));
		assertFalse(client.getObjectDatabase().has(one.toObjectId()));

		assertFalse(client.getObjectDatabase().has(three.toObjectId()));

		assertTrue(client.getObjectDatabase().has(merge.toObjectId()));
		assertTrue(client.getObjectDatabase().has(two.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenNot_excludeDescendantOfWant() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit four = remote.commit().message("four").parent(three).create();

		remote.update("two"
		remote.update("four"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not four\n"
			"want " + two.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchDeepenNot_supportAnnotatedTags() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit four = remote.commit().message("four").parent(three).create();
		RevTag twoTag = remote.tag("twotag"

		remote.update("refs/tags/twotag"
		remote.update("four"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not twotag\n"
			"want " + four.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.getObjectDatabase().has(one.toObjectId()));
		assertFalse(client.getObjectDatabase().has(two.toObjectId()));
		assertTrue(client.getObjectDatabase().has(three.toObjectId()));
		assertTrue(client.getObjectDatabase().has(four.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenNot_excludedParentWithMultipleChildren() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit base = remote.commit()
			.committer(new PersonIdent(person
		RevCommit child1 = remote.commit().parent(base)
			.committer(new PersonIdent(person
		RevCommit child2 = remote.commit().parent(base)
			.committer(new PersonIdent(person

		remote.update("base"
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not base\n"
			"want " + child1.toObjectId().getName() + "\n"
			"want " + child2.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"shallow " + child1.toObjectId().getName()
				"shallow " + child2.toObjectId().getName()));

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(base.toObjectId()));
		assertTrue(client.getObjectDatabase().has(child1.toObjectId()));
		assertTrue(client.getObjectDatabase().has(child2.toObjectId()));
	}

	@Test
	public void testV2FetchUnrecognizedArgument() throws Exception {
		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"invalid-argument\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchServerOptions() throws Exception {
		String[] lines = { "command=fetch\n"
				"server-option=two\n"
				PacketLineIn.END };

		TestV2Hook testHook = new TestV2Hook();
		uploadPackV2Setup(null

		FetchV2Request req = testHook.fetchRequest;
		assertNotNull(req);
		assertEquals(2
		assertThat(req.getServerOptions()
	}

	@Test
	public void testV2FetchFilter() throws Exception {
		RevBlob big = remote.blob("foobar");
		RevBlob small = remote.blob("fooba");
		RevTree tree = remote.tree(remote.file("1"
				remote.file("2"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(big.toObjectId()));
		assertTrue(client.getObjectDatabase().has(small.toObjectId()));
	}

	@Test
	public void testV2FetchFilterWhenNotAllowed() throws Exception {
		RevCommit commit = remote.commit().message("0").create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected filter blob:limit=5");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchWantRefIfNotAllowed() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		try {
			uploadPackV2(
				"command=fetch\n"
				PacketLineIn.DELIM
				"want-ref refs/heads/one\n"
				"done\n"
				PacketLineIn.END);
		} catch (PackProtocolException e) {
			assertThat(
				e.getMessage()
				containsString("unexpected want-ref refs/heads/one"));
			return;
		}
		fail("expected PackProtocolException");
	}

	@Test
	public void testV2FetchWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").create();
		RevCommit three = remote.commit().message("3").create();
		remote.update("one"
		remote.update("two"
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"want-ref refs/heads/two\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
				hasItems(
					one.toObjectId().getName() + " refs/heads/one"
					two.toObjectId().getName() + " refs/heads/two"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.getObjectDatabase().has(one.toObjectId()));
		assertTrue(client.getObjectDatabase().has(two.toObjectId()));
		assertFalse(client.getObjectDatabase().has(three.toObjectId()));
	}

	@Test
	public void testV2FetchBadWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		server.getConfig().setBoolean("uploadpack"

		try {
			uploadPackV2(
				"command=fetch\n"
				PacketLineIn.DELIM
				"want-ref refs/heads/one\n"
				"want-ref refs/heads/nonExistentRef\n"
				"done\n"
				PacketLineIn.END);
		} catch (PackProtocolException e) {
			assertThat(
				e.getMessage()
				containsString("Invalid ref name: refs/heads/nonExistentRef"));
			return;
		}
		fail("expected PackProtocolException");
	}

	@Test
	public void testV2FetchMixedWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").create();
		RevCommit three = remote.commit().message("3").create();
		remote.update("one"
		remote.update("two"
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"want " + two.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(
				pckIn.readString()
				is(one.toObjectId().getName() + " refs/heads/one"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.getObjectDatabase().has(one.toObjectId()));
		assertTrue(client.getObjectDatabase().has(two.toObjectId()));
		assertFalse(client.getObjectDatabase().has(three.toObjectId()));
	}

	@Test
	public void testV2FetchWantRefWeAlreadyHave() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"have " + one.toObjectId().getName()
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				pckIn.readString()
				is(one.toObjectId().getName() + " refs/heads/one"));
		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.getObjectDatabase().has(one.toObjectId()));
	}

	@Test
	public void testV2FetchWantRefAndDeepen() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();
		remote.update("branch1"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/branch1\n"
			"deepen 1\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(child.toObjectId()));
		assertFalse(client.getObjectDatabase().has(parent.toObjectId()));
	}

	@Test
	public void testV2FetchMissingShallow() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").parent(one).create();
		RevCommit three = remote.commit().message("3").parent(two).create();
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"
				true);

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.DELIM
				"want-ref refs/heads/three\n"
				"deepen 3"
				"shallow 0123012301230123012301230123012301230123"
				"shallow " + two.getName() + '\n'
				"done\n"
				PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is("shallow " + one.toObjectId().getName()));
		assertThat(pckIn.readString()
				is("unshallow " + two.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is(three.toObjectId().getName() + " refs/heads/three"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.getObjectDatabase().has(one.toObjectId()));
		assertTrue(client.getObjectDatabase().has(two.toObjectId()));
		assertTrue(client.getObjectDatabase().has(three.toObjectId()));
	}

	@Test
	public void testGetPeerAgentProtocolV0() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		UploadPack up = new UploadPack(server);
		ByteArrayInputStream send = linesAsInputStream(
				"want " + one.getName() + " agent=JGit-test/1.2.3\n"
				PacketLineIn.END
				"have 11cedf1b796d44207da702f7d420684022fc0f09\n"

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertEquals(up.getPeerUserAgent()
	}

	@Test
	public void testGetPeerAgentProtocolV2() throws Exception {
		server.getConfig().setString("protocol"

		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		UploadPack up = new UploadPack(server);
		up.setExtraParameters(Sets.of("version=2"));

		ByteArrayInputStream send = linesAsInputStream(
				"command=fetch\n"
				PacketLineIn.DELIM
				"have 11cedf1b796d44207da702f7d420684022fc0f09\n"
				PacketLineIn.END);

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertEquals(up.getPeerUserAgent()
	}

	private static class RejectAllRefFilter implements RefFilter {
		@Override
		public Map<String
			return new HashMap<>();
		}
	}
}
