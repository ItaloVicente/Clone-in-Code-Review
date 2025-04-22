package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

public class DynamicContributionItemPopup extends CompoundContributionItem {

	public DynamicContributionItemPopup() {
	}
	
	public DynamicContributionItemPopup(String id) {
		super(id);
	}
	
	private int count = 1;
	
	@Override
	protected IContributionItem[] getContributionItems() {
		ContributionItem contributionItem1 = new ActionContributionItem(new DoNothingAction("something " + (count++)));
		ContributionItem contributionItem2 = new ActionContributionItem(new DoNothingAction("something " + (count++)));
		ContributionItem contributionItem3 = new ActionContributionItem(new DoNothingAction("something " + (count++)));
		return new IContributionItem[] {contributionItem1, contributionItem2, contributionItem3};
	}
	
	class DoNothingAction extends Action{
		
		public DoNothingAction(String text){
			setText(text);
		}
	}
}
