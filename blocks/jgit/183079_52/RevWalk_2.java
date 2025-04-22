
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.commitgraph.CommitGraph;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class RevCommitCG extends RevCommit {

	private int graphPosition;

	private int generation = Constants.COMMIT_GENERATION_UNKNOWN;

	protected RevCommitCG(AnyObjectId id
		super(id);
		this.graphPosition = graphPosition;
	}

	@Override
	void parseHeaders(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		CommitGraph graph = walk.commitGraph();
		CommitGraph.CommitData data = graph.getCommitData(graphPosition);
		if (data == null) {
			throw new IllegalStateException();
		}
		if (!walk.shallowCommitsInitialized) {
			walk.initializeShallowCommits(this);
		}

		this.tree = walk.lookupTree(data.getTree());
		this.commitTime = (int) data.getCommitTime();
		this.generation = data.getGeneration();

		if (getParents() == null) {
			int[] pGraphList = data.getParents();
			RevCommit[] pList = new RevCommit[pGraphList.length];
			for (int i = 0; i < pList.length; i++) {
				int graphPos = pGraphList[i];
				ObjectId objId = graph.getObjectId(graphPos);
				pList[i] = walk.lookupCommit(objId
			}
			this.parents = pList;
		}

		flags |= PARSED;
		if (walk.isRetainBody()) {
			super.parseBody(walk);
		}
	}

	@Override
	int getGeneration() {
		return generation;
	}
}
