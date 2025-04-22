package org.eclipse.jgit.niofs.internal.op.commands;

import java.util.Map;
import java.util.Optional;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;

public class CreateCopyCommitTree extends BaseCreateCommitTree<CopyCommitContent> {

	public CreateCopyCommitTree(final Git git
			final CopyCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final Map<String

		final DirCacheEditor editor = DirCache.newInCore().editor();

		try {
			iterateOverTreeWalk(git
				final String toPath = content.get(walkPath);
				addToTemporaryInCoreIndex(editor
						hTree.getEntryFileMode());
				if (toPath != null) {
					addToTemporaryInCoreIndex(editor
							hTree.getEntryFileMode());
				}
			});

			editor.finish();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return buildTree(editor);
	}
}
