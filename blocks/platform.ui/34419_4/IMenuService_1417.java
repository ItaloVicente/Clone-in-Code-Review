
package org.eclipse.ui.menus;

import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;

public interface IContributionRoot {
	public void addContributionItem(IContributionItem item,
			Expression visibleWhen);

	public void registerVisibilityForChild(IContributionItem item,
			Expression visibleWhen);
}
