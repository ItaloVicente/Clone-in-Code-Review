package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class NotIgnoredFilter extends TreeFilter {
	private final int index;

	public NotIgnoredFilter(int workdirTreeIndex) {
		this.index = workdirTreeIndex;
	}

	@Override
	public boolean include(TreeWalk tw) throws MissingObjectException
			IncorrectObjectTypeException
		WorkingTreeIterator i = tw.getTree(index
		return i == null || !i.isEntryIgnored();
	}

	@Override
	public boolean shouldBeRecursive() {
		return false;
	}

	@Override
	public TreeFilter clone() {
		return this;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "NotIgnored(" + index + ")";
	}
}
