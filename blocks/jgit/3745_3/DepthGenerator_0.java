
package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.lib.AnyObjectId;

public class DepthCommit extends RevCommit {
	int depth;

	protected DepthCommit(AnyObjectId id) {
		super(id);
		depth = -1;
	}
}
