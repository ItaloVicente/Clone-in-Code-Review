package org.eclipse.egit.ui.internal.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.IAction;

public class SynchronizeWithDefaultAction extends RepositoryAction {

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	protected void execute(IAction action) throws InvocationTargetException,
			InterruptedException {

	}

}
