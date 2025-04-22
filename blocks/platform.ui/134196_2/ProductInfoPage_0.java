package org.eclipse.ui.internal.about;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

class CopyTableSelectionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		Object control = HandlerUtil.getVariable(event, ISources.ACTIVE_FOCUS_CONTROL_NAME);
		if (control instanceof Table) {
			copySelection((Table) control);
		}
		return null;
	}

	public void copySelection(Table table) {
		String text = selectionToString(table);
		if (text.isEmpty()) {
			return;
		}

		Clipboard clipboard = new Clipboard(table.getDisplay());
		clipboard.setContents(new Object[] { text }, new Transfer[] { TextTransfer.getInstance() });
		clipboard.dispose();
	}

	private static String selectionToString(Table table) {
		StringBuilder buffer = new StringBuilder();

		for (TableItem tableItem : table.getSelection()) {
			if (buffer.length() > 0) {
				buffer.append(System.lineSeparator());
			}

			for (int column = 0; column < table.getColumnCount(); column++) {
				if (column > 0) {
					buffer.append('\t');
				}
				buffer.append(tableItem.getText(column));
			}
		}

		return buffer.toString();
	}

}
