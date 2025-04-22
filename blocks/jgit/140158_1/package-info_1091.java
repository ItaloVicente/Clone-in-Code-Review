package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.lib.Repository;

public class FilterCommandRegistry {
	private static ConcurrentHashMap<String

	public static FilterCommandFactory register(String filterCommandName
			FilterCommandFactory factory) {
		return filterCommandRegistry.put(filterCommandName
	}

	public static FilterCommandFactory unregister(String filterCommandName) {
		return filterCommandRegistry.remove(filterCommandName);
	}

	public static boolean isRegistered(String filterCommandName) {
		return filterCommandRegistry.containsKey(filterCommandName);
	}

	public static Set<String> getRegisteredFilterCommands() {
		return filterCommandRegistry.keySet();
	}

	public static FilterCommand createFilterCommand(String filterCommandName
			Repository db
			throws IOException {
		FilterCommandFactory cf = filterCommandRegistry.get(filterCommandName);
		return (cf == null) ? null : cf.create(db
	}

}
