package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.ObjectContributorManager;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PropertyPagesRegistryReader;


public class PropertyPageContributorManager extends ObjectContributorManager {
	private static PropertyPageContributorManager sharedInstance = null;

	private class CategorizedPageNode {
		RegistryPageContributor contributor;

		CategorizedPageNode parent;

		CategorizedPageNode(RegistryPageContributor page) {
			contributor = page;
		}

		void setParent(CategorizedPageNode node) {
			parent = node;
		}

	}

	public PropertyPageContributorManager() {
		super();
		loadContributors();
	}

	public boolean contribute(PropertyPageManager manager, Object object) {

		Collection result = null;
		if (object instanceof IStructuredSelection) {
			Object[] objs = ((IStructuredSelection) object).toArray();
			for (int i = 0; i < objs.length; i++) {
				List contribs = getContributors(objs[i]);
				if (result == null)
					result = new LinkedHashSet(contribs);
				else
					result.retainAll(contribs);
			}
		} else
			result = getContributors(object);

		if (result == null || result.size() == 0) {
			return false;
		}

		List catNodes = buildNodeList(result);
		Iterator resultIterator = catNodes.iterator();

		Map catPageNodeToPages = new HashMap();

		boolean actualContributions = false;
		while (resultIterator.hasNext()) {
			CategorizedPageNode next = (CategorizedPageNode) resultIterator
					.next();
			IPropertyPageContributor ppcont = next.contributor;
			if (!ppcont.isApplicableTo(object)) {
				continue;
			}
			PreferenceNode page = ppcont.contributePropertyPage(manager, object);
			if (page != null) {
				catPageNodeToPages.put(next, page);
				actualContributions = true;
			}
		}

		if (actualContributions) {
			resultIterator = catNodes.iterator();
			while (resultIterator.hasNext()) {
				CategorizedPageNode next = (CategorizedPageNode) resultIterator
						.next();
				PreferenceNode child = (PreferenceNode) catPageNodeToPages.get(next);
				if (child == null)
					continue;
				PreferenceNode parent = null;
				if (next.parent != null)
					parent = (PreferenceNode) catPageNodeToPages.get(next.parent);
				
				if (parent == null) {
					manager.addToRoot(child);
				} else {
					parent.add(child);
				}
			}
		}
		return actualContributions;
	}

	private List buildNodeList(Collection nodes) {
		Hashtable mapping = new Hashtable();
		
		Iterator nodesIterator = nodes.iterator();
		while(nodesIterator.hasNext()){
			RegistryPageContributor page = (RegistryPageContributor) nodesIterator.next();
			mapping.put(page.getPageId(),new CategorizedPageNode(page));
		}
		
		Iterator values = mapping.values().iterator();
		List returnValue = new ArrayList();
		while(values.hasNext()){
			CategorizedPageNode next = (CategorizedPageNode) values.next();
			returnValue.add(next);
			if(next.contributor.getCategory() == null) {
				continue;
			}
			Object parent = mapping.get(next.contributor.getCategory());
			if(parent != null) {
				next.setParent((CategorizedPageNode) parent);
			}
		}
		return returnValue;
	}

	public static PropertyPageContributorManager getManager() {
		if (sharedInstance == null) {
			sharedInstance = new PropertyPageContributorManager();
		}
		return sharedInstance;
	}

	private void loadContributors() {
		PropertyPagesRegistryReader reader = new PropertyPagesRegistryReader(
				this);
		reader.registerPropertyPages(Platform.getExtensionRegistry());
	}
	
    @Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
        IConfigurationElement[] addedElements = extension.getConfigurationElements();
        for (int i = 0; i < addedElements.length; i++) {
            PropertyPagesRegistryReader reader = new PropertyPagesRegistryReader(this);
            reader.readElement(addedElements[i]);
        }
    }

	public Collection getApplicableContributors(Object element) {
		if (element instanceof IStructuredSelection)
			return getApplicableContributors((IStructuredSelection) element);
		Collection contributors = getContributors(element);
		Collection result = new ArrayList();
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor contributor = (RegistryPageContributor) iter.next();
			if(contributor.isApplicableTo(element))
				result.add(contributor);
			
		}
		return result;
	}

	public Collection getApplicableContributors(IStructuredSelection selection) {
		Iterator it = selection.iterator();
		Collection result = null;
		while (it.hasNext()) {
			Object element = it.next();
			Collection collection = getApplicableContributors(element);
			if (result == null)
				result = new LinkedHashSet(collection);
			else
				result.retainAll(collection);
		}
		if (result != null && !result.isEmpty() && selection.size() > 1) {
			it = result.iterator();
			while (it.hasNext()) {
				RegistryPageContributor contrib = (RegistryPageContributor) it
						.next();
				if (!contrib.supportsMultipleSelection())
					it.remove();
			}
		}
		return result;
	}

	@Override
	protected String getExtensionPointFilter() {
		return IWorkbenchRegistryConstants.PL_PROPERTY_PAGES;
	}
}
