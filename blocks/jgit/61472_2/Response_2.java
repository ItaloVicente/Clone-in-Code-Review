package org.eclipse.jgit.lfs.server;

import java.util.List;
import java.util.Map;

interface Response {
	class Action {
		String href;
		Map<String
	}

	class ObjectInfo {
		String oid;
		long size;
		Map<String
	}

	class Body {
		List<ObjectInfo> objects;
	}
}
