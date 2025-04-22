package org.eclipse.jgit.internal.diffmergetool;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.internal.BooleanTriState;

public class MergeTools {
	private final MergeToolConfig config;

	private final Map<String

	private final Map<String

	public MergeTools(Repository repo) {
		config = repo.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools();
	}

	public int merge(String localFile
			String remoteFile
			String toolName
			BooleanTriState trustExitCode)
			throws ToolException {
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

	public String getDefaultToolName(BooleanTriState gui) {
	}

	public boolean isInteractive() {
		return config.isPrompt();
	}

	private Map<String
		return new TreeMap<>();
	}

	private Map<String
		return new TreeMap<>();
	}
}
