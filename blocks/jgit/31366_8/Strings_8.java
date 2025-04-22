package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.*;

import java.util.*;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.FastIgnoreRule;

public class PathMatcher extends AbstractMatcher {

	private static final WildMatcher WILD = WildMatcher.INSTANCE;

	private final List<IMatcher> matchers;

	private final char slash;

	private boolean beginning;

	PathMatcher(String pattern
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
		return !isWildCard(path) && count(path
	}

	static private List<IMatcher> createMatchers(List<String> segments
			Character pathSeparator
			throws InvalidPatternException {
		List<IMatcher> matchers = new ArrayList<IMatcher>(segments.size());
		for (int i = 0; i < segments.size(); i++) {
			String segment = segments.get(i);
			IMatcher matcher = createNameMatcher0(segment
					dirOnly);
			if (matcher == WILD && i > 0
					&& matchers.get(matchers.size() - 1) == WILD)
				continue;
			matchers.add(matcher);
		}
		return matchers;
	}

	public static IMatcher createPathMatcher(String pattern
			Character pathSeparator
			throws InvalidPatternException {
		pattern = pattern.trim();
		char slash = Strings.getPathSeparator(pathSeparator);
		int slashIdx = pattern.indexOf(slash
		if (slashIdx > 0 && slashIdx < pattern.length() - 1)
			return new PathMatcher(pattern
		return createNameMatcher0(pattern
	}

	private static IMatcher createNameMatcher0(String segment
			Character pathSeparator
			throws InvalidPatternException {
		if (WildMatcher.WILDMATCH.equals(segment)
				|| WildMatcher.WILDMATCH2.equals(segment))
			return WILD;
		if (isWildCard(segment))
			return new WildCardMatcher(segment
		return new NameMatcher(segment
	}

	public boolean matches(String path
		if (matchers == null)
			return simpleMatch(path
		return iterate(path
	}

	private boolean simpleMatch(String path
		boolean hasSlash = path.indexOf(slash) == 0;
		if (beginning && !hasSlash)
			path = slash + path;

		if (!beginning && hasSlash)
			path = path.substring(1);

		if (path.equals(pattern))
			if (dirOnly && !assumeDirectory)
				return false;
			else
				return true;

		if (path.startsWith(pattern + FastIgnoreRule.PATH_SEPARATOR))
			return true;

		return false;
	}

	public boolean matches(String segment
			boolean assumeDirectory) {
		throw new UnsupportedOperationException(
	}

	boolean iterate(final String path
			boolean assumeDirectory) {
		int matcher = 0;
		int right = startIncl;
		boolean match = false;
		int lastWildmatch = -1;
		while (true) {
			int left = right;
			right = path.indexOf(slash
			if (right == -1) {
				if (left < endExcl)
					match = matches(matcher
							assumeDirectory);
				if (match) {
					if (matcher == matchers.size() - 2
							&& matchers.get(matcher + 1) == WILD)
						return true;
					if (matcher < matchers.size() - 1
							&& matchers.get(matcher) == WILD) {
						matcher++;
						match = matches(matcher
								assumeDirectory);
					} else if (dirOnly)
						return false;
				}
				return match && matcher + 1 == matchers.size();
			}
			if (right - left > 0)
				match = matches(matcher
			else {
				right++;
				continue;
			}
			if (match) {
				if (matchers.get(matcher) == WILD) {
					lastWildmatch = matcher;
					right = left - 1;
				}
				matcher++;
				if (matcher == matchers.size())
					return true;
			} else if (lastWildmatch != -1)
				matcher = lastWildmatch + 1;
			else
				return false;
			right++;
		}
	}

	boolean matches(int matcherIdx
			boolean assumeDirectory) {
		IMatcher matcher = matchers.get(matcherIdx);
		return matcher.matches(path
	}
}
