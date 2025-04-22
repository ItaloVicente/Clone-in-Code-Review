package org.eclipse.jgit.treewalk;

import org.eclipse.jgit.lib.RepositoryConfig;

public class WorkingTreeOptions {

	public static WorkingTreeOptions createDefaultInstance() {
		return new WorkingTreeOptions(false);
	}

	public static WorkingTreeOptions createConfigurationInstance(
			RepositoryConfig config) {
		return new WorkingTreeOptions(config.getCore().isAutocrlf());
	}

	private final boolean autocrlf;

	public WorkingTreeOptions(boolean autocrlf) {
		this.autocrlf = autocrlf;
	}

	public boolean isAutocrlf() {
		return autocrlf;
	}
}
