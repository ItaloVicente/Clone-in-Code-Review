
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public interface CommitGraph {

	int findGraphPosition(AnyObjectId commit);

	CommitData getCommitData(int graphPos);

	ObjectId getObjectId(int graphPos);

	long getCommitCnt();

	interface CommitData {

		ObjectId getTree();

		int[] getParents();

		long getCommitTime();

		int getGeneration();
	}
}
