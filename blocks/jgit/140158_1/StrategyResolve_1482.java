
package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectInserter;
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
	public ThreeWayMerger newMerger(ObjectInserter inserter
		return new RecursiveMerger(inserter
	}

	@Override
	public String getName() {
	}
}
