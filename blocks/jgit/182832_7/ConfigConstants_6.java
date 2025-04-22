
package org.eclipse.jgit.lib;

public interface CommitGraph {

	int GENERATION_UNKNOWN = -1;

	int GENERATION_NOT_COMPUTED = 0;

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
