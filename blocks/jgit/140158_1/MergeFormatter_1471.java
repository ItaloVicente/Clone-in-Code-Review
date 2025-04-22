package org.eclipse.jgit.merge;

import java.io.IOException;

import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;

public class MergeConfig {

	public static MergeConfig getConfigForCurrentBranch(Repository repo) {
		try {
			String branch = repo.getBranch();
			if (branch != null)
				return repo.getConfig().get(getParser(branch));
		} catch (IOException e) {
		}
		return new MergeConfig();
	}

	public static final SectionParser<MergeConfig> getParser(
			final String branch) {
		return new MergeConfigSectionParser(branch);
	}

	private final FastForwardMode fastForwardMode;

	private final boolean squash;

	private final boolean commit;

	private MergeConfig(String branch
		String[] mergeOptions = getMergeOptions(branch
		fastForwardMode = getFastForwardMode(config
		squash = isMergeConfigOptionSet("--squash"
		commit = !isMergeConfigOptionSet("--no-commit"
	}

	private MergeConfig() {
		fastForwardMode = FastForwardMode.FF;
		squash = false;
		commit = true;
	}

	public FastForwardMode getFastForwardMode() {
		return fastForwardMode;
	}

	public boolean isSquash() {
		return squash;
	}

	public boolean isCommit() {
		return commit;
	}

	private static FastForwardMode getFastForwardMode(Config config
			String[] mergeOptions) {
		for (String option : mergeOptions) {
			for (FastForwardMode mode : FastForwardMode.values())
				if (mode.matchConfigValue(option))
					return mode;
		}
		FastForwardMode ffmode = FastForwardMode.valueOf(config.getEnum(
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		return ffmode;
	}

	private static boolean isMergeConfigOptionSet(String optionToLookFor
			String[] mergeOptions) {
		for (String option : mergeOptions) {
			if (optionToLookFor.equals(option))
				return true;
		}
		return false;
	}

	private static String[] getMergeOptions(String branch
		String mergeOptions = config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS);
		if (mergeOptions != null)
		else
			return new String[0];
	}

	private static class MergeConfigSectionParser implements
			SectionParser<MergeConfig> {

		private final String branch;

		public MergeConfigSectionParser(String branch) {
			this.branch = branch;
		}

		@Override
		public MergeConfig parse(Config cfg) {
			return new MergeConfig(branch
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MergeConfigSectionParser)
				return branch.equals(((MergeConfigSectionParser) obj).branch);
			else
				return false;
		}

		@Override
		public int hashCode() {
			return branch.hashCode();
		}

	}

}
