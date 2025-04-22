package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class TreeFactory extends AbstractCompositeFactory<TreeFactory, Tree> {

	private TreeFactory(int style) {
		super(TreeFactory.class, (parent) -> new Tree(parent, style));
	}

	public static TreeFactory newTree(int style) {
		return new TreeFactory(style);
	}

	public TreeFactory linesVisible(boolean visible) {
		this.addProperty(w -> w.setLinesVisible(visible));
		return this;
	}

	public TreeFactory headerVisible(boolean visible) {
		this.addProperty(w -> w.setHeaderVisible(visible));
		return this;
	}

	public TreeFactory itemCount(int count) {
		this.addProperty(w -> w.setItemCount(count));
		return this;
	}

	public TreeFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public TreeFactory onCollapse(Consumer<TreeEvent> consumer) {
		addProperty(c -> c.addTreeListener(TreeListener.treeCollapsedAdapter(consumer)));
		return this;
	}

	public TreeFactory onExpand(Consumer<TreeEvent> consumer) {
		addProperty(c -> c.addTreeListener(TreeListener.treeExpandedAdapter(consumer)));
		return this;
	}

}
