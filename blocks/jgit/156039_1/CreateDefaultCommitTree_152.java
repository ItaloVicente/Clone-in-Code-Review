
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
                                final ObjectId headId
                                final ObjectInserter inserter
                                final CopyCommitContent commitContent) {
        super(git
              headId
              inserter
              commitContent);
    }

    public Optional<ObjectId> execute() {
        final Map<String

        final DirCacheEditor editor = DirCache.newInCore().editor();

        try {
            iterateOverTreeWalk(git
                                headId
                                (walkPath
                                    final String toPath = content.get(walkPath);
                                    addToTemporaryInCoreIndex(editor
                                                              new DirCacheEntry(walkPath)
                                                              hTree.getEntryObjectId()
                                                              hTree.getEntryFileMode());
                                    if (toPath != null) {
                                        addToTemporaryInCoreIndex(editor
                                                                  new DirCacheEntry(toPath)
                                                                  hTree.getEntryObjectId()
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
