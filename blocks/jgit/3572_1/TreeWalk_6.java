package org.eclipse.jgit.treewalk;

import java.nio.charset.Charset;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;

public class TreeOptions {
	public static final Config.SectionParser<TreeOptions> KEY = new SectionParser<TreeOptions>() {
		public TreeOptions parse(final Config cfg) {
			return new TreeOptions(cfg);
		}
	};

	private Charset pathEncoding;

	public TreeOptions(final Charset encoding) {
		pathEncoding = encoding;
	}

	public TreeOptions(final Config rc) {
		String encoding = rc.getString(ConfigConstants.CONFIG_JGIT_SECTION
				null
		if (encoding == null)
			pathEncoding = Constants.FILENAME_CHARSET;
		else
			pathEncoding = Charset.forName(encoding);
	}

	public Charset getPathEncoding() {
		return pathEncoding;
	}
}
