
package org.eclipse.jgit.lib;

import static java.util.zip.Deflater.DEFAULT_COMPRESSION;

import org.eclipse.jgit.lib.Config.SectionParser;

public class CoreConfig {
	public static final Config.SectionParser<CoreConfig> KEY = CoreConfig::new;

	public static enum AutoCRLF {
		FALSE

		TRUE

		INPUT;
	}

	public static enum EOL {
		CRLF

		LF

		NATIVE;
	}

	public static enum EolStreamType {
		TEXT_CRLF

		TEXT_LF

		AUTO_CRLF

		AUTO_LF

		DIRECT;
	}

	public static enum CheckStat {
		MINIMAL

		DEFAULT
	}

	private final int compression;

	private final int packIndexVersion;

	private final boolean logAllRefUpdates;

	private final String excludesfile;

	private final String attributesfile;

	public static enum SymLinks {
		FALSE
		TRUE
	}

	public static enum HideDotFiles {
		FALSE
		TRUE
		DOTGITONLY
	}

	private CoreConfig(Config rc) {
		compression = rc.getInt(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_COMPRESSION
		packIndexVersion = rc.getInt(ConfigConstants.CONFIG_PACK_SECTION
				ConfigConstants.CONFIG_KEY_INDEXVERSION
		logAllRefUpdates = rc.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
		excludesfile = rc.getString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_EXCLUDESFILE);
		attributesfile = rc.getString(ConfigConstants.CONFIG_CORE_SECTION
				null
	}

	public int getCompression() {
		return compression;
	}

	public int getPackIndexVersion() {
		return packIndexVersion;
	}

	public boolean isLogAllRefUpdates() {
		return logAllRefUpdates;
	}

	public String getExcludesFile() {
		return excludesfile;
	}

	public String getAttributesFile() {
		return attributesfile;
	}
}
