
package org.eclipse.jgit.subtree;

import org.eclipse.jgit.lib.Config;

public class PathBasedContext extends SubtreeContext {

	private String mPath;

	public PathBasedContext(String aId
		super(aId);
		mPath = aPath;
	}

	@Override
	protected String getPath(Config conf) {
		return mPath;
	}

}
