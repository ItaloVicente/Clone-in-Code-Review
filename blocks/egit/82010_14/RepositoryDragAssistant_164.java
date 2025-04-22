package org.eclipse.egit.ui.internal.repository;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;

public class RepositoriesViewStyledCellLabelProvider extends
		DelegatingStyledCellLabelProvider implements ILabelProvider {

	public RepositoriesViewStyledCellLabelProvider() {
		super(new RepositoriesViewLabelProvider());
	}

	@Override
	public String getText(Object element) {
		return getStyledStringProvider().getStyledText(element).getString();
	}

	@Override
	public String getToolTipText(Object element) {
		return ((RepositoriesViewLabelProvider) getStyledStringProvider())
				.getToolTipText(element);
	}

}
