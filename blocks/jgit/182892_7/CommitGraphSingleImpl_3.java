
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_DATA_EXTRA_LENGTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_GRAPH_MAGIC;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_CHUNK_LOOKUP_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EDGE_LAST_MASK;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EXTRA_EDGES_NEEDED;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_LAST_EDGE;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_NO_PARENT;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

public class CommitGraphDataV1 extends CommitGraphData {

	private static final int FANOUT = 256;

	private final long commitCnt;

	private final int hashLength;

	private final int commitDataLength;

	private long[] oidFanout;

	private byte[][] oidLookup;

	private byte[][] commitData;

	private byte[] extraEdgeList;

	CommitGraphDataV1(InputStream fd
		int magic = NB.decodeInt32(hdr
		if (magic != COMMIT_GRAPH_MAGIC) {
			throw new CommitGraphFormatException(
					JGitText.get().notACommitGraph);
		}

		int hashVersion = hdr[5];
		if (hashVersion != 1) {
			throw new CommitGraphFormatException(
					JGitText.get().incorrectOBJECT_ID_LENGTH);
		}

		hashLength = OBJECT_ID_LENGTH;
		commitDataLength = hashLength + COMMIT_DATA_EXTRA_LENGTH;

		int numberOfChunks = hdr[6];

		byte[] chunkLookup = new byte[GRAPH_CHUNK_LOOKUP_WIDTH
		IO.readFully(fd

		int[] chunkId = new int[numberOfChunks + 1];
		long[] chunkOffset = new long[numberOfChunks + 1];
		for (int i = 0; i <= numberOfChunks; i++) {
			chunkId[i] = NB.decodeInt32(chunkLookup
			for (int j = 0; j < i; j++) {
				if (chunkId[i] == chunkId[j]) {
					throw new CommitGraphFormatException(MessageFormat.format(
							JGitText.get().commitGraphChunkRepeated
							Integer.toHexString(chunkId[i])));
				}
			}
			chunkOffset[i] = NB.decodeInt64(chunkLookup
		}

		oidLookup = new byte[FANOUT][];
		commitData = new byte[FANOUT][];
		for (int i = 0; i < numberOfChunks; i++) {
			long length = chunkOffset[i + 1] - chunkOffset[i];
			long lengthReaded;
			if (chunkOffset[i] < 0
					|| chunkOffset[i] > chunkOffset[numberOfChunks]) {
				throw new CommitGraphFormatException(MessageFormat.format(
						JGitText.get().commitGraphChunkImproperOffset
						Integer.toHexString(chunkId[i])
						Long.valueOf(chunkOffset[i])));
			}
			switch (chunkId[i]) {
			case CHUNK_ID_OID_FANOUT:
				lengthReaded = loadChunkOidFanout(fd);
				break;
			case CHUNK_ID_OID_LOOKUP:
				lengthReaded = loadChunkDataBasedOnFanout(fd
						oidLookup);
				break;
			case CHUNK_ID_COMMIT_DATA:
				lengthReaded = loadChunkDataBasedOnFanout(fd
						commitData);
				break;
			case CHUNK_ID_EXTRA_EDGE_LIST:
				lengthReaded = loadChunkExtraEdgeList(fd
				break;
			default:
				throw new CommitGraphFormatException(MessageFormat.format(
						JGitText.get().commitGraphChunkUnknown
						Integer.toHexString(chunkId[i])));
			}
			if (length != lengthReaded) {
				throw new CommitGraphFormatException(MessageFormat.format(
						JGitText.get().commitGraphChunkImproperOffset
						Integer.toHexString(chunkId[i + 1])
						Long.valueOf(chunkOffset[i + 1])));
			}
		}

		if (oidFanout == null) {
			throw new CommitGraphFormatException(
					JGitText.get().commitGraphOidFanoutNeeded);
		}
		commitCnt = oidFanout[FANOUT - 1];
	}

	private long loadChunkOidFanout(InputStream fd) throws IOException {
		int fanoutLen = FANOUT * 4;
		byte[] fanoutTable = new byte[fanoutLen];
		IO.readFully(fd
		oidFanout = new long[FANOUT];
		for (int k = 0; k < oidFanout.length; k++) {
			oidFanout[k] = NB.decodeUInt32(fanoutTable
		}
		return fanoutLen;
	}

	private long loadChunkDataBasedOnFanout(InputStream fd
			byte[][] chunkData) throws IOException {
		if (oidFanout == null) {
			throw new CommitGraphFormatException(
					JGitText.get().commitGraphOidFanoutNeeded);
		}
		long readedLength = 0;
		for (int k = 0; k < oidFanout.length; k++) {
			long n;
			if (k == 0) {
				n = oidFanout[k];
			} else {
				n = oidFanout[k] - oidFanout[k - 1];
			}
			if (n > 0) {
				long len = n * itemLength;
					throw new CommitGraphFormatException(
							JGitText.get().commitGraphFileIsTooLargeForJgit);
				}

				chunkData[k] = new byte[(int) len];

				IO.readFully(fd
				readedLength += len;
			}
		}
		return readedLength;
	}

	private long loadChunkExtraEdgeList(InputStream fd
			throws IOException {
			throw new CommitGraphFormatException(
					JGitText.get().commitGraphFileIsTooLargeForJgit);
		}
		extraEdgeList = new byte[(int) len];
		IO.readFully(fd
		return len;
	}

	@Override
	public int findGraphPosition(AnyObjectId objId) {
		int levelOne = objId.getFirstByte();
		byte[] data = oidLookup[levelOne];
		if (data == null) {
			return -1;
		}
		int high = data.length / (hashLength);
		int low = 0;
		do {
			int mid = (low + high) >>> 1;
			int pos = objIdOffset(mid);
			int cmp = objId.compareTo(data
			if (cmp < 0) {
				high = mid;
			} else if (cmp == 0) {
				if (levelOne == 0) {
					return mid;
				}
				return (int) (mid + oidFanout[levelOne - 1]);
			} else {
				low = mid + 1;
			}
		} while (low < high);
		return -1;
	}

	@Override
	public ObjectId getObjectId(int graphPos) {
		if (graphPos < 0 || graphPos > commitCnt) {
			return null;
		}
		int levelOne = findLevelOne(graphPos);
		int p = getLevelTwo(graphPos
		int dataIdx = objIdOffset(p);
		return ObjectId.fromRaw(oidLookup[levelOne]
	}

	@Override
	public CommitGraph.CommitData getCommitData(int graphPos) {
		int levelOne = findLevelOne(graphPos);
		int p = getLevelTwo(graphPos
		int dataIdx = commitDataOffset(p);
		byte[] data = this.commitData[levelOne];

		if (graphPos < 0) {
			return null;
		}

		CommitDataImpl commit = new CommitDataImpl();

		commit.tree = ObjectId.fromRaw(data

		long dateHigh = NB.decodeInt32(data
		long dateLow = NB.decodeInt32(data
		commit.commitTime = dateHigh << 32 | dateLow;

		commit.generation = NB.decodeInt32(data

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
		commit.parents = pList;

		return commit;
	}

	int[] findExtraEdgeList(int pptr) {
		if (extraEdgeList == null) {
			return null;
		}
		int maxOffset = extraEdgeList.length - 4;
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
			parentPosition = NB.decodeInt32(extraEdgeList
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

	@Override
	public long getCommitCnt() {
		return commitCnt;
	}

	@Override
	public int getHashLength() {
		return hashLength;
	}

	private int findLevelOne(long nthPosition) {
		int levelOne = Arrays.binarySearch(oidFanout
		if (levelOne >= 0) {
			long base = oidFanout[levelOne];
			while (levelOne > 0 && base == oidFanout[levelOne - 1]) {
				levelOne--;
			}
		} else {
			levelOne = -(levelOne + 1);
		}
		return levelOne;
	}

	private int getLevelTwo(long nthPosition
		long base = levelOne > 0 ? oidFanout[levelOne - 1] : 0;
		return (int) (nthPosition - base);
	}

	private int objIdOffset(int mid) {
		return hashLength * mid;
	}

	private int commitDataOffset(int mid) {
		return commitDataLength * mid;
	}
}
