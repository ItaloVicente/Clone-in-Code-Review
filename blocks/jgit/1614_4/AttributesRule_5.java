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

	private String pattern;

	private List<Attribute> attributes;

	private boolean nameOnly;

	private boolean dirOnly;

	private FileNameMatcher matcher;

	public AttributesRule(String pattern
		this.pattern = pattern;
		this.attributes = parseAttributes(attributes);
		nameOnly = false;
		dirOnly = false;
		matcher = null;
		setup();
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

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public boolean getNameOnly() {
		return nameOnly;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isMatch(String relativTarget
		String target = relativTarget;

		if (matcher == null) {
			if (target.equals(pattern)) {
				if (dirOnly && !isDirectory)
					return false;
				else
					return true;
			}

				return true;

			if (nameOnly) {
				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					if (segmentName.equals(pattern)
							&& doesMatchDirectoryExpectations(isDirectory
									segments.length))
						return true;
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

	private void setup() {
		int endIndex = pattern.length();

			endIndex--;
			dirOnly = true;
		}

		pattern = pattern.substring(0

		if (!hasSlash)
			nameOnly = true;
		}

			try {
				matcher = new FileNameMatcher(pattern
			} catch (InvalidPatternException e) {
			}
		}
	}
}
