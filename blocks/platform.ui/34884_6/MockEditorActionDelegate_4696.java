package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.tests.harness.util.CallHistory;

public class MockEditorActionBarContributor extends EditorActionBarContributor {
    protected CallHistory callHistory;

    protected IEditorPart target;

    protected int ACTION_COUNT = 5;

    protected MockAction[] actions;

    public MockEditorActionBarContributor() {
        super();
        callHistory = new CallHistory(this);
    }

    public CallHistory getCallHistory() {
        return callHistory;
    }

    @Override
	public void init(IActionBars bars) {
        callHistory.add("init");
        actions = new MockAction[ACTION_COUNT];
        for (int nX = 0; nX < ACTION_COUNT; nX++) {
            actions[nX] = new MockAction(Integer.toString(nX));
            if (nX % 2 > 0)
                actions[nX].setEnabled(false);
        }
        super.init(bars);
    }

    @Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
        for (int i = 0; i < actions.length; ++i) {
            toolBarManager.add(actions[i]);
        }
    }

    @Override
	public void setActiveEditor(IEditorPart targetEditor) {
        callHistory.add("setActiveEditor");
        target = targetEditor;
    }

    public IEditorPart getActiveEditor() {
        return target;
    }

    public MockAction[] getActions() {
        return actions;
    }

    public void enableActions(boolean b) {
        for (int nX = 0; nX < ACTION_COUNT; nX++) {
            actions[nX].setEnabled(b);
        }
    }

}

