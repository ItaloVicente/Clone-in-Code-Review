
package org.eclipse.jgit.lib;

public interface CommitGraph {

	int GENERATION_NUMBER_INFINITY = -1;

	int GENERATION_NUMBER_ZERO = 0;

	CommitData getCommitData(AnyObjectId commit);

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
