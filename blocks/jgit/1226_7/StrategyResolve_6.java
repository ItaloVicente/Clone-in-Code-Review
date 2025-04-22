package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Repository;

public class StrategyResolve extends ThreeWayMergeStrategy {
	@Override
	public ThreeWayMerger newMerger(Repository db) {
		return new ResolveMerger(db);
	}

	@Override
	public String getName() {
		return "resolve";
	}
}
