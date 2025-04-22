
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_DATA_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EDGE_LAST_MASK;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EXTRA_EDGES_NEEDED;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_LAST_EDGE;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_NO_PARENT;

import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraph.CommitData;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;

class GraphCommitData {

	private static final int[] NO_PARENTS = {};

	private final byte[] data;

	private final byte[] extraList;

	private final int hashLength;

	private final int commitDataLength;

	GraphCommitData(int hashLength
			byte[] extraList) {
		this.data = commitData;
		this.extraList = extraList;
		this.hashLength = hashLength;
		this.commitDataLength = hashLength + COMMIT_DATA_WIDTH;
	}

	CommitData getCommitData(int graphPos) {
		int dataIdx = commitDataLength * graphPos;

		ObjectId tree = ObjectId.fromRaw(data

		long dateHigh = NB.decodeUInt32(data
		long dateLow = NB.decodeUInt32(data
		long commitTime = dateHigh << 32 | dateLow;

		int generation = NB.decodeInt32(data

		int parent1 = NB.decodeInt32(data
		if (parent1 == GRAPH_NO_PARENT) {
			return new CommitDataImpl(tree
		}

		int parent2 = NB.decodeInt32(data
		if (parent2 == GRAPH_NO_PARENT) {
			return new CommitDataImpl(tree
					generation);
		}

		if ((parent2 & GRAPH_EXTRA_EDGES_NEEDED) == 0) {
			return new CommitDataImpl(tree
					commitTime
		}

		return new CommitDataImpl(tree
				findParentsForOctopusMerge(parent1
						parent2 & GRAPH_EDGE_LAST_MASK)
				commitTime
	}

	private int[] findParentsForOctopusMerge(int parent1
		int maxOffset = extraList.length - 4;
		int offset = extraEdgePos * 4;
		if (offset < 0 || offset > maxOffset) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidExtraEdgeListPosition
					Integer.valueOf(extraEdgePos)));
		}
		int[] pList = new int[32];
		pList[0] = parent1;
		int count = 1;
		int parentPosition;
		for (; offset <= maxOffset; offset += 4) {
			if (count >= pList.length) {
				pList = Arrays.copyOf(pList
			}
			parentPosition = NB.decodeInt32(extraList
			if ((parentPosition & GRAPH_LAST_EDGE) != 0) {
				pList[count++] = parentPosition & GRAPH_EDGE_LAST_MASK;
				break;
			}
			pList[count++] = parentPosition;
		}
		return Arrays.copyOf(pList
	}

	private static class CommitDataImpl implements CommitData {

		private final ObjectId tree;

		private final int[] parents;

		private final long commitTime;

		private final int generation;

		public CommitDataImpl(ObjectId tree
				int generation) {
			this.tree = tree;
			this.parents = parents;
			this.commitTime = commitTime;
			this.generation = generation;
		}

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
