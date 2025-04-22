
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class CommitConfig {


	public static final Config.SectionParser<CommitConfig> KEY = CommitConfig::new;

	private String commitTemplatePath;

	private Charset commitMessageEncoding = StandardCharsets.UTF_8;

	private CommitConfig(Config rc) {
		commitTemplatePath = rc.getString(ConfigConstants.CONFIG_COMMIT_SECTION
				null

		String i18nCommitEncoding = rc.getString(
				ConfigConstants.CONFIG_SECTION_I18N
				ConfigConstants.CONFIG_KEY_COMMIT_ENCODING);

		if (i18nCommitEncoding != null) {
			commitMessageEncoding = Charset.forName(i18nCommitEncoding);
		}
	}

	@Nullable
	public String getCommitTemplatePath() {
		return commitTemplatePath;
	}

	@Nullable
	public String getCommitTemplateContent()
			throws FileNotFoundException
		if (commitTemplatePath != null) {
			File commitTemplateFile = new File(
					resolveHomePath(commitTemplatePath));

			return RawParseUtils.decode(commitMessageEncoding
					IO.readFully(commitTemplateFile));
		}
		return null;
	}

	private String resolveHomePath(String path) {
		return path.replaceFirst(
				"^~"
	}

}
