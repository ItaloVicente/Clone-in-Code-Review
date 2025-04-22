package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.Debug;
import org.eclipse.jgit.util.DotFileTree;
import org.eclipse.jgit.util.StringUtils;

public class AttributesHierarchy {

	private final DotFileTree dotFileTree;

	private long dotFileTreeSnapshotId;

	private Map<String

	private final ReadWriteLock m_macroCacheLock = new ReentrantReadWriteLock(
			true);

	public AttributesHierarchy(DotFileTree dotFileTree) {
		this.dotFileTree = dotFileTree;
		this.dotFileTreeSnapshotId = dotFileTree.getSnapshotId();
	}

	public AttributeSet getAttributes(String path
			throws IOException {
		final boolean isDirectory = FileMode.TREE.equals(fileMode);
		AttributeSet result = new AttributeSet();
		collectSubTreeAttributes(path

		if (node != null) {
			node.getAttributes(this
		}

		node = dotFileTree.getGlobalAttributesNode();
		if (node != null) {
			node.getAttributes(this
		}

		node = dotFileTree.getInfoAttributesNode();
		if (node != null) {
			AttributeSet overrides = new AttributeSet();
			node.getAttributes(this
			if (!overrides.isEmpty()) {
				for (Attribute a : overrides.getAttributes()) {
					result.putAttribute(a);
				}
			}
		}
		for (Attribute a : result.getAttributes()) {
			if (a.getState() == State.UNSPECIFIED)
				result.removeAttribute(a.getKey());
		}

		if (Debug.isDetail())
		return result;
	}

	private void collectSubTreeAttributes(String path
			AttributeSet result) throws IOException {
		List<String> pathElements = StringUtils.splitPath(path);
		int folderElementCount = (isDirectory ? pathElements.size()
				: pathElements.size() - 1);

		if (folderElementCount == 0)
			return;

		for (int i = folderElementCount; i > 0; i--) {
			String folderPath = StringUtils.join(pathElements.subList(0
			AttributesNode node = dotFileTree
					.getWorkTreeAttributesNode(folderPath);
				if (node != null) {
				String relativePath = i < pathElements.size() ? StringUtils
						.join(pathElements.subList(i
					node.getAttributes(this
				}
		}
	}


	public Collection<Attribute> expandMacro(Attribute a) throws IOException {
		Map<String
		List<Attribute> collector = new ArrayList<>(1);
		expandMacroRec(macroCache
		if (collector.size() <= 1)
			return collector;
		Map<String
				collector.size());
		for (Attribute elem : collector)
			result.put(elem.getKey()
		return result.values();
	}

	private void expandMacroRec(Map<String
			Attribute a
		if (collector.contains(a))
			return;

		collector.add(a);

		List<Attribute> expansion = macroCache.get(a.getKey());
		if (expansion == null) {
			return;
		}
		switch (a.getState()) {
		case UNSET: {
			for (Attribute e : expansion) {
				switch (e.getState()) {
				case SET:
					expandMacroRec(macroCache
							new Attribute(e.getKey()
					break;
				case UNSET:
					expandMacroRec(macroCache
							new Attribute(e.getKey()
					break;
				case UNSPECIFIED:
					expandMacroRec(macroCache
							new Attribute(e.getKey()
							collector);
					break;
				case CUSTOM:
				default:
					expandMacroRec(macroCache
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
					expandMacroRec(macroCache
					break;
				case CUSTOM:
				default:
					expandMacroRec(macroCache
							new Attribute(e.getKey()
				}
			}
			break;
		}
		case UNSPECIFIED: {
			for (Attribute e : expansion) {
				expandMacroRec(macroCache
						new Attribute(e.getKey()
						collector);
			}
			break;
		}
		case SET:
		default:
			for (Attribute e : expansion) {
				expandMacroRec(macroCache
			}
			break;
		}
	}

	private Map<String
			throws IOException {
		m_macroCacheLock.readLock().lock();
		try{
			if(dotFileTreeSnapshotId!=dotFileTree.getSnapshotId()){
				m_macroCache=null;
				dotFileTreeSnapshotId=dotFileTree.getSnapshotId();
			}
			Map<String
			if (cache!= null) {
				return cache;
			}
		}
		finally{
			m_macroCacheLock.readLock().unlock();
		}

		m_macroCacheLock.writeLock().lock();
		try {
			Map<String
			if (cache != null) {
				return cache;
			}

			Map<String
			AttributesRule predefinedRule = new AttributesRule(
					MACRO_PREFIX + "binary"
			tmp.put(predefinedRule.getPattern().substring(6)
					predefinedRule.getAttributes());

			AttributesNode[] nodes = new AttributesNode[] {
					dotFileTree.getGlobalAttributesNode()
					dotFileTree.getWorkTreeAttributesNode(null)
					dotFileTree.getInfoAttributesNode()
					};
			for (AttributesNode node : nodes) {
				if (node == null)
					continue;
				for (AttributesRule rule : node.getRules()) {
					if (rule.getPattern().startsWith(MACRO_PREFIX)) {
						tmp.put(rule.getPattern().substring(6)
								rule.getAttributes());
					}
				}
			}
			m_macroCache = tmp;
			return tmp;
		} finally {
			m_macroCacheLock.writeLock().unlock();
		}
	}

}
