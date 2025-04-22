
package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.util.Map;

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
	}

	private void setupUserDefinedTools() {
		userDefinedTools = new TreeMap<>();
	}

}
