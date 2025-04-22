package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

class TableToolTip extends NameAndDescriptionToolTip {
	private Table table;

	public TableToolTip(Table table) {
		super(table, RECREATE);
		this.table = table;
	}

	@Override
	protected Object getModelElement(Event event) {
		TableItem tableItem = table.getItem(new Point(event.x, event.y));
		if (tableItem == null) {
			return null;
		}
		return tableItem.getData();
	}
}
