package org.eclipse.jgit.attributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.attributes.Attribute.State;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.AbstractDotFileManager;
import org.eclipse.jgit.util.Debug;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;

public class AttributeManager
 extends AbstractDotFileManager<AttributesNode> {

	private Map<String

	public AttributeManager(File root
		super(root
		if (Debug.isInfo())
	}

	public void initFromRepository(final Repository repository) {
		setGlobalFileLocator(new IFileLocator() {
			@Override
			public File locateFile() throws IOException {
				String path = repository.getConfig().get(CoreConfig.KEY)
						.getAttributesFile();
				if (path == null)
					return null;
				FS fs = repository.getFS();
					return fs.resolve(fs.userHome()
				}
				return fs.resolve(null
			}
		});
		setInfoFileLocator(new IFileLocator() {
			@Override
			public File locateFile() throws IOException {
				FS fs = repository.getFS();
				return fs.resolve(repository.getDirectory()
						Constants.INFO_ATTRIBUTES);
			}
		});
	}

	@Override
	protected AttributesNode loadNode(File f) throws IOException {
		if (Debug.isInfo())
		AttributesNode node = new AttributesNode();
		try (FileInputStream in = new FileInputStream(f)) {
			node.parse(in);
		}
		if (Debug.isInfo()) {
			for (AttributesRule rule : node.getRules()) {
			}
		}
		return node;
	}

	@Override
	protected void clearSnapshots() {
		m_macrosSnapshot = null;
	}

	public AttributeSet getAttributes(String path
			throws IOException {
		final boolean isDirectory = FileMode.TREE.equals(fileMode);
		AttributeSet result = new AttributeSet();
		collectSubTreeAttributes(path

		AttributesNode node = lookupRootNode();
		if (node != null) {
			node.getAttributes(this
		}

		node = lookupGlobalNode();
		if (node != null) {
			node.getAttributes(this
		}

		node = lookupInfoNode();
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
			AttributesNode node = lookupWorkTreeNode(folderPath);
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
		expandMacroRec(snapshot
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

		List<Attribute> expansion = snapshot.get(a.getKey());
		if (expansion == null) {
			return;
		}
		switch (a.getState()) {
		case UNSET: {
			for (Attribute e : expansion) {
				switch (e.getState()) {
				case SET:
					expandMacroRec(snapshot
							new Attribute(e.getKey()
					break;
				case UNSET:
					expandMacroRec(snapshot
							new Attribute(e.getKey()
					break;
				case UNSPECIFIED:
					expandMacroRec(snapshot
							new Attribute(e.getKey()
							collector);
					break;
				case CUSTOM:
				default:
					expandMacroRec(snapshot
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
					expandMacroRec(snapshot
					break;
				case CUSTOM:
				default:
					expandMacroRec(snapshot
							new Attribute(e.getKey()
				}
			}
			break;
		}
		case UNSPECIFIED: {
			for (Attribute e : expansion) {
				expandMacroRec(snapshot
						new Attribute(e.getKey()
						collector);
			}
			break;
		}
		case SET:
		default:
			for (Attribute e : expansion) {
				expandMacroRec(snapshot
			}
			break;
		}
	}

	private Map<String
			throws IOException {
		Map<String
		if (snapshot != null) {
			return snapshot;
		}

		getLock().writeLock().lock();
		try {
			snapshot = m_macrosSnapshot;
			if (snapshot != null)
				return snapshot;

			Map<String
			AttributesRule predefinedRule = new AttributesRule(
					MACRO_PREFIX + "binary"
			tmp.put(predefinedRule.getPattern().substring(6)
					predefinedRule.getAttributes());

			AttributesNode[] nodes = new AttributesNode[] { lookupGlobalNode()
					lookupRootNode()
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
			m_macrosSnapshot = tmp;
			return tmp;
		} finally {
			getLock().writeLock().unlock();
		}
	}

}
