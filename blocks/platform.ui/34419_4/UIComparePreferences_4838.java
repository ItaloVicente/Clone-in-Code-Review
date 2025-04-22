package org.eclipse.ui.tests.commands;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.tests.api.workbenchpart.MenuContributionHarness;

public class ViewActionDelegate implements IViewActionDelegate {

	private IViewPart viewPart;

	@Override
	public void init(IViewPart view) {
		viewPart = view;
	}

	@Override
	public void run(IAction action) {
		if (viewPart instanceof MenuContributionHarness) {
			((MenuContributionHarness)viewPart).updateCount();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
