package org.eclipse.jgit.fnmatch2;

import static org.eclipse.jgit.fnmatch2.Strings.getPathSeparator;

public class NameMatcher extends AbstractMatcher {

	final boolean beginning;
	final char slash;
	final String subPattern;

	NameMatcher(String pattern
		super(pattern
		slash = getPathSeparator(pathSeparator);
		beginning = pattern.length() == 0 ? false : pattern.charAt(0) == slash;
		if(!beginning) {
			this.subPattern = pattern;
		} else {
			this.subPattern = pattern.substring(1
		}
	}

	public boolean matches(String path
		int end = 0;
		int firstChar = 0;
		do {
			firstChar = getFirstNotSlash(path
			end = getFirstSlash(path
			boolean match = matches(path
			if(match){
				boolean isDirMatch = (end > 0 && end != path.length())
						|| assumeDirectory;
				return dirOnly ? isDirMatch : true;
			}
		}
		while(!beginning && end != path.length());
		return false;
	}

	public boolean matches(String segment
			boolean assumeDirectory) {
		if(subPattern.length() != (endExcl - startIncl)){
			return false;
		}
		int i = 0;
		for (; i < subPattern.length(); i++) {
			char c1 = subPattern.charAt(i);
			char c2 = segment.charAt(i + startIncl);
			if(c1 != c2){
				return false;
			}
		}
		return true;
	}

	private int getFirstNotSlash(String s
		int slashIdx = s.indexOf(slash
		return slashIdx == start? start + 1 : start;
	}

	private int getFirstSlash(String s
		int slashIdx = s.indexOf(slash
		return slashIdx == -1? s.length() : slashIdx;
	}

}
