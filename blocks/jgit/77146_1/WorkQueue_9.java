package org.eclipse.jgit.lib;

import java.util.concurrent.TimeUnit;

public class RepositoryCacheConfig {

	public static final long NO_CLEANUP = 0;

	public static final long AUTO_CLEANUP_DELAY = -1;

	private long expireAfterMillis;

	private long cleanupDelayMillis;

	public RepositoryCacheConfig() {
		expireAfterMillis = TimeUnit.HOURS.toMillis(1);
		cleanupDelayMillis = AUTO_CLEANUP_DELAY;
	}

	public long getExpireAfter() {
		return expireAfterMillis;
	}

	public void setExpireAfter(long expireAfterMillis) {
		this.expireAfterMillis = expireAfterMillis;
	}

	public long getCleanupDelay() {
		if (cleanupDelayMillis < 0) {
			return Math.min(expireAfterMillis / 10
					TimeUnit.MINUTES.toMillis(10));
		}
		return cleanupDelayMillis;
	}

	public void setCleanupDelay(long cleanupDelayMillis) {
		this.cleanupDelayMillis = cleanupDelayMillis;
	}

	public RepositoryCacheConfig fromConfig(Config config) {
		setExpireAfter(
				config.getTimeUnit("core"
						getExpireAfter()
		setCleanupDelay(
				config.getTimeUnit("core"
						AUTO_CLEANUP_DELAY
		return this;
	}

	public void install() {
		RepositoryCache.reconfigure(this);
	}
}
