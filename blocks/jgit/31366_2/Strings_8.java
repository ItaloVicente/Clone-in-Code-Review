package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.*;

import java.util.*;

import org.eclipse.jgit.errors.InvalidPatternException;

public class PathMatcher extends AbstractMatcher {

	private static final WildMatcher WILD = WildMatcher.INSTANCE;
	private final List<IMatcher> matchers;
	private final char slash;

	PathMatcher(String pattern
		super(pattern
		slash = getPathSeparator(pathSeparator);
		matchers = createMatchers(split(pattern
	}

	static private List<IMatcher> createMatchers(List<String> segments
		List<IMatcher> matchers = new ArrayList<IMatcher>(segments.size());
		for (int i = 0; i < segments.size(); i++) {
			String segment = segments.get(i);
			IMatcher matcher = createNameMatcher0(segment
			if(matcher == WILD && i > 0 && matchers.get(matchers.size() - 1) == WILD){
				continue;
			}
			matchers.add(matcher);
		}
		return matchers;
	}

	public static IMatcher createPathMatcher(String pattern
		pattern = pattern.trim();
		char slash = Strings.getPathSeparator(pathSeparator);
		int slashIdx = pattern.indexOf(slash
		if(slashIdx > 0 && slashIdx < pattern.length() - 1){
			return new PathMatcher(pattern
		}
		return createNameMatcher0(pattern
	}

	private static IMatcher createNameMatcher0(String segment
		if(WildMatcher.WILDMATCH.equals(segment)) {
			return WILD;
		}
		if(isWildCard(segment)){
			return new WildCardMatcher(segment
		}
		return new NameMatcher(segment
	}

	public static IMatcher createNameMatcher(String segment
		return createNameMatcher0(segment
	}

	public boolean matches(String path
		return matches(path
	}

	public boolean matches(String segment
			boolean assumeDirectory) {
		return iterate(segment
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
			if(right == -1) {
				if(left < endExcl){
					match = matches(matcher
							assumeDirectory);
				}
				if(match){
					if(matcher == matchers.size() - 2 && matchers.get(matcher + 1) == WILD){
						return true;
					}
					if(matcher < matchers.size() - 1 && matchers.get(matcher) == WILD){
						matcher ++;
						match = matches(matcher
								assumeDirectory);
					} else
						if(dirOnly){
							return false;
						}
				}
				return match && matcher + 1 == matchers.size();
			}
			if(right - left > 0) {
				match = matches(matcher
			} else {
				right ++;
				continue;
			}
			if(match){
				if(matchers.get(matcher) == WILD){
					lastWildmatch = matcher;
					right = left - 1;
				}
				matcher ++;
				if (matcher == matchers.size()) {
					return true;
				}
			} else {
				if(lastWildmatch != -1){
					matcher = lastWildmatch + 1;
				} else {
					return false;
				}
			}
			right ++;
		}
	}

	boolean matches(int matcherIdx
			boolean assumeDirectory) {
		IMatcher matcher = matchers.get(matcherIdx);
		return matcher.matches(path
	}
}
