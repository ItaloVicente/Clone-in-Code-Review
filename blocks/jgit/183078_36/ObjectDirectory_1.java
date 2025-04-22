
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

	private final static GraphInfo NO_COMMIT_GRAPH = new GraphInfo(null);

	private final File baseFile;

	private final AtomicReference<GraphInfo> graph;

	FileCommitGraph(File objectsDir) {
		this.baseFile = new File(objectsDir
		this.graph = new AtomicReference<>(NO_COMMIT_GRAPH);
	}

	CommitGraph get() {
		GraphInfo now = scanCommitGraph(graph.get());
		if (now == NO_COMMIT_GRAPH) {
			return null;
		}

		if (now.baseGraph != null) {
			return now.baseGraph.graph;
		}

		return null;
	}

	private GraphInfo scanCommitGraph(GraphInfo original) {
		synchronized (graph) {
			GraphInfo o
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

	private GraphInfo scanGraphImpl(GraphInfo old) {
		File nowFile = new File(baseFile.getPath());

		if (nowFile.exists() && nowFile.isFile()) {
			GraphFile oldBaseGraph = old.baseGraph;
			if (oldBaseGraph != null
					&& !oldBaseGraph.snapshot.isModified(nowFile)) {
				return old;
			}
			try {
				GraphFile baseGraph = new GraphFile(nowFile);
				return new GraphInfo(baseGraph);
			} catch (IOException e) {
				handleGraphError(e
			}
		}

		return NO_COMMIT_GRAPH;
	}

	private void handleGraphError(IOException e
		String errTmpl = JGitText.get().exceptionWhileLoadingCommitGraph;
		String warnTmpl = null;
		if (e instanceof FileNotFoundException) {
			return;
		}
		if (e instanceof CommitGraphFormatException) {
			warnTmpl = JGitText.get().corruptCommitGraph;
		}
		if (warnTmpl != null) {
			LOG.warn(MessageFormat.format(warnTmpl
		} else {
			LOG.error(MessageFormat.format(errTmpl
		}
	}

	private static class GraphInfo {
		final GraphFile baseGraph;

		GraphInfo(GraphFile baseGraph) {
			this.baseGraph = baseGraph;
		}
	}

	private static class GraphFile {
		final FileSnapshot snapshot;

		final CommitGraph graph;

		GraphFile(File file) throws IOException {
			this.snapshot = FileSnapshot.save(file);
			this.graph = CommitGraphLoader.open(file);
		}
	}
}
