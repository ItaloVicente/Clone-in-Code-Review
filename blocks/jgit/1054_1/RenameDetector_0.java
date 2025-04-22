
package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;

public class DiffConfig {
	public static final Config.SectionParser<DiffConfig> KEY = new SectionParser<DiffConfig>() {
		public DiffConfig parse(final Config cfg) {
			return new DiffConfig(cfg);
		}
	};

	private final int renameLimit;

	private DiffConfig(final Config rc) {
		renameLimit = rc.getInt("diff"
	}

	public int getRenameLimit() {
		return renameLimit;
	}
}
