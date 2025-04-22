package org.eclipse.egit.ui.internal.history;

import org.eclipse.jgit.revwalk.RevCommit;

public interface TableLoader {
	void loadItem(int index);

	void loadCommit(RevCommit c);
}
