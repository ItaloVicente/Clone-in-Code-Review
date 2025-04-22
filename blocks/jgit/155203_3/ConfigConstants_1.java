
package org.eclipse.jgit.lib;

import java.util.Optional;

import org.eclipse.jgit.lib.Config.SectionParser;

public class CommitConfig {
	public static final Config.SectionParser<CommitConfig> KEY = CommitConfig::new;

	private String commitTemplatePath;

	private CommitConfig(Config rc) {
		commitTemplatePath = getCommitTemplatePathInternal(rc
				Constants.GIT_COMMIT_TEMPLATE_KEY);
	}


	public Optional<String> getCommitTemplatePath() {
		return Optional.ofNullable(commitTemplatePath);
	}

	private static String getCommitTemplatePathInternal(Config rc
		return rc.getString(
				ConfigConstants.CONFIG_COMMIT_SECTION
				ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE);
	}

}
