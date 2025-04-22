
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_DATA_EXTRA_LENGTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_GRAPH_MAGIC;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_CHUNK_LOOKUP_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_EXTRA_EDGES_NEEDED;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_LAST_EDGE;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.GRAPH_NO_PARENT;
import static org.eclipse.jgit.lib.Constants.COMMIT_GENERATION_NOT_COMPUTED;
import static org.eclipse.jgit.lib.Constants.COMMIT_GENERATION_UNKNOWN;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.CancellableDigestOutputStream;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.NB;

public class CommitGraphWriter {

	private static final int COMMIT_GRAPH_VERSION_GENERATED = 1;

	private static final int OID_HASH_VERSION = 1;

	private static final int GENERATION_NUMBER_MAX = 0x3FFFFFFF;

	private static final int MAX_NUM_CHUNKS = 5;

	private static final int GRAPH_FANOUT_SIZE = 4 * 256;

	private final int hashsz;

	private final ObjectReader reader;

	private InMemoryOidLookup oidLookup;

	private int[] generations;

	private ProgressMonitor pm;

	public CommitGraphWriter(Repository repo) {
		this.reader = repo.newObjectReader();
		this.hashsz = OBJECT_ID_LENGTH;
	}

	public void write(ProgressMonitor monitor
			@NonNull Set<? extends ObjectId> wants
			OutputStream commitGraphStream) throws IOException {
		if (monitor == null) {
			this.pm = NullProgressMonitor.INSTANCE;
		} else {
			this.pm = monitor;
		}
		prepareCommitGraph(wants);
		writeTo(commitGraphStream);
	}

