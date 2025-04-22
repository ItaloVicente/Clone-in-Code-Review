
package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.Optional;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlobAsInputStream {

    private static final Logger LOG = LoggerFactory.getLogger(BlobAsInputStream.class);

    private final Git git;
    private final String treeRef;
    private final String path;

    public BlobAsInputStream(final Git git
                             final String treeRef
                             final String path) {
        this.git = git;
        this.treeRef = treeRef;
        this.path = path;
    }

    public Optional<InputStream> execute() throws NoSuchFileException {
        try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
            final ObjectId tree = git.getTreeFromRef(treeRef);
            tw.setFilter(PathFilter.create(path));
            tw.reset(tree);
            while (tw.next()) {
                if (tw.isSubtree() && !path.equals(tw.getPathString())) {
                    tw.enterSubtree();
                    continue;
                }
                return Optional.of(new ByteArrayInputStream(git.getRepository().open(tw.getObjectId(0)
                                                                                     Constants.OBJ_BLOB).getBytes()));
            }
        } catch (final Throwable t) {
            LOG.debug("Unexpected exception
                      t);
            throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
        }
        throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
    }
}
