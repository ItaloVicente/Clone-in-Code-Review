
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_COMPUTE_GENERATION;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

public class CommitGraphConfig {

	public static final boolean DEFAULT_COMPUTE_GENERATION = true;

	private boolean computeGeneration = DEFAULT_COMPUTE_GENERATION;

	public CommitGraphConfig() {
	}

	public CommitGraphConfig(Repository db) {
		fromConfig(db.getConfig());
	}

	public CommitGraphConfig(Config cfg) {
		fromConfig(cfg);
	}

	public boolean isComputeGeneration() {
		return computeGeneration;
	}

	public void setComputeGeneration(boolean computeGeneration) {
		this.computeGeneration = computeGeneration;
	}

	public void fromConfig(Config rc) {
		computeGeneration = rc.getBoolean(CONFIG_COMMIT_GRAPH_SECTION
				CONFIG_KEY_COMPUTE_GENERATION
	}
}
