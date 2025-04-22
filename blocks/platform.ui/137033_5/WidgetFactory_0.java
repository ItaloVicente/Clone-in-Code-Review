package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TableColumnFactory extends AbstractItemFactory<TableColumnFactory, TableColumn, Table> {

	private TableColumnFactory(int style) {
		super(TableColumnFactory.class, table -> new TableColumn(table, style));
	}

	public static TableColumnFactory newTableColumn(int style) {
		return new TableColumnFactory(style);

	}

	public TableColumnFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public TableColumnFactory align(int alignment) {
		addProperty(c -> c.setAlignment(alignment));
		return this;
	}

	public TableColumnFactory tooltip(String tooltip) {
		addProperty(c -> c.setToolTipText(tooltip));
		return this;
	}

	public TableColumnFactory width(int width) {
		addProperty(c -> c.setWidth(width));
		return this;
	}

}
