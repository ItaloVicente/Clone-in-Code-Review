
package org.eclipse.jgit.transport;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public enum TagOpt {
	AUTO_FOLLOW("")

	NO_TAGS("--no-tags")


	private final String option;

	private TagOpt(String o) {
		option = o;
	}

	public String option() {
		return option;
	}

	public static TagOpt fromOption(String o) {
		if (o == null || o.length() == 0)
			return AUTO_FOLLOW;
		for (TagOpt tagopt : values()) {
			if (tagopt.option().equals(o))
				return tagopt;
		}
		throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidTagOption
	}
}
