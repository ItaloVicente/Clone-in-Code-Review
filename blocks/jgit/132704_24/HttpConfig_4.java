package org.eclipse.jgit.internal.transport.http;

import java.nio.file.Path;

import org.eclipse.jgit.transport.HttpConfig;
import org.eclipse.jgit.util.LRUMap;

public class NetscapeCookieFileCache {

	private final LRUMap<Path

	private static NetscapeCookieFileCache instance;

	private NetscapeCookieFileCache(HttpConfig config) {
		cookieFileMap = new LRUMap<>(config.getCookieFileCacheLimit()
				config.getCookieFileCacheLimit());
	}

	public static NetscapeCookieFileCache getInstance(HttpConfig config) {
		if (instance == null) {
			return new NetscapeCookieFileCache(config);
		} else {
			return instance;
		}
	}

	public NetscapeCookieFile getEntry(Path path) {
		if (!cookieFileMap.containsKey(path)) {
			synchronized (NetscapeCookieFileCache.class) {
				if (!cookieFileMap.containsKey(path)) {
					cookieFileMap.put(path
				}
			}
		}
		return cookieFileMap.get(path);
	}

}
