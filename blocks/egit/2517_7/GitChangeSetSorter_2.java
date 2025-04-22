package org.eclipse.jgit.revwalk;

import org.eclipse.egit.ui.internal.synchronize.mapping.GitChangeSetSorterTest;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelCommit;

public class MockRevCommit extends RevCommit {

	public MockRevCommit(int commitTime) {
		super(zeroId());
		this.commitTime = commitTime;
	}

}
