package org.eclipse.ui.actions;

import org.eclipse.jface.viewers.ISelection;

public class ActionContext {

    private ISelection selection;

    private Object input;

    public ActionContext(ISelection selection) {
        setSelection(selection);
    }

    public ISelection getSelection() {
        return selection;
    }

    public void setSelection(ISelection selection) {
        this.selection = selection;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }
}
