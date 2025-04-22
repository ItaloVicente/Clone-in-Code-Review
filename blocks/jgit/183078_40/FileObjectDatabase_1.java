
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

	private final AtomicReference<GraphSnapshot> baseGraph;

	FileCommitGraph(File objectsDir) {
		this.baseGraph = new AtomicReference<>(new GraphSnapshot(
				new File(objectsDir
	}

	CommitGraph get() {
		GraphSnapshot original = baseGraph.get();
		synchronized (baseGraph) {
			GraphSnapshot o
			do {
				o = baseGraph.get();
				if (o != original) {
					return o.getCommitGraph();
				}
				n = o.refresh();
				if (n == o) {
					return n.getCommitGraph();
				}
			} while (!baseGraph.compareAndSet(o
			return n.getCommitGraph();
		}
	}

	private static class GraphSnapshot {
		private final File file;

		private final FileSnapshot snapshot;

		private final CommitGraph graph;

		GraphSnapshot(File file) {
			this(file
		}

		GraphSnapshot(File file
			this.file = file;
			this.snapshot = snapshot;
			this.graph = graph;
		}

		CommitGraph getCommitGraph() {
			return graph;
		}

		GraphSnapshot refresh() {
			if (snapshot == null && !file.exists()) {
				return this;
			}
			if (snapshot != null && !snapshot.isModified(file)) {
				return this;
			}
			return new GraphSnapshot(file
		}

		private static CommitGraph open(File file) {
			try {
				return CommitGraphLoader.open(file);
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
				return null;
			}
		}
	}
}
