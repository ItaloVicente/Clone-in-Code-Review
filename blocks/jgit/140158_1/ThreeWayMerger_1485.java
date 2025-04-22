
package org.eclipse.jgit.merge;

import org.eclipse.jgit.lib.Repository;

public abstract class ThreeWayMergeStrategy extends MergeStrategy {
	@Override
	public abstract ThreeWayMerger newMerger(Repository db);

	@Override
	public abstract ThreeWayMerger newMerger(Repository db
}
