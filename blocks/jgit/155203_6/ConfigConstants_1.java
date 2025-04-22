
package org.eclipse.jgit.lib;

import java.util.Optional;

import org.eclipse.jgit.lib.Config.SectionParser;

public class CommitConfig {
	public static final Config.SectionParser<CommitConfig> KEY = CommitConfig::new;

	private String commitTemplatePath;

	private CommitConfig(Config rc) {
		commitTemplatePath = rc.getString(ConfigConstants.CONFIG_COMMIT_SECTION
				null
	}

	public Optional<String> getCommitTemplatePath() {
		return Optional.ofNullable(commitTemplatePath);
	}

}
