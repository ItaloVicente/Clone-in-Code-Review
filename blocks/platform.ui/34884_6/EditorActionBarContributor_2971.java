package org.eclipse.ui.part;

import java.util.Stack;

    Stack fStack = null;

    public DrillStack() {
        reset();
    }

    public DrillFrame add(DrillFrame oRecord) {
        fStack.push(oRecord);
        return oRecord;
    }

    public boolean canGoBack() {
        return (fStack.size() > 0);
    }

    public boolean canGoHome() {
        return (fStack.size() > 0);
    }

    public DrillFrame goBack() {
        DrillFrame aFrame = (DrillFrame) fStack.pop();
        return aFrame;
    }

    public DrillFrame goHome() {
        DrillFrame aFrame = (DrillFrame) fStack.elementAt(0);
        reset();
        return aFrame;
    }

    public void reset() {
        fStack = new Stack();
    }

    public int size() {
        return fStack.size();
    }

    public DrillFrame top() {
        return (DrillFrame) fStack.peek();
    }
}
