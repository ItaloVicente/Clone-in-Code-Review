
package org.eclipse.jgit.internal.diffmergetool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.internal.BooleanTriState;

public class DiffToolConfig {

	public static final Config.SectionParser<DiffToolConfig> KEY = DiffToolConfig::new;

	private final String toolName;

	private final String guiToolName;

	private final boolean prompt;

	private final BooleanTriState trustExitCode;

	private final Map<String

	private DiffToolConfig(Config rc) {
		toolName = rc.getString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_TOOL);
		guiToolName = rc.getString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_GUITOOL);
		prompt = rc.getBoolean(ConfigConstants.CONFIG_DIFFTOOL_SECTION
				ConfigConstants.CONFIG_KEY_PROMPT
		String trustStr = rc.getString(ConfigConstants.CONFIG_DIFFTOOL_SECTION
				null
		if (trustStr != null) {
			trustExitCode = Boolean.valueOf(trustStr).booleanValue()
					? BooleanTriState.TRUE
					: BooleanTriState.FALSE;
		} else {
			trustExitCode = BooleanTriState.UNSET;
		}
		tools = new HashMap<>();
		Set<String> subsections = rc
				.getSubsections(ConfigConstants.CONFIG_DIFFTOOL_SECTION);
		for (String name : subsections) {
			String cmd = rc.getString(ConfigConstants.CONFIG_DIFFTOOL_SECTION
					name
			String path = rc.getString(ConfigConstants.CONFIG_DIFFTOOL_SECTION
					name
			if ((cmd != null) || (path != null)) {
				tools.put(name
						new UserDefinedDiffTool(name
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

	public boolean isTrustExitCode() {
		return trustExitCode == BooleanTriState.TRUE;
	}

	public Map<String
		return tools;
	}

	public Set<String> getToolNames() {
		return tools.keySet();
	}
}
