
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectId;

public class CommitGraphSingleFile implements CommitGraph {

	private final CommitGraphFileContent content;

	public CommitGraphSingleFile(CommitGraphFileContent content) {
		this.content = content;
	}

	@Override
	public int findGraphPosition(AnyObjectId objId) {
		return content.findGraphPosition(objId);
	}

	@Override
	public CommitData getCommitData(int graphPos) {
		if (graphPos < 0 || graphPos > content.getCommitCnt()) {
			return null;
		}
		return content.getCommitData(graphPos);
	}

	@Override
	public ObjectId getObjectId(int graphPos) {
		return content.getObjectId(graphPos);
	}

	@Override
	public long getCommitCnt() {
		return content.getCommitCnt();
	}
}
