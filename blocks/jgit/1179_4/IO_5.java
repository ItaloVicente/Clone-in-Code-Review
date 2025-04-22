package org.eclipse.jgit.treewalk;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.CoreConfig;

public class WorkingTreeOptions {

	public static WorkingTreeOptions createDefaultInstance() {
		return new WorkingTreeOptions(false);
	}

	public static WorkingTreeOptions createConfigurationInstance(Config config) {
		return new WorkingTreeOptions(config.get(CoreConfig.KEY).isAutoCRLF());
	}

	private final boolean autoCRLF;

	public WorkingTreeOptions(boolean autoCRLF) {
		this.autoCRLF = autoCRLF;
	}

	public boolean isAutoCRLF() {
		return autoCRLF;
	}
}
