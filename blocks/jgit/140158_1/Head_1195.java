
package org.eclipse.jgit.fnmatch;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.internal.JGitText;

final class GroupHead extends AbstractHead {
	private final List<CharacterPattern> characterClasses;

	private static final Pattern REGEX_PATTERN = Pattern

	private final boolean inverse;

	GroupHead(String pattern
			throws InvalidPatternException {
		super(false);
		this.characterClasses = new ArrayList<>();
		if (inverse) {
			pattern = pattern.substring(1);
		}
		final Matcher matcher = REGEX_PATTERN.matcher(pattern);
		while (matcher.find()) {
			final String characterClass = matcher.group(0);
			if (characterClass.length() == 3 && characterClass.charAt(1) == '-') {
				final char start = characterClass.charAt(0);
				final char end = characterClass.charAt(2);
				characterClasses.add(new CharacterRange(start
				characterClasses.add(LetterPattern.INSTANCE);
				characterClasses.add(DigitPattern.INSTANCE);
				characterClasses.add(LetterPattern.INSTANCE);
				characterClasses.add(new OneCharacterPattern(' '));
				characterClasses.add(new OneCharacterPattern('\t'));
				characterClasses.add(new CharacterRange('\u0000'
				characterClasses.add(new OneCharacterPattern('\u007F'));
				characterClasses.add(DigitPattern.INSTANCE);
				characterClasses.add(new CharacterRange('\u0021'
				characterClasses.add(LetterPattern.INSTANCE);
				characterClasses.add(DigitPattern.INSTANCE);
				characterClasses.add(LowerPattern.INSTANCE);
				characterClasses.add(new CharacterRange('\u0020'
				characterClasses.add(LetterPattern.INSTANCE);
				characterClasses.add(DigitPattern.INSTANCE);
				characterClasses.add(PunctPattern.INSTANCE);
				characterClasses.add(WhitespacePattern.INSTANCE);
				characterClasses.add(UpperPattern.INSTANCE);
				characterClasses.add(new CharacterRange('0'
				characterClasses.add(new CharacterRange('a'
				characterClasses.add(new CharacterRange('A'
				characterClasses.add(new OneCharacterPattern('_'));
				characterClasses.add(LetterPattern.INSTANCE);
				characterClasses.add(DigitPattern.INSTANCE);
			} else {
				final String message = MessageFormat.format(
						JGitText.get().characterClassIsNotSupported
						characterClass);
				throw new InvalidPatternException(message
			}

			matcher.reset(pattern);
		}
		for (int i = 0; i < pattern.length(); i++) {
			final char c = pattern.charAt(i);
			characterClasses.add(new OneCharacterPattern(c));
		}
	}

	@Override
	protected final boolean matches(char c) {
		for (CharacterPattern pattern : characterClasses) {
			if (pattern.matches(c)) {
				return !inverse;
			}
		}
		return inverse;
	}

	private interface CharacterPattern {
		boolean matches(char c);
	}

	private static final class CharacterRange implements CharacterPattern {
		private final char start;

		private final char end;

		CharacterRange(char start
			this.start = start;
			this.end = end;
		}

		@Override
		public final boolean matches(char c) {
			return start <= c && c <= end;
		}
	}

	private static final class DigitPattern implements CharacterPattern {
		static final GroupHead.DigitPattern INSTANCE = new DigitPattern();

		@Override
		public final boolean matches(char c) {
			return Character.isDigit(c);
		}
	}

	private static final class LetterPattern implements CharacterPattern {
		static final GroupHead.LetterPattern INSTANCE = new LetterPattern();

		@Override
		public final boolean matches(char c) {
			return Character.isLetter(c);
		}
	}

	private static final class LowerPattern implements CharacterPattern {
		static final GroupHead.LowerPattern INSTANCE = new LowerPattern();

		@Override
		public final boolean matches(char c) {
			return Character.isLowerCase(c);
		}
	}

	private static final class UpperPattern implements CharacterPattern {
		static final GroupHead.UpperPattern INSTANCE = new UpperPattern();

		@Override
		public final boolean matches(char c) {
			return Character.isUpperCase(c);
		}
	}

	private static final class WhitespacePattern implements CharacterPattern {
		static final GroupHead.WhitespacePattern INSTANCE = new WhitespacePattern();

		@Override
		public final boolean matches(char c) {
			return Character.isWhitespace(c);
		}
	}

	private static final class OneCharacterPattern implements CharacterPattern {
		private char expectedCharacter;

		OneCharacterPattern(char c) {
			this.expectedCharacter = c;
		}

		@Override
		public final boolean matches(char c) {
			return this.expectedCharacter == c;
		}
	}

	private static final class PunctPattern implements CharacterPattern {
		static final GroupHead.PunctPattern INSTANCE = new PunctPattern();

		private static String punctCharacters = "-!\"#$%&'()*+

		@Override
		public boolean matches(char c) {
			return punctCharacters.indexOf(c) != -1;
		}
	}

}
