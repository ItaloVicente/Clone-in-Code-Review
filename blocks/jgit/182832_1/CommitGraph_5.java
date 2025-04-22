
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

class ObjectToCommitData extends ObjectIdOwnerMap.Entry {

	private int generation = CommitGraph.GENERATION_NUMBER_ZERO;

	private int oidPosition = -1;

	ObjectToCommitData(AnyObjectId id) {
		super(id);
	}

	int getGeneration() {
		return generation;
	}

	void setGeneration(int generation) {
		this.generation = generation;
	}

	int getOidPosition() {
		return oidPosition;
	}

	void setOidPosition(int oidPosition) {
		this.oidPosition = oidPosition;
	}
}
