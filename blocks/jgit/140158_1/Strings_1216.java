package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.checkWildCards;
import static org.eclipse.jgit.ignore.internal.Strings.count;
import static org.eclipse.jgit.ignore.internal.Strings.getPathSeparator;
import static org.eclipse.jgit.ignore.internal.Strings.isWildCard;
import static org.eclipse.jgit.ignore.internal.Strings.split;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.internal.Strings.PatternState;

public class PathMatcher extends AbstractMatcher {

	private static final WildMatcher WILD_NO_DIRECTORY = new WildMatcher(false);

	private static final WildMatcher WILD_ONLY_DIRECTORY = new WildMatcher(
			true);

	private final List<IMatcher> matchers;

	private final char slash;

	private final boolean beginning;

	private PathMatcher(String pattern
			boolean dirOnly)
			throws InvalidPatternException {
		super(pattern
		slash = getPathSeparator(pathSeparator);
		beginning = pattern.indexOf(slash) == 0;
		if (isSimplePathWithSegments(pattern))
			matchers = null;
		else
			matchers = createMatchers(split(pattern
					dirOnly);
	}

	private boolean isSimplePathWithSegments(String path) {
		return !isWildCard(path) && path.indexOf('\\') < 0
				&& count(path
	}

	private static List<IMatcher> createMatchers(List<String> segments
			Character pathSeparator
			throws InvalidPatternException {
		List<IMatcher> matchers = new ArrayList<>(segments.size());
		for (int i = 0; i < segments.size(); i++) {
			String segment = segments.get(i);
			IMatcher matcher = createNameMatcher0(segment
					dirOnly
			if (i > 0) {
				final IMatcher last = matchers.get(matchers.size() - 1);
				if (isWild(matcher) && isWild(last))
					matchers.remove(matchers.size() - 1);
			}

			matchers.add(matcher);
		}
		return matchers;
	}

	public static IMatcher createPathMatcher(String pattern
			Character pathSeparator
			throws InvalidPatternException {
		pattern = trim(pattern);
		char slash = Strings.getPathSeparator(pathSeparator);
		int slashIdx = pattern.indexOf(slash
		if (slashIdx > 0 && slashIdx < pattern.length() - 1)
			return new PathMatcher(pattern
		return createNameMatcher0(pattern
	}

	private static String trim(String pattern) {
		while (pattern.length() > 0
				&& pattern.charAt(pattern.length() - 1) == ' ') {
			if (pattern.length() > 1
					&& pattern.charAt(pattern.length() - 2) == '\\') {
				pattern = pattern.substring(0
				return pattern;
			}
			pattern = pattern.substring(0
		}
		return pattern;
	}

	private static IMatcher createNameMatcher0(String segment
			Character pathSeparator
			throws InvalidPatternException {
		if (WildMatcher.WILDMATCH.equals(segment)
				|| WildMatcher.WILDMATCH2.equals(segment))
			return dirOnly && lastSegment ? WILD_ONLY_DIRECTORY
					: WILD_NO_DIRECTORY;

		PatternState state = checkWildCards(segment);
		switch (state) {
		case LEADING_ASTERISK_ONLY:
			return new LeadingAsteriskMatcher(segment
		case TRAILING_ASTERISK_ONLY:
			return new TrailingAsteriskMatcher(segment
		case COMPLEX:
			return new WildCardMatcher(segment
		default:
			return new NameMatcher(segment
		}
	}

	@Override
	public boolean matches(String path
			boolean pathMatch) {
		if (matchers == null) {
			return simpleMatch(path
		}
		return iterate(path
	}

	private boolean simpleMatch(String path
			boolean pathMatch) {
		boolean hasSlash = path.indexOf(slash) == 0;
		if (beginning && !hasSlash) {
			path = slash + path;
		}
		if (!beginning && hasSlash) {
			path = path.substring(1);
		}
		if (path.equals(pattern)) {
			return !dirOnly || assumeDirectory;
		}
		String prefix = pattern + slash;
		if (pathMatch) {
			return path.equals(prefix) && (!dirOnly || assumeDirectory);
		}
		if (path.startsWith(prefix)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean matches(String segment
		throw new UnsupportedOperationException(
	}

	private boolean iterate(final String path
			final int endExcl
		int matcher = 0;
		int right = startIncl;
		boolean match = false;
		int lastWildmatch = -1;
		int wildmatchBacktrackPos = -1;
		while (true) {
			int left = right;
			right = path.indexOf(slash
			if (right == -1) {
				if (left < endExcl) {
					match = matches(matcher
							assumeDirectory
				} else {
					match = match && !isWild(matchers.get(matcher));
				}
				if (match) {
					if (matcher < matchers.size() - 1
							&& isWild(matchers.get(matcher))) {
						matcher++;
						match = matches(matcher
								assumeDirectory
					} else if (dirOnly && !assumeDirectory) {
						return false;
					}
				}
				return match && matcher + 1 == matchers.size();
			}
			if (wildmatchBacktrackPos < 0) {
				wildmatchBacktrackPos = right;
			}
			if (right - left > 0) {
				match = matches(matcher
						pathMatch);
			} else {
				right++;
				continue;
			}
			if (match) {
				boolean wasWild = isWild(matchers.get(matcher));
				if (wasWild) {
					lastWildmatch = matcher;
					wildmatchBacktrackPos = -1;
					right = left - 1;
				}
				matcher++;
				if (matcher == matchers.size()) {
					if (!pathMatch) {
						return true;
					} else {
						if (right == endExcl - 1) {
							return !dirOnly || assumeDirectory;
						}
						if (wasWild) {
							return true;
						}
						if (lastWildmatch >= 0) {
							matcher = lastWildmatch + 1;
							right = wildmatchBacktrackPos;
							wildmatchBacktrackPos = -1;
						} else {
							return false;
						}
					}
				}
			} else if (lastWildmatch != -1) {
				matcher = lastWildmatch + 1;
				right = wildmatchBacktrackPos;
				wildmatchBacktrackPos = -1;
			} else {
				return false;
			}
			right++;
		}
	}

	private boolean matches(int matcherIdx
			int endExcl
		IMatcher matcher = matchers.get(matcherIdx);

		final boolean matches = matcher.matches(path
		if (!matches || !pathMatch || matcherIdx < matchers.size() - 1
				|| !(matcher instanceof AbstractMatcher)) {
			return matches;
		}

		return assumeDirectory || !((AbstractMatcher) matcher).dirOnly;
	}

	private static boolean isWild(IMatcher matcher) {
		return matcher == WILD_NO_DIRECTORY || matcher == WILD_ONLY_DIRECTORY;
	}
}
