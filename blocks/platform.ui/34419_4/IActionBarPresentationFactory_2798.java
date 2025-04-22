
package org.eclipse.ui.internal.provisional.application;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.ui.application.IActionBarConfigurer;

public interface IActionBarConfigurer2 extends IActionBarConfigurer {
	
	public IToolBarManager createToolBarManager();

	public IToolBarContributionItem createToolBarContributionItem(
			IToolBarManager toolBarManager, String id);

}
