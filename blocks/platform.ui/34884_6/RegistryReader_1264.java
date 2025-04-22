package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;
import org.eclipse.ui.internal.dialogs.RegistryPageContributor;

public class PropertyPagesRegistryReader extends CategorizedPageRegistryReader {

	public static final String ATT_NAME_FILTER = "nameFilter";//$NON-NLS-1$

	public static final String ATT_FILTER_NAME = "name";//$NON-NLS-1$

	public static final String ATT_FILTER_VALUE = "value";//$NON-NLS-1$

	public static final String ATT_SELECTION_FILTER = "selectionFilter";//$NON-NLS-1$

	public static final String ATT_SELECTION_FILTER_MULTI = "multi";//$NON-NLS-1$

	private static final String TAG_PAGE = "page";//$NON-NLS-1$

	public static final String TAG_FILTER = "filter";//$NON-NLS-1$

	public static final String TAG_KEYWORD_REFERENCE = "keywordReference";//$NON-NLS-1$

	public static final String ATT_OBJECTCLASS = "objectClass";//$NON-NLS-1$

	public static final String ATT_ADAPTABLE = "adaptable";//$NON-NLS-1$

	private static final String CHILD_ENABLED_WHEN = "enabledWhen"; //$NON-NLS-1$;

	private Collection pages = new ArrayList();

	private PropertyPageContributorManager manager;

	class PropertyCategoryNode extends CategoryNode {

		RegistryPageContributor page;

		PropertyCategoryNode(CategorizedPageRegistryReader reader,
				RegistryPageContributor propertyPage) {
			super(reader);
			page = propertyPage;
		}

		@Override
		String getLabelText() {
			return page.getPageName();
		}

		@Override
		String getLabelText(Object element) {
			return ((RegistryPageContributor) element).getPageName();
		}

		@Override
		Object getNode() {
			return page;
		}
	}

	public PropertyPagesRegistryReader(PropertyPageContributorManager manager) {
		this.manager = manager;
	}

	private void processPageElement(IConfigurationElement element) {
		String pageId = element
				.getAttribute(IWorkbenchRegistryConstants.ATT_ID);

		if (pageId == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_ID);
			return;
		}

		RegistryPageContributor contributor = new RegistryPageContributor(
				pageId, element);

		String pageClassName = getClassValue(element,
				IWorkbenchRegistryConstants.ATT_CLASS);
		if (pageClassName == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_CLASS);
			return;
		}
		if (element.getAttribute(ATT_OBJECTCLASS) == null) {
			pages.add(contributor);
			manager.registerContributor(contributor, Object.class.getName());
		} else {
			List objectClassNames = new ArrayList();
			objectClassNames.add(element.getAttribute(ATT_OBJECTCLASS));
			registerContributors(contributor, objectClassNames);
		}
	}

	private void registerContributors(RegistryPageContributor contributor,
			List objectClassNames) {

		pages.add(contributor);
		for (Iterator iter = objectClassNames.iterator(); iter.hasNext();) {
			manager.registerContributor(contributor, (String) iter.next());
		}

	}


	@Override
	public boolean readElement(IConfigurationElement element) {
		if (element.getName().equals(TAG_PAGE)) {
			processPageElement(element);
			readElementChildren(element);
			return true;
		}
		if (element.getName().equals(TAG_FILTER)) {
			return true;
		}

		if (element.getName().equals(CHILD_ENABLED_WHEN)) {
			return true;
		}

		if (element.getName().equals(TAG_KEYWORD_REFERENCE)) {
			return true;
		}

		return false;
	}

	public void registerPropertyPages(IExtensionRegistry registry) {
		readRegistry(registry, PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_PROPERTY_PAGES);
		processNodes();
	}

	@Override
	void add(Object parent, Object node) {
		((RegistryPageContributor) parent)
				.addSubPage((RegistryPageContributor) node);

	}

	@Override
	CategoryNode createCategoryNode(CategorizedPageRegistryReader reader,
			Object object) {
		return new PropertyCategoryNode(reader,
				(RegistryPageContributor) object);
	}

	@Override
	Object findNode(Object parent, String currentToken) {
		return ((RegistryPageContributor) parent).getChild(currentToken);
	}

	@Override
	Object findNode(String id) {
		Iterator iterator = pages.iterator();
		while (iterator.hasNext()) {
			RegistryPageContributor next = (RegistryPageContributor) iterator
					.next();
			if (next.getPageId().equals(id))
				return next;
		}
		return null;
	}

	@Override
	String getCategory(Object node) {
		return ((RegistryPageContributor) node).getCategory();
	}

	@Override
	protected String invalidCategoryNodeMessage(CategoryNode categoryNode) {
		RegistryPageContributor rpc = (RegistryPageContributor) categoryNode.getNode();
		return "Invalid property category path: " + rpc.getCategory() + " (bundle: " + rpc.getPluginId() + ", propertyPage: " + rpc.getLocalId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Override
	Collection getNodes() {
		return pages;
	}
}
