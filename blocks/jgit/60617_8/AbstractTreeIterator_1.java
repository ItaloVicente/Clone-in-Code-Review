package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;

public class MacroExpander {


	private static final List<Attribute> BINARY_RULE_ATTRIBUTES = new AttributesRule(
			MACRO_PREFIX + BINARY_RULE_KEY
					.getAttributes();

	private final TreeWalk treeWalk;

	private final AttributesNode globalNode;

	private final AttributesNode infoNode;

	private final Map<String

	public MacroExpander() {
		treeWalk = null;
		globalNode = null;
		infoNode = null;
		expansions.put(BINARY_RULE_KEY
	}

	public MacroExpander(TreeWalk treeWalk) throws IOException {
		this.treeWalk = treeWalk;
		AttributesNodeProvider attributesNodeProvider =treeWalk.getAttributesNodeProvider();
		this.globalNode = attributesNodeProvider != null
				? attributesNodeProvider.getGlobalAttributesNode() : null;
		this.infoNode = attributesNodeProvider != null
				? attributesNodeProvider.getInfoAttributesNode() : null;

		AttributesNode rootNode = attributesNode(treeWalk
				rootOf(
						treeWalk.getTree(WorkingTreeIterator.class))
				rootOf(
						treeWalk.getTree(DirCacheIterator.class))
				rootOf(treeWalk
						.getTree(CanonicalTreeParser.class)));

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

	public Attributes getAttributes() throws IOException {
		String entryPath = treeWalk.getPathString();
		boolean isDirectory = (treeWalk.getFileMode() == FileMode.TREE);
		Attributes attributes = new Attributes();

		mergeInfoAttributes(entryPath

		mergePerDirectoryEntryAttributes(entryPath
				treeWalk.getTree(WorkingTreeIterator.class)
				treeWalk.getTree(DirCacheIterator.class)
				treeWalk.getTree(CanonicalTreeParser.class)
				attributes);

		mergeGlobalAttributes(entryPath

		for (Attribute a : attributes.getAll()) {
			if (a.getState() == State.UNSPECIFIED)
				attributes.remove(a.getKey());
		}

		return attributes;
	}

	private void mergeGlobalAttributes(String entryPath
			Attributes result) {
		mergeAttributes(globalNode
	}

	private void mergeInfoAttributes(String entryPath
			Attributes result) {
		mergeAttributes(infoNode
	}

	private void mergePerDirectoryEntryAttributes(String entryPath
			boolean isDirectory
			@Nullable WorkingTreeIterator workingTreeIterator
			@Nullable DirCacheIterator dirCacheIterator
			@Nullable CanonicalTreeParser otherTree
					throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null
				|| otherTree != null) {
			AttributesNode attributesNode = attributesNode(
					treeWalk
			if (attributesNode != null) {
				mergeAttributes(attributesNode
			}
			mergePerDirectoryEntryAttributes(entryPath
					parentOf(workingTreeIterator)
					parentOf(otherTree)
		}
	}

	protected void mergeAttributes(@Nullable AttributesNode node
			String entryPath
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

	private static AttributesNode attributesNode(TreeWalk treeWalk
			@Nullable WorkingTreeIterator workingTreeIterator
			@Nullable DirCacheIterator dirCacheIterator
			@Nullable CanonicalTreeParser otherTree) throws IOException {
		AttributesNode attributesNode = null;
		switch (treeWalk.getOperationType()) {
		case CHECKIN_OP:
			if (workingTreeIterator != null) {
				attributesNode = workingTreeIterator.getEntryAttributesNode();
			}
			if (attributesNode == null && dirCacheIterator != null) {
				attributesNode = dirCacheIterator
						.getEntryAttributesNode(treeWalk.getObjectReader());
			}
			if (attributesNode == null && otherTree != null) {
				attributesNode = otherTree
						.getEntryAttributesNode(treeWalk.getObjectReader());
			}
			break;
		case CHECKOUT_OP:
			if (otherTree != null) {
				attributesNode = otherTree
						.getEntryAttributesNode(treeWalk.getObjectReader());
			}
			if (attributesNode == null && dirCacheIterator != null) {
				attributesNode = dirCacheIterator
						.getEntryAttributesNode(treeWalk.getObjectReader());
			}
			if (attributesNode == null && workingTreeIterator != null) {
				attributesNode = workingTreeIterator.getEntryAttributesNode();
			}
			break;
		default:
			throw new IllegalStateException(
							+ OperationType.CHECKIN_OP + "
		Class<T> type = (Class<T>) node.getClass();
		AbstractTreeIterator parent = node.parent;
		if (type.isInstance(parent)) {
			return type.cast(parent);
		}
		return null;
	}

	private static <T extends AbstractTreeIterator> T rootOf(
			@Nullable T node) {
		if(node==null) return null;
		AbstractTreeIterator t=node;
		while (t!= null && t.parent != null) {
			t= t.parent;
		}
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) node.getClass();
		if (type.isInstance(t)) {
			return type.cast(t);
		}
		return null;
	}

}
