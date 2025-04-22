
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class CommitGraphV1 implements CommitGraph {

	private final GraphObjectIndex idx;

	private final GraphCommitData commitData;

	CommitGraphV1(GraphObjectIndex index
		this.idx = index;
		this.commitData = commitData;
	}

	@Override
	public int findGraphPosition(AnyObjectId commit) {
		return idx.findGraphPosition(commit);
	}

	@Override
	public CommitData getCommitData(int graphPos) {
		if (graphPos < 0 || graphPos >= getCommitCnt()) {
			return null;
		}
		return commitData.getCommitData(graphPos);
	}

	@Override
	public ObjectId getObjectId(int graphPos) {
		return idx.getObjectId(graphPos);
	}

	@Override
	public long getCommitCnt() {
		return idx.getCommitCnt();
	}
}
