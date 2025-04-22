package org.eclipse.jgit.util;

public enum Hook {
	PRE_COMMIT("pre-commit")

	PREPARE_COMMIT_MSG("prepare-commit-msg")

	COMMIT_MSG("commit-msg")

	POST_COMMIT("post-commit")

	POST_REWRITE("post-rewrite")


	private final String name;

	private Hook(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
