package org.eclipse.jgit.lfs.server;

import java.util.List;
import java.util.Map;

public interface Response {
	class Action {
		public String href;
		public Map<String
	}

	class Error {
		public int code;
		public String message;
	}

	class ObjectInfo {
		public String oid;
		public long size;
		public Map<String
		public Error error;
	}

	class Body {
		public List<ObjectInfo> objects;
	}
}
