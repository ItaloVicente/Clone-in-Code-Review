package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Repository;

public class StrategyResolveInCore extends ThreeWayMergeStrategy {
	@Override
	public ThreeWayMerger newMerger(Repository db) {
		return new ResolveMergerInCore(db);
	}

	@Override
	public String getName() {
		return "resolve-in-core";
	}
}
