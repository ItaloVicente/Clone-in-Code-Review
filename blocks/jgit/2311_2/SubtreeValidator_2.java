
package org.eclipse.jgit.subtree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalkTestCase;
import org.eclipse.jgit.treewalk.TreeWalk;

public abstract class SubtreeTestCase extends RevWalkTestCase {

	RevTree editTree(ObjectId srcTree
			throws Exception {

		DirCache dirCache = DirCache.newInCore();
		DirCacheBuilder builder = dirCache.builder();
		builder.addTree(new byte[0]
		builder.finish();

		DirCacheEditor editor = dirCache.editor();
		for (final DirCacheEntry entry : entries) {
			editor.add(new PathEdit(entry.getPathString()) {
				@Override
				public void apply(DirCacheEntry entry2) {
					try {
						entry2.setObjectId(entry.getObjectId());
						entry2.setFileMode(FileMode.REGULAR_FILE);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			});
		}
		editor.finish();

		ObjectInserter oi = db.newObjectInserter();
		try {
			return rw.parseTree(editor.getDirCache().writeTree(oi));
		} finally {
			oi.release();
		}
	}

	RevCommit edit(RevCommit c
			DirCacheEntry... entries) throws Exception {
		rw.parseCommit(c);

		ObjectInserter oi = db.newObjectInserter();
		try {
			RevTree tree = editTree(c.getTree()
			if (inPlace) {
				return commit(rw.parseTree(tree)
			} else {
				return commit(rw.parseTree(tree)
			}
		} finally {
			oi.release();
		}
	}

	RevCommit subtreeAdd(String path
			final RevCommit subCommit) throws IOException {

		rw.parseCommit(superCommit);
		rw.parseCommit(subCommit);

		ObjectInserter oi = db.newObjectInserter();
		try {

			DirCache dirCache = DirCache.newInCore();
			DirCacheBuilder builder = dirCache.builder();
			builder.addTree(new byte[0]
					superCommit.getTree());
			builder.finish();

			DirCacheEditor editor = dirCache.editor();
			final TreeWalk tw = new TreeWalk(db);
			tw.setRecursive(true);
			tw.addTree(subCommit.getTree());
			while (tw.next()) {
				final ObjectId subId = tw.getObjectId(0);
				final FileMode mode = tw.getFileMode(0);
				editor.add(new PathEdit(path + "/" + tw.getPathString()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setObjectId(subId);
						ent.setFileMode(mode);
					}
				});
			}
			editor.finish();

			ObjectId tree = editor.getDirCache().writeTree(oi);
			List<SubtreeContext> contexts = new ArrayList<SubtreeContext>();
			contexts.add(new PathBasedContext(path
			tree = SubtreeSplitter.updateSubtreeConfig(db
					tree);

			CommitBuilder cb = new CommitBuilder();
			cb.addParentId(superCommit);
			cb.addParentId(subCommit);
			cb.setAuthor(superCommit.getAuthorIdent());
			cb.setCommitter(superCommit.getCommitterIdent());
			cb.setTreeId(tree);
			return rw.parseCommit(oi.insert(cb));

		} finally {
			oi.release();
		}
	}

}
