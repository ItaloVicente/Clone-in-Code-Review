
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.NB;

class CommitGraphBuilder {

	private final int hashLength;

	private long[] oidFanout;

	private byte[] oidLookup;

	private byte[] commitData;

	private byte[] extraList;

	static CommitGraphBuilder builder() {
		return new CommitGraphBuilder(OBJECT_ID_LENGTH);
	}

	private CommitGraphBuilder(int hashLength) {
		this.hashLength = hashLength;
	}

	CommitGraphBuilder addOidFanout(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotRepeated(oidFanout
		oidFanout = new long[GraphObjectIdIndex.FANOUT];
		for (int k = 0; k < oidFanout.length; k++) {
			oidFanout[k] = NB.decodeUInt32(buffer
		}
		return this;
	}

	CommitGraphBuilder addOidLookUp(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotRepeated(oidLookup
		oidLookup = buffer;
		return this;
	}

	CommitGraphBuilder addCommitData(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotRepeated(commitData
		commitData = buffer;
		return this;
	}

	CommitGraphBuilder addExtraList(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotRepeated(extraList
		extraList = buffer;
		return this;
	}

	CommitGraph build() throws CommitGraphFormatException {
		assertChunkNotNull(oidFanout
		assertChunkNotNull(oidLookup
		assertChunkNotNull(commitData

		GraphObjectIdIndex lookupIndex = new GraphObjectIdIndex(hashLength
				oidFanout
		GraphCommitData commitDataChunk = new GraphCommitData(hashLength
				commitData
		return new CommitGraphV1(lookupIndex
	}

	private void assertChunkNotNull(Object object
			throws CommitGraphFormatException {
		if (object == null) {
			throw new CommitGraphFormatException(
					MessageFormat.format(JGitText.get().commitGraphChunkNeeded
							Integer.toHexString(chunkId)));
		}
	}

	private void assertChunkNotRepeated(Object object
			throws CommitGraphFormatException {
		if (object != null) {
			throw new CommitGraphFormatException(MessageFormat.format(
					JGitText.get().commitGraphChunkRepeated
					Integer.toHexString(chunkId)));
		}
	}
}
