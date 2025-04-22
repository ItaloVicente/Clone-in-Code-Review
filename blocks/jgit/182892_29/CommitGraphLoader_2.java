
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectId;

interface CommitGraphFileContent {

	int findGraphPosition(AnyObjectId objId);

	ObjectId getObjectId(int graphPos);

	CommitGraph.CommitData getCommitData(int graphPos);

	long getCommitCnt();

	int getHashLength();

	class CommitDataImpl implements CommitGraph.CommitData {

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
