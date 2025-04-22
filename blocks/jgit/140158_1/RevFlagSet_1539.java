
package org.eclipse.jgit.revwalk;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class RevFlag {
	public static final RevFlag UNINTERESTING = new StaticRevFlag(
			"UNINTERESTING"

	public static final RevFlag SEEN = new StaticRevFlag("SEEN"

	final RevWalk walker;

	final String name;

	final int mask;

	RevFlag(RevWalk w
		walker = w;
		name = n;
		mask = m;
	}

	public RevWalk getRevWalk() {
		return walker;
	}

	@Override
	public String toString() {
		return name;
	}

	static class StaticRevFlag extends RevFlag {
		StaticRevFlag(String n
			super(null
		}

		@Override
		public RevWalk getRevWalk() {
			throw new UnsupportedOperationException(MessageFormat.format(
					JGitText.get().isAStaticFlagAndHasNorevWalkInstance
		}
	}
}
