package org.eclipse.jgit.merge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.PathMatcher;

public class MergeDriverRegistry {
	private static final Map<String

	private static final Map<String

	private MergeDriverRegistry() {
	}

	public static MergeDriver registerDriver(MergeDriver driver) {
		checkNotNull(driver);
		return REGISTERED_DRIVERS.put(driver.getName()
	}

	public static String associate(String globPattern
		return DRIVER_ASSOCIATION.put(checkNotNull(globPattern)
				checkNotNull(driverName));
	}

	public static MergeDriver findMergeDriver(String path) {
		String fileName = path;
		int separatorIndex = path.lastIndexOf('/');
		if (separatorIndex >= 0)
			fileName = path.substring(separatorIndex + 1);

		for (Map.Entry<String
				.entrySet()) {
			final PathMatcher matcher = FS.DETECTED.getPathMatcher(association
					.getKey());
			if (matcher.matches(fileName)) {
				final MergeDriver driver = REGISTERED_DRIVERS.get(association
						.getValue());
				if (driver != null)
					return driver;
			}
		}
		return null;
	}

	public static void clear() {
		REGISTERED_DRIVERS.clear();
		DRIVER_ASSOCIATION.clear();
	}

	private static <T> T checkNotNull(T reference) {
		if (reference == null)
			throw new NullPointerException();
		return reference;
	}
}
