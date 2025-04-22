package org.eclipse.jgit.treewalk;

import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.StoredConfig;

public class WorkingTreeOptions {

	public static WorkingTreeOptions createDefaultInstance() {
		return new WorkingTreeOptions(false);
	}

	public static WorkingTreeOptions createConfigurationInstance(
			StoredConfig config) {
		return new WorkingTreeOptions(config.get(CoreConfig.KEY).isAutocrlf());
	}

	private final boolean autocrlf;

	public WorkingTreeOptions(boolean autocrlf) {
		this.autocrlf = autocrlf;
	}

	public boolean isAutocrlf() {
		return autocrlf;
	}
}
