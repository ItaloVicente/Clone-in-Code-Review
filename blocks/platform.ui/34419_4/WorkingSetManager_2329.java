package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class WorkingSetFactory implements IElementFactory {

    @Override
	public IAdaptable createElement(IMemento memento) {
        String workingSetName = memento.getString(IWorkbenchConstants.TAG_NAME);
        String label = memento.getString(IWorkbenchConstants.TAG_LABEL);
        if (label == null) {
			label = workingSetName;
		}
        String workingSetEditPageId = memento
                .getString(IWorkbenchConstants.TAG_EDIT_PAGE_ID);
        String aggregateString = memento
				.getString(AbstractWorkingSet.TAG_AGGREGATE);
		boolean isAggregate = aggregateString != null
				&& Boolean.valueOf(aggregateString).booleanValue();

		if (workingSetName == null) {
			return null;
		}

        AbstractWorkingSet workingSet = null;
        
        if (isAggregate) {
			workingSet = new AggregateWorkingSet(workingSetName, label, memento);
		} else {
			workingSet = new WorkingSet(workingSetName, label, memento);
		}
        
        if (workingSetEditPageId != null) {
            workingSet.setId(workingSetEditPageId);
        } else if (!isAggregate) {
            workingSet.setId("org.eclipse.ui.resourceWorkingSetPage"); //$NON-NLS-1$
        }
        return workingSet;
    }
}
