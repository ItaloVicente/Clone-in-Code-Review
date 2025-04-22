
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
                                  final ObjectId headId
                                  final ObjectInserter inserter
                                  final RevertCommitContent commitContent) {
        super(git
              headId
              inserter
              commitContent);
    }

    public Optional<ObjectId> execute() {
        final DirCacheEditor editor = DirCache.newInCore().editor();

        try {
            iterateOverTreeWalk(git
                                headId
                                (walkPath
                                    addToTemporaryInCoreIndex(editor
                                                              new DirCacheEntry(walkPath)
                                                              hTree.getEntryObjectId()
                                                              hTree.getEntryFileMode());
                                });

            editor.finish();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return buildTree(editor);
    }
}
