package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.diff.DiffEntry;

public abstract class RenameCallback {
	public abstract void renamed(DiffEntry entry);
}
