
package org.eclipse.ui.part;

import org.eclipse.jface.viewers.ISelection;

public class ShowInContext {

    private Object input;

    private ISelection selection;

    public ShowInContext(Object input, ISelection selection) {
        setInput(input);
        setSelection(selection);
    }

    public Object getInput() {
        return input;
    }

    public ISelection getSelection() {
        return selection;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public void setSelection(ISelection selection) {
        this.selection = selection;
    }

}
