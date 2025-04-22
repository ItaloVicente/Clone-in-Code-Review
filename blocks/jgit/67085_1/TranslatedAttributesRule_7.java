package org.eclipse.jgit.attributes;

import java.util.List;

public class TranslatedAttributesRule {

	private final String ruleDeclarationPath;

	private final AttributesRule rule;

	public TranslatedAttributesRule(String ruleDeclarationPath
			AttributesRule rule) {
		this.ruleDeclarationPath = ruleDeclarationPath;
		this.rule = rule;
	}

	public List<Attribute> getAttributes() {
		return rule.getAttributes();
	}

	public boolean isMatch(String entryPath
		if (entryPath == null)
			return false;
		if (entryPath.length() == 0)
			return false;
		if (!entryPath.startsWith(ruleDeclarationPath)) {
			return false;
		}
		String relativeTarget = entryPath
				.substring(ruleDeclarationPath.length());
		if (relativeTarget.length() == 0 || ruleDeclarationPath.length() == 0) {
			return rule.isMatch(relativeTarget
		}
		if (relativeTarget.charAt(0) == '/') {
			return rule.isMatch(relativeTarget.substring(1)
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ruleDeclarationPath);
		sb.append(rule);
		return sb.toString();

	}
}
