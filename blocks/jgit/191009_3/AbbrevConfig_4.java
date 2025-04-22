package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_ABBREV_STRING_LENGTH;

import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.internal.JGitText;

public final class AbbrevConfig {


	public static final int MIN_ABBREV = 4;

	public static int capAbbrev(int len) {
		return Math.min(Math.max(MIN_ABBREV
				Constants.OBJECT_ID_STRING_LENGTH);
	}

	public final static AbbrevConfig NO = new AbbrevConfig(
			Constants.OBJECT_ID_STRING_LENGTH);

	public static AbbrevConfig parseFromConfig(Repository repo)
			throws InvalidConfigurationException {
		Config config = repo.getConfig();
		String value = config.getString(ConfigConstants.CONFIG_CORE_SECTION
				null
		if (value == null || value.equalsIgnoreCase(VALUE_AUTO)) {
			return auto(repo);
		}
		if (value.equalsIgnoreCase(VALUE_NO)) {
			return NO;
		}
		try {
			int len = config.getIntInRange(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_ABBREV
					Constants.OBJECT_ID_STRING_LENGTH
			return new AbbrevConfig(len);
		} catch (IllegalArgumentException e) {
			throw new InvalidConfigurationException(MessageFormat
					.format(JGitText.get().invalidCoreAbbrev
		}
	}

	private static AbbrevConfig auto(Repository repo) {
		long count = repo.getObjectDatabase().getApproximateObjectCount();
		if (count == -1) {
			return new AbbrevConfig(OBJECT_ID_ABBREV_STRING_LENGTH);
		}
		int len = 63 - Long.numberOfLeadingZeros(count) + 1;
		len = (len + 1) / 2;
		return new AbbrevConfig(
				Math.max(len
	}

	private int abbrev;

	private AbbrevConfig(int abbrev) {
		this.abbrev = capAbbrev(abbrev);
	}

	public int get() {
		return abbrev;
	}

	@Override
	public String toString() {
		return Integer.toString(abbrev);
	}
}
