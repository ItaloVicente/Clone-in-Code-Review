
package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.treewalk.TreeWalk;

class AlwaysCloneTreeFilter extends TreeFilter {
	@Override
	public TreeFilter clone() {
		return new AlwaysCloneTreeFilter();
	}

	@Override
	public boolean include(TreeWalk walker) {
		return false;
	}

	@Override
	public boolean shouldBeRecursive() {
		return false;
	}
}
