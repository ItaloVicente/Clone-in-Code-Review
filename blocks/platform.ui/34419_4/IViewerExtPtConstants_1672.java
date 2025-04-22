package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.ui.internal.navigator.TextActionHandler;


public interface INavigatorSiteEditor {
	public void edit(Runnable runnable);

	public String getText();

	public void setTextActionHandler(TextActionHandler actionHandler);
}
