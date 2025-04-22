package org.eclipse.egit.core.op;

import org.eclipse.egit.core.Activator;
import org.eclipse.jgit.merge.MergeStrategy;

public abstract class AbstractMergingOperation implements IEGitOperation {
	private MergeStrategy mergeStrategy;

	protected MergeStrategy getApplicableMergeStrategy() {
		if (mergeStrategy == null) {
			return Activator.getDefault().getPreferredMergeStrategy();
		}
		return mergeStrategy;
	}

	public void setMergeStrategy(MergeStrategy strategy) {
		this.mergeStrategy = strategy;
	}

}
