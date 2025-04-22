
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("boxing")
public class RefDirectoryTest extends LocalDiskRepositoryTestCase {
	private Repository diskRepo;

	private TestRepository<Repository> repo;

	private RefDirectory refdir;

	private RevCommit A;

	private RevCommit B;

	private RevTag v1_0;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		diskRepo = createBareRepository();
		refdir = (RefDirectory) diskRepo.getRefDatabase();

		repo = new TestRepository<>(diskRepo);
		A = repo.commit().create();
		B = repo.commit(repo.getRevWalk().parseCommit(A));
		v1_0 = repo.tag("v1_0"
		repo.getRevWalk().parseBody(v1_0);
	}

	@Test
	public void testCreate() throws IOException {
		File d = diskRepo.getDirectory();
		assertSame(diskRepo

		assertTrue(new File(d
		assertTrue(new File(d
		assertTrue(new File(d
		assertFalse(new File(d

		assertTrue(new File(d
		assertTrue(new File(d
		assertEquals(2
		assertEquals(0
		assertEquals(0

		assertTrue(new File(d
		assertFalse(new File(d
		assertEquals(0

		assertEquals("ref: refs/heads/master\n"
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testVersioningNotImplemented_exactRef() throws IOException {
		assertFalse(refdir.hasVersioning());

		Ref ref = refdir.exactRef(HEAD);
		assertNotNull(ref);
	}

	@Test
	public void testVersioningNotImplemented_getRefs() throws Exception {
		assertFalse(refdir.hasVersioning());

		RevCommit C = repo.commit().parent(B).create();
		repo.update("master"
		List<Ref> refs = refdir.getRefs();

		for (Ref ref : refs) {
			try {
				ref.getUpdateIndex();
				fail("FS doesn't implement ref versioning");
			} catch (UnsupportedOperationException e) {
			}
		}
	}

	@Test
	public void testGetRefs_EmptyDatabase() throws IOException {
		Map<String

		all = refdir.getRefs(RefDatabase.ALL);
		assertTrue("no references"

		all = refdir.getRefs(R_HEADS);
		assertTrue("no references"

		all = refdir.getRefs(R_TAGS);
		assertTrue("no references"
	}

	@Test
	public void testGetRefs_HeadOnOneBranch() throws IOException {
		Map<String
		Ref head

		writeLooseRef("refs/heads/master"

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertTrue("has HEAD"
		assertTrue("has master"

		head = all.get(HEAD);
		master = all.get("refs/heads/master");

		assertEquals(HEAD
		assertTrue(head.isSymbolic());
		assertSame(LOOSE
		assertSame("uses same ref as target"

		assertEquals("refs/heads/master"
		assertFalse(master.isSymbolic());
		assertSame(LOOSE
		assertEquals(A
	}

	@Test
	public void testGetRefs_DeatchedHead1() throws IOException {
		Map<String
		Ref head;

		writeLooseRef(HEAD

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1
		assertTrue("has HEAD"

		head = all.get(HEAD);

		assertEquals(HEAD
		assertFalse(head.isSymbolic());
		assertSame(LOOSE
		assertEquals(A
	}

	@Test
	public void testGetRefs_DeatchedHead2() throws IOException {
		Map<String
		Ref head

		writeLooseRef(HEAD
		writeLooseRef("refs/heads/master"

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2

		head = all.get(HEAD);
		master = all.get("refs/heads/master");

		assertEquals(HEAD
		assertFalse(head.isSymbolic());
		assertSame(LOOSE
		assertEquals(A

		assertEquals("refs/heads/master"
		assertFalse(master.isSymbolic());
		assertSame(LOOSE
		assertEquals(B
	}

	@Test
	public void testGetRefs_DeeplyNestedBranch() throws IOException {
		String name = "refs/heads/a/b/c/d/e/f/g/h/i/j/k";
		Map<String
		Ref r;

		writeLooseRef(name

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1

		r = all.get(name);
		assertEquals(name
		assertFalse(r.isSymbolic());
		assertSame(LOOSE
		assertEquals(A
	}

	@Test
	public void testGetRefs_HeadBranchNotBorn() throws IOException {
		Map<String
		Ref a

		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/heads/B"

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertFalse("no HEAD"

		a = all.get("refs/heads/A");
		b = all.get("refs/heads/B");

		assertEquals(A
		assertEquals(B

		assertEquals("refs/heads/A"
		assertEquals("refs/heads/B"
	}

	@Test
	public void testGetRefs_LooseOverridesPacked() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/master"
		writePackedRef("refs/heads/master"

		heads = refdir.getRefs(R_HEADS);
		assertEquals(1

		a = heads.get("master");
		assertEquals("refs/heads/master"
		assertEquals(B
	}

	@Test
	public void testGetRefs_IgnoresGarbageRef1() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1

		a = heads.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testGetRefs_IgnoresGarbageRef2() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1

		a = heads.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testGetRefs_IgnoresGarbageRef3() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1

		a = heads.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testGetRefs_IgnoresGarbageRef4() throws IOException {
		Map<String
		Ref a

		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/heads/B"
		writeLooseRef("refs/heads/C"
		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(3
		assertTrue(heads.containsKey("refs/heads/A"));
		assertTrue(heads.containsKey("refs/heads/B"));
		assertTrue(heads.containsKey("refs/heads/C"));

		writeLooseRef("refs/heads/B"

		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2

		a = heads.get("refs/heads/A");
		b = heads.get("refs/heads/B");
		c = heads.get("refs/heads/C");

		assertEquals("refs/heads/A"
		assertEquals(A

		assertNull("no refs/heads/B"

		assertEquals("refs/heads/C"
		assertEquals(A
	}

	@Test
	public void testFirstExactRef_IgnoresGarbageRef() throws IOException {
		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		Ref a = refdir.firstExactRef("refs/heads/bad"
		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testExactRef_IgnoresGarbageRef() throws IOException {
		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		Map<String
				refdir.exactRef("refs/heads/bad"

		assertNull("no refs/heads/bad"

		Ref a = refs.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A

		assertEquals(1
	}

	@Test
	public void testGetRefs_InvalidName() throws IOException {
		writeLooseRef("refs/heads/A"

		assertTrue("empty refs/heads"
		assertTrue("empty objects"
		assertTrue("empty objects/"
	}

	@Test
	public void testReadNotExistingBranchConfig() throws IOException {
		assertNull("find branch config"
		assertNull("find branch config"
	}

	@Test
	public void testReadBranchConfig() throws IOException {
		writeLooseRef("refs/heads/config"

		assertNotNull("find branch config"
	}

	@Test
	public void testGetRefs_HeadsOnly_AllLoose() throws IOException {
		Map<String
		Ref a

		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/heads/B"
		writeLooseRef("refs/tags/v1.0"

		heads = refdir.getRefs(R_HEADS);
		assertEquals(2

		a = heads.get("A");
		b = heads.get("B");

		assertEquals("refs/heads/A"
		assertEquals("refs/heads/B"

		assertEquals(A
		assertEquals(B
	}

	@Test
	public void testGetRefs_HeadsOnly_AllPacked1() throws IOException {
		Map<String
		Ref a;

		deleteLooseRef(HEAD);
		writePackedRef("refs/heads/A"

		heads = refdir.getRefs(R_HEADS);
		assertEquals(1

		a = heads.get("A");

		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testGetRefs_HeadsOnly_SymrefToPacked() throws IOException {
		Map<String
		Ref master

		writeLooseRef("refs/heads/other"
		writePackedRef("refs/heads/master"

		heads = refdir.getRefs(R_HEADS);
		assertEquals(2

		master = heads.get("master");
		other = heads.get("other");

		assertEquals("refs/heads/master"
		assertEquals(A

		assertEquals("refs/heads/other"
		assertEquals(A
		assertSame(master
	}

	@Test
	public void testGetRefs_HeadsOnly_Mixed() throws IOException {
		Map<String
		Ref a

		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/heads/B"
		writePackedRef("refs/tags/v1.0"

		heads = refdir.getRefs(R_HEADS);
		assertEquals(2

		a = heads.get("A");
		b = heads.get("B");

		assertEquals("refs/heads/A"
		assertEquals("refs/heads/B"

		assertEquals(A
		assertEquals(B
	}

	@Test
	public void testFirstExactRef_Mixed() throws IOException {
		writeLooseRef("refs/heads/A"
		writePackedRef("refs/tags/v1.0"

		Ref a = refdir.firstExactRef("refs/heads/A"
		Ref one = refdir.firstExactRef("refs/tags/v1.0"

		assertEquals("refs/heads/A"
		assertEquals("refs/tags/v1.0"

		assertEquals(A
		assertEquals(v1_0
	}

	@Test
	public void testGetRefs_TagsOnly_AllLoose() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/A"
		writeLooseRef("refs/tags/v1.0"

		tags = refdir.getRefs(R_TAGS);
		assertEquals(1

		a = tags.get("v1.0");

		assertEquals("refs/tags/v1.0"
		assertEquals(v1_0
	}

	@Test
	public void testGetRefs_LooseSortedCorrectly() throws IOException {
		Map<String

		writeLooseRef("refs/heads/project1/A"
		writeLooseRef("refs/heads/project1-B"

		refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(2
		assertEquals(A
		assertEquals(B
	}

	@Test
	public void testGetRefs_LooseSorting_Bug_348834() throws IOException {
		Map<String

		writeLooseRef("refs/heads/my/a+b"
		writeLooseRef("refs/heads/my/a/b/c"

		final int[] count = new int[1];

		ListenerHandle listener = Repository.getGlobalListenerList()
				.addRefsChangedListener(new RefsChangedListener() {

					@Override
					public void onRefsChanged(RefsChangedEvent event) {
						count[0]++;
					}
				});

		refs = refdir.getRefs(RefDatabase.ALL);
		refs = refdir.getRefs(RefDatabase.ALL);
		listener.remove();
		assertEquals(1
		assertEquals(2
		assertEquals(A
		assertEquals(B

	}

	@Test
	public void testGetRefs_TagsOnly_AllPacked() throws IOException {
		Map<String
		Ref a;

		deleteLooseRef(HEAD);
		writePackedRef("refs/tags/v1.0"

		tags = refdir.getRefs(R_TAGS);
		assertEquals(1

		a = tags.get("v1.0");

		assertEquals("refs/tags/v1.0"
		assertEquals(v1_0
	}

	@Test
	public void testGetRefs_DiscoversNewLoose1() throws IOException {
		Map<String
		Ref orig_r

		writeLooseRef("refs/heads/master"
		orig = refdir.getRefs(RefDatabase.ALL);

		writeLooseRef("refs/heads/next"
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(2
		assertEquals(3

		assertFalse(orig.containsKey("refs/heads/next"));
		assertTrue(next.containsKey("refs/heads/next"));

		orig_r = orig.get("refs/heads/master");
		next_r = next.get("refs/heads/master");
		assertEquals(A
		assertSame("uses cached instance"
		assertSame("same HEAD"
		assertSame("same HEAD"

		next_r = next.get("refs/heads/next");
		assertSame(LOOSE
		assertEquals(B
	}

	@Test
	public void testGetRefs_DiscoversNewLoose2() throws IOException {
		Map<String

		writeLooseRef("refs/heads/pu"
		orig = refdir.getRefs(RefDatabase.ALL);

		writeLooseRef("refs/heads/new/B"
		news = refdir.getRefs("refs/heads/new/");
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(1
		assertEquals(2
		assertEquals(1

		assertTrue(orig.containsKey("refs/heads/pu"));
		assertTrue(next.containsKey("refs/heads/pu"));
		assertFalse(news.containsKey("refs/heads/pu"));

		assertFalse(orig.containsKey("refs/heads/new/B"));
		assertTrue(next.containsKey("refs/heads/new/B"));
		assertTrue(news.containsKey("B"));
	}

	@Test
	public void testGetRefs_DiscoversModifiedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(B
	}

	@Test
	public void testFindRef_DiscoversModifiedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		writeLooseRef("refs/heads/master"

		Ref master = refdir.findRef("refs/heads/master");
		assertEquals(B
	}

	@Test
	public void testGetRefs_DiscoversDeletedLoose1() throws IOException {
		Map<String
		Ref orig_r

		writeLooseRef("refs/heads/B"
		writeLooseRef("refs/heads/master"
		orig = refdir.getRefs(RefDatabase.ALL);

		deleteLooseRef("refs/heads/B");
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(3
		assertEquals(2

		assertTrue(orig.containsKey("refs/heads/B"));
		assertFalse(next.containsKey("refs/heads/B"));

		orig_r = orig.get("refs/heads/master");
		next_r = next.get("refs/heads/master");
		assertEquals(A
		assertSame("uses cached instance"
		assertSame("same HEAD"
		assertSame("same HEAD"

		orig_r = orig.get("refs/heads/B");
		assertSame(LOOSE
		assertEquals(B
	}

	@Test
	public void testFindRef_DiscoversDeletedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		deleteLooseRef("refs/heads/master");
		assertNull(refdir.findRef("refs/heads/master"));
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

	@Test
	public void testGetRefs_DiscoversDeletedLoose2() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/pu"
		orig = refdir.getRefs(RefDatabase.ALL);

		deleteLooseRef("refs/heads/pu");
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(3
		assertEquals(2

		assertTrue(orig.containsKey("refs/heads/pu"));
		assertFalse(next.containsKey("refs/heads/pu"));
	}

	@Test
	public void testGetRefs_DiscoversDeletedLoose3() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/next"
		writeLooseRef("refs/heads/pu"
		writeLooseRef("refs/tags/v1.0"
		orig = refdir.getRefs(RefDatabase.ALL);

		deleteLooseRef("refs/heads/pu");
		deleteLooseRef("refs/heads/next");
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(5
		assertEquals(3

		assertTrue(orig.containsKey("refs/heads/pu"));
		assertTrue(orig.containsKey("refs/heads/next"));
		assertFalse(next.containsKey("refs/heads/pu"));
		assertFalse(next.containsKey("refs/heads/next"));
	}

	@Test
	public void testGetRefs_DiscoversDeletedLoose4() throws IOException {
		Map<String
		Ref orig_r

		writeLooseRef("refs/heads/B"
		writeLooseRef("refs/heads/master"
		orig = refdir.getRefs(RefDatabase.ALL);

		deleteLooseRef("refs/heads/master");
		next = refdir.getRefs("refs/heads/");

		assertEquals(3
		assertEquals(1

		assertTrue(orig.containsKey("refs/heads/B"));
		assertTrue(orig.containsKey("refs/heads/master"));
		assertTrue(next.containsKey("B"));
		assertFalse(next.containsKey("master"));

		orig_r = orig.get("refs/heads/B");
		next_r = next.get("B");
		assertEquals(B
		assertSame("uses cached instance"
	}

	@Test
	public void testGetRefs_DiscoversDeletedLoose5() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/pu"
		orig = refdir.getRefs(RefDatabase.ALL);

		deleteLooseRef("refs/heads/pu");
		writeLooseRef("refs/tags/v1.0"
		next = refdir.getRefs(RefDatabase.ALL);

		assertEquals(3
		assertEquals(3

		assertTrue(orig.containsKey("refs/heads/pu"));
		assertFalse(orig.containsKey("refs/tags/v1.0"));
		assertFalse(next.containsKey("refs/heads/pu"));
		assertTrue(next.containsKey("refs/tags/v1.0"));
	}

	@Test
	public void testGetRefs_SkipsLockFiles() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/pu.lock"
		all = refdir.getRefs(RefDatabase.ALL);

		assertEquals(2

		assertTrue(all.containsKey(HEAD));
		assertTrue(all.containsKey("refs/heads/master"));
		assertFalse(all.containsKey("refs/heads/pu.lock"));
	}

	@Test
	public void testGetRefs_CycleInSymbolicRef() throws IOException {
		Map<String
		Ref r;

		writeLooseRef("refs/1"
		writeLooseRef("refs/2"
		writeLooseRef("refs/3"
		writeLooseRef("refs/4"
		writeLooseRef("refs/5"
		writeLooseRef("refs/end"

		all = refdir.getRefs(RefDatabase.ALL);
		r = all.get("refs/1");
		assertNotNull("has 1"

		assertEquals("refs/1"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/2"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/3"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/4"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/5"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/end"
		assertEquals(A
		assertFalse(r.isSymbolic());

		writeLooseRef("refs/5"
		writeLooseRef("refs/6"
		all = refdir.getRefs(RefDatabase.ALL);
		r = all.get("refs/1");
		assertNull("mising 1 due to cycle"
	}

	@Test
	public void testFindRef_CycleInSymbolicRef() throws IOException {
		Ref r;

		writeLooseRef("refs/1"
		writeLooseRef("refs/2"
		writeLooseRef("refs/3"
		writeLooseRef("refs/4"
		writeLooseRef("refs/5"
		writeLooseRef("refs/end"

		r = refdir.findRef("1");
		assertEquals("refs/1"
		assertEquals(A
		assertTrue(r.isSymbolic());

		writeLooseRef("refs/5"
		writeLooseRef("refs/6"

		r = refdir.findRef("1");
		assertNull("missing 1 due to cycle"

		writeLooseRef("refs/heads/1"

		r = refdir.findRef("1");
		assertEquals("refs/heads/1"
		assertEquals(B
		assertFalse(r.isSymbolic());
	}

	@Test
	public void testGetRefs_PackedNotPeeled_Sorted() throws IOException {
		Map<String

				v1_0.name() + " refs/tags/v1.0\n");
		all = refdir.getRefs(RefDatabase.ALL);

		assertEquals(4
		final Ref head = all.get(HEAD);
		final Ref master = all.get("refs/heads/master");
		final Ref other = all.get("refs/heads/other");
		final Ref tag = all.get("refs/tags/v1.0");

		assertEquals(A
		assertFalse(master.isPeeled());
		assertNull(master.getPeeledObjectId());

		assertEquals(B
		assertFalse(other.isPeeled());
		assertNull(other.getPeeledObjectId());

		assertSame(master
		assertEquals(A
		assertFalse(head.isPeeled());
		assertNull(head.getPeeledObjectId());

		assertEquals(v1_0
		assertFalse(tag.isPeeled());
		assertNull(tag.getPeeledObjectId());
	}

	@Test
	public void testFindRef_PackedNotPeeled_WrongSort() throws IOException {
				A.name() + " refs/heads/master\n");

		final Ref head = refdir.findRef(HEAD);
		final Ref master = refdir.findRef("refs/heads/master");
		final Ref other = refdir.findRef("refs/heads/other");
		final Ref tag = refdir.findRef("refs/tags/v1.0");

		assertEquals(A
		assertFalse(master.isPeeled());
		assertNull(master.getPeeledObjectId());

		assertEquals(B
		assertFalse(other.isPeeled());
		assertNull(other.getPeeledObjectId());

		assertSame(master
		assertEquals(A
		assertFalse(head.isPeeled());
		assertNull(head.getPeeledObjectId());

		assertEquals(v1_0
		assertFalse(tag.isPeeled());
		assertNull(tag.getPeeledObjectId());
	}

	@Test
	public void testGetRefs_PackedWithPeeled() throws IOException {
		Map<String

				"^" + v1_0.getObject().name() + "\n");
		all = refdir.getRefs(RefDatabase.ALL);

		assertEquals(4
		final Ref head = all.get(HEAD);
		final Ref master = all.get("refs/heads/master");
		final Ref other = all.get("refs/heads/other");
		final Ref tag = all.get("refs/tags/v1.0");

		assertEquals(A
		assertTrue(master.isPeeled());
		assertNull(master.getPeeledObjectId());

		assertEquals(B
		assertTrue(other.isPeeled());
		assertNull(other.getPeeledObjectId());

		assertSame(master
		assertEquals(A
		assertTrue(head.isPeeled());
		assertNull(head.getPeeledObjectId());

		assertEquals(v1_0
		assertTrue(tag.isPeeled());
		assertEquals(v1_0.getObject()
	}

	@Test
	public void test_repack() throws Exception {
		Map<String

				"^" + v1_0.getObject().name() + "\n");
		all = refdir.getRefs(RefDatabase.ALL);

		assertEquals(4
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(A.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED

		repo.update("refs/heads/master"
		RevTag v0_1 = repo.tag("v0.1"
		repo.update("refs/tags/v0.1"

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(5
		assertEquals(Storage.LOOSE
		assertEquals(Storage.LOOSE
				.getStorage());
		assertEquals(B.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(Storage.LOOSE
		assertEquals(v0_1.getId()

		all = refdir.getRefs(RefDatabase.ALL);
		refdir.pack(new ArrayList<>(all.keySet()));

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(5
		assertEquals(Storage.LOOSE
		assertEquals(Storage.PACKED
		assertEquals(B.getId()
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(Storage.PACKED
		assertEquals(v0_1.getId()
	}

	@Test
	public void testFindRef_EmptyDatabase() throws IOException {
		Ref r;

		r = refdir.findRef(HEAD);
		assertTrue(r.isSymbolic());
		assertSame(LOOSE
		assertEquals("refs/heads/master"
		assertSame(NEW
		assertNull(r.getTarget().getObjectId());

		assertNull(refdir.findRef("refs/heads/master"));
		assertNull(refdir.findRef("refs/tags/v1.0"));
		assertNull(refdir.findRef("FETCH_HEAD"));
		assertNull(refdir.findRef("NOT.A.REF.NAME"));
		assertNull(refdir.findRef("master"));
		assertNull(refdir.findRef("v1.0"));
	}

	@Test
	public void testExactRef_EmptyDatabase() throws IOException {
		Ref r;

		r = refdir.exactRef(HEAD);
		assertTrue(r.isSymbolic());
		assertSame(LOOSE
		assertEquals("refs/heads/master"
		assertSame(NEW
		assertNull(r.getTarget().getObjectId());

		assertNull(refdir.exactRef("refs/heads/master"));
		assertNull(refdir.exactRef("refs/tags/v1.0"));
		assertNull(refdir.exactRef("FETCH_HEAD"));
		assertNull(refdir.exactRef("NOT.A.REF.NAME"));
		assertNull(refdir.exactRef("master"));
		assertNull(refdir.exactRef("v1.0"));
	}

	@Test
	public void testGetAdditionalRefs_OrigHead() throws IOException {
		writeLooseRef("ORIG_HEAD"

		List<Ref> refs = refdir.getAdditionalRefs();
		assertEquals(1

		Ref r = refs.get(0);
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("ORIG_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	@Test
	public void testGetAdditionalRefs_OrigHeadBranch() throws IOException {
		writeLooseRef("refs/heads/ORIG_HEAD"
		List<Ref> refs = refdir.getAdditionalRefs();
		assertArrayEquals(new Ref[0]
	}

	@Test
	public void testFindRef_FetchHead() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "\tnot-for-merge"

		Ref r = refdir.findRef("FETCH_HEAD");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("FETCH_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	@Test
	public void testExactRef_FetchHead() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "\tnot-for-merge"

		Ref r = refdir.exactRef("FETCH_HEAD");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("FETCH_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	@Test
	public void testFindRef_AnyHeadWithGarbage() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "012345 . this is not a standard reference\n"
				+ "#and even more junk\n");

		Ref r = refdir.findRef("refs/heads/A");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("refs/heads/A"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	@Test
	public void testGetRefs_CorruptSymbolicReference() throws IOException {
		String name = "refs/heads/A";
		writeLooseRef(name
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

	@Test
	public void testFindRef_CorruptSymbolicReference() throws IOException {
		String name = "refs/heads/A";
		writeLooseRef(name
		try {
			refdir.findRef(name);
			fail("read an invalid reference");
		} catch (IOException err) {
			String msg = err.getMessage();
			assertEquals("Not a ref: " + name + ": ref:"
		}
	}

	@Test
	public void testGetRefs_CorruptObjectIdReference() throws IOException {
		String name = "refs/heads/A";
		String content = "zoo" + A.name();
		writeLooseRef(name
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

	@Test
	public void testFindRef_CorruptObjectIdReference() throws IOException {
		String name = "refs/heads/A";
		String content = "zoo" + A.name();
		writeLooseRef(name
		try {
			refdir.findRef(name);
			fail("read an invalid reference");
		} catch (IOException err) {
			String msg = err.getMessage();
			assertEquals("Not a ref: " + name + ": " + content
		}
	}

	@Test
	public void testIsNameConflicting() throws IOException {
		writeLooseRef("refs/heads/a/b"
		writePackedRef("refs/heads/q"

		assertTrue(refdir.isNameConflicting("refs"));
		assertTrue(refdir.isNameConflicting("refs/heads"));
		assertTrue(refdir.isNameConflicting("refs/heads/a"));

		assertFalse(refdir.isNameConflicting("refs/heads/a/b"));

		assertFalse(refdir.isNameConflicting("refs/heads/a/d"));
		assertFalse(refdir.isNameConflicting("refs/heads/master"));

		assertTrue(refdir.isNameConflicting("refs/heads/a/b/c"));
		assertTrue(refdir.isNameConflicting("refs/heads/q/master"));
	}

	@Test
	public void testPeelLooseTag() throws IOException {
		writeLooseRef("refs/tags/v1_0"
		writeLooseRef("refs/tags/current"

		final Ref tag = refdir.findRef("refs/tags/v1_0");
		final Ref cur = refdir.findRef("refs/tags/current");

		assertEquals(v1_0
		assertFalse(tag.isSymbolic());
		assertFalse(tag.isPeeled());
		assertNull(tag.getPeeledObjectId());

		assertEquals(v1_0
		assertTrue(cur.isSymbolic());
		assertFalse(cur.isPeeled());
		assertNull(cur.getPeeledObjectId());

		final Ref tag_p = refdir.peel(tag);
		final Ref cur_p = refdir.peel(cur);

		assertNotSame(tag
		assertFalse(tag_p.isSymbolic());
		assertTrue(tag_p.isPeeled());
		assertEquals(v1_0
		assertEquals(v1_0.getObject()
		assertSame(tag_p

		assertNotSame(cur
		assertEquals("refs/tags/current"
		assertTrue(cur_p.isSymbolic());
		assertEquals("refs/tags/v1_0"
		assertTrue(cur_p.isPeeled());
		assertEquals(v1_0
		assertEquals(v1_0.getObject()

		final Ref tag_p2 = refdir.findRef("refs/tags/v1_0");
		assertFalse(tag_p2.isSymbolic());
		assertTrue(tag_p2.isPeeled());
		assertEquals(v1_0
		assertEquals(v1_0.getObject()

		assertSame(tag_p2
		assertSame(tag_p2
		assertSame(tag_p2
	}

	@Test
	public void testPeelCommit() throws IOException {
		writeLooseRef("refs/heads/master"

		Ref master = refdir.findRef("refs/heads/master");
		assertEquals(A
		assertFalse(master.isPeeled());
		assertNull(master.getPeeledObjectId());

		Ref master_p = refdir.peel(master);
		assertNotSame(master
		assertEquals(A
		assertTrue(master_p.isPeeled());
		assertNull(master_p.getPeeledObjectId());

		Ref master_p2 = refdir.findRef("refs/heads/master");
		assertNotSame(master
		assertEquals(A
		assertTrue(master_p2.isPeeled());
		assertNull(master_p2.getPeeledObjectId());
		assertSame(master_p2
	}

	@Test
	public void testRefsChangedStackOverflow() throws Exception {
		final FileRepository newRepo = createBareRepository();
		final RefDatabase refDb = newRepo.getRefDatabase();
		File packedRefs = new File(newRepo.getDirectory()
		assertTrue(packedRefs.createNewFile());
		final AtomicReference<StackOverflowError> error = new AtomicReference<>();
		final AtomicReference<IOException> exception = new AtomicReference<>();
		final AtomicInteger changeCount = new AtomicInteger();
		newRepo.getListenerList().addRefsChangedListener(
				new RefsChangedListener() {

					@Override
					public void onRefsChanged(RefsChangedEvent event) {
						try {
							refDb.getRefsByPrefix("ref");
							changeCount.incrementAndGet();
						} catch (StackOverflowError soe) {
							error.set(soe);
						} catch (IOException ioe) {
							exception.set(ioe);
						}
					}
				});
		refDb.getRefsByPrefix("ref");
		refDb.getRefsByPrefix("ref");
		assertNull(error.get());
		assertNull(exception.get());
		assertEquals(1
	}

	@Test
	public void testPackedRefsLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"
		refdir.setRetrySleepMs(Arrays.asList(0
		LockFile myLock = refdir.lockPackedRefs();
		try {
			refdir.pack(Arrays.asList("refs/heads/master"));
			fail("expected LockFailedException");
		} catch (LockFailedException e) {
			assertEquals(refdir.packedRefsFile.getPath()
		} finally {
			myLock.unlock();
		}
		Ref ref = refdir.findRef("refs/heads/master");
		assertEquals(Storage.LOOSE
	}

	private void writeLooseRef(String name
		writeLooseRef(name
	}

	private void writeLooseRef(String name
		write(new File(diskRepo.getDirectory()
	}

	private void writePackedRef(String name
		writePackedRefs(id.name() + " " + name + "\n");
	}

	private void writePackedRefs(String content) throws IOException {
		File pr = new File(diskRepo.getDirectory()
		write(pr

		final long now = System.currentTimeMillis();
		final int oneHourAgo = 3600 * 1000;
		pr.setLastModified(now - oneHourAgo);
	}

	private void deleteLooseRef(String name) {
		File path = new File(diskRepo.getDirectory()
		assertTrue("deleted " + name
	}
}
