
package org.eclipse.jgit.transport;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class Depth {

	public static int DEPTH_INFINITE = 0x7fffffff;

	private int depth;

	public Depth(final int depth) {
		setDepth(depth);
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(final int depth) {
		if ((depth <= 0) || (depth > DEPTH_INFINITE)) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidDepth
		}
		this.depth = depth;
	}

	public static boolean isSet(final Depth depth) {
		if (depth == null) {
			return false;
		}
		if (depth.depth != DEPTH_INFINITE) {
			return true;
		}
		return false;
	}

}
