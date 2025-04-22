package org.eclipse.jgit.transport.http;

import java.nio.file.Path;

import org.eclipse.jgit.util.LRUMap;

public class NetscapeCookieFileCache {


	private static final Integer CACHE_LIMIT_DEFAULT = new Integer(10);

	static LRUMap<Path
			Integer.getInteger(CACHE_LIMIT_PROPERTY_NAME
					.intValue());

	private NetscapeCookieFileCache() {

	}

	public static NetscapeCookieFile getEntry(Path file) {
		if (!cookieFileMap.containsKey(file)) {
			synchronized (NetscapeCookieFileCache.class) {
				if (!cookieFileMap.containsKey(file)) {
					cookieFileMap.put(file
				}
			}
		}
		return cookieFileMap.get(file);
	}

}
