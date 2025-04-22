
package org.eclipse.jgit.junit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheEditor.DeletePath;
import org.eclipse.jgit.dircache.DirCacheEditor.DeleteTree;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.LockFile;
import org.eclipse.jgit.lib.ObjectDirectory;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tag;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class TestRepository {
	private static final PersonIdent author;

	private static final PersonIdent committer;

	static {
		final MockSystemReader m = new MockSystemReader();
		final long now = m.getCurrentTime();
		final int tz = m.getTimezone(now);

		final String an = "J. Author";
		final String ae = "jauthor@example.com";
		author = new PersonIdent(an

		final String cn = "J. Committer";
		final String ce = "jcommitter@example.com";
		committer = new PersonIdent(cn
	}

	private final Repository db;

	private final RevWalk pool;

	private final ObjectWriter writer;

	private long now;

	public TestRepository(Repository db) throws Exception {
		this(db
	}

	public TestRepository(Repository db
		this.db = db;
		this.pool = rw;
		this.writer = new ObjectWriter(db);
		this.now = 1236977987000L;
	}

	public Repository getRepository() {
		return db;
	}

	public RevWalk getRevWalk() {
		return pool;
	}

	public Date getClock() {
		return new Date(now);
	}

	public void tick(final int secDelta) {
		now += secDelta * 1000L;
	}

	public RevBlob blob(final String content) throws Exception {
		return blob(content.getBytes("UTF-8"));
	}

	public RevBlob blob(final byte[] content) throws Exception {
		return pool.lookupBlob(writer.writeBlob(content));
	}

	public DirCacheEntry file(final String path
			throws Exception {
		final DirCacheEntry e = new DirCacheEntry(path);
		e.setFileMode(FileMode.REGULAR_FILE);
		e.setObjectId(blob);
		return e;
	}

	public RevTree tree(final DirCacheEntry... entries) throws Exception {
		final DirCache dc = DirCache.newInCore();
		final DirCacheBuilder b = dc.builder();
		for (final DirCacheEntry e : entries)
			b.add(e);
		b.finish();
		return pool.lookupTree(dc.writeTree(writer));
	}

	public RevObject get(final RevTree tree
			throws AssertionFailedError
		final TreeWalk tw = new TreeWalk(db);
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
		Assert.fail("Can't find " + path + " in tree " + tree.name());
	}

	public RevCommit commit(final RevCommit... parents) throws Exception {
		return commit(1
	}

	public RevCommit commit(final RevTree tree
			throws Exception {
		return commit(1
	}

	public RevCommit commit(final int secDelta
			throws Exception {
		return commit(secDelta
	}

	public RevCommit commit(final int secDelta
			final RevCommit... parents) throws Exception {
		tick(secDelta);

		final Commit c = new Commit(db);
		c.setTreeId(tree);
		c.setParentIds(parents);
		c.setAuthor(new PersonIdent(author
		c.setCommitter(new PersonIdent(committer
		c.setMessage("");
		return pool.lookupCommit(writer.writeCommit(c));
	}

	public CommitBuilder commit() {
		return new CommitBuilder();
	}

	public RevTag tag(final String name
		final Tag t = new Tag(db);
		t.setType(Constants.typeString(dst.getType()));
		t.setObjId(dst.toObjectId());
		t.setTag(name);
		t.setTagger(new PersonIdent(committer
		t.setMessage("");
		return (RevTag) pool.lookupAny(writer.writeTag(t)
	}

	public RevCommit update(String ref
		return update(ref
	}

	public <T extends AnyObjectId> T update(String ref
		if (Constants.HEAD.equals(ref)) {
		} else if ("FETCH_HEAD".equals(ref)) {
		} else if ("MERGE_HEAD".equals(ref)) {
		} else if (ref.startsWith(Constants.R_REFS)) {
		} else
			ref = Constants.R_HEADS + ref;

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

	public void updateServerInfo() throws Exception {
		if (db.getObjectDatabase() instanceof ObjectDirectory) {
			RefWriter rw = new RefWriter(db.getAllRefs().values()) {
				@Override
				protected void writeFile(final String name
						throws IOException {
					final File p = new File(db.getDirectory()
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
			};
			rw.writePackedRefs();
			rw.writeInfoRefs();
		}
	}

	public <T extends RevObject> T parseBody(final T object) throws Exception {
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

	public class BranchBuilder {
		private final String ref;

		BranchBuilder(final String ref) {
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
	}

	public class CommitBuilder {
		private final BranchBuilder branch;

		private final DirCache tree = DirCache.newInCore();

		private final List<RevCommit> parents = new ArrayList<RevCommit>(2);

		private int tick = 1;

		private String message = "";

		private RevCommit self;

		CommitBuilder() {
			branch = null;
		}

		CommitBuilder(BranchBuilder b) throws Exception {
			branch = b;

			Ref ref = db.getRef(branch.ref);
			if (ref != null) {
				parent(pool.parseCommit(ref.getObjectId()));
			}
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
				b.finish();
			}
			parents.add(p);
			return this;
		}

		public CommitBuilder noParents() {
			parents.clear();
			return this;
		}

		public CommitBuilder noFiles() {
			tree.clear();
			return this;
		}

		public CommitBuilder add(String path
			return add(path
		}

		public CommitBuilder add(String path
				throws Exception {
			DirCacheEditor e = tree.editor();
			e.add(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.REGULAR_FILE);
					ent.setObjectId(id);
				}
			});
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

		public CommitBuilder tick(int secs) {
			tick = secs;
			return this;
		}

		public RevCommit create() throws Exception {
			if (self == null) {
				TestRepository.this.tick(tick);

				final Commit c = new Commit(db);
				c.setTreeId(pool.lookupTree(tree.writeTree(writer)));
				c.setParentIds(parents.toArray(new RevCommit[parents.size()]));
				c.setAuthor(new PersonIdent(author
				c.setCommitter(new PersonIdent(committer
				c.setMessage(message);

				self = pool.lookupCommit(writer.writeCommit(c));

				if (branch != null)
					branch.update(self);
			}
			return self;
		}

		public CommitBuilder child() throws Exception {
			return new CommitBuilder(this);
		}
	}
}
