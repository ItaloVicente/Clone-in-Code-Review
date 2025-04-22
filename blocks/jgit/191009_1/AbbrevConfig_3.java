package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_ABBREV_STRING_LENGTH;

public class AbbrevConfig {


	public final static AbbrevConfig AUTO = new AbbrevConfig(-1);

	public static int capAbbrev(int len) {
		return Math.min(Math.max(4
	}

	public final static AbbrevConfig NO = new AbbrevConfig(
			Constants.OBJECT_ID_STRING_LENGTH);

	public static AbbrevConfig parseFromConfig(Config config) {
		String value = config.getString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_ABBREV
		if (value == null || value.equalsIgnoreCase(VALUE_AUTO)) {
			return AUTO;
		}
		if (value.equalsIgnoreCase(VALUE_NO)) {
			return NO;
		}
		int len = Integer.parseInt(value);
		return new AbbrevConfig(len);
	}

	private int abbrev;

	public AbbrevConfig(int abbrev) {
		if (abbrev == -1) {
			this.abbrev = abbrev;
		} else {
			this.abbrev = capAbbrev(abbrev);
		}
	}

	public int get(Repository repo) {
		if (abbrev < 0) {
			long count = repo.getObjectDatabase()
					.getApproximateObjectCount();
			int len = 63 - Long.numberOfLeadingZeros(count) + 1;
			len = (len + 1) / 2;
			return Math.max(len
		}
		return abbrev;
	}

	@Override
	public String toString() {
		if (abbrev == Short.MIN_VALUE) {
			return VALUE_AUTO;
		}
		if (abbrev == Constants.OBJECT_ID_STRING_LENGTH) {
			return VALUE_NO;
		}
		return Integer.toString(abbrev);
	}
}
