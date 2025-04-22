
package org.eclipse.jgit.internal.diffmergetool;

import java.util.TreeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.BooleanTriState;
import org.eclipse.jgit.lib.Repository;

public class DiffTools {

	private Map<String

	private Map<String

	public DiffTools(Repository repo) {
		setupPredefinedTools();
		setupUserDefinedTools();
	}

	public int compare(String newPath
			String oldId
			BooleanTriState gui
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

	public String getDefaultToolName(BooleanTriState gui) {
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
