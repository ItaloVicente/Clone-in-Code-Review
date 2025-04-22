package org.eclipse.jgit.attributes;

import static org.eclipse.jgit.ignore.internal.IMatcher.NO_MATCH;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.FastIgnoreRule;
import org.eclipse.jgit.ignore.internal.IMatcher;
import org.eclipse.jgit.ignore.internal.PathMatcher;

public class AttributesRule {


	private static List<Attribute> parseAttributes(String attributesLine) {
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		for (String attribute : attributesLine.split(ATTRIBUTES_SPLIT_REGEX)) {
			attribute = attribute.trim();
			if (attribute.length() == 0)
				continue;

				result.add(new Attribute(attribute.substring(1)
				continue;
			}

			if (equalsIndex == -1)
				result.add(new Attribute(attribute
			else
				result.add(new Attribute(attribute.substring(0
						attribute.substring(equalsIndex + 1)));
		}
		return result;
	}


	private final String pattern;
	private final List<Attribute> attributes;

	private boolean nameOnly;
	private boolean dirOnly;

	private IMatcher matcher;

	public AttributesRule(String pattern
		this.attributes = parseAttributes(attributes);
		nameOnly = false;
		dirOnly = false;
		if (pattern.charAt(0) == '!') {
			this.pattern = pattern;
			this.matcher = NO_MATCH;
			return;
		}

		int endIndex = pattern.length();

			endIndex--;
			dirOnly = true;
		}

		String pattern2 = pattern.substring(0

		if (!hasSlash)
			nameOnly = true;
		}

			try {
				matcher = PathMatcher.createPathMatcher(pattern2
						Character.valueOf(FastIgnoreRule.PATH_SEPARATOR)
						dirOnly);
			} catch (InvalidPatternException e) {
			}
		}

		this.pattern = pattern2;
	}

	public boolean dirOnly() {
		return dirOnly;
	}

	private boolean doesMatchDirectoryExpectations(boolean isDirectory
			boolean lastSegment) {
		if (!lastSegment) {
			return true;
		}

		return !dirOnly || isDirectory;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public boolean isNameOnly() {
		return nameOnly;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isMatch(String relativeTarget
		String target = relativeTarget;

		if (matcher == null) {
			if (target.equals(pattern)) {
				if (dirOnly && !isDirectory)
					return false;
				else
					return true;
			}

				return true;

			if (nameOnly) {
				int segmentStart = 0;
				int segmentEnd = target.indexOf('/');
				while (true) {
					if (segmentEnd > segmentStart
							&& target.regionMatches(segmentStart
									segmentEnd - segmentStart)
							&& doesMatchDirectoryExpectations(isDirectory
						return true;
					else if (segmentEnd == -1
							&& target.regionMatches(segmentStart
									target.length() - segmentStart)
							&& doesMatchDirectoryExpectations(isDirectory
						return true;

					if (segmentEnd == -1 || segmentStart >= target.length())
						break;

					segmentStart = segmentEnd + 1;
					segmentEnd = target.indexOf('/'
				}
			}

		} else {
			return matcher.matches(target
		}

		return false;
	}
}
