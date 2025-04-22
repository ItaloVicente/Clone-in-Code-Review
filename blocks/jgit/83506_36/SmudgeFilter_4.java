package org.eclipse.jgit.lfs;

import java.util.List;
import java.util.Map;

public interface Protocol {
	class Request {
		public String operation;

		public List<ObjectSpec> objects;
	}

	class Response {
		public List<ObjectInfo> objects;
	}

	class ObjectSpec {

	}

	class ObjectInfo extends ObjectSpec {
		public Map<String

		public Error error;
	}

	class Action {
		public String href;

		public Map<String
	}

	class Error {
		public int code;

		public String message;
	}







}
