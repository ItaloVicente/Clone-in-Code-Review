package org.eclipse.egit.ui.internal.components;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

public abstract class ControlLabelProvider extends ColumnLabelProvider {

	private final Map<TableItem, Editor> editors = new HashMap<>();

	@Override
	public void dispose() {
		editors.clear();
		super.dispose();
	}

	@Override
	public String getText(Object element) {
		return null;
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
		Object obj = cell.getElement();
		Widget w = cell.getViewerRow().getItem();
		if (w instanceof TableItem) {
			TableItem item = (TableItem) w;
			Table table = item.getParent();
			Editor editor = editors.get(item);
			if (editor == null) {
				Control control = setEditor(cell, table, null, obj);
				if (control == null) {
					return;
				}
				control.pack();
				editor = new Editor(table);
				editor.horizontalAlignment = SWT.CENTER;
				editor.verticalAlignment = SWT.CENTER;
				Point size = control.getSize();
				editor.minimumWidth = size.x;
				editor.minimumHeight = size.y;
				editors.put(item, editor);
				editor.setEditor(control, item, cell.getColumnIndex());
				editor.connect();
			} else {
				Control existing = editor.getEditor();
				Control control = setEditor(cell, table, existing, obj);
				if (control != existing) {
					if (!existing.isDisposed()) {
						existing.dispose();
					}
					if (control == null) {
						editor.dispose();
						return;
					}
					control.pack();
					editor.setEditor(control);
				}
			}
		}
	}

	public abstract Control setEditor(ViewerCell cell, Composite parent,
			Control control, Object element);

	private class Editor extends TableEditor {

		private DisposeListener disposer;

		private final Table table;

		public Editor(Table table) {
			super(table);
			this.table = table;
		}

		@Override
		public void layout() {
			if (Util.isGtk() && SWT.getVersion() <= 4924) {
				TableItem item = getItem();
				if (item != null) {
					Rectangle rect = item.getBounds();
					if (table.getHeaderVisible()) {
						Control editor = getEditor();
						if (editor != null && !editor.isDisposed()) {
							editor.setVisible(
									rect.y >= table.getHeaderHeight());
						}
					}
				}
			}
			super.layout();
		}

		public void connect() {
			TableItem item = getItem();
			if (item != null) {
				disposer = e -> {
					Control editor = getEditor();
					if (editor != null && !editor.isDisposed()) {
						editor.dispose();
					}
					dispose();
				};
				item.addDisposeListener(disposer);
			}
		}

		private void disconnect() {
			if (disposer != null) {
				TableItem item = getItem();
				if (item != null) {
					editors.remove(item);
					item.removeDisposeListener(disposer);
				}
				disposer = null;
			}
		}

		@Override
		public void dispose() {
			disconnect();
			super.dispose();
		}
	}
}
