package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;

public class StrategyResolve extends ThreeWayMergeStrategy {

	@Override
	public ThreeWayMerger newMerger(Repository db) {
		return new ResolveMerger(db
	}

	@Override
	public ThreeWayMerger newMerger(Repository db
		return new ResolveMerger(db
	}

	@Override
	public ThreeWayMerger newMerger(ObjectInserter inserter
		return new ResolveMerger(inserter
	}

	@Override
	public String getName() {
	}
}
