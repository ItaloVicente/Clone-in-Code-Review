
package org.eclipse.jgit.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.subtree.SubtreeContext;

public class SubtreeSplitResult {

	private List<SubtreeContext> subtreeContexts;

	private Map<ObjectId

	public SubtreeSplitResult(List<SubtreeContext> subtreeContexts
			Map<ObjectId
		this.subtreeContexts = subtreeContexts;
		if (rewrittenCommits != null) {
			this.rewrittenCommits = new HashMap<ObjectId
			for (ObjectId key : rewrittenCommits.keySet()) {
				this.rewrittenCommits.put(key
			}
		}
	}

	public Map<ObjectId
		return rewrittenCommits;
	}

	public List<SubtreeContext> getSubtreeContexts() {
		return subtreeContexts;
	}

}
