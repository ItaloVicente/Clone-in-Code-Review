
package org.eclipse.ui.internal.quickaccess;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.resource.ImageDescriptor;

public class ActionElement extends QuickAccessElement {

	private static final String separator = " - "; //$NON-NLS-1$

	private ActionContributionItem item;

		super(actionProvider);
		this.item = item;
	}

	@Override
	public void execute() {
		item.getAction().run();
	}

	@Override
	public String getId() {
		return item.getId();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return item.getAction().getImageDescriptor();
	}

	@Override
	public String getLabel() {
		IAction action = item.getAction();
		if (action.getToolTipText() != null
				&& action.getToolTipText().length() != 0) {
			return LegacyActionTools.removeMnemonics(action.getText()
					+ separator + action.getToolTipText());
		}
		return LegacyActionTools.removeMnemonics(action.getText());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ActionElement other = (ActionElement) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
}
