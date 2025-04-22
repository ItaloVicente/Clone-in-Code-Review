
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.JGitText;

public class UnmergedPathException extends IOException {
	private static final long serialVersionUID = 1L;

	private final DirCacheEntry entry;

	public UnmergedPathException(DirCacheEntry dce) {
		super(MessageFormat.format(JGitText.get().unmergedPath
		entry = dce;
	}

	public DirCacheEntry getDirCacheEntry() {
		return entry;
	}
}
