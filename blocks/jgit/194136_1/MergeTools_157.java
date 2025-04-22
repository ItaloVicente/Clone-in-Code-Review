
package org.eclipse.jgit.internal.diffmergetool;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_GUITOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_KEEP_BACKUP;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_KEEP_TEMPORARIES;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_WRITE_TO_TEMP;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.internal.BooleanTriState;

public class MergeToolConfig {

	public static final Config.SectionParser<MergeToolConfig> KEY = MergeToolConfig::new;

	private final String toolName;

	private final String guiToolName;

	private final boolean prompt;

	private final boolean keepBackup;

	private final boolean keepTemporaries;

	private final boolean writeToTemp;

	private final Map<String

	private MergeToolConfig(Config rc) {
		toolName = rc.getString(CONFIG_MERGE_SECTION
		guiToolName = rc.getString(CONFIG_MERGE_SECTION
				CONFIG_KEY_GUITOOL);
		prompt = rc.getBoolean(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_PROMPT
		keepBackup = rc.getBoolean(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_KEEP_BACKUP
		keepTemporaries = rc.getBoolean(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_KEEP_TEMPORARIES
		writeToTemp = rc.getBoolean(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_WRITE_TO_TEMP
		tools = new HashMap<>();
		Set<String> subsections = rc.getSubsections(CONFIG_MERGETOOL_SECTION);
		for (String name : subsections) {
			String cmd = rc.getString(CONFIG_MERGETOOL_SECTION
					CONFIG_KEY_CMD);
			String path = rc.getString(CONFIG_MERGETOOL_SECTION
					CONFIG_KEY_PATH);
			BooleanTriState trustExitCode = BooleanTriState.FALSE;
			String trustStr = rc.getString(CONFIG_MERGETOOL_SECTION
					CONFIG_KEY_TRUST_EXIT_CODE);
			if (trustStr != null) {
				trustExitCode = Boolean.valueOf(trustStr).booleanValue()
						? BooleanTriState.TRUE
						: BooleanTriState.FALSE;
			} else {
				trustExitCode = BooleanTriState.UNSET;
			}
			if ((cmd != null) || (path != null)) {
				tools.put(name
						trustExitCode));
			}
		}
	}

	public String getDefaultToolName() {
		return toolName;
	}

	public String getDefaultGuiToolName() {
		return guiToolName;
	}

	public boolean isPrompt() {
		return prompt;
	}

	public boolean isKeepBackup() {
		return keepBackup;
	}

	public boolean isKeepTemporaries() {
		return keepTemporaries;
	}

	public boolean isWriteToTemp() {
		return writeToTemp;
	}

	public Map<String
		return tools;
	}

	public Set<String> getToolNames() {
		return tools.keySet();
	}

}
