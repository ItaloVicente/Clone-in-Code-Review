package org.eclipse.egit.ui.internal.synchronize.mapping;

import org.eclipse.egit.ui.internal.synchronize.model.GitModelCommit;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitCommitChangeSetSorter extends ViewerSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof GitModelCommit && e2 instanceof GitModelCommit) {
			RevCommit rc1 = ((GitModelCommit) e1).getRemoteCommit();
			RevCommit rc2 = ((GitModelCommit) e2).getRemoteCommit();

			return rc2.getCommitTime() - rc1.getCommitTime();
		}

		return super.compare(viewer, e1, e2);
	}

}