	private void prepareCommitGraph(@NonNull Set<? extends ObjectId> wants)
			throws IOException {
		try (RevWalk walk = new RevWalk(reader)) {
			oidLookup = InMemoryOidLookup.load(pm
		}
		generations = new int[oidLookup.size()];
		computeGenerationNumbers();
	}

	private void writeTo(OutputStream commitGraphStream) throws IOException {
		if (getCommitCnt() == 0) {
			return;
		}

		Chunk[] chunks = createChunks();

		long writeCount = 256 + 2 * oidLookup.size()
				+ oidLookup.getExtraEdgeCnt();
		pm.beginTask(MessageFormat.format(JGitText.get().writingOutCommitGraph
				Integer.valueOf(chunks.length))

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

	private Chunk[] createChunks() {
		Chunk[] chunks = new Chunk[MAX_NUM_CHUNKS];
		for (int i = 0; i < chunks.length; i++) {
			chunks[i] = new Chunk();
		}
		int numChunks = 3;

		chunks[0].id = CHUNK_ID_OID_FANOUT;
		chunks[0].size = GRAPH_FANOUT_SIZE;

		chunks[1].id = CHUNK_ID_OID_LOOKUP;
		chunks[1].size = hashsz * oidLookup.size();

		chunks[2].id = CHUNK_ID_COMMIT_DATA;
		chunks[2].size = (hashsz + 16) * oidLookup.size();

		if (oidLookup.getExtraEdgeCnt() > 0) {
			chunks[numChunks].id = CHUNK_ID_EXTRA_EDGE_LIST;
			chunks[numChunks].size = oidLookup.getExtraEdgeCnt() * 4;
			numChunks++;
		}
		chunks[numChunks].id = 0;
		chunks[numChunks].size = 0L;
		return Arrays.copyOfRange(chunks
	}

	private void writeHeader(CancellableDigestOutputStream out
			throws IOException {
		byte[] headerBuffer = new byte[8];
		NB.encodeInt32(headerBuffer
		byte[] buff = { (byte) getVersion()
				(byte) numChunks
		System.arraycopy(buff
		out.write(headerBuffer
		out.flush();
	}

	private void writeChunkLookup(CancellableDigestOutputStream out
			Chunk[] chunks) throws IOException {
		int numChunks = chunks.length;
		long chunkOffset = 8 + (numChunks + 1) * GRAPH_CHUNK_LOOKUP_WIDTH;
		byte[] buffer = new byte[GRAPH_CHUNK_LOOKUP_WIDTH];
		for (int i = 0; i < numChunks; i++) {
			Chunk chunk = chunks[i];

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
			throws IOException {
		for (int i = 0; i < chunks.length; i++) {
			int chunkId = chunks[i].id;

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
		return oidLookup == null ? 0 : oidLookup.size();
	}

	private void writeFanoutTable(CancellableDigestOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		int[] fanout = new int[256];
		for (RevCommit c : oidLookup) {
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

		for (RevCommit c : oidLookup) {
			c.copyRawTo(tmp
			out.write(tmp
			out.getWriteMonitor().update(1);
		}
	}

	private void writeCommitData(CancellableDigestOutputStream out)
			throws IOException {
		int num = 0;
		byte[] tmp = new byte[hashsz + COMMIT_DATA_EXTRA_LENGTH];
		for (int i = 0; i < oidLookup.size(); i++) {
			RevCommit commit = oidLookup.getCommit(i);
			int edgeValue;
			int[] packedDate = new int[2];

			ObjectId treeId = commit.getTree();
			treeId.copyRawTo(tmp

			RevCommit[] parents = commit.getParents();
			if (parents.length == 0) {
				edgeValue = GRAPH_NO_PARENT;
			} else {
				RevCommit parent = parents[0];
				edgeValue = oidLookup.getOidPosition(parent);
			}
			NB.encodeInt32(tmp
			if (parents.length == 1) {
				edgeValue = GRAPH_NO_PARENT;
			} else if (parents.length == 2) {
				RevCommit parent = parents[1];
				edgeValue = oidLookup.getOidPosition(parent);
			} else if (parents.length > 2) {
				edgeValue = GRAPH_EXTRA_EDGES_NEEDED | num;
				num += parents.length - 1;
			}

			NB.encodeInt32(tmp

			packedDate[0] |= generations[i] << 2;
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
		for (RevCommit commit : oidLookup) {
			RevCommit[] parents = commit.getParents();
			if (parents.length > 2) {
				int edgeValue;
				for (int n = 1; n < parents.length; n++) {
					RevCommit parent = parents[n];
					edgeValue = oidLookup.getOidPosition(parent);
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

	private void computeGenerationNumbers() throws MissingObjectException {
		pm.beginTask(JGitText.get().computingCommitGeneration
				oidLookup.size());
		for (RevCommit cmit : oidLookup) {
			pm.update(1);
			int generation = generations[oidLookup.getOidPosition(cmit)];
			if (generation != COMMIT_GENERATION_NOT_COMPUTED
					&& generation != COMMIT_GENERATION_UNKNOWN) {
				continue;
			}

			Stack<RevCommit> commitStack = new Stack<>();
			commitStack.push(cmit);

			while (!commitStack.empty()) {
				int maxGeneration = 0;
				boolean allParentComputed = true;
				RevCommit current = commitStack.peek();
				RevCommit parent;

				for (int i = 0; i < current.getParentCount(); i++) {
					parent = current.getParent(i);
					generation = generations[oidLookup.getOidPosition(parent)];
					if (generation == COMMIT_GENERATION_NOT_COMPUTED
							|| generation == COMMIT_GENERATION_UNKNOWN) {
						allParentComputed = false;
						commitStack.push(parent);
						break;
					} else if (generation > maxGeneration) {
						maxGeneration = generation;
					}
				}

				if (allParentComputed) {
					RevCommit commit = commitStack.pop();
					generation = maxGeneration + 1;
					if (generation > GENERATION_NUMBER_MAX) {
						generation = GENERATION_NUMBER_MAX;
					}
					generations[oidLookup.getOidPosition(commit)] = generation;
				}
			}
		}
		pm.endTask();
	}

	private int getVersion() {
		return COMMIT_GRAPH_VERSION_GENERATED;
	}

	private static class Chunk {
		int id;

		long size;
	}
}
