package org.eclipse.ui.quickaccess;

public interface IQuickAccessComputer {

	QuickAccessElement[] computeElements();

	void resetState();

	boolean needsRefresh();

}
