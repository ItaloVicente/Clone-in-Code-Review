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
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		for (String attribute : attributesLine.split(ATTRIBUTES_SPLIT_REGEX)) {
			attribute = attribute.trim();
			if (attribute.length() == 0)
				continue;

				if (attribute.length() > 1)
					result.add(new Attribute(attribute.substring(1)
							State.UNSET));
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

	private boolean nameOnly;
	private boolean dirOnly;

	private IMatcher matcher;

	public AttributesRule(String pattern
		this.attributes = parseAttributes(attributes);
		nameOnly = false;
		dirOnly = false;

			pattern = pattern.substring(0
			dirOnly = true;
		}


		if (!hasSlash)
			nameOnly = true;
		}

		try {
			matcher = PathMatcher.createPathMatcher(pattern
					Character.valueOf(FastIgnoreRule.PATH_SEPARATOR)
		} catch (InvalidPatternException e) {
			matcher = NO_MATCH;
		}

		this.pattern = pattern;
	}

	public boolean dirOnly() {
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
}
