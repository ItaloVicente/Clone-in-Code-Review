package org.eclipse.egit.core.info;

import org.eclipse.egit.core.internal.info.GitItemStateFactory;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;

public interface GitInfo {

	Repository getRepository();

	String getGitPath();

	enum Source {
		WORKING_TREE, INDEX, COMMIT
	}

	Source getSource();

	AnyObjectId getCommitId();

	default GitItemState getGitState() {
		return GitItemStateFactory.getInstance().get(getRepository(),
				getGitPath());
	}
}
