package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;

public abstract class CategorizedPageRegistryReader extends RegistryReader {

	public static final String ATT_CATEGORY = "category"; //$NON-NLS-1$

	static final String PREFERENCE_SEPARATOR = "/"; //$NON-NLS-1$

	List topLevelNodes;

	abstract class CategoryNode {
		private final CategorizedPageRegistryReader reader;


		private String flatCategory;

		public CategoryNode(CategorizedPageRegistryReader reader) {
			this.reader = reader;
		}

		public String getFlatCategory() {
			if (flatCategory == null) {
				initialize();
				if (flatCategory == null) {
					flatCategory = getLabelText();
				}
			}
			return flatCategory;
		}

		abstract String getLabelText();

		private void initialize() {
			String category = reader.getCategory(getNode());
			if (category == null) {
				return;
			}

			StringBuffer sb = new StringBuffer();
			StringTokenizer stok = new StringTokenizer(category, PREFERENCE_SEPARATOR);
			Object immediateParent = null;
			while (stok.hasMoreTokens()) {
				String pathID = stok.nextToken();
				immediateParent = this.reader.findNode(pathID);
				if (immediateParent == null) {
					return;
				}
				if (sb.length() > 0) {
					sb.append(PREFERENCE_SEPARATOR);
				}
				sb.append(getLabelText(immediateParent));
			}

			if (sb.length() > 0) {
				sb.append(PREFERENCE_SEPARATOR);
			}
			sb.append(getLabelText());
			flatCategory = sb.toString();
		}

		abstract String getLabelText(Object element);

		abstract Object getNode();
	}

	public CategorizedPageRegistryReader() {
		super();
	}

	void processNodes() {
		topLevelNodes = new ArrayList();

		StringTokenizer tokenizer;
		String currentToken;


		CategoryNode[] nodes = createCategoryNodes(getNodes());
		boolean workDone;
		do {
			workDone = false;
			List deferred = new ArrayList();
			for (int i = 0; i < nodes.length; i++) {
				CategoryNode categoryNode = nodes[i];
				Object node = categoryNode.getNode();

				String category = getCategory(node);
				if (category == null) {
					topLevelNodes.add(node);
					continue;
				}
				tokenizer = new StringTokenizer(category, PREFERENCE_SEPARATOR);
				Object parent = null;
				while (tokenizer.hasMoreElements()) {
					currentToken = tokenizer.nextToken();
					Object child = null;
					if (parent == null) {
						child = findNode(currentToken);
					} else {
						child = findNode(parent, currentToken);
					}

					if (child == null) {
						parent = null;
						break;
					}
					parent = child;
				}
				if (parent != null) {
					workDone = true;
					add(parent, node);
				} else {
					deferred.add(categoryNode);
				}
			}
			nodes = (CategoryNode[]) deferred.toArray(new CategoryNode[deferred
					.size()]);
		} while (nodes.length > 0 && workDone); // loop while we still have nodes to work on and the list is shrinking
		
		for (int i = 0; i < nodes.length; i++) {
			CategoryNode categoryNode = nodes[i];
			WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING,
					invalidCategoryNodeMessage(categoryNode), null));
			topLevelNodes.add(categoryNode.getNode());
		}
	}

	protected abstract String invalidCategoryNodeMessage(CategoryNode categoryNode);

	abstract String getCategory(Object node);

	abstract void add(Object parent, Object node);

	abstract Collection getNodes();

	CategoryNode[] createCategoryNodes(Collection nodesToCategorize) {
		List nodes = new ArrayList();

		Iterator nodesIterator = nodesToCategorize.iterator();
		while (nodesIterator.hasNext()) {
			nodes.add(createCategoryNode(this, nodesIterator.next()));
		}

		return (CategoryNode[]) nodes.toArray(new CategoryNode[nodes.size()]);
	}

	abstract CategoryNode createCategoryNode(CategorizedPageRegistryReader reader, Object object);

	abstract Object findNode(String id);

	abstract Object findNode(Object parent, String currentToken);

}
