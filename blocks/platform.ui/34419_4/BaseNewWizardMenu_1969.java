package org.eclipse.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;

public abstract class ActionGroup {

    private ActionContext context;

    public ActionContext getContext() {
        return context;
    }

    public void setContext(ActionContext context) {
        this.context = context;
    }

    public void fillContextMenu(IMenuManager menu) {
    }

    public void fillActionBars(IActionBars actionBars) {
    }

    public void updateActionBars() {
    }

    public void dispose() {
        setContext(null);
    }
}
