package org.eclipse.jgit.attributes;

import static org.eclipse.jgit.ignore.internal.IMatcher.NO_MATCH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.FastIgnoreRule;
import org.eclipse.jgit.ignore.internal.IMatcher;
import org.eclipse.jgit.ignore.internal.PathMatcher;

public class AttributesRule {


	private static List<Attribute> parseAttributes(String attributesLine) {
		ArrayList<Attribute> result = new ArrayList<>();
		for (String attribute : attributesLine.split(ATTRIBUTES_SPLIT_REGEX)) {
			attribute = attribute.trim();
			if (attribute.length() == 0)
				continue;

				if (attribute.length() > 1)
					result.add(new Attribute(attribute.substring(1)
							State.UNSET));
				continue;
			}

				if (attribute.length() > 1)
					result.add(new Attribute(attribute.substring(1)
							State.UNSPECIFIED));
				continue;
			}

			if (equalsIndex == -1)
				result.add(new Attribute(attribute
			else {
				String attributeKey = attribute.substring(0
				if (attributeKey.length() > 0) {
					String attributeValue = attribute
							.substring(equalsIndex + 1);
					result.add(new Attribute(attributeKey
				}
			}
		}
		return result;
	}

	private final String pattern;
	private final List<Attribute> attributes;

	private final boolean nameOnly;

	private final boolean dirOnly;

	private final IMatcher matcher;

	public AttributesRule(String pattern
		this.attributes = parseAttributes(attributes);

			pattern = pattern.substring(0
			dirOnly = true;
		} else {
			dirOnly = false;
		}

		int slashIndex = pattern.indexOf('/');

		if (slashIndex < 0) {
			nameOnly = true;
		} else if (slashIndex == 0) {
			nameOnly = false;
		} else {
			nameOnly = false;
		}

		IMatcher candidateMatcher = NO_MATCH;
		try {
			candidateMatcher = PathMatcher.createPathMatcher(pattern
					Character.valueOf(FastIgnoreRule.PATH_SEPARATOR)
		} catch (InvalidPatternException e) {
		}
		this.matcher = candidateMatcher;
		this.pattern = pattern;
	}

	public boolean isDirOnly() {
		return dirOnly;
	}

	public List<Attribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	public boolean isNameOnly() {
		return nameOnly;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isMatch(String relativeTarget
		if (relativeTarget == null)
			return false;
		if (relativeTarget.length() == 0)
			return false;
		boolean match = matcher.matches(relativeTarget
		return match;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(pattern);
		for (Attribute a : attributes) {
			sb.append(a);
		}
		return sb.toString();

	}
}
