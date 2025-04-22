
package org.eclipse.jgit.diff;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.util.StringUtils;

public class DiffConfig {
	public static final Config.SectionParser<DiffConfig> KEY = DiffConfig::new;

	public static enum RenameDetectionType {
		FALSE

		TRUE

		COPY
	}

	private final boolean noPrefix;

	private final RenameDetectionType renameDetectionType;

	private final int renameLimit;

	private DiffConfig(Config rc) {
		noPrefix = rc.getBoolean(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_NOPREFIX
		renameDetectionType = parseRenameDetectionType(rc.getString(
				ConfigConstants.CONFIG_DIFF_SECTION
		renameLimit = rc.getInt(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_RENAMELIMIT
	}

	public boolean isNoPrefix() {
		return noPrefix;
	}

	public boolean isRenameDetectionEnabled() {
		return renameDetectionType != RenameDetectionType.FALSE;
	}

	public RenameDetectionType getRenameDetectionType() {
		return renameDetectionType;
	}

	public int getRenameLimit() {
		return renameLimit;
	}

	private static RenameDetectionType parseRenameDetectionType(
			final String renameString) {
		if (renameString == null)
			return RenameDetectionType.FALSE;
		else if (StringUtils.equalsIgnoreCase(
				ConfigConstants.CONFIG_RENAMELIMIT_COPY
				|| StringUtils
						.equalsIgnoreCase(
								ConfigConstants.CONFIG_RENAMELIMIT_COPIES
								renameString))
			return RenameDetectionType.COPY;
		else {
			final Boolean renameBoolean = StringUtils
					.toBooleanOrNull(renameString);
			if (renameBoolean == null)
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported2
						ConfigConstants.CONFIG_DIFF_SECTION
						ConfigConstants.CONFIG_KEY_RENAMES
			else if (renameBoolean.booleanValue())
				return RenameDetectionType.TRUE;
			else
				return RenameDetectionType.FALSE;
		}
	}
}
