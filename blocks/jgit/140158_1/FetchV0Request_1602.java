
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchResult extends OperationResult {
	private final List<FetchHeadRecord> forMerge;

	private final Map<String

	FetchResult() {
		forMerge = new ArrayList<>();
		submodules = new HashMap<>();
	}

	void add(FetchHeadRecord r) {
		if (!r.notForMerge)
			forMerge.add(r);
	}

	public void addSubmodule(String path
		submodules.put(path
	}

	public Map<String
		return Collections.unmodifiableMap(submodules);
	}
}
