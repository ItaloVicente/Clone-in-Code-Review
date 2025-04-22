
package org.eclipse.jgit.merge;

import java.io.IOException;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;

public class StrategyOneSided extends MergeStrategy {
	private final String strategyName;

	private final int treeIndex;

	protected StrategyOneSided(String name
		strategyName = name;
		treeIndex = index;
	}

	@Override
	public String getName() {
		return strategyName;
	}

	@Override
	public Merger newMerger(Repository db) {
		return new OneSide(db
	}

	@Override
	public Merger newMerger(Repository db
		return new OneSide(db
	}

	@Override
	public Merger newMerger(ObjectInserter inserter
		return new OneSide(inserter
	}

	static class OneSide extends Merger {
		private final int treeIndex;

		protected OneSide(Repository local
			super(local);
			treeIndex = index;
		}

		protected OneSide(ObjectInserter inserter
			super(inserter);
			treeIndex = index;
		}

		@Override
		protected boolean mergeImpl() throws IOException {
			return treeIndex < sourceTrees.length;
		}

		@Override
		public ObjectId getResultTreeId() {
			return sourceTrees[treeIndex];
		}

		@Override
		public ObjectId getBaseCommitId() {
			return null;
		}
	}
}
