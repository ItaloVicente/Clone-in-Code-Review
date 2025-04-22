
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommitConfig {

	private static final Logger LOG = LoggerFactory
			.getLogger(CommitConfig.class);

	public static final Config.SectionParser<CommitConfig> KEY = CommitConfig::new;

	private String commitTemplatePath;

	private CommitConfig(Config rc) {
		commitTemplatePath = rc.getString(ConfigConstants.CONFIG_COMMIT_SECTION
				null
	}

	public Optional<String> getCommitTemplatePath() {
		return Optional.ofNullable(commitTemplatePath);
	}

	public Optional<String> getCommitTemplateContent() {
		if (commitTemplatePath != null) {
			File commitTemplateFile = new File(commitTemplatePath);

			try {
				return Optional.ofNullable(
						RawParseUtils.decode(IO.readFully(commitTemplateFile)));
			} catch (IOException e) {
				LOG.error(e.getMessage()
			}
		}
		return Optional.empty();
	}

}
