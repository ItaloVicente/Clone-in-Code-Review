
package org.eclipse.jgit.internal.diffmergetool;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DIFFTOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DIFF_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_GUITOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.internal.BooleanTriState;

public class DiffToolConfig {

	public static final Config.SectionParser<DiffToolConfig> KEY = DiffToolConfig::new;

	private final String toolName;

	private final String guiToolName;

	private final boolean prompt;

	private final BooleanTriState trustExitCode;

	private final Map<String

	private DiffToolConfig(Config rc) {
		toolName = rc.getString(CONFIG_DIFF_SECTION
		guiToolName = rc.getString(CONFIG_DIFF_SECTION
				CONFIG_KEY_GUITOOL);
		prompt = rc.getBoolean(CONFIG_DIFFTOOL_SECTION
				true);
		String trustStr = rc.getString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_TRUST_EXIT_CODE);
		if (trustStr != null) {
			trustExitCode = Boolean.parseBoolean(trustStr)
					? BooleanTriState.TRUE
					: BooleanTriState.FALSE;
		} else {
			trustExitCode = BooleanTriState.UNSET;
		}
		tools = new HashMap<>();
		Set<String> subsections = rc.getSubsections(CONFIG_DIFFTOOL_SECTION);
		for (String name : subsections) {
			String cmd = rc.getString(CONFIG_DIFFTOOL_SECTION
					CONFIG_KEY_CMD);
			String path = rc.getString(CONFIG_DIFFTOOL_SECTION
					CONFIG_KEY_PATH);
			if ((cmd != null) || (path != null)) {
				tools.put(name
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
