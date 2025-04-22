package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class TreeColumnFactory extends AbstractItemFactory<TreeColumnFactory, TreeColumn, Tree> {

	private TreeColumnFactory(int style) {
		super(TreeColumnFactory.class, tree -> new TreeColumn(tree, style));
	}

	public static TreeColumnFactory newTreeColumn(int style) {
		return new TreeColumnFactory(style);

	}

	public TreeColumnFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public TreeColumnFactory align(int alignment) {
		addProperty(c -> c.setAlignment(alignment));
		return this;
	}

	public TreeColumnFactory tooltip(String tooltip) {
		addProperty(c -> c.setToolTipText(tooltip));
		return this;
	}

	public TreeColumnFactory width(int width) {
		addProperty(c -> c.setWidth(width));
		return this;
	}

}
