package org.eclipse.ui.internal.navigator.framelist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class FrameList extends EventManager {

    public static final String P_CURRENT_FRAME = "currentFrame"; //$NON-NLS-1$

    public static final String P_RESET = "reset"; //$NON-NLS-1$

    private IFrameSource source;

    private List frames;

    private int current;

    public FrameList(IFrameSource source) {
        this.source = source;
        init();
    }

    public void addPropertyChangeListener(IPropertyChangeListener listener) {
    	addListenerObject(listener);
    }

    public void back() {
        if (current > 0) {
            setCurrent(current - 1);
        }
    }

    protected void firePropertyChange(PropertyChangeEvent event) {
        Object[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((IPropertyChangeListener) listeners[i]).propertyChange(event);
        }
    }

    public void forward() {
        if (current < frames.size() - 1) {
            setCurrent(current + 1);
        }
    }

    public Frame getCurrentFrame() {
        return getFrame(current);
    }

    public int getCurrentIndex() {
        return current;
    }

    public Frame getFrame(int index) {
        if (index < 0 || index >= frames.size()) {
			return null;
		}
        return (Frame) frames.get(index);
    }

    public IFrameSource getSource() {
        return source;
    }

    public void gotoFrame(Frame frame) {
        for (int i = frames.size(); --i > current;) {
            frames.remove(i);
        }
        frame.setParent(this);
        int index = frames.size();
        frame.setIndex(index);
        frames.add(frame);
        setCurrent(index);
    }

    private void init() {
        Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME, 0);
        frame.setParent(this);
        frame.setIndex(0);
        frames = new ArrayList();
        frames.add(frame);
        current = 0;
    }
    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        removeListenerObject(listener);
    }

    public void reset() {
    	init();
        firePropertyChange(new PropertyChangeEvent(this, P_RESET,
                null, getFrame(current)));
    }
    
    void setCurrent(int newCurrent) {
        Assert.isTrue(newCurrent >= 0 && newCurrent < frames.size());
        int oldCurrent = this.current;
        if (oldCurrent != newCurrent) {
            updateCurrentFrame();
            this.current = newCurrent;
            firePropertyChange(new PropertyChangeEvent(this, P_CURRENT_FRAME,
                    getFrame(oldCurrent), getFrame(newCurrent)));
        }
    }

    public void setCurrentIndex(int index) {
        if (index != -1 && index != current) {
			setCurrent(index);
		}
    }

    public int size() {
        return frames.size();
    }

    public void updateCurrentFrame() {
        Assert.isTrue(current >= 0);
        Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME,
                IFrameSource.FULL_CONTEXT);
        frame.setParent(this);
        frame.setIndex(current);
        frames.set(current, frame);
    }
    
}
