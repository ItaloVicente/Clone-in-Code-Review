
package org.eclipse.jgit.fnmatch;

import java.util.List;

import org.eclipse.jgit.internal.JGitText;

abstract class AbstractHead implements Head {
	private List<Head> newHeads = null;

	private final boolean star;

	protected abstract boolean matches(char c);

	AbstractHead(boolean star) {
		this.star = star;
	}

	public final void setNewHeads(List<Head> newHeads) {
		if (this.newHeads != null)
			throw new IllegalStateException(JGitText.get().propertyIsAlreadyNonNull);
		this.newHeads = newHeads;
	}

	@Override
	public List<Head> getNextHeads(char c) {
		if (matches(c))
			return newHeads;
		else
			return FileNameMatcher.EMPTY_HEAD_LIST;
	}

	boolean isStar() {
		return star;
	}
}
