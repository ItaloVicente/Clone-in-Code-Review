package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;
import org.eclipse.jgit.ignore.IgnoreRule;

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

	private FileNameMatcher matcher;

	public AttributesRule(String pattern
		this.attributes = parseAttributes(attributes);
		nameOnly = false;
		dirOnly = false;

		int endIndex = pattern.length();

			endIndex--;
			dirOnly = true;
		}

		String pattern2 = pattern.substring(0

		if (!hasSlash)
			nameOnly = true;
		}

			try {
				matcher = new FileNameMatcher(pattern2
			} catch (InvalidPatternException e) {
			}
		}

		this.pattern = pattern2;
	}

	public boolean dirOnly() {
		return dirOnly;
	}

	private boolean doesMatchDirectoryExpectations(boolean isDirectory
			int segmentIdx
		if (segmentIdx < segmentLength - 1) {
			return true;
		}

		return !dirOnly || isDirectory;
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
			matcher.append(target);
			if (matcher.isMatch())
				return true;

			if (nameOnly) {
				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					matcher.reset();
					matcher.append(segmentName);
					if (matcher.isMatch()
							&& doesMatchDirectoryExpectations(isDirectory
									segments.length))
						return true;
				}
			} else {
				matcher.reset();
				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					if (segmentName.length() > 0) {
					}

					if (matcher.isMatch()
							&& doesMatchDirectoryExpectations(isDirectory
									segments.length))
						return true;
				}
			}
		}

		return false;
	}
}
