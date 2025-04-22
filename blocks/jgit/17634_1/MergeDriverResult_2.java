package org.eclipse.jgit.merge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeDriverRegistry {
	private static final Map<String

	private static final Map<Pattern

	private MergeDriverRegistry() {
	}

	public static IMergeDriver registerDriver(IMergeDriver driver) {
		checkNotNull(driver);
		return REGISTERED_DRIVERS.put(driver.getName()
	}

	public static IMergeDriver removeDriver(String name) {
		return REGISTERED_DRIVERS.remove(name);
	}

	public static String associate(Pattern fileNamePattern
		return DRIVER_ASSOCIATION.put(checkNotNull(fileNamePattern)
				checkNotNull(driverName));
	}

	public static IMergeDriver findMergeDriver(String path) {
		String fileName = path;
		int separatorIndex = path.indexOf('/');
		if (separatorIndex >= 0) {
			fileName = path.substring(separatorIndex + 1);
		}
		for (Map.Entry<Pattern
				.entrySet()) {
			final Matcher matcher = association.getKey().matcher(fileName);
			if (matcher.matches()) {
				final IMergeDriver driver = REGISTERED_DRIVERS.get(association
						.getValue());
				if (driver != null)
					return driver;
			}
		}
		return null;
	}

	private static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}
}
