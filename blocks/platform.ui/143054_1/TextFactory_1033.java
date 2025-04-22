package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class TableFactory extends AbstractCompositeFactory<TableFactory, Table> {

	private TableFactory(int style) {
		super(TableFactory.class, (parent) -> new Table(parent, style));
	}

	public static TableFactory newTable(int style) {
		return new TableFactory(style);
	}

	public TableFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public TableFactory headerVisible(boolean visible) {
		addProperty((t) -> t.setHeaderVisible(visible));
		return this;
	}

	public TableFactory linesVisible(boolean visible) {
		addProperty((t) -> t.setLinesVisible(visible));
		return this;
	}

	public TableFactory itemCount(int count) {
		addProperty((t) -> t.setItemCount(count));
		return this;
	}

}
