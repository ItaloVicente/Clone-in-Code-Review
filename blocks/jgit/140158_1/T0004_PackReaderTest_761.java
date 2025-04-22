
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class T0003_BasicTest extends SampleDataRepositoryTestCase {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void test001_Initalize() {
		final File gitdir = new File(trash
		final File hooks = new File(gitdir
		final File objects = new File(gitdir
		final File objects_pack = new File(objects
		final File objects_info = new File(objects
		final File refs = new File(gitdir
		final File refs_heads = new File(refs
		final File refs_tags = new File(refs
		final File HEAD = new File(gitdir

		assertTrue("Exists " + trash
		assertTrue("Exists " + hooks
		assertTrue("Exists " + objects
		assertTrue("Exists " + objects_pack
		assertTrue("Exists " + objects_info
		assertEquals(2L
		assertTrue("Exists " + refs
		assertTrue("Exists " + refs_heads
		assertTrue("Exists " + refs_tags
		assertTrue("Exists " + HEAD
		assertEquals(23
	}

	@Test
	public void test000_openRepoBadArgs() throws IOException {
		try {
			new FileRepositoryBuilder().build();
			fail("Must pass either GIT_DIR or GIT_WORK_TREE");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().eitherGitDirOrWorkTreeRequired
					.getMessage());
		}
	}

	@Test
	public void test000_openrepo_default_gitDirSet() throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		try (Repository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
		}

		File theDir = new File(repo1Parent
		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).build();
		assertEqualsPath(theDir
		assertEqualsPath(repo1Parent
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
				.getDirectory());
	}

	@Test
	public void test000_openrepo_default_gitDirAndWorkTreeSet()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		try (Repository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
		}

		File theDir = new File(repo1Parent
		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).setWorkTree(repo1Parent.getParentFile())
				.build();
		assertEqualsPath(theDir
		assertEqualsPath(repo1Parent.getParentFile()
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
				.getDirectory());
	}

	@Test
	public void test000_openrepo_default_workDirSet() throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		try (Repository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
		}

		File theDir = new File(repo1Parent
		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setWorkTree(repo1Parent).build();
		assertEqualsPath(theDir
		assertEqualsPath(repo1Parent
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
				.getDirectory());
	}

	@Test
	public void test000_openrepo_default_absolute_workdirconfig()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File workdir = new File(trash.getParentFile()
		FileUtils.mkdir(workdir);
		try (FileRepository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
			final FileBasedConfig cfg = repo1initial.getConfig();
			cfg.setString("core"
			cfg.save();
		}

		File theDir = new File(repo1Parent
		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).build();
		assertEqualsPath(theDir
		assertEqualsPath(workdir
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
				.getDirectory());
	}

	@Test
	public void test000_openrepo_default_relative_workdirconfig()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File workdir = new File(trash.getParentFile()
		FileUtils.mkdir(workdir);
		try (FileRepository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
			final FileBasedConfig cfg = repo1initial.getConfig();
			cfg.setString("core"
			cfg.save();
		}

		File theDir = new File(repo1Parent
		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).build();
		assertEqualsPath(theDir
		assertEqualsPath(workdir
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
				.getDirectory());
	}

	@Test
	public void test000_openrepo_alternate_index_file_and_objdirs()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File indexFile = new File(trash
		File objDir = new File(trash
		File altObjDir = db.getObjectDatabase().getDirectory();
		try (Repository repo1initial = new FileRepository(
				new File(repo1Parent
			repo1initial.create();
		}

		File theDir = new File(repo1Parent
				.build()) {
			assertEqualsPath(theDir
			assertEqualsPath(theDir.getParentFile()
			assertEqualsPath(indexFile
			assertEqualsPath(objDir
			assertNotNull(r.open(ObjectId
					.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4")));
		}
	}

	protected void assertEqualsPath(File expected
			throws IOException {
		assertEquals(expected.getCanonicalPath()
	}

	@Test
	public void test002_WriteEmptyTree() throws IOException {
		final Repository newdb = createBareRepository();
		try (ObjectInserter oi = newdb.newObjectInserter()) {
			final ObjectId treeId = oi.insert(new TreeFormatter());
			assertEquals("4b825dc642cb6eb9a060e54bf8d69288fbee4904"
					treeId.name());
		}

		final File o = new File(new File(new File(newdb.getDirectory()
				"objects")
		assertTrue("Exists " + o
		assertTrue("Read-only " + o
	}

	@Test
	public void test002_WriteEmptyTree2() throws IOException {
		final ObjectId treeId = insertTree(new TreeFormatter());
		assertEquals("4b825dc642cb6eb9a060e54bf8d69288fbee4904"
		final File o = new File(new File(
				new File(db.getDirectory()
				"825dc642cb6eb9a060e54bf8d69288fbee4904");
		assertFalse("Exists " + o
	}

	@Test
	public void test002_CreateBadTree() throws Exception {
		final TreeFormatter formatter = new TreeFormatter();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(JGitText.get().invalidTreeZeroLengthName);
		formatter.append(""
				ObjectId.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904"));
	}

	@Test
	public void test006_ReadUglyConfig() throws IOException
			ConfigInvalidException {
		final File cfg = new File(db.getDirectory()
		final FileBasedConfig c = new FileBasedConfig(cfg
		final String configStr = "  [core];comment\n\tfilemode = yes\n"
				+ "[user]\n"
				+ "  email = A U Thor <thor@example.com> # Just an example...\n"
				+ " name = \"A  Thor \\\\ \\\"\\t \"\n"
				+ "    defaultCheckInComment = a many line\\n\\\ncomment\\n\\\n"
				+ " to test\n";
		write(cfg
		c.load();
		assertEquals("yes"
		assertEquals("A U Thor <thor@example.com>"
				"email"));
		assertEquals("A  Thor \\ \"\t "
		assertEquals("a many line\ncomment\n to test"
				null
		c.save();

		final String expectedStr = "  [core];comment\n\tfilemode = yes\n"
				+ "[user]\n"
				+ "  email = A U Thor <thor@example.com> # Just an example...\n"
				+ " name = \"A  Thor \\\\ \\\"\\t \"\n"
				+ "    defaultCheckInComment = a many line\\ncomment\\n to test\n";
		assertEquals(expectedStr
	}

	@Test
	public void test007_Open() throws IOException {
		try (FileRepository db2 = new FileRepository(db.getDirectory())) {
			assertEquals(db.getDirectory()
			assertEquals(db.getObjectDatabase().getDirectory()
					.getObjectDatabase().getDirectory());
			assertNotSame(db.getConfig()
		}
	}

	@Test
	public void test008_FailOnWrongVersion() throws IOException {
		final File cfg = new File(db.getDirectory()
		final String badvers = "ihopethisisneveraversion";
		final String configStr = "[core]\n" + "\trepositoryFormatVersion="
				+ badvers + "\n";
		write(cfg

		try (FileRepository unused = new FileRepository(db.getDirectory())) {
			fail("incorrectly opened a bad repository");
		} catch (IllegalArgumentException ioe) {
			assertNotNull(ioe.getMessage());
		}
	}

	@Test
	public void test009_CreateCommitOldFormat() throws IOException {
		final ObjectId treeId = insertTree(new TreeFormatter());
		final CommitBuilder c = new CommitBuilder();
		c.setAuthor(new PersonIdent(author
		c.setCommitter(new PersonIdent(committer
		c.setMessage("A Commit\n");
		c.setTreeId(treeId);
		assertEquals(treeId

		ObjectId actid = insertCommit(c);

		final ObjectId cmtid = ObjectId
				.fromString("9208b2459ea6609a5af68627cc031796d0d9329b");
		assertEquals(cmtid

		ObjectDatabase odb = db.getObjectDatabase();
		assertTrue("is ObjectDirectory"
		try (XInputStream xis = new XInputStream(
				new FileInputStream(((ObjectDirectory) odb).fileFor(cmtid)))) {
			assertEquals(0x78
			assertEquals(0x9c
			assertEquals(0
		}

		RevCommit c2 = parseCommit(actid);
		assertNotNull(c2);
		assertEquals(c.getMessage()
		assertEquals(c.getTreeId()
		assertEquals(c.getAuthor()
		assertEquals(c.getCommitter()
	}

	@Test
	public void test020_createBlobTag() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		final TagBuilder t = new TagBuilder();
		t.setObjectId(emptyId
		t.setTag("test020");
		t.setTagger(new PersonIdent(author
		t.setMessage("test020 tagged\n");
		ObjectId actid = insertTag(t);
		assertEquals("6759556b09fbb4fd8ae5e315134481cc25d46954"

		RevTag mapTag = parseTag(actid);
		assertEquals(Constants.OBJ_BLOB
		assertEquals("test020 tagged\n"
		assertEquals(new PersonIdent(author
				.getTaggerIdent());
		assertEquals("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391"
				.getObject().getId().name());
	}

	@Test
	public void test021_createTreeTag() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		TreeFormatter almostEmptyTree = new TreeFormatter();
		almostEmptyTree.append("empty"
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		final TagBuilder t = new TagBuilder();
		t.setObjectId(almostEmptyTreeId
		t.setTag("test021");
		t.setTagger(new PersonIdent(author
		t.setMessage("test021 tagged\n");
		ObjectId actid = insertTag(t);
		assertEquals("b0517bc8dbe2096b419d42424cd7030733f4abe5"

		RevTag mapTag = parseTag(actid);
		assertEquals(Constants.OBJ_TREE
		assertEquals("test021 tagged\n"
		assertEquals(new PersonIdent(author
				.getTaggerIdent());
		assertEquals("417c01c8795a35b8e835113a85a5c0c1c77f67fb"
				.getObject().getId().name());
	}

	@Test
	public void test022_createCommitTag() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		TreeFormatter almostEmptyTree = new TreeFormatter();
		almostEmptyTree.append("empty"
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		final CommitBuilder almostEmptyCommit = new CommitBuilder();
		almostEmptyCommit.setAuthor(new PersonIdent(author
		almostEmptyCommit.setCommitter(new PersonIdent(author
				-2 * 60));
		almostEmptyCommit.setMessage("test022\n");
		almostEmptyCommit.setTreeId(almostEmptyTreeId);
		ObjectId almostEmptyCommitId = insertCommit(almostEmptyCommit);
		final TagBuilder t = new TagBuilder();
		t.setObjectId(almostEmptyCommitId
		t.setTag("test022");
		t.setTagger(new PersonIdent(author
		t.setMessage("test022 tagged\n");
		ObjectId actid = insertTag(t);
		assertEquals("0ce2ebdb36076ef0b38adbe077a07d43b43e3807"

		RevTag mapTag = parseTag(actid);
		assertEquals(Constants.OBJ_COMMIT
		assertEquals("test022 tagged\n"
		assertEquals(new PersonIdent(author
				.getTaggerIdent());
		assertEquals("b5d3b45a96b340441f5abb9080411705c51cc86c"
				.getObject().getId().name());
	}

	@Test
	public void test023_createCommitNonAnullii() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		TreeFormatter almostEmptyTree = new TreeFormatter();
		almostEmptyTree.append("empty"
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(almostEmptyTreeId);
		commit.setAuthor(new PersonIdent("Joe H\u00e4cker"
				4294967295000L
		commit.setCommitter(new PersonIdent("Joe Hacker"
				4294967295000L
		commit.setEncoding(UTF_8);
		commit.setMessage("\u00dcbergeeks");
		ObjectId cid = insertCommit(commit);
		assertEquals("4680908112778718f37e686cbebcc912730b3154"

		RevCommit loadedCommit = parseCommit(cid);
		assertEquals(commit.getMessage()
	}

	@Test
	public void test024_createCommitNonAscii() throws IOException {
		final ObjectId emptyId = insertEmptyBlob();
		TreeFormatter almostEmptyTree = new TreeFormatter();
		almostEmptyTree.append("empty"
		final ObjectId almostEmptyTreeId = insertTree(almostEmptyTree);
		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(almostEmptyTreeId);
		commit.setAuthor(new PersonIdent("Joe H\u00e4cker"
				4294967295000L
		commit.setCommitter(new PersonIdent("Joe Hacker"
				4294967295000L
		commit.setEncoding(ISO_8859_1);
		commit.setMessage("\u00dcbergeeks");
		ObjectId cid = insertCommit(commit);
		assertEquals("2979b39d385014b33287054b87f77bcb3ecb5ebf"
	}

	@Test
	public void test025_computeSha1NoStore() {
		byte[] data = "test025 some data
				.getBytes(ISO_8859_1);
		try (ObjectInserter.Formatter formatter = new ObjectInserter.Formatter()) {
			final ObjectId id = formatter.idFor(Constants.OBJ_BLOB
			assertEquals("4f561df5ecf0dfbd53a0dc0f37262fef075d9dde"
		}
	}

	@Test
	public void test026_CreateCommitMultipleparents() throws IOException {
		final ObjectId treeId;
		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId blobId = oi.insert(Constants.OBJ_BLOB
					"and this is the data in me\n".getBytes(UTF_8
							.name()));
			TreeFormatter fmt = new TreeFormatter();
			fmt.append("i-am-a-file"
			treeId = oi.insert(fmt);
			oi.flush();
		}
		assertEquals(ObjectId
				.fromString("00b1f73724f493096d1ffa0b0f1f1482dbb8c936")

		final CommitBuilder c1 = new CommitBuilder();
		c1.setAuthor(new PersonIdent(author
		c1.setCommitter(new PersonIdent(committer
		c1.setMessage("A Commit\n");
		c1.setTreeId(treeId);
		assertEquals(treeId
		ObjectId actid1 = insertCommit(c1);
		final ObjectId cmtid1 = ObjectId
				.fromString("803aec4aba175e8ab1d666873c984c0308179099");
		assertEquals(cmtid1

		final CommitBuilder c2 = new CommitBuilder();
		c2.setAuthor(new PersonIdent(author
		c2.setCommitter(new PersonIdent(committer
		c2.setMessage("A Commit 2\n");
		c2.setTreeId(treeId);
		assertEquals(treeId
		c2.setParentIds(actid1);
		ObjectId actid2 = insertCommit(c2);
		final ObjectId cmtid2 = ObjectId
				.fromString("95d068687c91c5c044fb8c77c5154d5247901553");
		assertEquals(cmtid2

		RevCommit rm2 = parseCommit(cmtid2);
		assertNotSame(c2
		assertEquals(c2.getAuthor()
		assertEquals(actid2
		assertEquals(c2.getMessage()
		assertEquals(c2.getTreeId()
		assertEquals(1
		assertEquals(actid1

		final CommitBuilder c3 = new CommitBuilder();
		c3.setAuthor(new PersonIdent(author
		c3.setCommitter(new PersonIdent(committer
		c3.setMessage("A Commit 3\n");
		c3.setTreeId(treeId);
		assertEquals(treeId
		c3.setParentIds(actid1
		ObjectId actid3 = insertCommit(c3);
		final ObjectId cmtid3 = ObjectId
				.fromString("ce6e1ce48fbeeb15a83f628dc8dc2debefa066f4");
		assertEquals(cmtid3

		RevCommit rm3 = parseCommit(cmtid3);
		assertNotSame(c3
		assertEquals(c3.getAuthor()
		assertEquals(actid3
		assertEquals(c3.getMessage()
		assertEquals(c3.getTreeId()
		assertEquals(2
		assertEquals(actid1
		assertEquals(actid2

		final CommitBuilder c4 = new CommitBuilder();
		c4.setAuthor(new PersonIdent(author
		c4.setCommitter(new PersonIdent(committer
		c4.setMessage("A Commit 4\n");
		c4.setTreeId(treeId);
		assertEquals(treeId
		c4.setParentIds(actid1
		ObjectId actid4 = insertCommit(c4);
		final ObjectId cmtid4 = ObjectId
				.fromString("d1fca9fe3fef54e5212eb67902c8ed3e79736e27");
		assertEquals(cmtid4

		RevCommit rm4 = parseCommit(cmtid4);
		assertNotSame(c4
		assertEquals(c4.getAuthor()
		assertEquals(actid4
		assertEquals(c4.getMessage()
		assertEquals(c4.getTreeId()
		assertEquals(3
		assertEquals(actid1
		assertEquals(actid2
		assertEquals(actid3
	}

	@Test
	public void test027_UnpackedRefHigherPriorityThanPacked()
			throws IOException {
		String unpackedId = "7f822839a2fe9760f386cbbbcb3f92c5fe81def7";
		write(new File(db.getDirectory()

		ObjectId resolved = db.resolve("refs/heads/a");
		assertEquals(unpackedId
	}

	@Test
	public void test028_LockPackedRef() throws IOException {
		ObjectId id1;
		ObjectId id2;
		try (ObjectInserter ins = db.newObjectInserter()) {
			id1 = ins.insert(
					Constants.OBJ_BLOB
			id2 = ins.insert(
					Constants.OBJ_BLOB
			ins.flush();
		}

		writeTrashFile(".git/packed-refs"
				id1.name() + " refs/heads/foobar");
		writeTrashFile(".git/HEAD"
		BUG_WorkAroundRacyGitIssues("packed-refs");
		BUG_WorkAroundRacyGitIssues("HEAD");

		ObjectId resolve = db.resolve("HEAD");
		assertEquals(id1

		RefUpdate lockRef = db.updateRef("HEAD");
		lockRef.setNewObjectId(id2);
		assertEquals(RefUpdate.Result.FORCED

		assertTrue(new File(db.getDirectory()
		assertEquals(id2

		RefUpdate lockRef2 = db.updateRef("HEAD");
		lockRef2.setNewObjectId(id1);
		assertEquals(RefUpdate.Result.FORCED

		assertTrue(new File(db.getDirectory()
		assertEquals(id1
	}

	@Test
	public void test30_stripWorkDir() {
		File relCwd = new File(".");
		File absCwd = relCwd.getAbsoluteFile();
		File absBase = new File(new File(absCwd
		File relBase = new File(new File(relCwd
		assertEquals(absBase.getAbsolutePath()

		File relBaseFile = new File(new File(relBase
		File absBaseFile = new File(new File(absBase
		assertEquals("other/module.c"
				relBaseFile));
		assertEquals("other/module.c"
				absBaseFile));
		assertEquals("other/module.c"
				relBaseFile));
		assertEquals("other/module.c"
				absBaseFile));

		File relNonFile = new File(new File(relCwd
		File absNonFile = new File(new File(absCwd
		assertEquals(""
		assertEquals(""

		assertEquals(""
				.getWorkTree()));

		File file = new File(new File(db.getWorkTree()
		assertEquals("subdir/File.java"
				.getWorkTree()

	}

	private ObjectId insertEmptyBlob() throws IOException {
		final ObjectId emptyId;
		try (ObjectInserter oi = db.newObjectInserter()) {
			emptyId = oi.insert(Constants.OBJ_BLOB
			oi.flush();
		}
		return emptyId;
	}

	private ObjectId insertTree(TreeFormatter tree) throws IOException {
		try (ObjectInserter oi = db.newObjectInserter()) {
			ObjectId id = oi.insert(tree);
			oi.flush();
			return id;
		}
	}

	private ObjectId insertCommit(CommitBuilder builder)
			throws IOException
		try (ObjectInserter oi = db.newObjectInserter()) {
			ObjectId id = oi.insert(builder);
			oi.flush();
			return id;
		}
	}

	private RevCommit parseCommit(AnyObjectId id)
			throws MissingObjectException
			IOException {
		try (RevWalk rw = new RevWalk(db)) {
			return rw.parseCommit(id);
		}
	}

	private ObjectId insertTag(TagBuilder tag) throws IOException
			UnsupportedEncodingException {
		try (ObjectInserter oi = db.newObjectInserter()) {
			ObjectId id = oi.insert(tag);
			oi.flush();
			return id;
		}
	}

	private RevTag parseTag(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		try (RevWalk rw = new RevWalk(db)) {
			return rw.parseTag(id);
		}
	}

	private void BUG_WorkAroundRacyGitIssues(String name) {
		File path = new File(db.getDirectory()
		long old = path.lastModified();
		path.setLastModified(set);
		assertTrue("time changed"
	}
}
