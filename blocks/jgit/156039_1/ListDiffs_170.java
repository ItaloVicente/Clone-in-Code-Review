
package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RenameCallback;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.TreeRevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class ListCommits {

    private final Git git;
    private final ObjectId startRange;
    private final ObjectId endRange;
    private final String path;

    public ListCommits(final Git git
                       final Ref ref
                       final String path) {
        this.git = git;
        this.path = makeRelative(path);
        this.startRange = null;
        this.endRange = ref.getObjectId();
    }

    private static String makeRelative(String path) {
        return (path != null && path.startsWith("/")) ? path.substring(1) : path;
    }

    public ListCommits(final GitImpl git
                       final ObjectId startRange
                       final ObjectId endRange) {
        this.git = git;
        this.startRange = startRange;
        this.endRange = endRange;
        this.path = null;
    }

    public CommitHistory execute() throws IOException
        try (final RevWalk rw = buildWalk()) {
            if (path == null || path.isEmpty()) {
                return fullCommitHistory(rw);
            } else {
                return pathCommitHistory(rw);
            }
        }
    }

    private CommitHistory pathCommitHistory(final RevWalk rw) throws MissingObjectException
        final Map<AnyObjectId
        final List<RevCommit> commits = new ArrayList<>();
        final RenameCaptor renameCaptor = new RenameCaptor();
        final TreeRevFilter revFilter = createTreeRevFilter(rw
        String curPath = path;
        for (final RevCommit commit : rw) {
            if (revFilter.include(rw
                @SuppressWarnings("resource")
                final TreeWalk tw = new TreeWalk(rw.getObjectReader());
                tw.setRecursive(true);
                tw.setFilter(PathFilter.create(curPath));
                tw.addTree(commit.getTree());
                if (tw.next()) {
                    commits.add(commit);
                    pathByCommit.put(commit.getId()
                    if (renameCaptor.hasCaptured()) {
                        curPath = renameCaptor.getAndReset().getOldPath();
                    }
                }
            }
        }

        return new CommitHistory(commits
    }

    private CommitHistory fullCommitHistory(final RevWalk rw) {
        final List<RevCommit> commits = stream(rw.spliterator()
        return new CommitHistory(commits
    }

    private TreeRevFilter createTreeRevFilter(final RevWalk rw
        final FollowFilter followFilter = FollowFilter.create(curPath
        followFilter.setRenameCallback(renameCallback);
        final TreeRevFilter revFilter = new TreeRevFilter(rw
        return revFilter;
    }

    private RevWalk buildWalk() throws GitAPIException
        final RevWalk rw = new RevWalk(git.getRepository());
        rw.setTreeFilter(TreeFilter.ANY_DIFF);
        rw.markStart(rw.parseCommit(endRange));
        rw.sort(RevSort.TOPO);
        if (startRange != null) {
            rw.markUninteresting(rw.parseCommit(startRange));
        }

        return rw;
    }

    private static class RenameCaptor extends RenameCallback {

        private DiffEntry captured;

        @Override
        public void renamed(final DiffEntry entry) {
            captured = entry;
        }

        public boolean hasCaptured() {
            return captured != null;
        }

        public DiffEntry getAndReset() {
            if (captured == null) {
                throw new NullPointerException("Cannot get DiffEntry when none was captured.");
            }

            final DiffEntry retVal = captured;
            captured = null;

            return retVal;
        }
    }
}
