
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_BLOCK_SIZE;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

public class ReftableConfig {
	private int refBlockSize = 4 << 10;
	private int logBlockSize;
	private int restartInterval;
	private int maxIndexLevels;
	private boolean indexObjects = true;

	public ReftableConfig() {
	}

	public ReftableConfig(Repository db) {
		fromConfig(db.getConfig());
	}

	public ReftableConfig(Config cfg) {
		fromConfig(cfg);
	}

	public ReftableConfig(ReftableConfig cfg) {
		this.refBlockSize = cfg.refBlockSize;
		this.logBlockSize = cfg.logBlockSize;
		this.restartInterval = cfg.restartInterval;
		this.maxIndexLevels = cfg.maxIndexLevels;
		this.indexObjects = cfg.indexObjects;
	}

	public int getRefBlockSize() {
		return refBlockSize;
	}

	public void setRefBlockSize(int szBytes) {
		if (szBytes > MAX_BLOCK_SIZE) {
			throw new IllegalArgumentException();
		}
		refBlockSize = Math.max(0
	}

	public int getLogBlockSize() {
		return logBlockSize;
	}

	public void setLogBlockSize(int szBytes) {
		if (szBytes > MAX_BLOCK_SIZE) {
			throw new IllegalArgumentException();
		}
		logBlockSize = Math.max(0
	}

	public int getRestartInterval() {
		return restartInterval;
	}

	public void setRestartInterval(int interval) {
		restartInterval = Math.max(0
	}

	public int getMaxIndexLevels() {
		return maxIndexLevels;
	}

	public void setMaxIndexLevels(int levels) {
		maxIndexLevels = Math.max(0
	}

	public boolean isIndexObjects() {
		return indexObjects;
	}

	public void setIndexObjects(boolean index) {
		indexObjects = index;
	}

	public void fromConfig(Config rc) {
		refBlockSize = rc.getInt("reftable"
		logBlockSize = rc.getInt("reftable"
		restartInterval = rc.getInt("reftable"
		maxIndexLevels = rc.getInt("reftable"
		indexObjects = rc.getBoolean("reftable"
	}
}
