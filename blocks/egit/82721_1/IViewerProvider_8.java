package org.eclipse.egit.ui.internal.history;

import java.util.Collection;

import org.eclipse.jface.action.IAction;

public interface IGlobalActionProvider extends IViewerProvider {

	Collection<IAction> getActions();
}
