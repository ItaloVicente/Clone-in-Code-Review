package org.eclipse.ui.internal.dialogs;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.IWorkbenchPropertyPageMulti;
import org.eclipse.ui.SelectionEnabler;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.CategorizedPageRegistryReader;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PropertyPagesRegistryReader;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class RegistryPageContributor implements IPropertyPageContributor,
		IAdaptable,
		IPluginContribution
		{
	private static final String CHILD_ENABLED_WHEN = "enabledWhen"; //$NON-NLS-1$

	private String pageId;

	private Collection subPages = new ArrayList();

	private boolean adaptable = false;

	private final boolean supportsMultiSelect;

	private IConfigurationElement pageElement;

	private SoftReference filterProperties;

	private Expression enablementExpression;

	public RegistryPageContributor(String pageId, IConfigurationElement element) {
		this.pageId = pageId;
		this.pageElement = element;
		adaptable = Boolean
				.valueOf(
						pageElement
								.getAttribute(PropertyPagesRegistryReader.ATT_ADAPTABLE))
				.booleanValue();
		supportsMultiSelect = PropertyPagesRegistryReader.ATT_SELECTION_FILTER_MULTI
				.equals(pageElement.getAttribute(PropertyPagesRegistryReader.ATT_SELECTION_FILTER));
		initializeEnablement(element);
	}

	@Override
	public PreferenceNode contributePropertyPage(PropertyPageManager mng,
			Object element) {
		PropertyPageNode node = new PropertyPageNode(this, element);
		if (IWorkbenchConstants.WORKBENCH_PROPERTIES_PAGE_INFO.equals(node.getId()))
			node.setPriority(-1);
		return node;
	}
	
	public IPreferencePage createPage(Object element)
			throws CoreException {
		IPreferencePage ppage = null;
		ppage = (IPreferencePage) WorkbenchPlugin.createExtension(
				pageElement, IWorkbenchRegistryConstants.ATT_CLASS);

		ppage.setTitle(getPageName());

		Object[] elements = getObjects(element);
		IAdaptable[] adapt = new IAdaptable[elements.length];

		for (int i = 0; i < elements.length; i++) {
			Object adapted = elements[i];
			if (adaptable) {
				adapted = getAdaptedElement(adapted);
				if (adapted == null) {
					String message = "Error adapting selection to Property page " + pageId + " is being ignored"; //$NON-NLS-1$ //$NON-NLS-2$            	
					throw new CoreException(new Status(IStatus.ERROR,
							WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR,
							message, null));
				}
			}
			adapt[i] = (IAdaptable) ((adapted instanceof IAdaptable) ? adapted
					: new AdaptableForwarder(adapted));
		}

		if (supportsMultiSelect) {
			if ((ppage instanceof IWorkbenchPropertyPageMulti))
				((IWorkbenchPropertyPageMulti) ppage).setElements(adapt);
			else
				throw new CoreException(
						new Status(
								IStatus.ERROR,
								WorkbenchPlugin.PI_WORKBENCH,
								IStatus.ERROR,
								"Property page must implement IWorkbenchPropertyPageMulti: " + getPageName(), //$NON-NLS-1$
								null));
		} else
			((IWorkbenchPropertyPage) ppage).setElement(adapt[0]);

		return ppage;
	}

	private Object getAdaptedElement(Object element) {
		Object adapted = LegacyResourceSupport.getAdapter(element,
				getObjectClass());
		if (adapted != null)
			return adapted;

		return null;
	}

	public String getObjectClass() {
		return pageElement
				.getAttribute(PropertyPagesRegistryReader.ATT_OBJECTCLASS);
	}

	public ImageDescriptor getPageIcon() {
		String iconName = pageElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
		if (iconName == null)
			return null;
		return AbstractUIPlugin.imageDescriptorFromPlugin(pageElement
				.getNamespaceIdentifier(), iconName);
	}


	public String getPageId() {
		return pageId;
	}

	public String getPageName() {
		return pageElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

	@Override
	public boolean isApplicableTo(Object object) {
		Object[] objs = getObjects(object);

		if (objs.length > 1 && !supportsMultiSelect)
			return false;

		if (failsEnablement(objs))
			return false;

		String nameFilter = pageElement
				.getAttribute(PropertyPagesRegistryReader.ATT_NAME_FILTER);

		for (int i = 0; i < objs.length; i++) {
			object = objs[i];
			if (nameFilter != null) {
				String objectName = object.toString();
				IWorkbenchAdapter adapter = (IWorkbenchAdapter) Util
						.getAdapter(object, IWorkbenchAdapter.class);
				if (adapter != null) {
					String elementName = adapter.getLabel(object);
					if (elementName != null) {
						objectName = elementName;
					}
				}
				if (!SelectionEnabler.verifyNameMatch(objectName, nameFilter))
					return false;
			}

			if (getFilterProperties() == null)
				return true;
			IActionFilter filter = null;

			Object adaptedObject = LegacyResourceSupport
					.getAdaptedResource(object);
			if (adaptedObject != null) {
				object = adaptedObject;
			}

			filter = (IActionFilter) Util.getAdapter(object,
					IActionFilter.class);

			if (filter != null && !testCustom(object, filter))
				return false;
		}

		return true;
	}

	private boolean failsEnablement(Object[] objs) {
		if (enablementExpression == null)
			return false;
		try {
			Object object = (supportsMultiSelect) ? Arrays.asList(objs) : objs[0];
			EvaluationContext context = new EvaluationContext(null, object);
			context.setAllowPluginActivation(true);
			return enablementExpression.evaluate(
					context).equals(
					EvaluationResult.FALSE);
		} catch (CoreException e) {
			WorkbenchPlugin.log(e);
			return false;
		}
	}

	private Object[] getObjects(Object obj) {
		if (obj instanceof IStructuredSelection)
			return ((IStructuredSelection) obj).toArray();
		return new Object[] { obj };
	}

	protected void initializeEnablement(IConfigurationElement definingElement) {
		IConfigurationElement[] elements = definingElement
				.getChildren(CHILD_ENABLED_WHEN);

		if (elements.length == 0)
			return;

		try {
			IConfigurationElement[] enablement = elements[0].getChildren();
			if (enablement.length == 0)
				return;
			enablementExpression = ExpressionConverter.getDefault().perform(
					enablement[0]);
		} catch (CoreException e) {
			WorkbenchPlugin.log(e);
		}

	}

	private boolean testCustom(Object object, IActionFilter filter) {
		Map filterProperties = getFilterProperties();

		if (filterProperties == null)
			return false;
		Iterator iter = filterProperties.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) filterProperties.get(key);
			if (!filter.testAttribute(object, key, value))
				return false;
		}
		return true;
	}

	@Override
	public boolean canAdapt() {
		return adaptable;
	}

	public String getCategory() {
		return pageElement
				.getAttribute(CategorizedPageRegistryReader.ATT_CATEGORY);
	}

	public Collection getSubPages() {
		return subPages;
	}

	public void addSubPage(RegistryPageContributor child) {
		subPages.add(child);
	}

	private Map getFilterProperties() {
		if (filterProperties == null || filterProperties.get() == null) {
			Map map = new HashMap();
			filterProperties = new SoftReference(map);
			IConfigurationElement[] children = pageElement.getChildren();
			for (int i = 0; i < children.length; i++) {
				processChildElement(map, children[i]);
			}
		}
		return (Map) filterProperties.get();
	}

	public Object getChild(String id) {
		Iterator iterator = subPages.iterator();
		while (iterator.hasNext()) {
			RegistryPageContributor next = (RegistryPageContributor) iterator
					.next();
			if (next.getPageId().equals(id))
				return next;
		}
		return null;
	}

	private void processChildElement(Map map, IConfigurationElement element) {
		String tag = element.getName();
		if (tag.equals(PropertyPagesRegistryReader.TAG_FILTER)) {
			String key = element
					.getAttribute(PropertyPagesRegistryReader.ATT_FILTER_NAME);
			String value = element
					.getAttribute(PropertyPagesRegistryReader.ATT_FILTER_VALUE);
			if (key == null || value == null)
				return;
			map.put(key, value);
		}
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IConfigurationElement.class)) {
			return getConfigurationElement();
		}
		return null;
	}

	boolean supportsMultipleSelection() {
		return supportsMultiSelect;
	}

	IConfigurationElement getConfigurationElement() {
		return pageElement;
	}

	@Override
	public String getLocalId() {
		return pageId;
	}

    @Override
	public String getPluginId() {
    	return pageElement.getContributor().getName();
    }
}
