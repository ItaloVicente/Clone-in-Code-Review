
package org.eclipse.jgit.lib;

public interface CommitGraph {

	int GENERATION_UNKNOWN = Integer.MAX_VALUE;

	int GENERATION_NOT_COMPUTED = 0;


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
