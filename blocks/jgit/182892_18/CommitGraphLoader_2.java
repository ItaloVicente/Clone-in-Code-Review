
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectId;

abstract class CommitGraphFileContent {

	public abstract int findGraphPosition(AnyObjectId objId);

	public abstract ObjectId getObjectId(int graphPos);

	public abstract CommitGraph.CommitData getCommitData(int graphPos);

	public abstract long getCommitCnt();

	public abstract int getHashLength();

	static class CommitDataImpl implements CommitGraph.CommitData {

		ObjectId tree;

		int[] parents;

		long commitTime;

		int generation;

		@Override
		public ObjectId getTree() {
			return tree;
		}

		@Override
		public int[] getParents() {
			return parents;
		}

		@Override
		public long getCommitTime() {
			return commitTime;
		}

		@Override
		public int getGeneration() {
			return generation;
		}
	}
}
