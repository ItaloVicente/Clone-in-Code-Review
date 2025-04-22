
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
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.CancellableDigestOutputStream;

public class CommitGraphWriter {

	private static final int COMMIT_GRAPH_VERSION_GENERATED = 1;

	private static final int OID_HASH_VERSION = 1;

	private static final int GENERATION_NUMBER_MAX = 0x3FFFFFFF;

	private static final int MAX_NUM_CHUNKS = 5;

	private static final int GRAPH_FANOUT_SIZE = 4 * 256;

	private final int hashsz;

	private final RevWalk walk;

	private List<GraphCommitData> commitDataList = new BlockList<>();

	private ObjectIdOwnerMap<GraphCommitData> commitDataMap = new ObjectIdOwnerMap<>();

	private int numExtraEdges;

	private boolean computeGeneration;

	public CommitGraphWriter(Repository repo) {
		this(repo
	}

	public CommitGraphWriter(Repository repo
		this(new CommitGraphConfig(repo)
	}

	public CommitGraphWriter(CommitGraphConfig cfg
		this.walk = new RevWalk(reader);
		this.computeGeneration = cfg.isComputeGeneration();
		this.hashsz = OBJECT_ID_LENGTH;
	}

	public void prepareCommitGraph(ProgressMonitor findingMonitor
			ProgressMonitor computeGenerationMonitor
			@NonNull Set<? extends ObjectId> wants) throws IOException {
		BlockList<RevCommit> commits = findCommits(findingMonitor
		if (computeGeneration) {
			computeGenerationNumbers(computeGenerationMonitor
		}
	}

	public void writeCommitGraph(ProgressMonitor writeMonitor
			OutputStream commitGraphStream) throws IOException {
		if (writeMonitor == null) {
			writeMonitor = NullProgressMonitor.INSTANCE;
		}

		Chunk[] chunks = createChunks();

		long writeCount = 256 + 2 * commitDataList.size() + numExtraEdges;
		beginPhase(
				MessageFormat.format(JGitText.get().writingOutCommitGraph
						Integer.valueOf(chunks.length))
				writeMonitor

		try (CancellableDigestOutputStream out = new CancellableDigestOutputStream(
				writeMonitor
			writeHeader(out
			writeChunkLookup(out
			writeChunks(out
			writeCheckSum(out);
		} catch (InterruptedIOException e) {
			throw new IOException(JGitText.get().commitGraphWritingCancelled);
		} finally {
			endPhase(writeMonitor);
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
		chunks[1].size = hashsz * commitDataList.size();

		chunks[2].id = CHUNK_ID_COMMIT_DATA;
		chunks[2].size = (hashsz + 16) * commitDataList.size();

		if (numExtraEdges > 0) {
			chunks[numChunks].id = CHUNK_ID_EXTRA_EDGE_LIST;
			chunks[numChunks].size = numExtraEdges * 4;
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
			throws IOException {
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
		return commitDataList.size();
	}

	public boolean isComputeGeneration() {
		return computeGeneration;
	}

	public void setComputeGeneration(boolean computeGeneration) {
		this.computeGeneration = computeGeneration;
	}

	public boolean willWriteExtraEdgeList() {
		return numExtraEdges > 0;
	}

	private void writeFanoutTable(CancellableDigestOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		int[] fanout = new int[256];
		for (GraphCommitData c : commitDataList) {
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

		for (int i = 0; i < commitDataList.size(); i++) {
			GraphCommitData commitData = commitDataList.get(i);
			commitData.setOidPosition(i);
			commitData.copyRawTo(tmp
			out.write(tmp
			out.getWriteMonitor().update(1);
		}
	}

	private void writeCommitData(CancellableDigestOutputStream out)
			throws IOException {
		int num = 0;
		byte[] tmp = new byte[hashsz + COMMIT_DATA_EXTRA_LENGTH];
		for (GraphCommitData c : commitDataList) {
			int edgeValue;
			int[] packedDate = new int[2];

			RevCommit commit = walk.parseCommit(c);
			ObjectId treeId = commit.getTree();
			treeId.copyRawTo(tmp

			RevCommit[] parents = commit.getParents();
			if (parents.length == 0) {
				edgeValue = GRAPH_NO_PARENT;
			} else {
				RevCommit parent = parents[0];
				edgeValue = getCommitOidPosition(parent);
			}
			NB.encodeInt32(tmp
			if (parents.length == 1) {
				edgeValue = GRAPH_NO_PARENT;
			} else if (parents.length == 2) {
				RevCommit parent = parents[1];
				edgeValue = getCommitOidPosition(parent);
			} else if (parents.length > 2) {
				edgeValue = GRAPH_EXTRA_EDGES_NEEDED | num;
				num += parents.length - 1;
			}

			NB.encodeInt32(tmp

			packedDate[0] |= c.getGeneration() << 2;
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
		for (GraphCommitData c : commitDataList) {
			RevCommit commit = walk.parseCommit(c);
			RevCommit[] parents = commit.getParents();
			if (parents.length > 2) {
				int edgeValue;
				for (int n = 1; n < parents.length; n++) {
					RevCommit parent = parents[n];
					edgeValue = getCommitOidPosition(parent);
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

	private BlockList<RevCommit> findCommits(ProgressMonitor findingMonitor
			Set<? extends ObjectId> wants) throws IOException {
		if (findingMonitor == null) {
			findingMonitor = NullProgressMonitor.INSTANCE;
		}

		for (ObjectId id : wants) {
			RevObject o = walk.parseAny(id);
			if (o instanceof RevCommit) {
				walk.markStart((RevCommit) o);
			}
		}

		walk.sort(RevSort.COMMIT_TIME_DESC);
		BlockList<RevCommit> commits = new BlockList<>();

		RevCommit c;
		beginPhase(JGitText.get().findingCommitsForCommitGraph
				ProgressMonitor.UNKNOWN);
		while ((c = walk.next()) != null) {
			findingMonitor.update(1);
			commits.add(c);
			addCommitData(c);
			if (c.getParentCount() > 2) {
				numExtraEdges += c.getParentCount() - 1;
			}
		}
		endPhase(findingMonitor);

		return commits;
	}

	private void computeGenerationNumbers(
			ProgressMonitor computeGenerationMonitor
			throws MissingObjectException {
		if (computeGenerationMonitor == null) {
			computeGenerationMonitor = NullProgressMonitor.INSTANCE;
		}

		beginPhase(JGitText.get().computingCommitGeneration
				computeGenerationMonitor
		for (RevCommit cmit : commits) {
			computeGenerationMonitor.update(1);
			int generation = getCommitGeneration(cmit);
			if (generation != CommitGraph.GENERATION_NOT_COMPUTED
					&& generation != CommitGraph.GENERATION_UNKNOWN) {
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
					generation = getCommitGeneration(parent);
					if (generation == CommitGraph.GENERATION_NOT_COMPUTED
							|| generation == CommitGraph.GENERATION_UNKNOWN) {
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
					setCommitGeneration(commit
				}
			}
		}
		endPhase(computeGenerationMonitor);
	}

	private int getVersion() {
		return COMMIT_GRAPH_VERSION_GENERATED;
	}

	private static class Chunk {
		int id;

		long size;
	}

	private int getCommitGeneration(RevCommit commit)
			throws MissingObjectException {
		GraphCommitData c = commitDataMap.get(commit);
		if (c == null) {
			throw new MissingObjectException(commit
		}
		return c.getGeneration();
	}

	private void setCommitGeneration(RevCommit commit
			throws MissingObjectException {
		GraphCommitData c = commitDataMap.get(commit);
		if (c == null) {
			throw new MissingObjectException(commit
		}
		c.setGeneration(generation);
	}

	private int getCommitOidPosition(RevCommit commit)
			throws MissingObjectException {
		GraphCommitData c = commitDataMap.get(commit);
		if (c == null) {
			throw new MissingObjectException(commit
		}
		return c.getOidPosition();
	}

	private void addCommitData(RevCommit commit) {
		GraphCommitData c = new GraphCommitData(commit);
		commitDataList.add(c);
		commitDataMap.add(c);
	}

	private void beginPhase(String task
		monitor.beginTask(task
	}

	private void endPhase(ProgressMonitor monitor) {
		monitor.endTask();
	}
}
