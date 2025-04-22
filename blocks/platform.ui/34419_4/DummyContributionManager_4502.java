package org.eclipse.jface.tests.action;

import org.eclipse.jface.action.Action;


class DummyAction extends Action {

    static int Count = 0;

    public DummyAction() {
        super("DummyAction " + ++Count);
    }
}
