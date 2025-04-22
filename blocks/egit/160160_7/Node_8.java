package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.egit.ui.internal.repository.tree.command.FilterCommand;

public interface FilterableNode extends Node {

	String getFilter();

	void setFilter(String filter);
}
