package org.eclipse.egit.ui.internal.history;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RenameCallback;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

class RenameTracker {

	private final RevFilter filter = new RevFilter() {

		public boolean include(final RevWalk walker, final RevCommit commit)
				throws IOException {
			if (currentPath != null)
				renames.put(commit, currentPath);
			else if (currentDiff != null) {
				renames.put(commit, currentDiff.getNewPath());
				currentPath = currentDiff.getOldPath();
				currentDiff = null;
			}
			return true;
		}

		public RevFilter clone() {
			return null;
		}
	};

	private final RenameCallback callback = new RenameCallback() {

		public void renamed(final DiffEntry entry) {
			currentDiff = entry;
			currentPath = null;
		}
	};

	private String initialPath;

	private DiffEntry currentDiff;

	private String currentPath;

	private Map<RevCommit, String> renames = new LinkedHashMap<RevCommit, String>();

	public RevFilter getFilter() {
		return filter;
	}

	public RenameCallback getCallback() {
		return callback;
	}

	public String getPath(final ObjectId target, final String startingPath) {
		if (!startingPath.equals(initialPath))
			return startingPath;
		String renamed = renames.get(target);
		return renamed != null ? renamed : startingPath;
	}

	public RenameTracker reset(final String path) {
		renames.clear();
		initialPath = path;
		currentPath = path;
		return this;
	}
}
