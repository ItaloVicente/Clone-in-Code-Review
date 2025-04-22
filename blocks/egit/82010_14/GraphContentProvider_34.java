package org.eclipse.egit.ui.internal.history;

import org.eclipse.team.ui.history.HistoryPageSource;
import org.eclipse.ui.part.Page;

public class GitHistoryPageSource extends HistoryPageSource {

	public static final GitHistoryPageSource INSTANCE = new GitHistoryPageSource();

	private GitHistoryPageSource() {
		super();
	}

	@Override
	public boolean canShowHistoryFor(final Object object) {
		return GitHistoryPage.canShowHistoryFor(object);
	}

	@Override
	public Page createPage(final Object object) {
		return new GitHistoryPage();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof GitHistoryPageSource;
	}

	@Override
	public int hashCode() {
		return 42;
	}
}
