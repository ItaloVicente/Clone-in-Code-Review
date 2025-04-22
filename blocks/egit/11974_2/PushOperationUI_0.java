package org.eclipse.egit.ui.internal.push;

import org.eclipse.egit.ui.internal.commit.RepositoryCommit;

public interface IPushTasksProvider {

	public void performTasksAfterPush(RepositoryCommit[] commits);

}
