
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class CommitGraphV1 implements CommitGraph {

	private final GraphObjectIdIndex idIdx;

	private final GraphCommitData commitData;

	CommitGraphV1(GraphObjectIdIndex lookupIndex
		this.idIdx = lookupIndex;
		this.commitData = commitData;
	}

	@Override
	public int findGraphPosition(AnyObjectId commit) {
		return idIdx.findGraphPosition(commit);
	}

	@Override
	public CommitData getCommitData(int graphPos) {
		return commitData.getCommitData(graphPos);
	}

	@Override
	public ObjectId getObjectId(int graphPos) {
		return idIdx.getObjectId(graphPos);
	}

	@Override
	public long getCommitCnt() {
		return idIdx.getCommitCnt();
	}
}
