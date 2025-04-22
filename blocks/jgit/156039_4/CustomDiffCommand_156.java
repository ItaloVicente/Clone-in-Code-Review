package org.eclipse.jgit.niofs.internal.op.commands;

import java.util.Optional;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;

public class CreateRevertCommitTree extends BaseCreateCommitTree<RevertCommitContent> {

	public CreateRevertCommitTree(final Git git
			final RevertCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final DirCacheEditor editor = DirCache.newInCore().editor();

		try {
			iterateOverTreeWalk(git
				addToTemporaryInCoreIndex(editor
						hTree.getEntryFileMode());
			});

			editor.finish();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return buildTree(editor);
	}
}
