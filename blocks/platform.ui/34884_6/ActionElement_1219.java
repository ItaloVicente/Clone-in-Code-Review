
package org.eclipse.ui.internal.provisional.presentations;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.internal.provisional.action.ICoolBarManager2;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.jface.internal.provisional.action.IToolBarManager2;

public interface IActionBarPresentationFactory {
	
	public ICoolBarManager2 createCoolBarManager();

	public IToolBarManager2 createToolBarManager();

	public IToolBarManager2 createViewToolBarManager();

	public IToolBarContributionItem createToolBarContributionItem(
			IToolBarManager toolBarManager, String id);

}
