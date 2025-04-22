
package org.eclipse.ui.internal.dialogs;

import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IWorkingSet;

public class WorkingSetFilter extends ViewerFilter {
		Set workingSetIds;
		
		public WorkingSetFilter(Set workingSetIds) {
			this.workingSetIds = workingSetIds;
		}
		
    @Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IWorkingSet) {
            IWorkingSet workingSet = (IWorkingSet) element;
			String id = workingSet.getId();
            if (workingSetIds != null && id != null) {
                return workingSetIds.contains(id);
            }
        }
        return true;
    }
}
