
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;

public class RefDirectoryTest extends LocalDiskRepositoryTestCase {
	private Repository diskRepo;

	private TestRepository repo;

	private RefDirectory refdir;

	private RevCommit A;

	private RevCommit B;

	private RevTag v1_0;

	protected void setUp() throws Exception {
		super.setUp();

		diskRepo = createBareRepository();
		refdir = (RefDirectory) diskRepo.getRefDatabase();

		repo = new TestRepository(diskRepo);
		A = repo.commit().create();
		B = repo.commit(repo.getRevWalk().parseCommit(A));
		v1_0 = repo.tag("v1_0"
		repo.getRevWalk().parseBody(v1_0);
	}

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

	public void testGetRefs_EmptyDatabase() throws IOException {
		Map<String

		all = refdir.getRefs(RefDatabase.ALL);
		assertTrue("no references"

		all = refdir.getRefs(R_HEADS);
		assertTrue("no references"

		all = refdir.getRefs(R_TAGS);
		assertTrue("no references"
	}

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

	public void testGetRefs_DeatchedHead1() throws IOException {
		Map<String
		Ref head;

		writeLooseRef(HEAD
		BUG_WorkAroundRacyGitIssues(HEAD);

		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1
		assertTrue("has HEAD"

		head = all.get(HEAD);

		assertEquals(HEAD
		assertFalse(head.isSymbolic());
		assertSame(LOOSE
		assertEquals(A
	}

	public void testGetRefs_DeatchedHead2() throws IOException {
		Map<String
		Ref head

		writeLooseRef(HEAD
		writeLooseRef("refs/heads/master"
		BUG_WorkAroundRacyGitIssues(HEAD);

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
		BUG_WorkAroundRacyGitIssues("refs/heads/B");

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

	public void testGetRefs_InvalidName() throws IOException {
		writeLooseRef("refs/heads/A"

		assertTrue("empty refs/heads"
		assertTrue("empty objects"
		assertTrue("empty objects/"
	}

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

	public void testGetRefs_DiscoversModifiedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		writeLooseRef("refs/heads/master"
		BUG_WorkAroundRacyGitIssues("refs/heads/master");
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(B
	}

	public void testGetRef_DiscoversModifiedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		writeLooseRef("refs/heads/master"
		BUG_WorkAroundRacyGitIssues("refs/heads/master");

		Ref master = refdir.getRef("refs/heads/master");
		assertEquals(B
	}

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

	public void testGetRef_DiscoversDeletedLoose() throws IOException {
		Map<String

		writeLooseRef("refs/heads/master"
		all = refdir.getRefs(RefDatabase.ALL);
		assertEquals(A

		deleteLooseRef("refs/heads/master");
		assertNull(refdir.getRef("refs/heads/master"));
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

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
		BUG_WorkAroundRacyGitIssues("refs/5");
		all = refdir.getRefs(RefDatabase.ALL);
		r = all.get("refs/1");
		assertNull("mising 1 due to cycle"
	}

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

	public void testGetRef_PackedNotPeeled_WrongSort() throws IOException {
				A.name() + " refs/heads/master\n");

		final Ref head = refdir.getRef(HEAD);
		final Ref master = refdir.getRef("refs/heads/master");
		final Ref other = refdir.getRef("refs/heads/other");
		final Ref tag = refdir.getRef("refs/tags/v1.0");

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

	public void testGetRef_EmptyDatabase() throws IOException {
		Ref r;

		r = refdir.getRef(HEAD);
		assertTrue(r.isSymbolic());
		assertSame(LOOSE
		assertEquals("refs/heads/master"
		assertSame(NEW
		assertNull(r.getTarget().getObjectId());

		assertNull(refdir.getRef("refs/heads/master"));
		assertNull(refdir.getRef("refs/tags/v1.0"));
		assertNull(refdir.getRef("FETCH_HEAD"));
		assertNull(refdir.getRef("NOT.A.REF.NAME"));
		assertNull(refdir.getRef("master"));
		assertNull(refdir.getRef("v1.0"));
	}

	public void testGetRef_FetchHead() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "\tnot-for-merge"

		Ref r = refdir.getRef("FETCH_HEAD");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("FETCH_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	public void testGetRef_AnyHeadWithGarbage() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "012345 . this is not a standard reference\n"
				+ "#and even more junk\n");

		Ref r = refdir.getRef("refs/heads/A");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("refs/heads/A"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	public void testGetRefs_CorruptSymbolicReference() throws IOException {
		String name = "refs/heads/A";
		writeLooseRef(name
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

	public void testGetRef_CorruptSymbolicReference() throws IOException {
		String name = "refs/heads/A";
		writeLooseRef(name
		try {
			refdir.getRef(name);
			fail("read an invalid reference");
		} catch (IOException err) {
			String msg = err.getMessage();
			assertEquals("Not a ref: " + name + ": ref:"
		}
	}

	public void testGetRefs_CorruptObjectIdReference() throws IOException {
		String name = "refs/heads/A";
		String content = "zoo" + A.name();
		writeLooseRef(name
		assertTrue(refdir.getRefs(RefDatabase.ALL).isEmpty());
	}

	public void testGetRef_CorruptObjectIdReference() throws IOException {
		String name = "refs/heads/A";
		String content = "zoo" + A.name();
		writeLooseRef(name
		try {
			refdir.getRef(name);
			fail("read an invalid reference");
		} catch (IOException err) {
			String msg = err.getMessage();
			assertEquals("Not a ref: " + name + ": " + content
		}
	}

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

	public void testPeelLooseTag() throws IOException {
		writeLooseRef("refs/tags/v1_0"
		writeLooseRef("refs/tags/current"

		final Ref tag = refdir.getRef("refs/tags/v1_0");
		final Ref cur = refdir.getRef("refs/tags/current");

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

		final Ref tag_p2 = refdir.getRef("refs/tags/v1_0");
		assertFalse(tag_p2.isSymbolic());
		assertTrue(tag_p2.isPeeled());
		assertEquals(v1_0
		assertEquals(v1_0.getObject()

		assertSame(tag_p2
		assertSame(tag_p2
		assertSame(tag_p2
	}

	public void testPeelCommit() throws IOException {
		writeLooseRef("refs/heads/master"

		Ref master = refdir.getRef("refs/heads/master");
		assertEquals(A
		assertFalse(master.isPeeled());
		assertNull(master.getPeeledObjectId());

		Ref master_p = refdir.peel(master);
		assertNotSame(master
		assertEquals(A
		assertTrue(master_p.isPeeled());
		assertNull(master_p.getPeeledObjectId());

		Ref master_p2 = refdir.getRef("refs/heads/master");
		assertNotSame(master
		assertEquals(A
		assertTrue(master_p2.isPeeled());
		assertNull(master_p2.getPeeledObjectId());
		assertSame(master_p2
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
	}

	private void deleteLooseRef(String name) {
		File path = new File(diskRepo.getDirectory()
		assertTrue("deleted " + name
	}

	private void BUG_WorkAroundRacyGitIssues(String name) {
		File path = new File(diskRepo.getDirectory()
		long old = path.lastModified();
		path.setLastModified(set);
		assertTrue("time changed"
	}
}
