package org.eclipse.egit.core.op;

import org.eclipse.jgit.merge.MergeStrategy;

public interface MergingOperation extends IEGitOperation {

	void setMergeStrategy(MergeStrategy strategy);
}
