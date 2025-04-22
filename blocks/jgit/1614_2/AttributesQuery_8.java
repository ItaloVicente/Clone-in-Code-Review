
package org.eclipse.jgit.attributes;

import org.eclipse.jgit.util.RawParseUtils;

import java.util.HashSet;
import java.util.Set;

public class AttributesQuery {

	private final String basePath;

	private final AttributesQuery parent;

	private final Attributes attributes;

	public AttributesQuery(Attributes attributes
			AttributesQuery parent) {
        basePath = RawParseUtils.pathAddTrailingSlash(basePath);
        basePath = RawParseUtils.pathTrimLeadingSlash(basePath);
		this.attributes = attributes;
		this.basePath = basePath;
		this.parent = parent;
	}

	public void collect(String path
		collect(path
	}

	private boolean collect(String path
			Set<String> keysToIgnore) {
		path = RawParseUtils.pathTrimLeadingSlash(path);

		if (path.startsWith(basePath)) {
			final String relativePath = path.substring(basePath.length());
			if (!attributes.collect(relativePath
				return false;
		}

		if (parent != null && !parent.collect(path
			return false;

		return true;
	}
}
