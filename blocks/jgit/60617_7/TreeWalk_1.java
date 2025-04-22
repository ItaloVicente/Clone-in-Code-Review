package org.eclipse.jgit.attributes;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attribute.State;

public class MacroExpander {


	private static final List<Attribute> BINARY_RULE_ATTRIBUTES = new AttributesRule(
			MACRO_PREFIX + BINARY_RULE_KEY
					.getAttributes();

	private final AttributesNode globalNode;

	private final AttributesNode infoNode;

	private final Map<String

	public MacroExpander() {
		expansions.put(BINARY_RULE_KEY
		globalNode = null;
		infoNode = null;
	}

	public MacroExpander(@Nullable AttributesNode globalNode
			@Nullable AttributesNode infoNode
			@Nullable AttributesNode rootNode) {
		this.globalNode = globalNode;
		this.infoNode = infoNode;
		expansions.put(BINARY_RULE_KEY
		for (AttributesNode node : new AttributesNode[] { globalNode
				infoNode }) {
			if (node == null) {
				continue;
			}
			for (AttributesRule rule : node.getRules()) {
				if (rule.getPattern().startsWith(MACRO_PREFIX)) {
					expansions.put(rule.getPattern()
							.substring(MACRO_PREFIX.length()).trim()
							rule.getAttributes());
				}
			}
		}
	}

	public void mergeGlobalAttributes(String entryPath
			Attributes result) {
		mergeAttributes(globalNode
	}

	public void mergeInfoAttributes(String entryPath
			Attributes result) {
		mergeAttributes(infoNode
	}

	public void mergeAttributes(@Nullable AttributesNode node
			boolean isDirectory
		if (node == null)
			return;
		List<AttributesRule> rules = node.getRules();
		ListIterator<AttributesRule> ruleIterator = rules
				.listIterator(rules.size());
		while (ruleIterator.hasPrevious()) {
			AttributesRule rule = ruleIterator.previous();
			if (rule.isMatch(entryPath
				ListIterator<Attribute> attributeIte = rule.getAttributes()
						.listIterator(rule.getAttributes().size());
				while (attributeIte.hasPrevious()) {
					expandMacro(attributeIte.previous()
				}
			}
		}
	}

	protected void expandMacro(Attribute attr
		if (result.containsKey(attr.getKey()))
			return;

		result.put(attr);

		List<Attribute> expansion = expansions.get(attr.getKey());
		if (expansion == null) {
			return;
		}
		switch (attr.getState()) {
		case UNSET: {
			for (Attribute e : expansion) {
				switch (e.getState()) {
				case SET:
					expandMacro(new Attribute(e.getKey()
					break;
				case UNSET:
					expandMacro(new Attribute(e.getKey()
					break;
				case UNSPECIFIED:
					expandMacro(new Attribute(e.getKey()
							result);
					break;
				case CUSTOM:
				default:
					expandMacro(e
				}
			}
			break;
		}
		case CUSTOM: {
			for (Attribute e : expansion) {
				switch (e.getState()) {
				case SET:
				case UNSET:
				case UNSPECIFIED:
					expandMacro(e
					break;
				case CUSTOM:
				default:
					expandMacro(new Attribute(e.getKey()
							result);
				}
			}
			break;
		}
		case UNSPECIFIED: {
			for (Attribute e : expansion) {
				expandMacro(new Attribute(e.getKey()
						result);
			}
			break;
		}
		case SET:
		default:
			for (Attribute e : expansion) {
				expandMacro(e
			}
			break;
		}
	}
}
