
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;

class CommitGraphBuilder {

	private final int hashLength;

	private byte[] oidFanout;

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
		assertChunkNotSeenYet(oidFanout
		oidFanout = buffer;
		return this;
	}

	CommitGraphBuilder addOidLookUp(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotSeenYet(oidLookup
		oidLookup = buffer;
		return this;
	}

	CommitGraphBuilder addCommitData(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotSeenYet(commitData
		commitData = buffer;
		return this;
	}

	CommitGraphBuilder addExtraList(byte[] buffer)
			throws CommitGraphFormatException {
		assertChunkNotSeenYet(extraList
		extraList = buffer;
		return this;
	}

	CommitGraph build() throws CommitGraphFormatException {
		assertChunkNotNull(oidFanout
		assertChunkNotNull(oidLookup
		assertChunkNotNull(commitData

		GraphObjectIndex index = new GraphObjectIndex(hashLength
				oidLookup);
		GraphCommitData commitDataChunk = new GraphCommitData(hashLength
				commitData
		return new CommitGraphV1(index
	}

	private void assertChunkNotNull(Object object
			throws CommitGraphFormatException {
		if (object == null) {
			throw new CommitGraphFormatException(
					MessageFormat.format(JGitText.get().commitGraphChunkNeeded
							Integer.toHexString(chunkId)));
		}
	}

	private void assertChunkNotSeenYet(Object object
			throws CommitGraphFormatException {
		if (object != null) {
			throw new CommitGraphFormatException(MessageFormat.format(
					JGitText.get().commitGraphChunkRepeated
					Integer.toHexString(chunkId)));
		}
	}
}
