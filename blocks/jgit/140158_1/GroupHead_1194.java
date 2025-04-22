
package org.eclipse.jgit.fnmatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.errors.NoClosingBracketException;

public class FileNameMatcher {
	static final List<Head> EMPTY_HEAD_LIST = Collections.emptyList();

	private static final Pattern characterClassStartPattern = Pattern

	private List<Head> headsStartValue;

	private List<Head> heads;

	private List<Head> listForLocalUseage;

	private FileNameMatcher(List<Head> headsStartValue) {
		this(headsStartValue
	}

	private FileNameMatcher(final List<Head> headsStartValue
			final List<Head> heads) {
		this.headsStartValue = headsStartValue;
		this.heads = new ArrayList<>(heads.size());
		this.heads.addAll(heads);
		this.listForLocalUseage = new ArrayList<>(heads.size());
	}

	public FileNameMatcher(final String patternString
			final Character invalidWildgetCharacter)
			throws InvalidPatternException {
		this(createHeadsStartValues(patternString
	}

	public FileNameMatcher(FileNameMatcher other) {
		this(other.headsStartValue
	}

	private static List<Head> createHeadsStartValues(
			final String patternString
			throws InvalidPatternException {

		final List<AbstractHead> allHeads = parseHeads(patternString
				invalidWildgetCharacter);

		List<Head> nextHeadsSuggestion = new ArrayList<>(2);
		nextHeadsSuggestion.add(LastHead.INSTANCE);
		for (int i = allHeads.size() - 1; i >= 0; i--) {
			final AbstractHead head = allHeads.get(i);

			if (head.isStar()) {
				nextHeadsSuggestion.add(head);
				head.setNewHeads(nextHeadsSuggestion);
			} else {
				head.setNewHeads(nextHeadsSuggestion);
				nextHeadsSuggestion = new ArrayList<>(2);
				nextHeadsSuggestion.add(head);
			}
		}
		return nextHeadsSuggestion;
	}

	private static int findGroupEnd(final int indexOfStartBracket
			final String pattern) throws InvalidPatternException {
		int firstValidCharClassIndex = indexOfStartBracket + 1;
		int firstValidEndBracketIndex = indexOfStartBracket + 2;

		if (indexOfStartBracket + 1 >= pattern.length())
			throw new NoClosingBracketException(indexOfStartBracket
					pattern);

		if (pattern.charAt(firstValidCharClassIndex) == '!') {
			firstValidCharClassIndex++;
			firstValidEndBracketIndex++;
		}

		final Matcher charClassStartMatcher = characterClassStartPattern
				.matcher(pattern);

		int groupEnd = -1;
		while (groupEnd == -1) {

			final int possibleGroupEnd = indexOfUnescaped(pattern
					firstValidEndBracketIndex);
			if (possibleGroupEnd == -1)
				throw new NoClosingBracketException(indexOfStartBracket
						"]"

			final boolean foundCharClass = charClassStartMatcher
					.find(firstValidCharClassIndex);

			if (foundCharClass
					&& charClassStartMatcher.start() < possibleGroupEnd) {

				final String classStart = charClassStartMatcher.group(0);

				final int classStartIndex = charClassStartMatcher.start();
				final int classEndIndex = pattern.indexOf(classEnd
						classStartIndex + 2);

				if (classEndIndex == -1)
					throw new NoClosingBracketException(classStartIndex
							classStart

				firstValidCharClassIndex = classEndIndex + 2;
				firstValidEndBracketIndex = firstValidCharClassIndex;
			} else {
				groupEnd = possibleGroupEnd;
			}
		}
		return groupEnd;
	}

	private static List<AbstractHead> parseHeads(final String pattern
			final Character invalidWildgetCharacter)
			throws InvalidPatternException {

		int currentIndex = 0;
		List<AbstractHead> heads = new ArrayList<>();
		while (currentIndex < pattern.length()) {
			final int groupStart = indexOfUnescaped(pattern
			if (groupStart == -1) {
				final String patternPart = pattern.substring(currentIndex);
				heads.addAll(createSimpleHeads(patternPart
						invalidWildgetCharacter));
				currentIndex = pattern.length();
			} else {
				final String patternPart = pattern.substring(currentIndex
						groupStart);
				heads.addAll(createSimpleHeads(patternPart
						invalidWildgetCharacter));

				final int groupEnd = findGroupEnd(groupStart
				final String groupPart = pattern.substring(groupStart + 1
						groupEnd);
				heads.add(new GroupHead(groupPart
				currentIndex = groupEnd + 1;
			}
		}
		return heads;
	}

	private static List<AbstractHead> createSimpleHeads(
			final String patternPart
		final List<AbstractHead> heads = new ArrayList<>(
				patternPart.length());

		boolean escaped = false;
		for (int i = 0; i < patternPart.length(); i++) {
			final char c = patternPart.charAt(i);
			if (escaped) {
				final CharacterHead head = new CharacterHead(c);
				heads.add(head);
				escaped = false;
			} else {
				switch (c) {
				case '*': {
					final AbstractHead head = createWildCardHead(
							invalidWildgetCharacter
					heads.add(head);
					break;
				}
				case '?': {
					final AbstractHead head = createWildCardHead(
							invalidWildgetCharacter
					heads.add(head);
					break;
				}
				case '\\':
					escaped = true;
					break;
				default:
					final CharacterHead head = new CharacterHead(c);
					heads.add(head);
				}
			}
		}
		return heads;
	}

	private static AbstractHead createWildCardHead(
			final Character invalidWildgetCharacter
		if (invalidWildgetCharacter != null)
			return new RestrictedWildCardHead(invalidWildgetCharacter
					.charValue()
		else
			return new WildCardHead(star);
	}

	private boolean extendStringToMatchByOneCharacter(char c) {
		final List<Head> newHeads = listForLocalUseage;
		newHeads.clear();
		List<Head> lastAddedHeads = null;
		for (int i = 0; i < heads.size(); i++) {
			final Head head = heads.get(i);
			final List<Head> headsToAdd = head.getNextHeads(c);
			if (headsToAdd != lastAddedHeads) {
				if (!headsToAdd.isEmpty())
					newHeads.addAll(headsToAdd);
				lastAddedHeads = headsToAdd;
			}
		}
		listForLocalUseage = heads;
		heads = newHeads;
		return !newHeads.isEmpty();
	}

	private static int indexOfUnescaped(final String searchString
			final char ch
		for (int i = fromIndex; i < searchString.length(); i++) {
			char current = searchString.charAt(i);
			if (current == ch)
				return i;
			if (current == '\\')
		}
		return -1;
	}

	public void append(String stringToMatch) {
		for (int i = 0; i < stringToMatch.length(); i++) {
			final char c = stringToMatch.charAt(i);
			if (!extendStringToMatchByOneCharacter(c))
				break;
		}
	}

	public void reset() {
		heads.clear();
		heads.addAll(headsStartValue);
	}

	public FileNameMatcher createMatcherForSuffix() {
		final List<Head> copyOfHeads = new ArrayList<>(heads.size());
		copyOfHeads.addAll(heads);
		return new FileNameMatcher(copyOfHeads);
	}

	public boolean isMatch() {
		if (heads.isEmpty())
			return false;

		final ListIterator<Head> headIterator = heads
				.listIterator(heads.size());
		while (headIterator.hasPrevious()) {
			final Head head = headIterator.previous();
			if (head == LastHead.INSTANCE) {
				return true;
			}
		}
		return false;
	}

	public boolean canAppendMatch() {
		for (int i = 0; i < heads.size(); i++) {
			if (heads.get(i) != LastHead.INSTANCE) {
				return true;
			}
		}
		return false;
	}
}
