package org.eclipse.jgit.niofs.fs.options;

import java.nio.file.CopyOption;

public class CherryPickCopyOption implements CopyOption {

	private final String[] commits;

	public CherryPickCopyOption(final String... commits) {
		this.commits = commits;
	}

	public String[] getCommits() {
		return commits;
	}
}
