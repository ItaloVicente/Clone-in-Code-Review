package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attribute.State;

public class MacroExpanderImpl implements MacroExpander {

	private final Map<String

	private AttributesNode globalNode;

	private AttributesNode infoNode;

	private AttributesNode rootNode;

	public MacroExpanderImpl() {
		reload();
	}

	public void updateWhenDifferent(@Nullable AttributesNode actualGlobalNode
			@Nullable AttributesNode actualInfoNode
			@Nullable AttributesNode actualRootNode) {
		if (globalNode == actualGlobalNode && infoNode == actualInfoNode
				&& rootNode == actualRootNode) {
			return;
		}
		globalNode = actualGlobalNode;
		infoNode = actualInfoNode;
		rootNode = actualRootNode;
		reload();
	}

	private void reload() {
		AttributesRule predefinedRule = new AttributesRule(
				MACRO_PREFIX + "binary"
		expansions.put(predefinedRule.getPattern().substring(6)
				predefinedRule.getAttributes());

		for (AttributesNode node : new AttributesNode[] { globalNode
				infoNode }) {
			if (node == null)
				continue;
			for (AttributesRule rule : node.getRules()) {
				if (rule.getPattern().startsWith(MACRO_PREFIX)) {
					expansions.put(rule.getPattern().substring(6)
							rule.getAttributes());
				}
			}
	}
	}

	@Override
	public Collection<Attribute> expandMacro(Attribute attr) {
		List<Attribute> collector = new ArrayList<>(1);
		expandMacroRec(attr
		if (collector.size() <= 1)
			return collector;
		Map<String
				collector.size());
		for (Attribute elem : collector)
			result.put(elem.getKey()
		return result.values();
	}

	private void expandMacroRec(Attribute a
		if (collector.contains(a))
			return;

		collector.add(a);

		List<Attribute> expansion = expansions.get(a.getKey());
		if (expansion == null) {
			return;
		}
		switch (a.getState()) {
		case UNSET: {
			for (Attribute e : expansion) {
				switch (e.getState()) {
				case SET:
					expandMacroRec(new Attribute(e.getKey()
							collector);
					break;
				case UNSET:
					expandMacroRec(new Attribute(e.getKey()
							collector);
					break;
				case UNSPECIFIED:
					expandMacroRec(new Attribute(e.getKey()
							collector);
					break;
				case CUSTOM:
				default:
					expandMacroRec(e
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
					expandMacroRec(e
					break;
				case CUSTOM:
				default:
					expandMacroRec(new Attribute(e.getKey()
							collector);
				}
			}
			break;
		}
		case UNSPECIFIED: {
			for (Attribute e : expansion) {
				expandMacroRec(new Attribute(e.getKey()
						collector);
			}
			break;
		}
		case SET:
		default:
			for (Attribute e : expansion) {
				expandMacroRec(e
			}
			break;
		}
	}
}
