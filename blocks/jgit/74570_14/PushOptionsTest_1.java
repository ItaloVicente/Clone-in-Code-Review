
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.RemoteRefUpdate.Status.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushOptionsTest extends RepositoryTestCase {
	private URIish uri;
	private TestProtocol<Object> testProtocol;
	private Object ctx = new Object();
	private InMemoryRepository server;
	private InMemoryRepository client;
	private ObjectId obj1;
	private ObjectId obj2;

	private ObjectId obj3;
	private String refName = "refs/tags/blob";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		server = newRepo("server");
		client = newRepo("client");
		testProtocol = new TestProtocol<>(null
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						return new ReceivePack(database);
					}
				});
		uri = testProtocol.register(ctx

		try (ObjectInserter ins = server.newObjectInserter()) {
			obj1 = ins.insert(Constants.OBJ_BLOB
			obj3 = ins.insert(Constants.OBJ_BLOB
			ins.flush();

			RefUpdate u = server.updateRef(refName);
			u.setNewObjectId(obj1);
			assertEquals(RefUpdate.Result.NEW
		}

		try (ObjectInserter ins = client.newObjectInserter()) {
			obj2 = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}


		src = createBareRepository();
		dst = createBareRepository();

		TestRepository<Repository> d = new TestRepository<Repository>(dst);
		a = d.blob("a");
		A = d.commit(d.tree(d.file("a"
		B = d.commit().parent(A).create();
		d.update(R_MASTER

		try (Transport t = Transport.open(src
				new URIish(dst.getDirectory().getAbsolutePath()))) {
			t.fetch(PM
			assertEquals(B
		}

		b = d.blob("b");
		P = d.commit(d.tree(d.file("b"
		d.update(R_PRIVATE
	}

	@After
	public void tearDown() {
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	private static final NullProgressMonitor PM = NullProgressMonitor.INSTANCE;

	private static final String R_MASTER = Constants.R_HEADS + Constants.MASTER;

	private static final String R_PRIVATE = Constants.R_HEADS + "private";

	private Repository src;

	private Repository dst;

	private RevCommit A

	private RevBlob a

	@Test
	public void testPush() throws Exception {

		Repository db2 = createWorkRepository();

		System.out.println("PushOptionsTest: setting up first repository");
		System.out.println("PushOptionsTest: db = " + db.toString());
		final StoredConfig config = db.getConfig();
		config.setBoolean("receive"
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addURI(new URIish(db.getDirectory().toURI().toURL()));
		remoteConfig.update(config);
		config.save();


		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit")
					.call();
			Ref tagRef = git1.tag().setName("tag").call();

			try {
				db2.resolve(commit.getId().getName() + "^{commit}");
				fail("id shouldn't exist yet");
			} catch (MissingObjectException e) {
			}

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			List<String> pushOptions = Arrays.asList("Hello"

			System.out.println("PushOptionsTest: setting up second repository");
			final StoredConfig config2 = db2.getConfig();
			config2.setBoolean("receive"
			RemoteConfig remoteConfig2 = new RemoteConfig(config2
			remoteConfig2
					.addURI(new URIish(db2.getDirectory().toURI().toURL()));
			remoteConfig2.update(config2);
			config2.save();

			ReceivePack receivePack = new ReceivePack(db2);
			TestRepository<Repository> s = new TestRepository<Repository>(src);
			RevCommit N = s.commit().parent(B).add("q"

			final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
			packHeader(pack
			copy(pack
			digest(pack);

			final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
			final PacketLineOut inPckLine = new PacketLineOut(inBuf);
			inPckLine.writeString(ObjectId.zeroId().getName()
							+ ' '
							+ N.name()
					+ ' ' + "refs/heads/s" + '\0'
					+ BasePackPushConnection.CAPABILITY_PUSH_OPTIONS);
			inPckLine.end();
			pack.writeTo(inBuf


			System.out.println("PushOptionsTest: receivePack.receive(...)");

			System.out.println(
					"PushOptionsTest: receivePack.isAllowPushOptions() = "
					+ receivePack.isAllowPushOptions());

			receivePack.receive(new ByteArrayInputStream(inBuf.toByteArray())
					System.out

			System.out.println(
					"receivePack.isCapabilityEnabled(BasePackPushConnection.CAPABILITY_REPORT_STATUS): "
					+ receivePack.isCapabilityEnabled(
							BasePackPushConnection.CAPABILITY_REPORT_STATUS));
			System.out.println(
					"receivePack.isCapabilityEnabled(BasePackPushConnection.CAPABILITY_PUSH_OPTIONS): "
							+ receivePack.isCapabilityEnabled(
									BasePackPushConnection.CAPABILITY_PUSH_OPTIONS));

			PushCommand pushCommand = git1.push().setRemote("test")
					.setRefSpecs(spec).setPushOptions(pushOptions);
			System.out.println("pushCommand.call();");
			pushCommand.call();

			assertEquals(commit.getId()
					db2.resolve(commit.getId().getName() + "^{commit}"));
			assertEquals(tagRef.getObjectId()
					db2.resolve(tagRef.getObjectId().getName()));
			assertEquals(pushOptions
		}
	}

	public static class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
		}
	}

	public void testWrongOldIdDoesNotReplace() throws IOException {
		RemoteRefUpdate rru = new RemoteRefUpdate(null
				false

		Map<String
		updates.put(rru.getRemoteName()

		Transport tn = testProtocol.open(uri
		try {
			PushConnection connection = tn.openPush();
			try {
				connection.push(NullProgressMonitor.INSTANCE
			} finally {
				connection.close();
			}
		} finally {
			tn.close();
		}

		assertEquals(REJECTED_OTHER_REASON
		assertEquals("invalid old id sent"
	}

	private static void packHeader(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr

		tinyPack.write(Constants.PACK_SIGNATURE);
		tinyPack.write(hdr
	}

	private static void copy(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] buf = new byte[64];
		final byte[] content = ldr.getCachedBytes();
		int dataLength = content.length;
		int nextLength = dataLength >>> 4;
		int size = 0;
		buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
				| (ldr.getType() << 4) | (dataLength & 0x0F));
		dataLength = nextLength;
		while (dataLength > 0) {
			nextLength >>>= 7;
			buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
					| (dataLength & 0x7F));
			dataLength = nextLength;
		}
		tinyPack.write(buf
		deflate(tinyPack
	}

	private static void deflate(TemporaryBuffer.Heap tinyPack
			final byte[] content) throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				tinyPack.write(buf
		} while (!deflater.finished());
	}

	private static void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}

	public static PacketLineIn newPacketLineIn(String input) {
		return new PacketLineIn(
				new ByteArrayInputStream(Constants.encode(input)));
	}
}
