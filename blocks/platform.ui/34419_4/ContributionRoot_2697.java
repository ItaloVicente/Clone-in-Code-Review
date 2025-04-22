
package org.eclipse.ui.internal.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.internal.expressions.AlwaysEnabledExpression;
import org.eclipse.ui.menus.AbstractContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.menus.IMenuService;

final class ContributionRoot implements
		IContributionRoot {

	private List topLevelItems = new ArrayList();
	private Map<IContributionItem, Expression> itemsToExpressions = new HashMap<IContributionItem, Expression>();
	Set restriction;
	private ContributionManager mgr;
	private AbstractContributionFactory factory;

	public ContributionRoot(IMenuService menuService, Set restriction,
			ContributionManager mgr, AbstractContributionFactory factory) {
		this.restriction = restriction;
		this.mgr = mgr;
		this.factory = factory;
	}

	@Override
	public void addContributionItem(IContributionItem item,
			Expression visibleWhen) {
		if (item == null)
			throw new IllegalArgumentException();
		topLevelItems.add(item);
		if (visibleWhen == null) 
			visibleWhen = AlwaysEnabledExpression.INSTANCE;
		
		itemsToExpressions.put(item, visibleWhen);
	}

	String createIdentifierId(IContributionItem item) {
		String namespace = factory.getNamespace();
		String identifierID = namespace != null ? namespace + '/'
				+ item.getId() : null; // create the activity identifier ID. If
		return identifierID;
	}

	public List getItems() {
		return topLevelItems;
	}

	public Map<IContributionItem, Expression> getVisibleWhen() {
		return itemsToExpressions;
	}

	public void release() {
		for (Iterator<IContributionItem> itemIter = itemsToExpressions.keySet().iterator(); itemIter
				.hasNext();) {
			IContributionItem item = itemIter.next();
			item.dispose();
		}
		itemsToExpressions.clear();
		topLevelItems.clear();
	}

	@Override
	public void registerVisibilityForChild(IContributionItem item,
			Expression visibleWhen) {
		if (item == null)
			throw new IllegalArgumentException();
		if (visibleWhen == null) 
			visibleWhen = AlwaysEnabledExpression.INSTANCE;
		itemsToExpressions.put(item, visibleWhen);
	}

	public ContributionManager getManager() {
		return mgr;
	}
}
