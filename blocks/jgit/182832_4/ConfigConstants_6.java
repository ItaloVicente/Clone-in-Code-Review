
package org.eclipse.jgit.lib;

public abstract class CommitGraph {

	public static final int GENERATION_NUMBER_INFINITY = -1;

	public static final int GENERATION_NUMBER_ZERO = 0;

	public abstract CommitData getCommitData(AnyObjectId commit);

	public abstract CommitData getCommitData(int graphPos);

	public abstract ObjectId getObjectId(int graphPos);

	public abstract long getCommitCnt();

	public abstract static class CommitData {

		public abstract ObjectId getTree();

		public abstract int[] getParents();

		public abstract long getCommitTime();

		public abstract int getGeneration();
	}
}
