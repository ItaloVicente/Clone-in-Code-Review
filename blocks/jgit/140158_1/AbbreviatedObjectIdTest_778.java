
package org.eclipse.jgit.junit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRepositoryTest {
	private TestRepository<InMemoryRepository> tr;
	private InMemoryRepository repo;
	private RevWalk rw;

	@Before
	public void setUp() throws Exception {
		tr = new TestRepository<>(new InMemoryRepository(
				new DfsRepositoryDescription("test")));
		repo = tr.getRepository();
		rw = tr.getRevWalk();
	}

	@After
	public void tearDown() {
		rw.close();
		repo.close();
	}

	@Test
	public void insertChangeId() throws Exception {
		RevCommit c1 = tr.commit().message("message").insertChangeId().create();
		rw.parseBody(c1);
		assertTrue(Pattern.matches(
				"^message\n\nChange-Id: I[0-9a-f]{40}\n$"

		RevCommit c2 = tr.commit().message("").insertChangeId().create();
		rw.parseBody(c2);
		assertEquals("\n\nChange-Id: I0000000000000000000000000000000000000000\n"
				c2.getFullMessage());
	}

	@Test
	public void insertChangeIdIgnoresExisting() throws Exception {
		String msg = "message\n"
				+ "\n"
				+	"Change-Id: Ideadbeefdeadbeefdeadbeefdeadbeefdeadbeef\n";
		RevCommit c = tr.commit().message(msg).insertChangeId().create();
		rw.parseBody(c);
		assertEquals(msg
	}

	@Test
	public void insertExplicitChangeId() throws Exception {
		RevCommit c = tr.commit().message("message")
				.insertChangeId("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef")
				.create();
		rw.parseBody(c);
		assertEquals("message\n\n"
				+ "Change-Id: Ideadbeefdeadbeefdeadbeefdeadbeefdeadbeef\n"
				
	}

	@Test
	public void resetFromSymref() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		Ref head = repo.exactRef("HEAD");
		RevCommit master = tr.branch("master").commit().create();
		RevCommit branch = tr.branch("branch").commit().create();
		RevCommit detached = tr.commit().create();

		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		assertEquals(master
		assertEquals(branch

		tr.reset("master");
		head = repo.exactRef("HEAD");
		assertEquals(master
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		tr.reset("branch");
		head = repo.exactRef("HEAD");
		assertEquals(branch
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		ObjectId lastHeadBeforeDetach = head.getObjectId().copy();

		tr.reset(detached);
		head = repo.exactRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset(detached.name());
		head = repo.exactRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset("master");
		head = repo.exactRef("HEAD");
		assertEquals(lastHeadBeforeDetach
		assertFalse(head.isSymbolic());
	}

	@Test
	public void resetFromDetachedHead() throws Exception {
		Ref head = repo.exactRef("HEAD");
		RevCommit master = tr.branch("master").commit().create();
		RevCommit branch = tr.branch("branch").commit().create();
		RevCommit detached = tr.commit().create();

		assertNull(head);
		assertEquals(master
		assertEquals(branch

		tr.reset("master");
		head = repo.exactRef("HEAD");
		assertEquals(master
		assertFalse(head.isSymbolic());

		tr.reset("branch");
		head = repo.exactRef("HEAD");
		assertEquals(branch
		assertFalse(head.isSymbolic());

		tr.reset(detached);
		head = repo.exactRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset(detached.name());
		head = repo.exactRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());
	}

	@Test
	public void amendRef() throws Exception {
		RevCommit root = tr.commit()
				.add("todelete"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.rm("todelete")
				.add("foo"
				.add("bar"
				.add("dir/baz"
				.create();
		rw.parseBody(orig);
		tr.branch("master").update(orig);
		assertEquals("foo contents"
		assertEquals("bar contents"
		assertEquals("baz contents"

		RevCommit amended = tr.amendRef("master")
				.tick(3)
				.add("bar"
				.create();
		assertEquals(amended
		rw.parseBody(amended);

		assertEquals(1
		assertEquals(root
		assertEquals(orig.getFullMessage()
		assertEquals(orig.getAuthorIdent()

		assertEquals(new PersonIdent(orig.getCommitterIdent()
				new PersonIdent(amended.getCommitterIdent()
		assertTrue(orig.getCommitTime() < amended.getCommitTime());

		assertEquals("foo contents"
		assertEquals("fixed bar contents"
		assertEquals("baz contents"
		assertNull(TreeWalk.forPath(repo
	}

	@Test
	public void amendHead() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		RevCommit root = tr.commit()
				.add("foo"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.message("original message")
				.add("bar"
				.create();
		tr.branch("master").update(orig);

		RevCommit amended = tr.amendRef("HEAD")
				.add("foo"
				.create();

		Ref head = repo.exactRef(Constants.HEAD);
		assertEquals(amended
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		rw.parseBody(amended);
		assertEquals("original message"
		assertEquals("fixed foo contents"
		assertEquals("bar contents"
	}

	@Test
	public void amendCommit() throws Exception {
		RevCommit root = tr.commit()
				.add("foo"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.message("original message")
				.add("bar"
				.create();
		RevCommit amended = tr.amend(orig.copy())
				.add("foo"
				.create();

		rw.parseBody(amended);
		assertEquals("original message"
		assertEquals("fixed foo contents"
		assertEquals("bar contents"
	}

	@Test
	public void commitToUnbornHead() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		RevCommit root = tr.branch("HEAD").commit().create();
		Ref ref = repo.exactRef(Constants.HEAD);
		assertEquals(root
		assertTrue(ref.isSymbolic());
		assertEquals("refs/heads/master"
	}

	@Test
	public void cherryPick() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		RevCommit head = tr.branch("master").commit()
				.add("foo"
				.create();
		rw.parseBody(head);
		RevCommit toPick = tr.commit()
				.author(new PersonIdent("Cherrypick Author"
						tr.getDate()
				.author(new PersonIdent("Cherrypick Committer"
						tr.getDate()
				.message("message to cherry-pick")
				.add("bar"
				.create();
		RevCommit result = tr.cherryPick(toPick);
		rw.parseBody(result);

		Ref headRef = tr.getRepository().exactRef("HEAD");
		assertEquals(result
		assertTrue(headRef.isSymbolic());
		assertEquals("refs/heads/master"

		assertEquals(1
		assertEquals(head
		assertEquals(toPick.getAuthorIdent()

		assertEquals(new PersonIdent(head.getCommitterIdent()
				new PersonIdent(result.getCommitterIdent()
		assertTrue(toPick.getCommitTime() < result.getCommitTime());

		assertEquals("message to cherry-pick"
		assertEquals("foo contents\n"
		assertEquals("bar contents\n"
	}

	@Test
	public void cherryPickWithContentMerge() throws Exception {
		RevCommit base = tr.branch("HEAD").commit()
				.add("foo"
				.create();
		tr.branch("HEAD").commit()
				.add("foo"
				.create();
		RevCommit toPick = tr.commit()
				.message("message to cherry-pick")
				.parent(base)
				.add("foo"
				.create();
		RevCommit result = tr.cherryPick(toPick);
		rw.parseBody(result);

		assertEquals("message to cherry-pick"
		assertEquals("changed foo contents\n\nlast line\n"
				blobAsString(result
	}

	@Test
	public void cherryPickWithIdenticalContents() throws Exception {
		RevCommit base = tr.branch("HEAD").commit().add("foo"
				.create();
		RevCommit head = tr.branch("HEAD").commit()
				.parent(base)
				.add("bar"
				.create();
		RevCommit toPick = tr.commit()
				.parent(base)
				.message("message to cherry-pick")
				.add("bar"
				.create();
		assertNotEquals(head
		assertNull(tr.cherryPick(toPick));
		assertEquals(head
	}

	@Test
	public void reattachToMaster_Race() throws Exception {
		RevCommit commit = tr.branch("master").commit().create();
		tr.branch("master").update(commit);
		tr.branch("other").update(commit);
		repo.updateRef("HEAD").link("refs/heads/master");

		tr.reset(commit);
		Ref head = repo.exactRef("HEAD");
		assertEquals(commit
		assertFalse(head.isSymbolic());

		RefUpdate refUpdate = repo.updateRef("HEAD");

		repo.updateRef("HEAD").link("refs/heads/other");

		assertEquals(
				RefUpdate.Result.LOCK_FAILURE
	}

	@Test
	public void nonRacingChange() throws Exception {
		tr.branch("master").update(tr.branch("master").commit().create());
		tr.branch("other").update(tr.branch("other").commit().create());
		repo.updateRef("HEAD").link("refs/heads/master");

		RefUpdate refUpdate = repo.updateRef("HEAD");

		tr.branch("master").update(tr.branch("master").commit().create());

		assertEquals(RefUpdate.Result.FORCED
	}

	private String blobAsString(AnyObjectId treeish
			throws Exception {
		RevObject obj = tr.get(rw.parseTree(treeish)
		assertSame(RevBlob.class
		ObjectLoader loader = rw.getObjectReader().open(obj);
		return new String(loader.getCachedBytes()
	}
}
