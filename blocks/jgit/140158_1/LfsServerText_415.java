
package org.eclipse.jgit.lfs.server.internal;

import java.io.Reader;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class LfsGson {
	private static final Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.disableHtmlEscaping()
			.create();

	static class Error {
		String message;

		Error(String m) {
			this.message = m;
		}
	}

	public static void toJson(Object src
			throws JsonIOException {
		if (src instanceof String) {
			gson.toJson(new Error((String) src)
		} else {
			gson.toJson(src
		}
	}

	public static <T> T fromJson(Reader json
			throws JsonSyntaxException
		return gson.fromJson(json
	}
}
