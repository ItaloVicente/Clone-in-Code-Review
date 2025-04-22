
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public interface CommitGraph {

	int findGraphPosition(AnyObjectId commit);

	CommitData getCommitData(int graphPos);

	ObjectId getObjectId(int graphPos);

	long getCommitCnt();

	class CommitData {

		private final ObjectId tree;

		private final int[] parents;

		private final long commitTime;

		private final int generation;

		public CommitData(ObjectId tree
				int generation) {
			this.tree = tree;
			this.parents = parents;
			this.commitTime = commitTime;
			this.generation = generation;
		}

		public ObjectId getTree() {
			return tree;
		}

		public int[] getParents() {
			return parents;
		}

		public long getCommitTime() {
			return commitTime;
		}

		public int getGeneration() {
			return generation;
		}
	}
}
