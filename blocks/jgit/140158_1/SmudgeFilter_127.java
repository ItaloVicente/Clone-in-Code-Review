package org.eclipse.jgit.lfs;

import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

	class ExpiringAction extends Action {
		public String expiresAt;

		public String expiresIn;
	}

	class Error {
		public int code;

		public String message;
	}








	public static Gson gson() {
		return new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.disableHtmlEscaping().create();
	}
}
