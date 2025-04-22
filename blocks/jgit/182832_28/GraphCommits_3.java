
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_LOOKUP_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_DATA_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_GRAPH_MAGIC;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EXTRA_EDGES_NEEDED;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_LAST_EDGE;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_NO_PARENT;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.CancellableDigestOutputStream;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.NB;

public class CommitGraphWriter {

	private static final int COMMIT_GRAPH_VERSION_GENERATED = 1;

	private static final int OID_HASH_VERSION = 1;

	private static final int GRAPH_FANOUT_SIZE = 4 * 256;

	private final int hashsz;

	private GraphCommits graphCommits;

	private ProgressMonitor pm;

	public CommitGraphWriter(@NonNull GraphCommits graphCommits) {
		this.graphCommits = graphCommits;
		this.hashsz = OBJECT_ID_LENGTH;
	}

	public void write(ProgressMonitor monitor
			@NonNull OutputStream commitGraphStream) throws IOException {
		if (monitor == null) {
			this.pm = NullProgressMonitor.INSTANCE;
		} else {
			this.pm = monitor;
		}
		writeTo(commitGraphStream);
	}

	private void writeTo(OutputStream commitGraphStream) throws IOException {
		if (getCommitCnt() == 0) {
			return;
		}

		List<ChunkHeader> chunks = createChunks();
		long writeCount = 256 + 2 * graphCommits.size()
				+ graphCommits.getExtraEdgeCnt();
		pm.beginTask(MessageFormat.format(JGitText.get().writingOutCommitGraph
				Integer.valueOf(chunks.size()))

		try (CancellableDigestOutputStream out = new CancellableDigestOutputStream(
				pm
			writeHeader(out
			writeChunkLookup(out
			writeChunks(out
			writeCheckSum(out);
		} catch (InterruptedIOException e) {
			throw new IOException(JGitText.get().commitGraphWritingCancelled);
		} finally {
			pm.endTask();
		}
	}

	private List<ChunkHeader> createChunks() {
		List<ChunkHeader> chunks = new ArrayList<>();
		chunks.add(new ChunkHeader(CHUNK_ID_OID_FANOUT
		chunks.add(new ChunkHeader(CHUNK_ID_OID_LOOKUP
				hashsz * graphCommits.size()));
		chunks.add(new ChunkHeader(CHUNK_ID_COMMIT_DATA
				(hashsz + 16) * graphCommits.size()));
		if (graphCommits.getExtraEdgeCnt() > 0) {
			chunks.add(new ChunkHeader(CHUNK_ID_EXTRA_EDGE_LIST
					graphCommits.getExtraEdgeCnt() * 4));
		}
		return chunks;
	}

	private void writeHeader(CancellableDigestOutputStream out
			throws IOException {
		byte[] headerBuffer = new byte[8];
		NB.encodeInt32(headerBuffer
		byte[] buff = { (byte) COMMIT_GRAPH_VERSION_GENERATED
				(byte) OID_HASH_VERSION
		System.arraycopy(buff
		out.write(headerBuffer
		out.flush();
	}

	private void writeChunkLookup(CancellableDigestOutputStream out
			List<ChunkHeader> chunks) throws IOException {
		int numChunks = chunks.size();
		long chunkOffset = 8 + (numChunks + 1) * CHUNK_LOOKUP_WIDTH;
		byte[] buffer = new byte[CHUNK_LOOKUP_WIDTH];
		for (ChunkHeader chunk : chunks) {
			NB.encodeInt32(buffer
			NB.encodeInt64(buffer
			out.write(buffer);
			chunkOffset += chunk.size;
		}
		NB.encodeInt32(buffer
		NB.encodeInt64(buffer
		out.write(buffer);
	}

	private void writeChunks(CancellableDigestOutputStream out
			List<ChunkHeader> chunks) throws IOException {
		for (ChunkHeader chunk : chunks) {
			int chunkId = chunk.id;

			switch (chunkId) {
			case CHUNK_ID_OID_FANOUT:
				writeFanoutTable(out);
				break;
			case CHUNK_ID_OID_LOOKUP:
				writeOidLookUp(out);
				break;
			case CHUNK_ID_COMMIT_DATA:
				writeCommitData(out);
				break;
			case CHUNK_ID_EXTRA_EDGE_LIST:
				writeExtraEdges(out);
				break;
			}
		}
	}

	private void writeCheckSum(CancellableDigestOutputStream out)
			throws IOException {
		out.write(out.getDigest());
		out.flush();
	}

	public long getCommitCnt() {
		return graphCommits == null ? 0 : graphCommits.size();
	}

	private void writeFanoutTable(CancellableDigestOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		int[] fanout = new int[256];
		for (RevCommit c : graphCommits) {
			fanout[c.getFirstByte() & 0xff]++;
		}
		for (int i = 1; i < fanout.length; i++) {
			fanout[i] += fanout[i - 1];
		}
		for (int n : fanout) {
			NB.encodeInt32(tmp
			out.write(tmp
			out.getWriteMonitor().update(1);
		}
	}

	private void writeOidLookUp(CancellableDigestOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4 + hashsz];

		for (RevCommit c : graphCommits) {
			c.copyRawTo(tmp
			out.write(tmp
			out.getWriteMonitor().update(1);
		}
	}

	private void writeCommitData(CancellableDigestOutputStream out)
			throws IOException {
		int num = 0;
		byte[] tmp = new byte[hashsz + COMMIT_DATA_WIDTH];
		for (int i = 0; i < graphCommits.size(); i++) {
			RevCommit commit = graphCommits.getCommit(i);
			int edgeValue;
			int[] packedDate = new int[2];

			ObjectId treeId = commit.getTree();
			treeId.copyRawTo(tmp

			RevCommit[] parents = commit.getParents();
			if (parents.length == 0) {
				edgeValue = GRAPH_NO_PARENT;
			} else {
				RevCommit parent = parents[0];
				edgeValue = graphCommits.getOidPosition(parent);
			}
			NB.encodeInt32(tmp
			if (parents.length == 1) {
				edgeValue = GRAPH_NO_PARENT;
			} else if (parents.length == 2) {
				RevCommit parent = parents[1];
				edgeValue = graphCommits.getOidPosition(parent);
			} else if (parents.length > 2) {
				edgeValue = GRAPH_EXTRA_EDGES_NEEDED | num;
				num += parents.length - 1;
			}

			NB.encodeInt32(tmp

			packedDate[0] |= graphCommits.getGeneration(i) << 2;
			packedDate[1] = commit.getCommitTime();
			NB.encodeInt32(tmp
			NB.encodeInt32(tmp

			out.write(tmp);
			out.getWriteMonitor().update(1);
		}
	}

	private void writeExtraEdges(CancellableDigestOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		for (RevCommit commit : graphCommits) {
			RevCommit[] parents = commit.getParents();
			if (parents.length > 2) {
				int edgeValue;
				for (int n = 1; n < parents.length; n++) {
					RevCommit parent = parents[n];
					edgeValue = graphCommits.getOidPosition(parent);
					if (n == parents.length - 1) {
						edgeValue |= GRAPH_LAST_EDGE;
					}
					NB.encodeInt32(tmp
					out.write(tmp);
					out.getWriteMonitor().update(1);
				}
			}
		}
	}

	private static class ChunkHeader {
		final int id;

		final long size;

		public ChunkHeader(int id
			this.id = id;
			this.size = size;
		}
	}
}
