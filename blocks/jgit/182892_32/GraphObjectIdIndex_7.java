
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_DATA_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EDGE_LAST_MASK;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EXTRA_EDGES_NEEDED;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_LAST_EDGE;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_NO_PARENT;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraph.CommitData;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;

class GraphCommitData {

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
		if (graphPos < 0) {
			return null;
		}
		int dataIdx = commitDataLength * graphPos;

		ObjectId tree = ObjectId.fromRaw(data

		long dateHigh = NB.decodeUInt32(data
		long dateLow = NB.decodeUInt32(data
		long commitTime = dateHigh << 32 | dateLow;

		int generation = NB.decodeInt32(data

		boolean noParents = false;
		int[] pList = new int[0];
		int edgeValue = NB.decodeInt32(data
		if (edgeValue == GRAPH_NO_PARENT) {
			noParents = true;
		}

		if (!noParents) {
			pList = new int[1];
			int parent = edgeValue;
			pList[0] = parent;

			edgeValue = NB.decodeInt32(data
			if (edgeValue != GRAPH_NO_PARENT) {
				if ((edgeValue & GRAPH_EXTRA_EDGES_NEEDED) != 0) {
					int pptr = edgeValue & GRAPH_EDGE_LAST_MASK;
					int[] eList = findExtraEdgeList(pptr);
					if (eList == null) {
						return null;
					}
					int[] old = pList;
					pList = new int[eList.length + 1];
					pList[0] = old[0];
					for (int i = 0; i < eList.length; i++) {
						parent = eList[i];
						pList[i + 1] = parent;
					}
				} else {
					parent = edgeValue;
					pList = new int[] { pList[0]
				}
			}
		}
		int[] parents = pList;
		return new CommitData(tree
	}

	private int[] findExtraEdgeList(int pptr) {
		int maxOffset = extraList.length - 4;
		int offset = pptr * 4;
		if (offset < 0 || offset > maxOffset) {
			return null;
		}
		int[] pList = new int[32];
		int count = 0;
		int parentPosition;
		for (;;) {
			if (count >= pList.length) {
				int[] old = pList;
				pList = new int[pList.length + 32];
				System.arraycopy(old
			}
			if (offset > maxOffset) {
				return null;
			}
			parentPosition = NB.decodeInt32(extraList
			if ((parentPosition & GRAPH_LAST_EDGE) != 0) {
				pList[count] = parentPosition & GRAPH_EDGE_LAST_MASK;
				count++;
				break;
			}
			pList[count++] = parentPosition;
			offset += 4;
		}
		int[] old = pList;
		pList = new int[count];
		System.arraycopy(old

		return pList;
	}
}
