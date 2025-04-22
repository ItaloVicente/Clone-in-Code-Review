
package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class GitlinkMergeTest extends SampleDataRepositoryTestCase {
	private static final boolean NORMAL_MERGE_CASE = false;

	private static final boolean IGNORE_CONFLICTS_CASE = true;

	private String linkId1 = "DEADBEEFDEADBEEFBABEDEADBEEFDEADBEEFBABE";
	private String linkId2 = "DEADDEADDEADDEADDEADDEADDEADDEADDEADDEAD";
	private String linkId3 = "BEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEF";

	private String SUBMODULE_PATH = "submodule";

	@Test
	public void testGitLinkMerging_AddNew() throws Exception {
		assertGitLinkValue(
				testGitLink(null
				linkId3);
	}

	@Test
	public void testGitLinkMerging_Delete() throws Exception {
		assertGitLinkDoesntExist(
				testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateDelete() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_DeleteUpdate() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateUpdate() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_bothAddedSameLink() throws Exception {
		assertGitLinkValue(
				testGitLink(null
				linkId2);
	}

	@Test
	public void testGitLinkMerging_bothAddedDifferentLink() throws Exception {
		testGitLink(null
	}

	@Test
	public void testGitLinkMerging_AddNew_ignoreConflicts() throws Exception {
		assertGitLinkValue(
				testGitLink(null
				linkId3);
	}

	@Test
	public void testGitLinkMerging_Delete_ignoreConflicts() throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
				IGNORE_CONFLICTS_CASE
	}

	@Test
	public void testGitLinkMerging_UpdateDelete_ignoreConflicts()
			throws Exception {
		assertGitLinkValue(testGitLink(linkId1
				IGNORE_CONFLICTS_CASE
	}

	@Test
	public void testGitLinkMerging_DeleteUpdate_ignoreConflicts()
			throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
				IGNORE_CONFLICTS_CASE
	}

	@Test
	public void testGitLinkMerging_UpdateUpdate_ignoreConflicts()
			throws Exception {
		assertGitLinkValue(testGitLink(linkId1
				IGNORE_CONFLICTS_CASE
	}

	@Test
	public void testGitLinkMerging_bothAddedSameLink_ignoreConflicts()
			throws Exception {
		assertGitLinkValue(testGitLink(null
				IGNORE_CONFLICTS_CASE
	}

	@Test
	public void testGitLinkMerging_bothAddedDifferentLink_ignoreConflicts()
			throws Exception {
		assertGitLinkValue(testGitLink(null
				IGNORE_CONFLICTS_CASE
	}

	protected Merger testGitLink(@Nullable String baseLink
			@Nullable String oursLink
			boolean ignoreConflicts
			throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		maybeAddLink(oTreeBuilder
		maybeAddLink(tTreeBuilder

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = newResolveMerger(ignoreConflicts);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertEquals(shouldMerge

		return resolveMerger;
	}

	private Merger newResolveMerger(boolean ignoreConflicts) {
		if (!ignoreConflicts) {
			return MergeStrategy.RESOLVE.newMerger(db
		}
		return new ResolveMerger(db
			@Override
			protected boolean mergeImpl() throws IOException {
				if (implicitDirCache) {
					dircache = nonNullRepo().lockDirCache();
				}
				try {
					return mergeTrees(mergeBase()
							sourceTrees[1]
				} finally {
					if (implicitDirCache) {
						dircache.unlock();
					}
				}
			}
		};
	}

	@Test
	public void testGitLinkMerging_blobWithLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		oTreeBuilder.add(
				createEntry(SUBMODULE_PATH

		maybeAddLink(tTreeBuilder

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkWithBlob() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		maybeAddLink(oTreeBuilder
		tTreeBuilder.add(
				createEntry(SUBMODULE_PATH

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkWithLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		maybeAddLink(oTreeBuilder
		maybeAddLink(tTreeBuilder

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_blobWithBlobFromLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		oTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		tTreeBuilder.add(
				createEntry(SUBMODULE_PATH

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkBlobDeleted() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		oTreeBuilder.add(createEntry(SUBMODULE_PATH

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	private void maybeAddLink(DirCacheBuilder builder
			@Nullable String linkId) {
		if (linkId == null) {
			return;
		}
		DirCacheEntry newLink = createGitLink(SUBMODULE_PATH
				ObjectId.fromString(linkId));
		builder.add(newLink);
	}

	private void assertGitLinkValue(Merger resolveMerger
			throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.reset(resolveMerger.getResultTreeId());

			assertTrue(tw.next());
			assertEquals(SUBMODULE_PATH
			assertEquals(ObjectId.fromString(shouldChoose)

			assertFalse(tw.next());
		}
	}

	private void assertGitLinkDoesntExist(Merger resolveMerger)
			throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.reset(resolveMerger.getResultTreeId());

			assertFalse(tw.next());
		}
	}

	private static ObjectId commit(ObjectInserter odi
			ObjectId[] parentIds) throws Exception {
		CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor"
		c.setCommitter(c.getAuthor());
		c.setParentIds(parentIds);
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}
}
