package org.eclipse.jgit.treewalk;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.CheckStat;
import org.eclipse.jgit.lib.CoreConfig.EOL;
import org.eclipse.jgit.lib.CoreConfig.HideDotFiles;
import org.eclipse.jgit.lib.CoreConfig.SymLinks;

public class WorkingTreeOptions {
	public static final Config.SectionParser<WorkingTreeOptions> KEY =
			WorkingTreeOptions::new;

	private final boolean fileMode;

	private final AutoCRLF autoCRLF;

	private final EOL eol;

	private final CheckStat checkStat;

	private final SymLinks symlinks;

	private final HideDotFiles hideDotFiles;

	private final boolean dirNoGitLinks;

	private WorkingTreeOptions(Config rc) {
		fileMode = rc.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		autoCRLF = rc.getEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		eol = rc.getEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_EOL
		checkStat = rc.getEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_CHECKSTAT
		symlinks = rc.getEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SYMLINKS
		hideDotFiles = rc.getEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HIDEDOTFILES
				HideDotFiles.DOTGITONLY);
		dirNoGitLinks = rc.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
				false);
	}

	public boolean isFileMode() {
		return fileMode;
	}

	public AutoCRLF getAutoCRLF() {
		return autoCRLF;
	}

	public EOL getEOL() {
		return eol;
	}

	public CheckStat getCheckStat() {
		return checkStat;
	}

	public SymLinks getSymLinks() {
		return symlinks;
	}

	public HideDotFiles getHideDotFiles() {
		return hideDotFiles;
	}

	public boolean isDirNoGitLinks() { return dirNoGitLinks; }
}
