package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class ProgressContentProvider implements
        IProgressUpdateCollector, IStructuredContentProvider {

    private boolean canShowDebug = false;
    
    protected ProgressViewUpdater progressViewUpdater;
    
    private ProgressManager progressManager;

	public ProgressContentProvider(ProgressViewUpdater progressViewUpdater,
	        ProgressManager progressManager) {
    	this.progressViewUpdater = progressViewUpdater;
    	this.progressManager= progressManager;
    	progressViewUpdater.addCollector(this);
    }

	public ProgressContentProvider(ProgressViewUpdater progressViewUpdater,
	        ProgressManager progressManager, boolean debug) {
    	this(progressViewUpdater, progressManager);
    	canShowDebug = debug;
    }

    public Object[] getElements(Object inputElement) {

        return progressManager.getRootElements(debug());
    }

    public void dispose() {
        progressViewUpdater.removeCollector(this);
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public boolean debug(){
    	if(!canShowDebug) {
			return false;
		}
    	return progressViewUpdater.showsDebug();
    	
    }

}
