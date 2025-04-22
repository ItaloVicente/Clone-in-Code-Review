
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class CommitConfig {

	public static final Config.SectionParser<CommitConfig> KEY = CommitConfig::new;

	private final static Charset DEFAULT_COMMIT_MESSAGE_ENCODING = StandardCharsets.UTF_8;

	private String i18nCommitEncoding;

	private String commitTemplatePath;

	private CommitConfig(Config rc) {
		commitTemplatePath = rc.getString(ConfigConstants.CONFIG_COMMIT_SECTION
				null

		i18nCommitEncoding = rc.getString(ConfigConstants.CONFIG_SECTION_I18N
				null

	}

	@Nullable
	public String getCommitTemplatePath() {
		return commitTemplatePath;
	}

	@Nullable
	public String getCommitTemplateContent()
			throws FileNotFoundException
		if (commitTemplatePath != null) {

			Charset commitMessageEncoding = DEFAULT_COMMIT_MESSAGE_ENCODING;
			if (i18nCommitEncoding != null) {
				try {
					commitMessageEncoding = Charset.forName(i18nCommitEncoding);
				} catch (IllegalCharsetNameException
						| UnsupportedCharsetException e) {
					throw new ConfigInvalidException(
							MessageFormat.format(JGitText.get().invalidEncoding
									i18nCommitEncoding)
							e);
				}
			}

			File commitTemplateFile = FileUtils.resolveFile(FS.DETECTED
					commitTemplatePath);
			return RawParseUtils.decode(commitMessageEncoding
					IO.readFully(commitTemplateFile));
		}
		return null;
	}
}
