package org.eclipse.ui.internal.navigator.framelist;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public abstract class FrameAction extends Action {
    private FrameList frameList;
	
    private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            FrameAction.this.handlePropertyChange(event);
        }
    };

    protected FrameAction(FrameList frameList) {
        this.frameList = frameList;
        frameList.addPropertyChangeListener(propertyChangeListener);
    }

    public void dispose() {
        frameList.removePropertyChangeListener(propertyChangeListener);
    }

    public FrameList getFrameList() {
        return frameList;
    }

    protected void handlePropertyChange(PropertyChangeEvent event) {
        update();
    }

    public void update() {
    }

}
