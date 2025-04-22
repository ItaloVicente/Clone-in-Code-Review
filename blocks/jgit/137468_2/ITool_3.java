
package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Repository;

public class DiffToolManager {

	private final DiffToolConfig config;

	private Map<String

	private Map<String

	public DiffToolManager(Repository db) {
		config = db.getConfig().get(DiffToolConfig.KEY);
		setupPredefinedTools();
		setupUserDefinedTools();
	}

	public int compare(String newPath
			String oldId
			BooleanOption gui
		return 0;
	}

	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	public Map<String
		return userDefinedTools;
	}

	public Map<String
		return predefinedTools;
	}

	public Map<String
		return new TreeMap<>();
	}

	private void setupPredefinedTools() {
		predefinedTools = new TreeMap<>();
		for (PreDefinedDiffTools tool : PreDefinedDiffTools.values()) {
			predefinedTools
					.put(tool.name()
							new PreDefinedDiffTool(tool.name()
							tool.getParameters()
		}
	}

	private void setupUserDefinedTools() {
		userDefinedTools = new TreeMap<>();
		Map<String
		for (String name : userTools.keySet()) {
			ITool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				userDefinedTools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) predefinedTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
				}
			}
		}
	}

}
