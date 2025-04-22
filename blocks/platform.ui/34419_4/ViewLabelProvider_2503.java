package org.eclipse.ui.internal.dialogs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.intro.IIntroConstants;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class ViewContentProvider implements ITreeContentProvider {

	private Map<Object, Object[]> childMap = new HashMap<Object, Object[]>();

	private MApplication application;
	private IViewRegistry viewRegistry;

	public ViewContentProvider(MApplication application) {
		this.application = application;
		viewRegistry = WorkbenchPlugin.getDefault().getViewRegistry();
	}

	@Override
	public void dispose() {
		childMap.clear();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		childMap.clear();
		application = (MApplication) newInput;
	}

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof MApplication) {
			return true;
		} else if (element instanceof String) {
			return true;
		}
		return false;
	}

	@Override
	public Object[] getChildren(Object element) {
		Object[] children = childMap.get(element);
		if (children == null) {
			children = createChildren(element);
			childMap.put(element, children);
		}
		return children;
	}

	private Object[] createChildren(Object element) {
		if (element instanceof MApplication) {
			return determineTopLevelElements(element).toArray();
		} else if (element instanceof String) {
			return determineViewsInCategory((String) element).toArray();
		}
		return new Object[0];
	}

	private Set<MPartDescriptor> determineViewsInCategory(String categoryDescription) {
		List<MPartDescriptor> descriptors = application.getDescriptors();
		Set<MPartDescriptor> categoryDescriptors = new HashSet<MPartDescriptor>();
		for (MPartDescriptor descriptor : descriptors) {
			if (isFilteredByActivity(descriptor.getElementId()) || isIntroView(descriptor.getElementId())) {
				continue;
			}
			String category = descriptor.getCategory();
			if (categoryDescription.equals(category)) {
				categoryDescriptors.add(descriptor);
			}
		}
		return categoryDescriptors;
	}

	private Set<Object> determineTopLevelElements(Object element) {
		List<MPartDescriptor> descriptors = ((MApplication) element).getDescriptors();
		Set<String> categories = new HashSet<String>();
		Set<MPartDescriptor> visibleViews = new HashSet<MPartDescriptor>();
		for (MPartDescriptor descriptor : descriptors) {
			if (!isView(descriptor) || isFilteredByActivity(descriptor.getElementId())) {
				continue;
			}

			String category = descriptor.getCategory();

			if (category == null) {
				visibleViews.add(descriptor);
			} else {
				categories.add(category);
			}
		}

		Set<Object> combinedTopElements = new HashSet<Object>();
		combinedTopElements.addAll(categories);
		combinedTopElements.addAll(visibleViews);
		return combinedTopElements;
	}


	private boolean isView(MPartDescriptor descriptor) {
		return descriptor.getTags().contains("View"); //$NON-NLS-1$
	}

	private boolean isIntroView(String id) {
		return (id.equals(IIntroConstants.INTRO_VIEW_ID));
	}

	private boolean isFilteredByActivity(String elementId) {
		IViewDescriptor[] views = viewRegistry.getViews();
		for (IViewDescriptor descriptor : views) {
			if (descriptor.getId().equals(elementId) && WorkbenchActivityHelper.filterItem(descriptor)) {
				return true;
			}
		}
		return false;
	}
}
