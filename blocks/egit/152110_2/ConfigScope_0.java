package org.eclipse.egit.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.storage.file.FileBasedConfig;

@SuppressWarnings("restriction")
class CachingRepository extends FileRepository {

	private static final ThreadLocal<Map<File, CachedConfig>> CACHED_CONFIG = ThreadLocal
			.withInitial(() -> new HashMap<>());

	public CachingRepository(BaseRepositoryBuilder options) throws IOException {
		super(options);
	}

	public CachingRepository(String filename) throws IOException {
		super(filename);
	}

	@Override
	public FileBasedConfig getConfig() {
		Map<File, CachedConfig> cache = CACHED_CONFIG.get();
		CachedConfig cfg = cache.get(getDirectory());
		if (cfg == null) {
			return super.getConfig();
		} else {
			if (cfg.config == null) {
				cfg.config = super.getConfig();
			}
			return cfg.config;
		}
	}

	void cacheConfig(boolean doCache) {
		Map<File, CachedConfig> cache = CACHED_CONFIG.get();
		CachedConfig cfg = cache.get(getDirectory());
		if (cfg == null) {
			if (!doCache) {
				return;
			}
			cfg = new CachedConfig();
			cache.put(getDirectory(), cfg);
		}
		if (!doCache && cfg.level > 0) {
			if (--cfg.level == 0) {
				cache.remove(getDirectory());
			}
		} else if (doCache) {
			cfg.level++;
		}
	}

	void clearConfigCache() {
		Map<File, CachedConfig> cache = CACHED_CONFIG.get();
		cache.remove(getDirectory());
	}

	@Override
	public void close() {
		clearConfigCache();
		super.close();
	}

	private static class CachedConfig {
		int level;
		FileBasedConfig config;
	}
}
