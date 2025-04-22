
package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Repository;

public class DiffTools {

	private Map<String

	private Map<String

	public DiffTools(Repository db) {
		setupPredefinedTools();
		setupUserDefinedTools();
	}

	public int compare(String newPath
			String oldId
			BooleanOption gui
		return 0;
	}

	public Set<String> getToolNames() {
		return Collections.emptySet();
	}

	public Map<String
		return Collections.unmodifiableMap(userDefinedTools);
	}

	public Map<String
		return Collections.unmodifiableMap(predefinedTools);
	}

	public Map<String
		return Collections.unmodifiableMap(new TreeMap<>());
	}

	public String getDefaultToolName(BooleanOption gui) {
	}

	public boolean isInteractive() {
		return false;
	}

	private void setupPredefinedTools() {
		predefinedTools = new TreeMap<>();
	}

	private void setupUserDefinedTools() {
		userDefinedTools = new TreeMap<>();
	}

}
