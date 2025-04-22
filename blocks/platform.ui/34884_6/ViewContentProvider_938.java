package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.internal.registry.Category;
import org.eclipse.ui.internal.registry.ViewRegistry;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;

public class ViewComparator extends ViewerComparator {

	private static String GENERAL_VIEW_ID = "org.eclipse.ui"; //$NON-NLS-1$

    private ViewRegistry viewReg;

    public ViewComparator(ViewRegistry reg) {
        super();
        viewReg = reg;
    }

    @Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof IViewDescriptor) {
            String str1 = DialogUtil.removeAccel(((IViewDescriptor) e1)
                    .getLabel());
            String str2 = DialogUtil.removeAccel(((IViewDescriptor) e2)
                    .getLabel());
            return getComparator().compare(str1, str2);
        } else if (e1 instanceof IViewCategory) {
			IViewCategory generalCategory = viewReg.findCategory(GENERAL_VIEW_ID);
        	if (generalCategory != null){
        		if (((IViewCategory)e1).getId().equals(generalCategory.getId())) {
					return -1;
				}
        		if (((IViewCategory)e2).getId().equals(generalCategory.getId())) {
					return 1;
				}
        	}
			Category miscCategory = viewReg.getMiscCategory();
			if(miscCategory != null){
				if (((IViewCategory)e1).getId().equals(miscCategory.getId())) {
					return 1;
				}
				if (((IViewCategory)e2).getId().equals(miscCategory.getId())) {
					return -1;
				}
			}
            String str1 = DialogUtil.removeAccel(((IViewCategory) e1).getLabel());
            String str2 = DialogUtil.removeAccel(((IViewCategory) e2).getLabel());
            return getComparator().compare(str1, str2);
        }
        return 0;
    }
}
