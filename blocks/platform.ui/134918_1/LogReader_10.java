package org.eclipse.ui.internal.views.log;

import java.util.*;

public class LogFilesManager {

	private static List<ILogFileProvider> logFileProviders = new ArrayList<>();

	public static void addLogFileProvider(ILogFileProvider provider) {
		if (!logFileProviders.contains(provider)) {
			logFileProviders.add(provider);
		}
	}

	public static void removeLogFileProvider(ILogFileProvider provider) {
		logFileProviders.remove(provider);
	}

	static Map<String, String> getLogSources() {
		ILogFileProvider[] providers = logFileProviders.toArray(new ILogFileProvider[logFileProviders.size()]);
		Map<String, String> result = new HashMap<>(providers.length);

		for (ILogFileProvider provider : providers) {
			Map<String, String> sources = provider.getLogSources();
			result.putAll(sources);
		}

		return result;
	}
}
