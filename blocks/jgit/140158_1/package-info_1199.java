
package org.eclipse.jgit.fnmatch;

final class WildCardHead extends AbstractHead {
	WildCardHead(boolean star) {
		super(star);
	}

	@Override
	protected final boolean matches(char c) {
		return true;
	}
}
