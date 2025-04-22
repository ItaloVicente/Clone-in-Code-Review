
package org.eclipse.jgit.internal.diffmergetool;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Config.SectionParser;

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
		toolName = rc.getString(ConfigConstants.CONFIG_MERGE_SECTION
				ConfigConstants.CONFIG_KEY_TOOL);
		guiToolName = rc.getString(ConfigConstants.CONFIG_MERGE_SECTION
				ConfigConstants.CONFIG_KEY_GUITOOL);
		prompt = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION
				ConfigConstants.CONFIG_KEY_PROMPT
		keepBackup = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION
				ConfigConstants.CONFIG_KEY_KEEP_BACKUP
		keepTemporaries = rc.getBoolean(
				ConfigConstants.CONFIG_MERGETOOL_SECTION
				ConfigConstants.CONFIG_KEY_KEEP_TEMPORARIES
		writeToTemp = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_TO_TEMP
		tools = new HashMap<>();
		Set<String> subsections = rc
				.getSubsections(ConfigConstants.CONFIG_MERGETOOL_SECTION);
		for (String name : subsections) {
			String cmd = rc.getString(ConfigConstants.CONFIG_MERGETOOL_SECTION
					name
			String path = rc.getString(ConfigConstants.CONFIG_MERGETOOL_SECTION
					name
			Optional<Boolean> trustExitCode = Optional.of(Boolean.FALSE);
			String trustStr = rc.getString(
					ConfigConstants.CONFIG_MERGETOOL_SECTION
					ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE);
			if (trustStr != null) {
				trustExitCode = Optional.of(Boolean.valueOf(trustStr));
			}
			if ((cmd != null) || (path != null)) {
				tools.put(name
						new UserDefinedMergeTool(name
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
