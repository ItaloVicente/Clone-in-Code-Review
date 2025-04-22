package org.eclipse.egit.core.internal.merge;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.StrategyResolve;
import org.eclipse.jgit.merge.ThreeWayMerger;

public class StrategyRecursiveModel extends StrategyResolve {
	@Override
	public ThreeWayMerger newMerger(Repository db) {
		return new RecursiveModelMerger(db, false);
	}

	@Override
	public ThreeWayMerger newMerger(Repository db, boolean inCore) {
		return new RecursiveModelMerger(db, inCore);
	}

	@Override
	public String getName() {
		return "model recursive"; //$NON-NLS-1$
	}
}
