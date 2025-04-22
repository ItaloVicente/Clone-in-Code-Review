package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.intro.IIntroConstants;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class ViewContentProvider implements ITreeContentProvider {

    private Map<Object, Object[]> childMap = new HashMap<Object, Object[]>();

    public ViewContentProvider() {
    }

    @Override
	public void dispose() {
        childMap.clear();
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
        if (element instanceof IViewRegistry) {
            IViewRegistry reg = (IViewRegistry) element;
            IViewCategory [] categories = reg.getCategories();

			ArrayList<IViewCategory> filtered = new ArrayList<IViewCategory>();
            for (int i = 0; i < categories.length; i++) {
                if (!hasChildren(categories[i])) {
					continue;
				}

                filtered.add(categories[i]);
            }
			categories = filtered.toArray(new IViewCategory[filtered
                    .size()]);

            if (categories.length == 1) {
                return getChildren(categories[0]);
            }
            return categories;
        } else if (element instanceof IViewCategory) {
            IViewDescriptor [] views = ((IViewCategory) element).getViews();
            if (views != null) {
                ArrayList<Object> filtered = new ArrayList<Object>();
                for (int i = 0; i < views.length; i++) {
                    Object o = views[i];
                    if (WorkbenchActivityHelper.filterItem(o)) {
						continue;
					}
                    filtered.add(o);
                }
                return removeIntroView(filtered).toArray();
            }
        }

        return new Object[0];
    }

    private ArrayList<Object> removeIntroView(ArrayList<Object> list) {
        for (Iterator<Object> i = list.iterator(); i.hasNext();) {
            IViewDescriptor view = (IViewDescriptor) i.next();
            if (view.getId().equals(IIntroConstants.INTRO_VIEW_ID)) {
                i.remove();
            }
        }
        return list;
    }

    @Override
	public Object[] getElements(Object element) {
        return getChildren(element);
    }

    @Override
	public Object getParent(Object element) {
        return null;
    }

    @Override
	public boolean hasChildren(java.lang.Object element) {
        if (element instanceof IViewRegistry) {
			return true;
		} else if (element instanceof IViewCategory) {
            if (getChildren(element).length > 0) {
				return true;
			}
        }
        return false;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        childMap.clear();
    }
}
