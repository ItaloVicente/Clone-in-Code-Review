
package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Repository;

public class StrategyRecursive extends StrategyResolve {

	@Override
	public ThreeWayMerger newMerger(Repository db) {
		return new RecursiveMerger(db
	}

	@Override
	public ThreeWayMerger newMerger(Repository db
		return new RecursiveMerger(db
	}

	@Override
	public String getName() {
		return "recursive";
	}
}
