package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class FileTreeIteratorJava7Test extends RepositoryTestCase {
	@Test
	public void testFileTreeSymLinkMode() throws IOException {
		FS fs = db.getFS();
		writeTrashFile("mål/data"
		fs.createSymLink(new File(trash
		FileTreeIterator fti = new FileTreeIterator(db);
		assertFalse(fti.eof());
		assertEquals("länk"
		assertEquals(FileMode.SYMLINK
		fti.next(1);
		assertFalse(fti.eof());
		assertEquals("mål"
		assertEquals(FileMode.TREE
		fti.next(1);
		assertTrue(fti.eof());
	}

	@Test
	public void testSymlinkNotModifiedThoughNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String UNNORMALIZED = "target/";
		final byte[] UNNORMALIZED_BYTES = Constants.encode(UNNORMALIZED);
		ObjectInserter oi = db.newObjectInserter();
		final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
				UNNORMALIZED_BYTES
				UNNORMALIZED_BYTES.length);
		oi.release();
		dce.add(new DirCacheEditor.PathEdit("link") {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.SYMLINK);
				ent.setObjectId(linkid);
				ent.setLength(UNNORMALIZED_BYTES.length);
			}
		});
		assertTrue(dce.commit());
		new Git(db).commit().setMessage("Adding link").call();
		new Git(db).reset().setMode(ResetType.HARD).call();
		DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
		FileTreeIterator fti = new FileTreeIterator(db);

		assertEquals("link"
		assertEquals("link"

		assertFalse(fti.isModified(dci.getDirCacheEntry()
	}

	@Test
	public void testSymlinkModifiedNotNormalized() throws Exception {
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		ObjectInserter oi = db.newObjectInserter();
		final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
				0
		oi.release();
		dce.add(new DirCacheEditor.PathEdit("link") {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.SYMLINK);
				ent.setObjectId(linkid);
				ent.setLength(NORMALIZED_BYTES.length);
			}
		});
		assertTrue(dce.commit());
		new Git(db).commit().setMessage("Adding link").call();
		new Git(db).reset().setMode(ResetType.HARD).call();
		DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
		FileTreeIterator fti = new FileTreeIterator(db);

		assertEquals("link"
		assertEquals("link"

		assertFalse(fti.isModified(dci.getDirCacheEntry()
	}

	@Test
	public void testSymlinkActuallyModified() throws Exception {
		final String NORMALIZED = "target";
		final byte[] NORMALIZED_BYTES = Constants.encode(NORMALIZED);
		ObjectInserter oi = db.newObjectInserter();
		final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
				0
		oi.release();
		DirCache dc = db.lockDirCache();
		DirCacheEditor dce = dc.editor();
		dce.add(new DirCacheEditor.PathEdit("link") {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.SYMLINK);
				ent.setObjectId(linkid);
				ent.setLength(NORMALIZED_BYTES.length);
			}
		});
		assertTrue(dce.commit());
		new Git(db).commit().setMessage("Adding link").call();
		new Git(db).reset().setMode(ResetType.HARD).call();

		FileUtils.delete(new File(trash
		FS.DETECTED.createSymLink(new File(trash
		DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
		FileTreeIterator fti = new FileTreeIterator(db);

		assertEquals("link"
		assertEquals("link"

		assertTrue(fti.isModified(dci.getDirCacheEntry()
	}
}
