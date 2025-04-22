
package org.eclipse.jface.viewers;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeEditor;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

@Deprecated
public class TableTreeViewer extends AbstractTreeViewer {
	private TableTreeEditorImpl tableEditorImpl;

	private TableTree tableTree;

	private TableTreeEditor tableTreeEditor;

	class TableTreeEditorImpl {

		private CellEditor cellEditor;

		private CellEditor[] cellEditors;

		private ICellModifier cellModifier;

		private String[] columnProperties;

		private Item tableItem;

		private int columnNumber;

		private ICellEditorListener cellEditorListener;

		private FocusListener focusListener;

		private MouseListener mouseListener;

		private int doubleClickExpirationTime;

		private ColumnViewer viewer;

		private TableTreeEditorImpl(ColumnViewer viewer) {
			this.viewer = viewer;
			initCellEditorListener();
		}

		public ColumnViewer getViewer() {
			return viewer;
		}

		private void activateCellEditor() {
			if( cellEditors != null ) {
				if( cellEditors[columnNumber] != null && cellModifier != null ) {
					Object element = tableItem.getData();
					String property = columnProperties[columnNumber];

					if( cellModifier.canModify(element, property) ) {
						cellEditor = cellEditors[columnNumber];

						cellEditor.addListener(cellEditorListener);

						Object value = cellModifier.getValue(element, property);
						cellEditor.setValue(value);
						final Control control = cellEditor.getControl();
						cellEditor.activate();
						if (control == null) {
							return;
						}
						setLayoutData(cellEditor.getLayoutData());
						setEditor(control, tableItem, columnNumber);
						cellEditor.setFocus();
						if (focusListener == null) {
							focusListener = new FocusAdapter() {
								@Override
								public void focusLost(FocusEvent e) {
									applyEditorValue();
								}
							};
						}
						control.addFocusListener(focusListener);
						mouseListener = new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								if (e.time <= doubleClickExpirationTime) {
									control.removeMouseListener(mouseListener);
									cancelEditing();
									handleDoubleClickEvent();
								} else if (mouseListener != null) {
									control.removeMouseListener(mouseListener);
								}
							}
						};
						control.addMouseListener(mouseListener);
					}
				}
			}
		}

		private void activateCellEditor(MouseEvent event) {
			if (tableItem == null || tableItem.isDisposed()) {
				return;
			}
			int columnToEdit;
			int columns = getColumnCount();
			if (columns == 0) {
				columnToEdit = 0;
			} else {
				columnToEdit = -1;
				for (int i = 0; i < columns; i++) {
					Rectangle bounds = getBounds(tableItem, i);
					if (bounds.contains(event.x, event.y)) {
						columnToEdit = i;
						break;
					}
				}
				if (columnToEdit == -1) {
					return;
				}
			}

			columnNumber = columnToEdit;
			activateCellEditor();
		}

		public void applyEditorValue() {
			CellEditor c = this.cellEditor;
			if (c != null) {
				this.cellEditor = null;
				Item t = this.tableItem;
				if (t != null && !t.isDisposed()) {
					saveEditorValue(c, t);
				}
				setEditor(null, null, 0);
				c.removeListener(cellEditorListener);
				Control control = c.getControl();
				if (control != null) {
					if (mouseListener != null) {
						control.removeMouseListener(mouseListener);
					}
					if (focusListener != null) {
						control.removeFocusListener(focusListener);
					}
				}
				c.deactivate();
			}
		}

		public void cancelEditing() {
			if (cellEditor != null) {
				setEditor(null, null, 0);
				cellEditor.removeListener(cellEditorListener);
				CellEditor oldEditor = cellEditor;
				cellEditor = null;
				oldEditor.deactivate();
			}
		}

		public void editElement(Object element, int column) {
			if (cellEditor != null) {
				applyEditorValue();
			}

			setSelection(new StructuredSelection(element), true);
			Item[] selection = getSelection();
			if (selection.length != 1) {
				return;
			}

			tableItem = selection[0];

			showSelection();
			columnNumber = column;
			activateCellEditor();

		}

		public CellEditor[] getCellEditors() {
			return cellEditors;
		}

		public ICellModifier getCellModifier() {
			return cellModifier;
		}

		public Object[] getColumnProperties() {
			return columnProperties;
		}

		public void handleMouseDown(MouseEvent event) {
			if (event.button != 1) {
				return;
			}

			if (cellEditor != null) {
				applyEditorValue();
			}

			doubleClickExpirationTime = event.time
					+ Display.getCurrent().getDoubleClickTime();

			Item[] items = getSelection();
			if (items.length != 1) {
				tableItem = null;
				return;
			}
			tableItem = items[0];

			activateCellEditor(event);
		}

		private void initCellEditorListener() {
			cellEditorListener = new ICellEditorListener() {
				@Override
				public void editorValueChanged(boolean oldValidState,
						boolean newValidState) {
				}

				@Override
				public void cancelEditor() {
					TableTreeEditorImpl.this.cancelEditing();
				}

				@Override
				public void applyEditorValue() {
					TableTreeEditorImpl.this.applyEditorValue();
				}
			};
		}

		public boolean isCellEditorActive() {
			return cellEditor != null;
		}

		private void saveEditorValue(CellEditor cellEditor, Item tableItem) {
			if( cellModifier != null ) {
				if( ! cellEditor.isValueValid() ) {
				}
			}
			String property = null;

			if( columnProperties != null && columnNumber < columnProperties.length ) {
				property = columnProperties[columnNumber];
			}
			cellModifier.modify(tableItem, property, cellEditor.getValue());
		}

		public void setCellEditors(CellEditor[] editors) {
			this.cellEditors = editors;
		}

		public void setCellModifier(ICellModifier modifier) {
			this.cellModifier = modifier;
		}

		public void setColumnProperties(String[] columnProperties) {
			this.columnProperties = columnProperties;
		}

		Rectangle getBounds(Item item, int columnNumber) {
			return ((TableTreeItem) item).getBounds(columnNumber);
		}

		int getColumnCount() {
			return getTableTree().getTable().getColumnCount();
		}

		Item[] getSelection() {
			return getTableTree().getSelection();
		}

		void setEditor(Control w, Item item, int columnNumber) {
			tableTreeEditor.setEditor(w, (TableTreeItem) item, columnNumber);
		}

		void setSelection(StructuredSelection selection, boolean b) {
			TableTreeViewer.this.setSelection(selection, b);
		}

		void showSelection() {
			getTableTree().showSelection();
		}

		void setLayoutData(CellEditor.LayoutData layoutData) {
			tableTreeEditor.horizontalAlignment = layoutData.horizontalAlignment;
			tableTreeEditor.grabHorizontal = layoutData.grabHorizontal;
			tableTreeEditor.minimumWidth = layoutData.minimumWidth;
		}

		void handleDoubleClickEvent() {
			Viewer viewer = getViewer();
			fireDoubleClick(new DoubleClickEvent(viewer, viewer.getSelection()));
			fireOpen(new OpenEvent(viewer, viewer.getSelection()));
		}
	}

	@Deprecated
	public TableTreeViewer(TableTree tree) {
		super();
		tableTree = tree;
		hookControl(tree);
		tableTreeEditor = new TableTreeEditor(tableTree);
		tableEditorImpl = new TableTreeEditorImpl(this);
	}

	@Deprecated
	public TableTreeViewer(Composite parent) {
		this(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	@Deprecated
	public TableTreeViewer(Composite parent, int style) {
		this(new TableTree(parent, style));
	}

	@Override
	protected void addTreeListener(Control c, TreeListener listener) {
		((TableTree) c).addTreeListener(listener);
	}

	@Override
	@Deprecated
	public void cancelEditing() {
		tableEditorImpl.cancelEditing();
	}

	@Override
	protected void doUpdateItem(Item item, Object element) {
		IBaseLabelProvider prov = getLabelProvider();
		ITableLabelProvider tprov = null;

		if (prov instanceof ITableLabelProvider) {
			tprov = (ITableLabelProvider) prov;
		}

		int columnCount = tableTree.getTable().getColumnCount();
		TableTreeItem ti = (TableTreeItem) item;
		for (int column = 0; column < columnCount || column == 0; column++) {
			String text = "";//$NON-NLS-1$
			Image image = null;
			if (tprov != null) {
				text = tprov.getColumnText(element, column);
				image = tprov.getColumnImage(element, column);
			} else {
				if (column == 0) {
					ViewerLabel updateLabel = new ViewerLabel(item.getText(),
							item.getImage());
					buildLabel(updateLabel, element);

					if (item.isDisposed()) {
						unmapElement(element, item);
						return;
					}

					text = updateLabel.getText();
					image = updateLabel.getImage();
				}
			}

			if (text == null) {
				text = ""; //$NON-NLS-1$
			}

			ti.setText(column, text);
			if (ti.getImage(column) != image) {
				ti.setImage(column, image);
			}

			getColorAndFontCollector().setFontsAndColors(element);
			getColorAndFontCollector().applyFontsAndColors(ti);
		}

	}

	@Override
	@Deprecated
	public void editElement(Object element, int column) {
		tableEditorImpl.editElement(element, column);
	}

	@Override
	@Deprecated
	public CellEditor[] getCellEditors() {
		return tableEditorImpl.getCellEditors();
	}

	@Override
	@Deprecated
	public ICellModifier getCellModifier() {
		return tableEditorImpl.getCellModifier();
	}

	@Override
	protected Item[] getChildren(Widget o) {
		if (o instanceof TableTreeItem) {
			return ((TableTreeItem) o).getItems();
		}
		if (o instanceof TableTree) {
			return ((TableTree) o).getItems();
		}
		return null;
	}

	@Override
	protected Item getChild(Widget widget, int index) {
		if (widget instanceof TableTreeItem) {
			return ((TableTreeItem) widget).getItem(index);
		}
		if (widget instanceof TableTree) {
			return ((TableTree) widget).getItem(index);
		}
		return null;
	}

	@Override
	@Deprecated
	public Object[] getColumnProperties() {
		return tableEditorImpl.getColumnProperties();
	}

	@Override
	@Deprecated
	public Control getControl() {
		return tableTree;
	}

	@Deprecated
	public Object getElementAt(int index) {
		TableTreeItem i = tableTree.getItems()[index];
		if (i != null) {
			return i.getData();
		}
		return null;
	}

	@Override
	protected boolean getExpanded(Item item) {
		return ((TableTreeItem) item).getExpanded();
	}

	@Override
	protected Item getItemAt(Point p) {
		return getTableTree().getTable().getItem(p);
	}

	@Override
	protected int getItemCount(Control widget) {
		return ((TableTree) widget).getItemCount();
	}

	@Override
	protected int getItemCount(Item item) {
		return ((TableTreeItem) item).getItemCount();
	}

	@Override
	protected org.eclipse.swt.widgets.Item[] getItems(
			org.eclipse.swt.widgets.Item item) {
		return ((TableTreeItem) item).getItems();
	}

	@Override
	@Deprecated
	public IBaseLabelProvider getLabelProvider() {
		return super.getLabelProvider();
	}

	@Override
	protected Item getParentItem(Item item) {
		return ((TableTreeItem) item).getParentItem();
	}

	@Override
	protected Item[] getSelection(Control widget) {
		return ((TableTree) widget).getSelection();
	}

	@Deprecated
	public TableTree getTableTree() {
		return tableTree;
	}

	@Override
	protected void hookControl(Control control) {
		super.hookControl(control);
		tableTree.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				org.eclipse.swt.widgets.TableItem[] items = tableTree
						.getTable().getItems();
				for (TableItem item : items) {
					Rectangle rect = item.getImageBounds(0);
					if (rect.contains(e.x, e.y)) {
						return;
					}
				}

				tableEditorImpl.handleMouseDown(e);
			}
		});
	}

	@Override
	@Deprecated
	public boolean isCellEditorActive() {
		return tableEditorImpl.isCellEditorActive();
	}

	@Override
	protected Item newItem(Widget parent, int flags, int ix) {
		TableTreeItem item;
		if (ix >= 0) {
			if (parent instanceof TableTreeItem) {
				item = new TableTreeItem((TableTreeItem) parent, flags, ix);
			} else {
				item = new TableTreeItem((TableTree) parent, flags, ix);
			}
		} else {
			if (parent instanceof TableTreeItem) {
				item = new TableTreeItem((TableTreeItem) parent, flags);
			} else {
				item = new TableTreeItem((TableTree) parent, flags);
			}
		}
		return item;
	}

	@Override
	protected void removeAll(Control widget) {
		((TableTree) widget).removeAll();
	}

	@Override
	@Deprecated
	public void setCellEditors(CellEditor[] editors) {
		tableEditorImpl.setCellEditors(editors);
	}

	@Override
	@Deprecated
	public void setCellModifier(ICellModifier modifier) {
		tableEditorImpl.setCellModifier(modifier);
	}

	@Override
	@Deprecated
	public void setColumnProperties(String[] columnProperties) {
		tableEditorImpl.setColumnProperties(columnProperties);
	}

	@Override
	@Deprecated
	protected void setExpanded(Item node, boolean expand) {
		((TableTreeItem) node).setExpanded(expand);
	}

	@Override
	@Deprecated
	protected void setSelection(List items) {
		TableTreeItem[] newItems = new TableTreeItem[items.size()];
		items.toArray(newItems);
		getTableTree().setSelection(newItems);
	}

	@Override
	@Deprecated
	protected void showItem(Item item) {
		getTableTree().showItem((TableTreeItem) item);
	}
}
