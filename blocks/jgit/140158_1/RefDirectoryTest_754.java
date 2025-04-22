
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.internal.storage.pack.PackWriter.NONE;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.transport.PackParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PackWriterTest extends SampleDataRepositoryTestCase {

	private static final List<RevObject> EMPTY_LIST_REVS = Collections
			.<RevObject> emptyList();

	private static final Set<ObjectIdSet> EMPTY_ID_SET = Collections
			.<ObjectIdSet> emptySet();

	private PackConfig config;

	private PackWriter writer;

	private ByteArrayOutputStream os;

	private PackFile pack;

	private ObjectInserter inserter;

	private FileRepository dst;

	private RevBlob contentA;

	private RevBlob contentB;

	private RevBlob contentC;

	private RevBlob contentD;

	private RevBlob contentE;

	private RevCommit c1;

	private RevCommit c2;

	private RevCommit c3;

	private RevCommit c4;

	private RevCommit c5;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		os = new ByteArrayOutputStream();
		config = new PackConfig(db);

		dst = createBareRepository();
		File alt = new File(dst.getObjectDatabase().getDirectory()
		alt.getParentFile().mkdirs();
		write(alt
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (writer != null) {
			writer.close();
			writer = null;
		}
		if (inserter != null) {
			inserter.close();
			inserter = null;
		}
		super.tearDown();
	}

	@Test
	public void testContructor() throws IOException {
		writer = new PackWriter(config
		assertFalse(writer.isDeltaBaseAsOffset());
		assertTrue(config.isReuseDeltas());
		assertTrue(config.isReuseObjects());
		assertEquals(0
	}

	@Test
	public void testModifySettings() {
		config.setReuseDeltas(false);
		config.setReuseObjects(false);
		config.setDeltaBaseAsOffset(false);
		assertFalse(config.isReuseDeltas());
		assertFalse(config.isReuseObjects());
		assertFalse(config.isDeltaBaseAsOffset());

		writer = new PackWriter(config
		writer.setDeltaBaseAsOffset(true);
		assertTrue(writer.isDeltaBaseAsOffset());
		assertFalse(config.isDeltaBaseAsOffset());
	}

	@Test
	public void testWriteEmptyPack1() throws IOException {
		createVerifyOpenPack(NONE

		assertEquals(0
		assertEquals(0
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709"
				.computeName().name());
	}

	@Test
	public void testWriteEmptyPack2() throws IOException {
		createVerifyOpenPack(EMPTY_LIST_REVS);

		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testNotIgnoreNonExistingObjects() throws IOException {
		final ObjectId nonExisting = ObjectId
				.fromString("0000000000000000000000000000000000000001");
		try {
			createVerifyOpenPack(NONE
			fail("Should have thrown MissingObjectException");
		} catch (MissingObjectException x) {
		}
	}

	@Test
	public void testIgnoreNonExistingObjects() throws IOException {
		final ObjectId nonExisting = ObjectId
				.fromString("0000000000000000000000000000000000000001");
		createVerifyOpenPack(NONE
	}

	@Test
	public void testIgnoreNonExistingObjectsWithBitmaps() throws IOException
			ParseException {
		final ObjectId nonExisting = ObjectId
				.fromString("0000000000000000000000000000000000000001");
		new GC(db).gc();
		createVerifyOpenPack(NONE
	}

	@Test
	public void testWritePack1() throws IOException {
		config.setReuseDeltas(false);
		writeVerifyPack1();
	}

	@Test
	public void testWritePack1NoObjectReuse() throws IOException {
		config.setReuseDeltas(false);
		config.setReuseObjects(false);
		writeVerifyPack1();
	}

	@Test
	public void testWritePack2() throws IOException {
		writeVerifyPack2(false);
	}

	@Test
	public void testWritePack2DeltasReuseRefs() throws IOException {
		writeVerifyPack2(true);
	}

	@Test
	public void testWritePack2DeltasReuseOffsets() throws IOException {
		config.setDeltaBaseAsOffset(true);
		writeVerifyPack2(true);
	}

	@Test
	public void testWritePack2DeltasCRC32Copy() throws IOException {
		final File packDir = db.getObjectDatabase().getPackDirectory();
		final File crc32Pack = new File(packDir
				"pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f.pack");
		final File crc32Idx = new File(packDir
				"pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f.idx");
		copyFile(JGitTestUtil.getTestResourceFile(
				"pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f.idxV2")
				crc32Idx);
		db.openPack(crc32Pack);

		writeVerifyPack2(true);
	}

	@Test
	public void testWritePack3() throws MissingObjectException
		config.setReuseDeltas(false);
		final ObjectId forcedOrder[] = new ObjectId[] {
				ObjectId.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")
				ObjectId.fromString("c59759f143fb1fe21c197981df75a7ee00290799")
				ObjectId.fromString("aabf2ffaec9b497f0950352b3e582d73035c2035")
				ObjectId.fromString("902d5476fa249b7abc9d84c611577a81381f0327")
				ObjectId.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3") 
				ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259") };
		try (RevWalk parser = new RevWalk(db)) {
			final RevObject forcedOrderRevs[] = new RevObject[forcedOrder.length];
			for (int i = 0; i < forcedOrder.length; i++)
				forcedOrderRevs[i] = parser.parseAny(forcedOrder[i]);

			createVerifyOpenPack(Arrays.asList(forcedOrderRevs));
		}

		assertEquals(forcedOrder.length
		verifyObjectsOrder(forcedOrder);
		assertEquals("ed3f96b8327c7c66b0f8f70056129f0769323d86"
				.computeName().name());
	}

	@Test
	public void testWritePack4() throws IOException {
		writeVerifyPack4(false);
	}

	@Test
	public void testWritePack4ThinPack() throws IOException {
		writeVerifyPack4(true);
	}

	@Test
	public void testWritePack2SizeDeltasVsNoDeltas() throws Exception {
		config.setReuseDeltas(false);
		config.setDeltaCompress(false);
		testWritePack2();
		final long sizePack2NoDeltas = os.size();
		tearDown();
		setUp();
		testWritePack2DeltasReuseRefs();
		final long sizePack2DeltasRefs = os.size();

		assertTrue(sizePack2NoDeltas > sizePack2DeltasRefs);
	}

	@Test
	public void testWritePack2SizeOffsetsVsRefs() throws Exception {
		testWritePack2DeltasReuseRefs();
		final long sizePack2DeltasRefs = os.size();
		tearDown();
		setUp();
		testWritePack2DeltasReuseOffsets();
		final long sizePack2DeltasOffsets = os.size();

		assertTrue(sizePack2DeltasRefs > sizePack2DeltasOffsets);
	}

	@Test
	public void testWritePack4SizeThinVsNoThin() throws Exception {
		testWritePack4();
		final long sizePack4 = os.size();
		tearDown();
		setUp();
		testWritePack4ThinPack();
		final long sizePack4Thin = os.size();

		assertTrue(sizePack4 > sizePack4Thin);
	}

	@Test
	public void testDeltaStatistics() throws Exception {
		config.setDeltaCompress(true);
		FileRepository repo = createBareRepository();
		ArrayList<RevObject> blobs = new ArrayList<>();
		try (TestRepository<FileRepository> testRepo = new TestRepository<>(
				repo)) {
			blobs.add(testRepo.blob(genDeltableData(1000)));
			blobs.add(testRepo.blob(genDeltableData(1005)));
		}

		try (PackWriter pw = new PackWriter(repo)) {
			NullProgressMonitor m = NullProgressMonitor.INSTANCE;
			pw.preparePack(blobs.iterator());
			pw.writePack(m
			PackStatistics stats = pw.getStatistics();
			assertEquals(1
			assertTrue("Delta bytes not set."
					stats.byObjectType(OBJ_BLOB).getDeltaBytes() > 0);
		}
	}

	private String genDeltableData(int length) {
		assertTrue("Generated data must have a length > 0"
		char[] data = {'a'
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(data[i % 4]);
		}
		return builder.toString();
	}


	@Test
	public void testWriteIndex() throws Exception {
		config.setIndexVersion(2);
		writeVerifyPack4(false);

		File packFile = pack.getPackFile();
		String name = packFile.getName();
		String base = name.substring(0
		File indexFile = new File(packFile.getParentFile()

		final PackIndex idx1 = PackIndex.open(indexFile);
		assertTrue(idx1 instanceof PackIndexV2);
		assertEquals(0x4743F1E4L
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")));

		final File idx2File = new File(indexFile.getAbsolutePath() + ".2");
		try (FileOutputStream is = new FileOutputStream(idx2File)) {
			writer.writeIndex(is);
		}
		final PackIndex idx2 = PackIndex.open(idx2File);
		assertTrue(idx2 instanceof PackIndexV2);
		assertEquals(idx1.getObjectCount()
		assertEquals(idx1.getOffset64Count()

		for (int i = 0; i < idx1.getObjectCount(); i++) {
			final ObjectId id = idx1.getObjectId(i);
			assertEquals(id
			assertEquals(idx1.findOffset(id)
			assertEquals(idx1.findCRC32(id)
		}
	}

	@Test
	public void testExclude() throws Exception {
		FileRepository repo = createBareRepository();

		try (TestRepository<FileRepository> testRepo = new TestRepository<>(
				repo)) {
			BranchBuilder bb = testRepo.branch("refs/heads/master");
			contentA = testRepo.blob("A");
			c1 = bb.commit().add("f"
			testRepo.getRevWalk().parseHeaders(c1);
			PackIndex pf1 = writePack(repo
			assertContent(pf1
					contentA.getId()));
			contentB = testRepo.blob("B");
			c2 = bb.commit().add("f"
			testRepo.getRevWalk().parseHeaders(c2);
			PackIndex pf2 = writePack(repo
					Sets.of((ObjectIdSet) pf1));
			assertContent(pf2
					contentB.getId()));
		}
	}

	private static void assertContent(PackIndex pi
		assertEquals("Pack index has wrong size."
				pi.getObjectCount());
		for (int i = 0; i < pi.getObjectCount(); i++)
			assertTrue(
					"Pack index didn't contain the expected id "
							+ pi.getObjectId(i)
					expected.contains(pi.getObjectId(i)));
	}

	@Test
	public void testShallowIsMinimalDepth1() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				contentA.getId()

		idx = writeShallowPack(repo
		assertContent(idx
				contentC.getId()
	}

	@Test
	public void testShallowIsMinimalDepth2() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c1.getId()
						c2.getTree().getId()
						contentB.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentD.getId()
	}

	@Test
	public void testShallowFetchShallowParentDepth1() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c5.getId()
						contentA.getId()
						contentD.getId()

		idx = writeShallowPack(repo
		assertContent(idx
	}

	@Test
	public void testShallowFetchShallowParentDepth2() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentB.getId()
						contentE.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				c2.getTree().getId()
	}

	@Test
	public void testShallowFetchShallowAncestorDepth1() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c5.getId()
						contentA.getId()
						contentD.getId()

		idx = writeShallowPack(repo
		assertContent(idx
	}

	@Test
	public void testShallowFetchShallowAncestorDepth2() throws Exception {
		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo
		assertContent(idx
				Arrays.asList(c4.getId()
						c5.getTree().getId()
						contentB.getId()
						contentE.getId()));

		idx = writeShallowPack(repo
		assertContent(idx
				c1.getTree().getId()
	}

	private FileRepository setupRepoForShallowFetch() throws Exception {
		FileRepository repo = createBareRepository();
		try (TestRepository<Repository> r = new TestRepository<>(repo)) {
			BranchBuilder bb = r.branch("refs/heads/master");
			contentA = r.blob("A");
			contentB = r.blob("B");
			contentC = r.blob("C");
			contentD = r.blob("D");
			contentE = r.blob("E");
			c1 = bb.commit().add("a"
			c2 = bb.commit().add("b"
			c3 = bb.commit().add("c"
			c4 = bb.commit().add("d"
			c5 = bb.commit().add("e"
			return repo;
		}
	}

	private static PackIndex writePack(FileRepository repo
			Set<? extends ObjectId> want
					throws IOException {
		RevWalk walk = new RevWalk(repo);
		return writePack(repo
	}

	private static PackIndex writeShallowPack(FileRepository repo
			Set<? extends ObjectId> want
			Set<? extends ObjectId> shallow) throws IOException {
		DepthWalk.RevWalk walk = new DepthWalk.RevWalk(repo
		walk.assumeShallow(shallow);
		return writePack(repo
	}

	private static PackIndex writePack(FileRepository repo
			int depth
			Set<? extends ObjectId> have
					throws IOException {
		try (PackWriter pw = new PackWriter(repo)) {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);
			for (ObjectIdSet idx : excludeObjects) {
				pw.excludeObjects(idx);
			}
			if (depth > 0) {
				pw.setShallowPack(depth
			}
			ObjectWalk ow = walk.toObjectWalkWithSameObjects();

			pw.preparePack(NullProgressMonitor.INSTANCE
			String id = pw.computeName().getName();
			File packdir = repo.getObjectDatabase().getPackDirectory();
			File packFile = new File(packdir
			try (FileOutputStream packOS = new FileOutputStream(packFile)) {
				pw.writePack(NullProgressMonitor.INSTANCE
						NullProgressMonitor.INSTANCE
			}
			File idxFile = new File(packdir
			try (FileOutputStream idxOS = new FileOutputStream(idxFile)) {
				pw.writeIndex(idxOS);
			}
			return PackIndex.open(idxFile);
		}
	}


	private void writeVerifyPack1() throws IOException {
		final HashSet<ObjectId> interestings = new HashSet<>();
		interestings.add(ObjectId
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7"));
		createVerifyOpenPack(interestings

		final ObjectId expectedOrder[] = new ObjectId[] {
				ObjectId.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")
				ObjectId.fromString("c59759f143fb1fe21c197981df75a7ee00290799")
				ObjectId.fromString("540a36d136cf413e4b064c2b0e0a4db60f77feab")
				ObjectId.fromString("aabf2ffaec9b497f0950352b3e582d73035c2035")
				ObjectId.fromString("902d5476fa249b7abc9d84c611577a81381f0327")
				ObjectId.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904")
				ObjectId.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3")
				ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259") };

		assertEquals(expectedOrder.length
		verifyObjectsOrder(expectedOrder);
		assertEquals("34be9032ac282b11fa9babdc2b2a93ca996c9c2f"
				.computeName().name());
	}

	private void writeVerifyPack2(boolean deltaReuse) throws IOException {
		config.setReuseDeltas(deltaReuse);
		final HashSet<ObjectId> interestings = new HashSet<>();
		interestings.add(ObjectId
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7"));
		final HashSet<ObjectId> uninterestings = new HashSet<>();
		uninterestings.add(ObjectId
				.fromString("540a36d136cf413e4b064c2b0e0a4db60f77feab"));
		createVerifyOpenPack(interestings

		final ObjectId expectedOrder[] = new ObjectId[] {
				ObjectId.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")
				ObjectId.fromString("c59759f143fb1fe21c197981df75a7ee00290799")
				ObjectId.fromString("aabf2ffaec9b497f0950352b3e582d73035c2035")
				ObjectId.fromString("902d5476fa249b7abc9d84c611577a81381f0327")
				ObjectId.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3") 
				ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259") };
		if (!config.isReuseDeltas() && !config.isDeltaCompress()) {
			swap(expectedOrder
		}
		assertEquals(expectedOrder.length
		verifyObjectsOrder(expectedOrder);
		assertEquals("ed3f96b8327c7c66b0f8f70056129f0769323d86"
				.computeName().name());
	}

	private static void swap(ObjectId[] arr
		ObjectId tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	private void writeVerifyPack4(final boolean thin) throws IOException {
		final HashSet<ObjectId> interestings = new HashSet<>();
		interestings.add(ObjectId
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7"));
		final HashSet<ObjectId> uninterestings = new HashSet<>();
		uninterestings.add(ObjectId
				.fromString("c59759f143fb1fe21c197981df75a7ee00290799"));
		createVerifyOpenPack(interestings

		final ObjectId writtenObjects[] = new ObjectId[] {
				ObjectId.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")
				ObjectId.fromString("aabf2ffaec9b497f0950352b3e582d73035c2035")
				ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259") };
		assertEquals(writtenObjects.length
		ObjectId expectedObjects[];
		if (thin) {
			expectedObjects = new ObjectId[4];
			System.arraycopy(writtenObjects
					writtenObjects.length);
			expectedObjects[3] = ObjectId
					.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3");

		} else {
			expectedObjects = writtenObjects;
		}
		verifyObjectsOrder(expectedObjects);
		assertEquals("cded4b74176b4456afa456768b2b5aafb41c44fc"
				.computeName().name());
	}

	private void createVerifyOpenPack(final Set<ObjectId> interestings
			final Set<ObjectId> uninterestings
			final boolean ignoreMissingUninteresting)
			throws MissingObjectException
		createVerifyOpenPack(interestings
				ignoreMissingUninteresting
	}

	private void createVerifyOpenPack(final Set<ObjectId> interestings
			final Set<ObjectId> uninterestings
			final boolean ignoreMissingUninteresting
			throws MissingObjectException
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new PackWriter(config
		writer.setUseBitmaps(useBitmaps);
		writer.setThin(thin);
		writer.setIgnoreMissingUninteresting(ignoreMissingUninteresting);
		writer.preparePack(m
		writer.writePack(m
		writer.close();
		verifyOpenPack(thin);
	}

	private void createVerifyOpenPack(List<RevObject> objectSource)
			throws MissingObjectException
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new PackWriter(config
		writer.preparePack(objectSource.iterator());
		assertEquals(objectSource.size()
		writer.writePack(m
		writer.close();
		verifyOpenPack(false);
	}

	private void verifyOpenPack(boolean thin) throws IOException {
		final byte[] packData = os.toByteArray();

		if (thin) {
			PackParser p = index(packData);
			try {
				p.parse(NullProgressMonitor.INSTANCE);
				fail("indexer should grumble about missing object");
			} catch (IOException x) {
			}
		}

		ObjectDirectoryPackParser p = (ObjectDirectoryPackParser) index(packData);
		p.setKeepEmpty(true);
		p.setAllowThin(thin);
		p.setIndexVersion(2);
		p.parse(NullProgressMonitor.INSTANCE);
		pack = p.getPackFile();
		assertNotNull("have PackFile after parsing"
	}

	private PackParser index(byte[] packData) throws IOException {
		if (inserter == null)
			inserter = dst.newObjectInserter();
		return inserter.newPackParser(new ByteArrayInputStream(packData));
	}

	private void verifyObjectsOrder(ObjectId objectsOrder[]) {
		final List<PackIndex.MutableEntry> entries = new ArrayList<>();

		for (MutableEntry me : pack) {
			entries.add(me.cloneEntry());
		}
		Collections.sort(entries
			@Override
			public int compare(MutableEntry o1
				return Long.signum(o1.getOffset() - o2.getOffset());
			}
		});

		int i = 0;
		for (MutableEntry me : entries) {
			assertEquals(objectsOrder[i++].toObjectId()
		}
	}

	private static Set<ObjectId> haves(ObjectId... objects) {
		return Sets.of(objects);
	}

	private static Set<ObjectId> wants(ObjectId... objects) {
		return Sets.of(objects);
	}

	private static Set<ObjectId> shallows(ObjectId... objects) {
		return Sets.of(objects);
	}
}
