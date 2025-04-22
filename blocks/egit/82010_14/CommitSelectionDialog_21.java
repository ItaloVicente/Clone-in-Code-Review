package org.eclipse.egit.ui.internal.history;

import org.eclipse.jgit.revwalk.RevCommit;

interface CommitNavigationListener {
	void showCommit(RevCommit c);
}
