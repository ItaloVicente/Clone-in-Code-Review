
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.ConfigInvalidException;

public abstract class StoredConfig extends Config {
	public StoredConfig() {
		super();
	}

	public StoredConfig(Config defaultConfig) {
		super(defaultConfig);
	}

	public abstract void load() throws IOException

	public abstract void save() throws IOException;
}
