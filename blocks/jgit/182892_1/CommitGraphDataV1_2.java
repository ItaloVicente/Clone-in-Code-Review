
package org.eclipse.jgit.internal.storage.commitgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public abstract class CommitGraphData {

	public static CommitGraphData open(File graphFile)
			throws FileNotFoundException
			IOException {
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

	public static CommitGraphData read(InputStream fd) throws IOException {
		byte[] hdr = new byte[8];
		IO.readFully(fd

		int v = hdr[4];
		if (v != 1) {
			throw new CommitGraphFormatException(MessageFormat
					.format(JGitText.get().unsupportedCommitGraphVersion
		}

		return new CommitGraphDataV1(fd
	}

	public abstract int findGraphPosition(AnyObjectId objId);

	public abstract ObjectId getObjectId(int graphPos);

	public abstract CommitGraph.CommitData getCommitData(int graphPos);

	public abstract long getCommitCnt();

	public abstract int getHashLength();

	static class CommitDataImpl extends CommitGraph.CommitData {

		ObjectId tree;

		int[] parents;

		long commitTime;

		int generation;

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
