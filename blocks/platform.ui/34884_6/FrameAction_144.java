package org.eclipse.ui.internal.navigator.framelist;

public class Frame {

    private int index = -1;

    private FrameList parent;

    private String name = ""; //$NON-NLS-1$

    private String toolTipText;

    public Frame() {
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public FrameList getParent() {
        return parent;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(FrameList parent) {
        this.parent = parent;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }
}
