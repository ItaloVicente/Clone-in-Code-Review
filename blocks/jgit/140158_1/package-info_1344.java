
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.CoreConfig;

class WriteConfig {
	static final Config.SectionParser<WriteConfig> KEY = WriteConfig::new;

	private final int compression;

	private final boolean fsyncObjectFiles;

	private final boolean fsyncRefFiles;

	private WriteConfig(Config rc) {
		compression = rc.get(CoreConfig.KEY).getCompression();
		fsyncObjectFiles = rc.getBoolean("core"
		fsyncRefFiles = rc.getBoolean("core"
	}

	int getCompression() {
		return compression;
	}

	boolean getFSyncObjectFiles() {
		return fsyncObjectFiles;
	}

	boolean getFSyncRefFiles() {
		return fsyncRefFiles;
	}
}
