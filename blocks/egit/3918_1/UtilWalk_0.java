package org.eclipse.egit.core;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class UtilCommit extends RevCommit {
	private Object util;

	public UtilCommit(AnyObjectId id) {
		super(id);
	}

	public Object getUtil() {
		return util;
	}

	public void setUtil(Object util) {
		this.util = util;
	}
}
