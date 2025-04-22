package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class ProgressContentProvider implements
        IProgressUpdateCollector, IStructuredContentProvider {

    private boolean canShowDebug = false;

    public ProgressContentProvider() {
    	ProgressViewUpdater.getSingleton().addCollector(this);
    }

    public ProgressContentProvider(boolean debug) {
    	this();
    	canShowDebug = debug;
    }

    public Object[] getElements(Object inputElement) {

        return ProgressManager.getInstance().getRootElements(debug());
    }

    public void dispose() {
        ProgressViewUpdater.getSingleton().removeCollector(this);
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public boolean debug(){
    	if(!canShowDebug) {
			return false;
		}
    	return ProgressViewUpdater.getSingleton().debug;
    	
    }

}
