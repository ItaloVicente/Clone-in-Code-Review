package org.eclipse.ui.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.ui.model.IWorkbenchAdapter;

public interface IDeferredWorkbenchAdapter extends IWorkbenchAdapter {

    public void fetchDeferredChildren(Object object,
            IElementCollector collector, IProgressMonitor monitor);

    public boolean isContainer();

    public ISchedulingRule getRule(Object object);
}
