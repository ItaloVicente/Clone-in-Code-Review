
package org.eclipse.jgit.junit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.DeletePath;
import org.eclipse.jgit.dircache.DirCacheEditor.DeleteTree;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.internal.storage.file.ObjectDirectory;
import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.ChangeIdUtil;
import org.eclipse.jgit.util.FileUtils;

public class TestRepository<R extends Repository> implements AutoCloseable {

	public static final String AUTHOR = "J. Author";

	public static final String AUTHOR_EMAIL = "jauthor@example.com";

	public static final String COMMITTER = "J. Committer";

	public static final String COMMITTER_EMAIL = "jcommitter@example.com";

	private final PersonIdent defaultAuthor;

	private final PersonIdent defaultCommitter;

	private final R db;

	private final Git git;

	private final RevWalk pool;

	private final ObjectInserter inserter;

	private final MockSystemReader mockSystemReader;

	public TestRepository(R db) throws IOException {
		this(db
	}

	public TestRepository(R db
		this(db
	}

	public TestRepository(R db
			throws IOException {
		this.db = db;
		this.git = Git.wrap(db);
		this.pool = rw;
		this.inserter = db.newObjectInserter();
		this.mockSystemReader = reader;
		long now = mockSystemReader.getCurrentTime();
		int tz = mockSystemReader.getTimezone(now);
		defaultAuthor = new PersonIdent(AUTHOR
		defaultCommitter = new PersonIdent(COMMITTER
	}

	public R getRepository() {
		return db;
	}

	public RevWalk getRevWalk() {
		return pool;
	}

	public Git git() {
		return git;
	}

	public Date getDate() {
		return new Date(mockSystemReader.getCurrentTime());
	}

	public TimeZone getTimeZone() {
		return mockSystemReader.getTimeZone();
	}

	public void tick(int secDelta) {
		mockSystemReader.tick(secDelta);
	}

	public void setAuthorAndCommitter(org.eclipse.jgit.lib.CommitBuilder c) {
		c.setAuthor(new PersonIdent(defaultAuthor
		c.setCommitter(new PersonIdent(defaultCommitter
	}

	public RevBlob blob(String content) throws Exception {
		return blob(content.getBytes(UTF_8));
	}

	public RevBlob blob(byte[] content) throws Exception {
		ObjectId id;
		try (ObjectInserter ins = inserter) {
			id = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}
		return (RevBlob) pool.parseAny(id);
	}

	public DirCacheEntry file(String path
			throws Exception {
		final DirCacheEntry e = new DirCacheEntry(path);
		e.setFileMode(FileMode.REGULAR_FILE);
		e.setObjectId(blob);
		return e;
	}

	public RevTree tree(DirCacheEntry... entries) throws Exception {
		final DirCache dc = DirCache.newInCore();
		final DirCacheBuilder b = dc.builder();
		for (DirCacheEntry e : entries) {
			b.add(e);
		}
		b.finish();
		ObjectId root;
		try (ObjectInserter ins = inserter) {
			root = dc.writeTree(ins);
			ins.flush();
		}
		return pool.parseTree(root);
	}

	public RevObject get(RevTree tree
			throws Exception {
		try (TreeWalk tw = new TreeWalk(pool.getObjectReader())) {
			tw.setFilter(PathFilterGroup.createFromStrings(Collections
					.singleton(path)));
			tw.reset(tree);
			while (tw.next()) {
				if (tw.isSubtree() && !path.equals(tw.getPathString())) {
					tw.enterSubtree();
					continue;
				}
				final ObjectId entid = tw.getObjectId(0);
				final FileMode entmode = tw.getFileMode(0);
				return pool.lookupAny(entid
			}
		}
		fail("Can't find " + path + " in tree " + tree.name());
	}

	public RevCommit commit(RevCommit... parents) throws Exception {
		return commit(1
	}

	public RevCommit commit(RevTree tree
			throws Exception {
		return commit(1
	}

	public RevCommit commit(int secDelta
			throws Exception {
		return commit(secDelta
	}

	public RevCommit commit(final int secDelta
			final RevCommit... parents) throws Exception {
		tick(secDelta);

		final org.eclipse.jgit.lib.CommitBuilder c;

		c = new org.eclipse.jgit.lib.CommitBuilder();
		c.setTreeId(tree);
		c.setParentIds(parents);
		c.setAuthor(new PersonIdent(defaultAuthor
		c.setCommitter(new PersonIdent(defaultCommitter
		c.setMessage("");
		ObjectId id;
		try (ObjectInserter ins = inserter) {
			id = ins.insert(c);
			ins.flush();
		}
		return pool.parseCommit(id);
	}

	public CommitBuilder commit() {
		return new CommitBuilder();
	}

	public RevTag tag(String name
		final TagBuilder t = new TagBuilder();
		t.setObjectId(dst);
		t.setTag(name);
		t.setTagger(new PersonIdent(defaultCommitter
		t.setMessage("");
		ObjectId id;
		try (ObjectInserter ins = inserter) {
			id = ins.insert(t);
			ins.flush();
		}
		return pool.parseTag(id);
	}

	public RevCommit update(String ref
		return update(ref
	}

	public CommitBuilder amendRef(String ref) throws Exception {
		String name = normalizeRef(ref);
		Ref r = db.exactRef(name);
		if (r == null)
			throw new IOException("Not a ref: " + ref);
		return amend(pool.parseCommit(r.getObjectId())
	}

	public CommitBuilder amend(AnyObjectId id) throws Exception {
		return amend(pool.parseCommit(id)
	}

	private CommitBuilder amend(RevCommit old
		pool.parseBody(old);
		b.author(old.getAuthorIdent());
		b.committer(old.getCommitterIdent());
		b.message(old.getFullMessage());
		b.updateCommitterTime = true;

		b.noParents();
		for (int i = 0; i < old.getParentCount(); i++)
			b.parent(old.getParent(i));

		b.tree.clear();
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.reset(old.getTree());
			tw.setRecursive(true);
			while (tw.next()) {
				b.edit(new PathEdit(tw.getPathString()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(tw.getFileMode(0));
						ent.setObjectId(tw.getObjectId(0));
					}
				});
			}
		}

		return b;
	}

	public <T extends AnyObjectId> T update(String ref
		ref = normalizeRef(ref);
		RefUpdate u = db.updateRef(ref);
		u.setNewObjectId(obj);
		switch (u.forceUpdate()) {
		case FAST_FORWARD:
		case FORCED:
		case NEW:
		case NO_CHANGE:
			updateServerInfo();
			return obj;

		default:
			throw new IOException("Cannot write " + ref + " " + u.getResult());
		}
	}

	public void delete(String ref) throws Exception {
		ref = normalizeRef(ref);
		RefUpdate u = db.updateRef(ref);
		u.setForceUpdate(true);
		switch (u.delete()) {
		case FAST_FORWARD:
		case FORCED:
		case NEW:
		case NO_CHANGE:
			updateServerInfo();
			return;

		default:
			throw new IOException("Cannot delete " + ref + " " + u.getResult());
		}
	}

	private static String normalizeRef(String ref) {
		if (Constants.HEAD.equals(ref)) {
		} else if ("FETCH_HEAD".equals(ref)) {
		} else if ("MERGE_HEAD".equals(ref)) {
		} else if (ref.startsWith(Constants.R_REFS)) {
		} else
			ref = Constants.R_HEADS + ref;
		return ref;
	}

	public void reset(AnyObjectId id) throws Exception {
		RefUpdate ru = db.updateRef(Constants.HEAD
		ru.setNewObjectId(id);
		RefUpdate.Result result = ru.forceUpdate();
		switch (result) {
			case FAST_FORWARD:
			case FORCED:
			case NEW:
			case NO_CHANGE:
				break;
			default:
				throw new IOException(String.format(
						"Checkout \"%s\" failed: %s"
		}
	}

	public void reset(String name) throws Exception {
		RefUpdate.Result result;
		ObjectId id = db.resolve(name);
		if (id == null)
			throw new IOException("Not a revision: " + name);
		RefUpdate ru = db.updateRef(Constants.HEAD
		ru.setNewObjectId(id);
		result = ru.forceUpdate();
		switch (result) {
			case FAST_FORWARD:
			case FORCED:
			case NEW:
			case NO_CHANGE:
				break;
			default:
				throw new IOException(String.format(
						"Checkout \"%s\" failed: %s"
		}
	}

	public RevCommit cherryPick(AnyObjectId id) throws Exception {
		RevCommit commit = pool.parseCommit(id);
		pool.parseBody(commit);
		if (commit.getParentCount() != 1)
			throw new IOException(String.format(
					"Expected 1 parent for %s
					id.name()
		RevCommit parent = commit.getParent(0);
		pool.parseHeaders(parent);

		Ref headRef = db.exactRef(Constants.HEAD);
		if (headRef == null)
			throw new IOException("Missing HEAD");
		RevCommit head = pool.parseCommit(headRef.getObjectId());

		ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(db
		merger.setBase(parent.getTree());
		if (merger.merge(head
			if (AnyObjectId.equals(head.getTree()
				return null;
			tick(1);
			org.eclipse.jgit.lib.CommitBuilder b =
					new org.eclipse.jgit.lib.CommitBuilder();
			b.setParentId(head);
			b.setTreeId(merger.getResultTreeId());
			b.setAuthor(commit.getAuthorIdent());
			b.setCommitter(new PersonIdent(defaultCommitter
			b.setMessage(commit.getFullMessage());
			ObjectId result;
			try (ObjectInserter ins = inserter) {
				result = ins.insert(b);
				ins.flush();
			}
			update(Constants.HEAD
			return pool.parseCommit(result);
		} else {
			throw new IOException("Merge conflict");
		}
	}

	public void updateServerInfo() throws Exception {
		if (db instanceof FileRepository) {
			final FileRepository fr = (FileRepository) db;
			RefWriter rw = new RefWriter(fr.getRefDatabase().getRefs()) {
				@Override
				protected void writeFile(String name
						throws IOException {
					File path = new File(fr.getDirectory()
					TestRepository.this.writeFile(path
				}
			};
			rw.writePackedRefs();
			rw.writeInfoRefs();

			final StringBuilder w = new StringBuilder();
			for (PackFile p : fr.getObjectDatabase().getPacks()) {
				w.append("P ");
				w.append(p.getPackFile().getName());
				w.append('\n');
			}
			writeFile(new File(new File(fr.getObjectDatabase().getDirectory()
					"info")
		}
	}

	public <T extends RevObject> T parseBody(T object) throws Exception {
		pool.parseBody(object);
		return object;
	}

	public BranchBuilder branch(String ref) {
		if (Constants.HEAD.equals(ref)) {
		} else if (ref.startsWith(Constants.R_REFS)) {
		} else
			ref = Constants.R_HEADS + ref;
		return new BranchBuilder(ref);
	}

	public ObjectId lightweightTag(String name
		if (!name.startsWith(Constants.R_TAGS))
			name = Constants.R_TAGS + name;
		return update(name
	}

	public void fsck(RevObject... tips) throws MissingObjectException
			IncorrectObjectTypeException
		try (ObjectWalk ow = new ObjectWalk(db)) {
			if (tips.length != 0) {
				for (RevObject o : tips)
					ow.markStart(ow.parseAny(o));
			} else {
				for (Ref r : db.getRefDatabase().getRefs())
					ow.markStart(ow.parseAny(r.getObjectId()));
			}

			ObjectChecker oc = new ObjectChecker();
			for (;;) {
				final RevCommit o = ow.next();
				if (o == null)
					break;

				final byte[] bin = db.open(o
				oc.checkCommit(o
				assertHash(o
			}

			for (;;) {
				final RevObject o = ow.nextObject();
				if (o == null)
					break;

				final byte[] bin = db.open(o
				oc.check(o
				assertHash(o
			}
		}
	}

	private static void assertHash(RevObject id
		MessageDigest md = Constants.newMessageDigest();
		md.update(Constants.encodedTypeString(id.getType()));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(bin.length));
		md.update((byte) 0);
		md.update(bin);
		assertEquals(id
	}

	public void packAndPrune() throws Exception {
		if (db.getObjectDatabase() instanceof ObjectDirectory) {
			ObjectDirectory odb = (ObjectDirectory) db.getObjectDatabase();
			NullProgressMonitor m = NullProgressMonitor.INSTANCE;

			final File pack
			try (PackWriter pw = new PackWriter(db)) {
				Set<ObjectId> all = new HashSet<>();
				for (Ref r : db.getRefDatabase().getRefs())
					all.add(r.getObjectId());
				pw.preparePack(m

				final ObjectId name = pw.computeName();

				pack = nameFor(odb
				try (OutputStream out =
						new BufferedOutputStream(new FileOutputStream(pack))) {
					pw.writePack(m
				}
				pack.setReadOnly();

				idx = nameFor(odb
				try (OutputStream out =
						new BufferedOutputStream(new FileOutputStream(idx))) {
					pw.writeIndex(out);
				}
				idx.setReadOnly();
			}

			odb.openPack(pack);
			updateServerInfo();
			prunePacked(odb);
		}
	}

	@Override
	public void close() {
		try {
			inserter.close();
		} finally {
			db.close();
		}
	}

	private static void prunePacked(ObjectDirectory odb) throws IOException {
		for (PackFile p : odb.getPacks()) {
			for (MutableEntry e : p)
				FileUtils.delete(odb.fileFor(e.toObjectId()));
		}
	}

	private static File nameFor(ObjectDirectory odb
		File packdir = odb.getPackDirectory();
		return new File(packdir
	}

	private void writeFile(File p
			ObjectWritingException {
		final LockFile lck = new LockFile(p);
		if (!lck.lock())
			throw new ObjectWritingException("Can't write " + p);
		try {
			lck.write(bin);
		} catch (IOException ioe) {
			throw new ObjectWritingException("Can't write " + p);
		}
		if (!lck.commit())
			throw new ObjectWritingException("Can't write " + p);
	}

	public class BranchBuilder {
		private final String ref;

		BranchBuilder(String ref) {
			this.ref = ref;
		}

		public CommitBuilder commit() throws Exception {
			return new CommitBuilder(this);
		}

		public RevCommit update(CommitBuilder to) throws Exception {
			return update(to.create());
		}

		public RevCommit update(RevCommit to) throws Exception {
			return TestRepository.this.update(ref
		}

		public void delete() throws Exception {
			TestRepository.this.delete(ref);
		}
	}

	public class CommitBuilder {
		private final BranchBuilder branch;

		private final DirCache tree = DirCache.newInCore();

		private ObjectId topLevelTree;

		private final List<RevCommit> parents = new ArrayList<>(2);

		private int tick = 1;

		private String message = "";

		private RevCommit self;

		private PersonIdent author;
		private PersonIdent committer;

		private String changeId;

		private boolean updateCommitterTime;

		CommitBuilder() {
			branch = null;
		}

		CommitBuilder(BranchBuilder b) throws Exception {
			branch = b;

			Ref ref = db.exactRef(branch.ref);
			if (ref != null && ref.getObjectId() != null)
				parent(pool.parseCommit(ref.getObjectId()));
		}

		CommitBuilder(CommitBuilder prior) throws Exception {
			branch = prior.branch;

			DirCacheBuilder b = tree.builder();
			for (int i = 0; i < prior.tree.getEntryCount(); i++)
				b.add(prior.tree.getEntry(i));
			b.finish();

			parents.add(prior.create());
		}

		public CommitBuilder parent(RevCommit p) throws Exception {
			if (parents.isEmpty()) {
				DirCacheBuilder b = tree.builder();
				parseBody(p);
				b.addTree(new byte[0]
						.getObjectReader()
				b.finish();
			}
			parents.add(p);
			return this;
		}

		public List<RevCommit> parents() {
			return Collections.unmodifiableList(parents);
		}

		public CommitBuilder noParents() {
			parents.clear();
			return this;
		}

		public CommitBuilder noFiles() {
			tree.clear();
			return this;
		}

		public CommitBuilder setTopLevelTree(ObjectId treeId) {
			topLevelTree = treeId;
			return this;
		}

		public CommitBuilder add(String path
			return add(path
		}

		public CommitBuilder add(String path
				throws Exception {
			return edit(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.REGULAR_FILE);
					ent.setObjectId(id);
				}
			});
		}

		public CommitBuilder edit(PathEdit edit) {
			DirCacheEditor e = tree.editor();
			e.add(edit);
			e.finish();
			return this;
		}

		public CommitBuilder rm(String path) {
			DirCacheEditor e = tree.editor();
			e.add(new DeletePath(path));
			e.add(new DeleteTree(path));
			e.finish();
			return this;
		}

		public CommitBuilder message(String m) {
			message = m;
			return this;
		}

		public String message() {
			return message;
		}

		public CommitBuilder tick(int secs) {
			tick = secs;
			return this;
		}

		public CommitBuilder ident(PersonIdent ident) {
			author = ident;
			committer = ident;
			return this;
		}

		public CommitBuilder author(PersonIdent a) {
			author = a;
			return this;
		}

		public PersonIdent author() {
			return author;
		}

		public CommitBuilder committer(PersonIdent c) {
			committer = c;
			return this;
		}

		public PersonIdent committer() {
			return committer;
		}

		public CommitBuilder insertChangeId() {
			changeId = "";
			return this;
		}

		public CommitBuilder insertChangeId(String c) {
			ObjectId.fromString(c);
			changeId = c;
			return this;
		}

		public RevCommit create() throws Exception {
			if (self == null) {
				TestRepository.this.tick(tick);

				final org.eclipse.jgit.lib.CommitBuilder c;

				c = new org.eclipse.jgit.lib.CommitBuilder();
				c.setParentIds(parents);
				setAuthorAndCommitter(c);
				if (author != null)
					c.setAuthor(author);
				if (committer != null) {
					if (updateCommitterTime)
						committer = new PersonIdent(committer
					c.setCommitter(committer);
				}

				ObjectId commitId;
				try (ObjectInserter ins = inserter) {
					if (topLevelTree != null)
						c.setTreeId(topLevelTree);
					else
						c.setTreeId(tree.writeTree(ins));
					insertChangeId(c);
					c.setMessage(message);
					commitId = ins.insert(c);
					ins.flush();
				}
				self = pool.parseCommit(commitId);

				if (branch != null)
					branch.update(self);
			}
			return self;
		}

		private void insertChangeId(org.eclipse.jgit.lib.CommitBuilder c) {
			if (changeId == null)
				return;
			int idx = ChangeIdUtil.indexOfChangeId(message
			if (idx >= 0)
				return;

			ObjectId firstParentId = null;
			if (!parents.isEmpty())
				firstParentId = parents.get(0);

			ObjectId cid;
			if (changeId.equals(""))
				cid = ChangeIdUtil.computeChangeId(c.getTreeId()
						c.getAuthor()
			else
				cid = ObjectId.fromString(changeId);
			message = ChangeIdUtil.insertId(message
			if (cid != null)
						+ ObjectId.zeroId().getName() + "\n"
		}

		public CommitBuilder child() throws Exception {
			return new CommitBuilder(this);
		}
	}
}
