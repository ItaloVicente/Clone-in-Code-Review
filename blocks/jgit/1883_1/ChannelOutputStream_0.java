
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.CoreConfig;

class WriteConfig {
	static final Config.SectionParser<WriteConfig> KEY = new SectionParser<WriteConfig>() {
		public WriteConfig parse(final Config cfg) {
			return new WriteConfig(cfg);
		}
	};

	private final int compression;

	private final boolean fsyncObjectFiles;

	private WriteConfig(final Config rc) {
		compression = rc.get(CoreConfig.KEY).getCompression();
		fsyncObjectFiles = rc.getBoolean("core"
	}

	int getCompression() {
		return compression;
	}

	boolean getFSyncObjectFiles() {
		return fsyncObjectFiles;
	}
}
