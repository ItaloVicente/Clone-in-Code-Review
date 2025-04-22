
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_COMMIT_DATA;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_FANOUT;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_ID_OID_LOOKUP;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.CHUNK_LOOKUP_WIDTH;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_GRAPH_MAGIC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public class CommitGraphLoader {

	public static CommitGraph open(File graphFile) throws FileNotFoundException
			CommitGraphFormatException
		try (SilentFileInputStream fd = new SilentFileInputStream(graphFile)) {
			try {
				return read(fd);
			} catch (CommitGraphFormatException fe) {
				throw fe;
			} catch (IOException ioe) {
				throw new IOException(MessageFormat.format(
						JGitText.get().unreadableCommitGraph
						graphFile.getAbsolutePath())
			}
		}
	}

	public static CommitGraph read(InputStream fd) throws IOException {
		byte[] hdr = new byte[8];
		IO.readFully(fd

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

		int v = hdr[4];
		if (v != 1) {
			throw new CommitGraphFormatException(MessageFormat.format(
					JGitText.get().unsupportedCommitGraphVersion
					Integer.valueOf(v)));
		}

		int numberOfChunks = hdr[6];


		byte[] lookupBuffer = new byte[CHUNK_LOOKUP_WIDTH
		IO.readFully(fd
		List<ChunkSegment> chunks = new ArrayList<>();
		for (int i = 0; i <= numberOfChunks; i++) {
			int id = NB.decodeInt32(lookupBuffer
			long offset = NB.decodeInt64(lookupBuffer
			chunks.add(new ChunkSegment(id
		}

		CommitGraphBuilder builder = CommitGraphBuilder.builder();
		for (int i = 0; i < numberOfChunks; i++) {
			long chunkOffset = chunks.get(i).offset;
			int chunkId = chunks.get(i).id;
			long len = chunks.get(i + 1).offset - chunkOffset;

				throw new CommitGraphFormatException(
						JGitText.get().commitGraphFileIsTooLargeForJgit);
			}

			byte buffer[] = new byte[(int) len];
			IO.readFully(fd

			switch (chunkId) {
			case CHUNK_ID_OID_FANOUT:
				builder.addOidFanout(buffer);
				break;
			case CHUNK_ID_OID_LOOKUP:
				builder.addOidLookUp(buffer);
				break;
			case CHUNK_ID_COMMIT_DATA:
				builder.addCommitData(buffer);
				break;
			case CHUNK_ID_EXTRA_EDGE_LIST:
				builder.addExtraList(buffer);
				break;
			default:
				throw new CommitGraphFormatException(MessageFormat.format(
						JGitText.get().commitGraphChunkUnknown
						Integer.toHexString(chunkId)));
			}
		}
		return builder.build();
	}

	private static class ChunkSegment {
		final int id;

		final long offset;

		private ChunkSegment(int id
			this.id = id;
			this.offset = offset;
		}
	}
}
