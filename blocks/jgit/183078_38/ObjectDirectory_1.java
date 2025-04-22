
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraphFormatException;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraphLoader;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraph;
import org.eclipse.jgit.lib.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCommitGraph {
	private final static Logger LOG = LoggerFactory
			.getLogger(FileCommitGraph.class);

	private final File baseFile;

	private final AtomicReference<GraphSnapshot> graph;

	FileCommitGraph(File objectsDir) {
		this.baseFile = new File(objectsDir
		this.graph = new AtomicReference<>();
	}

	CommitGraph get() {
		GraphSnapshot now = scanCommitGraph(graph.get());
		if (now == null) {
			return null;
		}
		return now.graph;
	}

	private GraphSnapshot scanCommitGraph(GraphSnapshot original) {
		synchronized (graph) {
			GraphSnapshot o
			do {
				o = graph.get();
				if (o != original) {
					return o;
				}
				n = scanGraphImpl(o);
				if (n == o) {
					return n;
				}
			} while (!graph.compareAndSet(o
			return n;
		}
	}

	private GraphSnapshot scanGraphImpl(GraphSnapshot old) {
		if (baseFile.exists() && baseFile.isFile()) {
			if (old != null && !old.isModified(baseFile)) {
				return old;
			}
			return GraphSnapshot.open(baseFile);
		}
		return null;
	}

	private static class GraphSnapshot {
		final FileSnapshot snapshot;

		final CommitGraph graph;

		static GraphSnapshot open(File file) {
			FileSnapshot fileSnapshot = FileSnapshot.save(file);
			try {
				CommitGraph commitGraph = CommitGraphLoader.open(file);
				return new GraphSnapshot(fileSnapshot
			} catch (FileNotFoundException noFile) {
				return null;
			} catch (IOException e) {
				if (e instanceof CommitGraphFormatException) {
					LOG.warn(
							MessageFormat.format(
									JGitText.get().corruptCommitGraph
							e);
				} else {
					LOG.error(MessageFormat.format(
							JGitText.get().exceptionWhileLoadingCommitGraph
							file)
				}
				return new GraphSnapshot(fileSnapshot
			}
		}

		private GraphSnapshot(FileSnapshot snapshot
			this.snapshot = snapshot;
			this.graph = graph;
		}

		boolean isModified(File now) {
			return snapshot.isModified(now);
		}
	}
}
