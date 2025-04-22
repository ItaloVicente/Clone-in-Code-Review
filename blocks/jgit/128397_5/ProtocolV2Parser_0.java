package org.eclipse.jgit.transport;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProtocolV2ParserTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private TestRepository<InMemoryRepository> testRepo;

	@Before
	public void setUp() throws Exception {
		testRepo = new TestRepository<>(newRepo("protocol-v2-parser-test"));
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	private static class ConfigBuilder {

		private boolean allowRefInWant;

		private boolean allowFilter;

		private ConfigBuilder() {
		}

		static ConfigBuilder start() {
			return new ConfigBuilder();
		}

		static TransferConfig getDefault() {
			return start().done();
		}

		ConfigBuilder allowRefInWant() {
			allowRefInWant = true;
			return this;
		}

		ConfigBuilder allowFilter() {
			allowFilter = true;
			return this;
		}

		TransferConfig done() {
			Config rc = new Config();
			rc.setBoolean("uploadpack"
			rc.setBoolean("uploadpack"
			return new TransferConfig(rc);
		}
	}

	private PacketLineIn buildPckIn(String... inputLines) throws IOException {
		ByteArrayOutputStream send = new ByteArrayOutputStream();
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

		return new PacketLineIn(new ByteArrayInputStream(send.toByteArray()));
	}

	private List<String> objIdsAsStrings(Collection<ObjectId> objIds) {
		List<String> result = new ArrayList<>(objIds.size());
		for (ObjectId oid: objIds) {
			result.add(oid.name());
		}
		return result;
	}

	@Test
	public void testFetchBasicArguments()
			throws PackProtocolException
		PacketLineIn pckIn = buildPckIn(
				PacketLineIn.DELIM
				"thin-pack"
				"want 4624442d68ee402a94364191085b77137618633e"
				"want f900c8326a43303685c46b279b9f70411bff1a4b"
				"have 554f6e41067b9e3e565b6988a8294fac1cb78f4b"
				"have abc760ab9ad72f08209943251b36cb886a578f87"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertTrue(request.getOptions()
				.contains(GitProtocolConstants.OPTION_THIN_PACK));
		assertTrue(request.getOptions()
				.contains(GitProtocolConstants.OPTION_NO_PROGRESS));
		assertTrue(request.getOptions()
				.contains(GitProtocolConstants.OPTION_INCLUDE_TAG));
		assertTrue(request.getOptions()
				.contains(GitProtocolConstants.CAPABILITY_OFS_DELTA));
		assertThat(objIdsAsStrings(request.getWantsIds())
				hasItems("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
		assertThat(objIdsAsStrings(request.getPeerHas())
				hasItems("554f6e41067b9e3e565b6988a8294fac1cb78f4b"
						"abc760ab9ad72f08209943251b36cb886a578f87"));
		assertTrue(request.getWantedRefs().isEmpty());
		assertTrue(request.wasDoneReceived());
	}

	@Test
	public void testFetchWithShallow_deepen() throws IOException {
		PacketLineIn pckIn = buildPckIn(
				PacketLineIn.DELIM
				"deepen 15"
				"deepen-relative"
				"shallow 28274d02c489f4c7e68153056e9061a46f62d7a0"
				"shallow 145e683b229dcab9d0e2ccb01b386f9ecc17d29d"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertThat(objIdsAsStrings(request.getClientShallowCommits())
				hasItems("28274d02c489f4c7e68153056e9061a46f62d7a0"
						"145e683b229dcab9d0e2ccb01b386f9ecc17d29d"));
		assertTrue(request.getDeepenNotRefs().isEmpty());
		assertEquals(15
		assertTrue(request.getOptions()
				.contains(GitProtocolConstants.OPTION_DEEPEN_RELATIVE));
	}

	@Test
	public void testFetchWithShallow_deepenNot() throws IOException {
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"shallow 28274d02c489f4c7e68153056e9061a46f62d7a0"
				"shallow 145e683b229dcab9d0e2ccb01b386f9ecc17d29d"
				"deepen-not a08595f76159b09d57553e37a5123f1091bb13e7"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertThat(objIdsAsStrings(request.getClientShallowCommits())
				hasItems("28274d02c489f4c7e68153056e9061a46f62d7a0"
						"145e683b229dcab9d0e2ccb01b386f9ecc17d29d"));
		assertThat(request.getDeepenNotRefs()
				hasItems("a08595f76159b09d57553e37a5123f1091bb13e7"));
	}

	@Test
	public void testFetchWithShallow_deepenSince() throws IOException {
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"shallow 28274d02c489f4c7e68153056e9061a46f62d7a0"
				"shallow 145e683b229dcab9d0e2ccb01b386f9ecc17d29d"
				"deepen-since 123123123"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertThat(objIdsAsStrings(request.getClientShallowCommits())
				hasItems("28274d02c489f4c7e68153056e9061a46f62d7a0"
						"145e683b229dcab9d0e2ccb01b386f9ecc17d29d"));
		assertEquals(123123123
	}

	@Test
	public void testFetchWithNoneFilter() throws IOException {
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowFilter().done());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertEquals(0
	}

	@Test
	public void testFetchWithFilter() throws IOException {
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"filter blob:limit=15"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowFilter().done());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertEquals(15
	}

	@Test
	public void testFetchWithMultipleFilter() throws IOException {
		thrown.expect(PackProtocolException.class);
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"filter blob:limit=12"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowFilter().done());
		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertEquals(0
	}

	@Test
	public void testFetchWithFilterUnsupported() throws IOException {
		thrown.expect(PackProtocolException.class);
		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"filter blob:limit=12"
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
	}

	@Test
	public void testFetchWithRefInWant() throws Exception {
		RevCommit one = testRepo.commit().message("1").create();
		RevCommit two = testRepo.commit().message("2").create();
		testRepo.update("branchA"
		testRepo.update("branchB"

		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"want e4980cdc48cfa1301493ca94eb70523f6788b819"
				"want-ref refs/heads/branchA"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowRefInWant().done());


		FetchV2Request request = parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
		assertEquals(1
		assertThat(request.getWantedRefs().keySet()
				hasItems("refs/heads/branchA"));
		assertEquals(2
		assertThat(objIdsAsStrings(request.getWantsIds())
				hasItems("e4980cdc48cfa1301493ca94eb70523f6788b819"
						one.getName()));
	}

	@Test
	public void testFetchWithRefInWantUnknownRef() throws Exception {
		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("Invalid ref name: refs/heads/branchC");

		PacketLineIn pckIn = buildPckIn(PacketLineIn.DELIM
				"want e4980cdc48cfa1301493ca94eb70523f6788b819"
				"want-ref refs/heads/branchC"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowRefInWant().done());

		RevCommit one = testRepo.commit().message("1").create();
		RevCommit two = testRepo.commit().message("2").create();
		testRepo.update("branchA"
		testRepo.update("branchB"

		parser.parseFetchRequest(pckIn
				testRepo.getRepository().getRefDatabase());
	}

}
