package org.eclipse.jgit.transport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Sets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UploadPackAdvertiseRefsTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private InMemoryRepository server;

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

	@Before
	public void setUp() throws Exception {
		server = newRepo("server");
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}


	class TestAdvertisedRefsHook implements AdvertiseRefsHook {

		public boolean invoked;

		private final Map<String

		public TestAdvertisedRefsHook(String... refNames) {
			for (String refName : refNames) {
				this.advertisedRefs.put(refName
						new ObjectIdRef.PeeledNonTag(Storage.LOOSE
			}
		}

		@Override
		public void advertiseRefs(UploadPack uploadPack)
				throws ServiceMayNotContinueException {
			assertFalse(invoked);
			invoked = true;
			uploadPack.setAdvertisedRefs(advertisedRefs);

		}

		@Override
		public void advertiseRefs(BaseReceivePack receivePack)
				throws ServiceMayNotContinueException {

		}
	}

	@Test
	public void advertiseRefs_Onedi_V0() throws IOException {
		UploadPack up = new UploadPack(server);
		up.setBiDirectionalPipe(false);

		TestAdvertisedRefsHook hook = new TestAdvertisedRefsHook("refs/heads/master"
		up.setAdvertiseRefsHook(hook);

		ByteArrayInputStream send = linesAsInputStream(PacketLineIn.END);
		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertTrue(hook.invoked);
	}

	@Test
	public void advertiseRefs_Bidi_V0() throws IOException {
		UploadPack up = new UploadPack(server);
		up.setBiDirectionalPipe(true);

		TestAdvertisedRefsHook hook = new TestAdvertisedRefsHook(
				"refs/heads/master"
		up.setAdvertiseRefsHook(hook);

		ByteArrayInputStream send = linesAsInputStream(PacketLineIn.END);
		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertTrue(hook.invoked);
	}

	@Test
	public void advertiseRefs_Onedi_V2() throws IOException {
		server.getConfig().setString("protocol"
		UploadPack up = new UploadPack(server);
		up.setBiDirectionalPipe(false);
		up.setExtraParameters(Sets.of("version=2"));

		TestAdvertisedRefsHook hook = new TestAdvertisedRefsHook(
				"refs/heads/master"
		up.setAdvertiseRefsHook(hook);

		ByteArrayInputStream send = linesAsInputStream(PacketLineIn.END);
		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertFalse(hook.invoked);
	}

	@Test
	public void advertiseRefs_Bidi_V2() throws IOException {
		server.getConfig().setString("protocol"
		UploadPack up = new UploadPack(server);
		up.setBiDirectionalPipe(false);
		up.setExtraParameters(Sets.of("version=2"));

		TestAdvertisedRefsHook hook = new TestAdvertisedRefsHook(
				"refs/heads/master"
		up.setAdvertiseRefsHook(hook);

		ByteArrayInputStream send = linesAsInputStream(PacketLineIn.END);
		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertFalse(hook.invoked);
	}

}
