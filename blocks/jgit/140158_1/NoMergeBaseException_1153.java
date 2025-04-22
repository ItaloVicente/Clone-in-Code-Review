
package org.eclipse.jgit.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class NoClosingBracketException extends InvalidPatternException {
	private static final long serialVersionUID = 1L;

	public NoClosingBracketException(final int indexOfOpeningBracket
			final String openingBracket
			final String pattern) {
		super(createMessage(indexOfOpeningBracket
				closingBracket)
	}

	private static String createMessage(final int indexOfOpeningBracket
			final String openingBracket
		return MessageFormat.format(JGitText.get().noClosingBracket
				closingBracket
				Integer.valueOf(indexOfOpeningBracket));
	}
}
