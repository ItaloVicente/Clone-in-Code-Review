package org.eclipse.ui.internal.progress;

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

    @Override
	public Object[] getElements(Object inputElement) {

        return ProgressManager.getInstance().getRootElements(debug());
    }

    @Override
	public void dispose() {
        ProgressViewUpdater.getSingleton().removeCollector(this);
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public boolean debug(){
    	if(!canShowDebug) {
			return false;
		}
    	return ProgressViewUpdater.getSingleton().debug;
    	
    }

}
