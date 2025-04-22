package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.egit.ui.internal.repository.tree.command.FilterCommand;

public interface FilterableNode {

	String getFilter();

	void setFilter(String filter);
}
