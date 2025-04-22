
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class EmptyCommitGraph implements CommitGraph {

    @Override
    public int findGraphPosition(AnyObjectId commit) {
        return -1;
    }

    @Override
    public CommitData getCommitData(int graphPos) {
        return null;
    }

    @Override
    public ObjectId getObjectId(int graphPos) {
        return null;
    }

    @Override
    public long getCommitCnt() {
        return 0;
    }
}
