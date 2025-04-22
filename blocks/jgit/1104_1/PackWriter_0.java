
package org.eclipse.jgit.storage.pack;

import static java.util.zip.Deflater.DEFAULT_COMPRESSION;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;

class PackConfig {
	static final Config.SectionParser<PackConfig> KEY = new SectionParser<PackConfig>() {
		public PackConfig parse(final Config cfg) {
			return new PackConfig(cfg);
		}
	};

	final int deltaWindow;

	final long deltaWindowMemory;

	final int deltaDepth;

	final int compression;

	final int indexVersion;

	private PackConfig(Config rc) {
		deltaWindow = rc.getInt("pack"
		deltaWindowMemory = rc.getLong("pack"
		deltaDepth = rc.getInt("pack"
		compression = compression(rc);
		indexVersion = rc.getInt("pack"
	}

	private static int compression(Config rc) {
		if (rc.getString("pack"
			return rc.getInt("pack"
		return rc.getInt("core"
	}
}
