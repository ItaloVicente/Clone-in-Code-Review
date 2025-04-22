
package org.eclipse.jgit.subtree;

import org.eclipse.jgit.lib.Config;

public class NameBasedSubtreeContext extends SubtreeContext {

	NameBasedSubtreeContext(String name) {
		super(name);
	}

	@Override
	protected String getPath(Config conf) {
		return conf != null ? conf.getString(SubtreeSplitter.SUBTREE_SECTION
				id
	}

}
