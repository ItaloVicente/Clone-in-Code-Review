
package org.eclipse.jgit.internal.storage.commitgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.CommitGraphFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public class CommitGraphLoader {

	public static CommitGraphSingleFile open(File graphFile)
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

	public static CommitGraphSingleFile read(InputStream fd)
			throws IOException {
		byte[] hdr = new byte[8];
		IO.readFully(fd

		int v = hdr[4];
		if (v != 1) {
			throw new CommitGraphFormatException(MessageFormat.format(
					JGitText.get().unsupportedCommitGraphVersion
					Integer.valueOf(v)));
		}

		CommitGraphFileContent content = new CommitGraphParserV1(fd
		return new CommitGraphSingleFile(content);
	}
}
