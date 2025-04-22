
package org.eclipse.jgit.internal.diffmergetool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Config.SectionParser;

public class DiffToolConfig {

	public static final Config.SectionParser<DiffToolConfig> KEY = DiffToolConfig::new;

	private final String toolName;

	private final String guiToolName;

	private final boolean prompt;

	private final BooleanOption trustExitCode;

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
			trustExitCode = BooleanOption
					.toConfigured(Boolean.parseBoolean(trustStr));
		} else {
			trustExitCode = BooleanOption.toDefault(false);
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
		return trustExitCode.toBoolean();
	}

	public Map<String
		return tools;
	}

	public Set<String> getToolNames() {
		return tools.keySet();
	}
}
