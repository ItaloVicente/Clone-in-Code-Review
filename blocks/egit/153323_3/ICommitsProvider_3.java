package org.eclipse.egit.ui.internal.history;

import org.eclipse.jgit.revwalk.RevFlag;

interface ICommitsProvider {

	Object getSearchContext();

	SWTCommit[] getCommits();

	RevFlag getHighlight();
}
