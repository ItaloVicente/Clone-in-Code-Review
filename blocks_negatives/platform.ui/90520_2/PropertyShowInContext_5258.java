/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Gunnar Wagenknecht - fix for bug 21756 [PropertiesView] property view sorting
 *     Kevin Milburn - [Bug 423214] [PropertiesView] add support for IColorProvider and IFontProvider
 *******************************************************************************/

package org.eclipse.ui.views.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;

/**
 * The PropertySheetViewer displays the properties of objects. The model for the
 * viewer consists of a hierarchy of <code>IPropertySheetEntry</code>.
 * <p>
 * This viewer also supports the optional catogorization of the first level
 * <code>IPropertySheetEntry</code> s by using instances of
 * <code>PropertySheetCategory</code>.
 *
 */
/* package */
class PropertySheetViewer extends Viewer {
	private Object[] input;

	private IPropertySheetEntry rootEntry;

	private PropertySheetCategory[] categories;

	private Tree tree;

	/**
	 * Maintain a map from the PropertySheet entry to its
	 * corresponding TreeItem. This is used in 'findItem' to
	 * greatly increase the performance.
	 */
	private HashMap<Object, TreeItem> entryToItemMap = new HashMap<>();

	private TreeEditor treeEditor;

	private static String[] columnLabels = {
			PropertiesMessages.PropertyViewer_property, PropertiesMessages.PropertyViewer_value };

	private static String MISCELLANEOUS_CATEGORY_NAME = PropertiesMessages.PropertyViewer_misc;

	private int columnToEdit = 1;

	private CellEditor cellEditor;

	private IPropertySheetEntryListener entryListener;

	private ICellEditorListener editorListener;

	private boolean isShowingCategories = true;

	private boolean isShowingExpertProperties = false;

	private IStatusLineManager statusLineManager;

	private ListenerList<ICellEditorActivationListener> activationListeners = new ListenerList<>();

	private PropertySheetSorter sorter = new PropertySheetSorter();

	/**
	 * Creates a property sheet viewer on a newly-created tree control
	 * under the given parent. The viewer has no input, and no root entry.
	 *
	 * @param parent
	 *			the parent control
	 */
	public PropertySheetViewer(Composite parent) {
		tree = new Tree(parent, SWT.FULL_SELECTION | SWT.SINGLE
				| SWT.HIDE_SELECTION);

		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		addColumns();

		hookControl();

		treeEditor = new TreeEditor(tree);

		createEntryListener();
		createEditorListener();
	}

	/**
	 * Activate a cell editor for the given selected tree item.
	 *
	 * @param item
	 *			the selected tree item
	 */
	private void activateCellEditor(TreeItem item) {
		tree.showSelection();

		IPropertySheetEntry activeEntry = (IPropertySheetEntry) item.getData();

		cellEditor = activeEntry.getEditor(tree);

		if (cellEditor == null) {
			return;
		}

		cellEditor.activate();

		Control control = cellEditor.getControl();
		if (control == null) {
			cellEditor.deactivate();
			cellEditor = null;
			return;
		}

		cellEditor.addListener(editorListener);

		CellEditor.LayoutData layout = cellEditor.getLayoutData();
		treeEditor.horizontalAlignment = layout.horizontalAlignment;
		treeEditor.grabHorizontal = layout.grabHorizontal;
		treeEditor.minimumWidth = layout.minimumWidth;
		treeEditor.setEditor(control, item, columnToEdit);

		setErrorMessage(cellEditor.getErrorMessage());

		cellEditor.setFocus();

		fireCellEditorActivated(cellEditor);
	}

	/**
	 * Adds a cell editor activation listener. Has no effect if an identical
	 * activation listener is already registered.
	 *
	 * @param listener
	 *			a cell editor activation listener
	 */
	/* package */
	void addActivationListener(ICellEditorActivationListener listener) {
		activationListeners.add(listener);
	}

	/**
	 * Add columns to the tree and set up the layout manager accordingly.
	 */
	private void addColumns() {

		TreeColumn[] columns = tree.getColumns();
		for (int i = 0; i < columnLabels.length; i++) {
			String string = columnLabels[i];
			if (string != null) {
				TreeColumn column;
				if (i < columns.length) {
					column = columns[i];
				} else {
					column = new TreeColumn(tree, 0);
				}
				column.setText(string);
			}
		}

		tree.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = tree.getClientArea();
				TreeColumn[] columns = tree.getColumns();
				if (area.width > 0) {
					columns[0].setWidth(area.width * 40 / 100);
					columns[1].setWidth(area.width - columns[0].getWidth() - 4);
					tree.removeControlListener(this);
				}
			}
		});

	}

	/**
	 * Asks the entry currently being edited to apply its current cell editor
	 * value.
	 */
	private void applyEditorValue() {
		TreeItem treeItem = treeEditor.getItem();
		if (treeItem == null || treeItem.isDisposed()) {
			return;
		}
		IPropertySheetEntry entry = (IPropertySheetEntry) treeItem.getData();
		entry.applyEditorValue();
	}

	/**
	 * Creates the child items for the given widget (item or tree). This
	 * method is called when the item is expanded for the first time or when an
	 * item is assigned as the root of the tree.
	 * @param widget TreeItem or Tree to create the children in.
	 */
	private void createChildren(Widget widget) {
		TreeItem[] childItems = getChildItems(widget);

		if (childItems.length > 0) {
			Object data = childItems[0].getData();
			if (data != null) {
				return;
			}
			childItems[0].dispose();
		}

		Object node = widget.getData();
		List<?> children = getChildren(node);
		if (children.isEmpty()) {
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			createItem(children.get(i), widget, i);
		}
	}

	/**
	 * Creates a new cell editor listener.
	 */
	private void createEditorListener() {
		editorListener = new ICellEditorListener() {
			@Override
			public void cancelEditor() {
				deactivateCellEditor();
			}

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
			}

			@Override
			public void applyEditorValue() {
			}
		};
	}

	/**
	 * Creates a new property sheet entry listener.
	 */
	private void createEntryListener() {
		entryListener = new IPropertySheetEntryListener() {
			@Override
			public void childEntriesChanged(IPropertySheetEntry entry) {
				if (entry == rootEntry) {
					updateChildrenOf(entry, tree);
				} else {
					TreeItem item = findItem(entry);
					if (item != null) {
						updateChildrenOf(entry, item);
					}
				}
			}

			@Override
			public void valueChanged(IPropertySheetEntry entry) {
				TreeItem item = findItem(entry);
				if (item != null) {
					updateEntry(entry, item);
				}
			}

			@Override
			public void errorMessageChanged(IPropertySheetEntry entry) {
				setErrorMessage(entry.getErrorText());
			}
		};
	}

	/**
	 * Creates a new tree item, sets the given entry or category (node)in
	 * its user data field, and adds a listener to the node if it is an entry.
	 *
	 * @param node
	 *          the entry or category associated with this item
	 * @param parent
	 *			the parent widget
	 * @param index
	 *			indicates the position to insert the item into its parent
	 */
	private void createItem(Object node, Widget parent, int index) {
		TreeItem item;
		if (parent instanceof TreeItem) {
			item = new TreeItem((TreeItem) parent, SWT.NONE, index);
		} else {
			item = new TreeItem((Tree) parent, SWT.NONE, index);
		}

		item.setData(node);

		entryToItemMap.put(node, item);

		item.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Object possibleEntry = e.widget.getData();
				if (possibleEntry != null)
					entryToItemMap.remove(possibleEntry);
			}
		});

		if (node instanceof IPropertySheetEntry) {
			((IPropertySheetEntry) node).addPropertySheetEntryListener(entryListener);
		}

		if (node instanceof IPropertySheetEntry) {
			updateEntry((IPropertySheetEntry) node, item);
		} else {
			updateCategory((PropertySheetCategory) node, item);
		}
	}

	/**
	 * Deactivate the currently active cell editor.
	 */
	/* package */
	void deactivateCellEditor() {
		treeEditor.setEditor(null, null, columnToEdit);
		if (cellEditor != null) {
			cellEditor.deactivate();
			fireCellEditorDeactivated(cellEditor);
			cellEditor.removeListener(editorListener);
			cellEditor = null;
		}
		setErrorMessage(null);
	}

	/**
	 * Sends out a selection changed event for the entry tree to all registered
	 * listeners.
	 */
	private void entrySelectionChanged() {
		SelectionChangedEvent changeEvent = new SelectionChangedEvent(this, getSelection());
		fireSelectionChanged(changeEvent);
	}

	/**
	 * Return a tree item in the property sheet that has the same entry in
	 * its user data field as the supplied entry. Return <code>null</code> if
	 * there is no such item.
	 *
	 * @param entry
	 *			the entry to serach for
	 * @return the TreeItem for the entry or <code>null</code> if
	 * there isn't one.
	 */
	private TreeItem findItem(IPropertySheetEntry entry) {
		TreeItem[] items = tree.getItems();
		for (TreeItem item : items) {
			TreeItem findItem = findItem(entry, item);
			if (findItem != null) {
				return findItem;
			}
		}
		return null;
	}

	/**
	 * Return a tree item in the property sheet that has the same entry in
	 * its user data field as the supplied entry. Return <code>null</code> if
	 * there is no such item.
	 *
	 * @param entry
	 *			the entry to search for
	 * @param item
	 *			the item look in
	 * @return the TreeItem for the entry or <code>null</code> if
	 * there isn't one.
	 */
	private TreeItem findItem(IPropertySheetEntry entry, TreeItem item) {
		Object mapItem = entryToItemMap.get(entry);
		if (mapItem != null && mapItem instanceof TreeItem)
			return (TreeItem) mapItem;

		if (entry == item.getData()) {
			return item;
		}

		TreeItem[] items = item.getItems();
		for (TreeItem childItem : items) {
			TreeItem findItem = findItem(entry, childItem);
			if (findItem != null) {
				return findItem;
			}
		}
		return null;
	}

	/**
	 * Notifies all registered cell editor activation listeners of a cell editor
	 * activation.
	 *
	 * @param activatedCellEditor
	 *			the activated cell editor
	 */
	private void fireCellEditorActivated(CellEditor activatedCellEditor) {
		for (ICellEditorActivationListener listener : activationListeners) {
			listener.cellEditorActivated(activatedCellEditor);
		}
	}

	/**
	 * Notifies all registered cell editor activation listeners of a cell editor
	 * deactivation.
	 *
	 * @param activatedCellEditor
	 *			the deactivated cell editor
	 */
	private void fireCellEditorDeactivated(CellEditor activatedCellEditor) {
		for (ICellEditorActivationListener listener : activationListeners) {
			listener.cellEditorDeactivated(activatedCellEditor);
		}
	}

	/**
	 * Returns the active cell editor of this property sheet viewer or
	 * <code>null</code> if no cell editor is active.
	 *
	 * @return the active cell editor
	 */
	public CellEditor getActiveCellEditor() {
		return cellEditor;
	}

	private TreeItem[] getChildItems(Widget widget) {
		if (widget instanceof Tree) {
			return ((Tree) widget).getItems();
		}
		else if (widget instanceof TreeItem) {
			return ((TreeItem) widget).getItems();
		}
		return new TreeItem[0];
	}

	/**
	 * Returns the sorted children of the given category or entry
	 *
	 * @param node a category or entry
	 * @return the children of the given category or entry
	 *  (element type <code>IPropertySheetEntry</code> or
	 *  <code>PropertySheetCategory</code>)
	 */
	private List<?> getChildren(Object node) {
		IPropertySheetEntry entry = null;
		PropertySheetCategory category = null;
		if (node instanceof IPropertySheetEntry) {
			entry = (IPropertySheetEntry) node;
		} else {
			category = (PropertySheetCategory) node;
		}

		List<?> children;
		if (category == null) {
			children = getChildren(entry);
		} else {
			children = getChildren(category);
		}

		return children;
	}

	/**
	 * Returns the child entries of the given entry
	 * @param entry The entry to search
	 *
	 * @return the children of the given entry (element type
	 *		 <code>IPropertySheetEntry</code>)
	 */
	private List<?> getChildren(IPropertySheetEntry entry) {
		if (entry == rootEntry && isShowingCategories) {
			if (categories.length > 1
					|| (categories.length == 1 && !categories[0]
							.getCategoryName().equals(
									MISCELLANEOUS_CATEGORY_NAME))) {
				return Arrays.asList(categories);
			}
		}

		return getSortedEntries(getFilteredEntries(entry.getChildEntries()));
	}

	/**
	 * Returns the child entries of the given category
	 *
	 * @param category The category to search
	 *
	 * @return the children of the given category (element type
	 *		 <code>IPropertySheetEntry</code>)
	 */
	private List<IPropertySheetEntry> getChildren(PropertySheetCategory category) {
		return getSortedEntries(getFilteredEntries(category.getChildEntries()));
	}

	@Override
	public Control getControl() {
		return tree;
	}

	/**
	 * Returns the entries which match the current filter.
	 *
	 * @param entries the entries to filter
	 * @return the entries which match the current filter
	 *  (element type <code>IPropertySheetEntry</code>)
	 */
	private List<IPropertySheetEntry> getFilteredEntries(IPropertySheetEntry[] entries) {
		if (isShowingExpertProperties) {
			return Arrays.asList(entries);
		}

		List<IPropertySheetEntry> filteredEntries = new ArrayList<>(entries.length);
		for (IPropertySheetEntry entry : entries) {
			if (entry != null) {
				String[] filters = entry.getFilters();
				boolean expert = false;
				if (filters != null) {
					for (String filter : filters) {
						if (filter.equals(IPropertySheetEntry.FILTER_ID_EXPERT)) {
							expert = true;
							break;
						}
					}
				}
				if (!expert) {
					filteredEntries.add(entry);
				}
			}
		}
		return filteredEntries;
	}

	/**
	 * Returns a sorted list of <code>IPropertySheetEntry</code> entries.
	 *
	 * @param unsortedEntries
	 *			unsorted list of <code>IPropertySheetEntry</code>
	 * @return a sorted list of the specified entries
	 */
	private List<IPropertySheetEntry> getSortedEntries(List<IPropertySheetEntry> unsortedEntries) {
		IPropertySheetEntry[] propertySheetEntries = unsortedEntries
				.toArray(new IPropertySheetEntry[unsortedEntries.size()]);
		sorter.sort(propertySheetEntries);
		return Arrays.asList(propertySheetEntries);
	}


	/**
	 * The <code>PropertySheetViewer</code> implementation of this method
	 * declared on <code>IInputProvider</code> returns the objects for which
	 * the viewer is currently showing properties. It returns an
	 * <code>Object[]</code> or <code>null</code>.
	 */
	@Override
	public Object getInput() {
		return input;
	}

	/**
	 * Returns the root entry for this property sheet viewer. The root entry is
	 * not visible in the viewer.
	 *
	 * @return the root entry or <code>null</code>.
	 */
	public IPropertySheetEntry getRootEntry() {
		return rootEntry;
	}

	/**
	 * The <code>PropertySheetViewer</code> implementation of this
	 * <code>ISelectionProvider</code> method returns the result as a
	 * <code>StructuredSelection</code>.
	 * <p>
	 * Note that this method only includes <code>IPropertySheetEntry</code> in
	 * the selection (no categories).
	 * </p>
	 */
	@Override
	public ISelection getSelection() {
		if (tree.getSelectionCount() == 0) {
			return StructuredSelection.EMPTY;
		}
		TreeItem[] sel = tree.getSelection();
		List<IPropertySheetEntry> entries = new ArrayList<>(sel.length);
		for (TreeItem ti : sel) {
			Object data = ti.getData();
			if (data instanceof IPropertySheetEntry) {
				entries.add((IPropertySheetEntry) data);
			}
		}
		return new StructuredSelection(entries);
	}

	/**
	 * Selection in the viewer occurred. Check if there is an active cell
	 * editor. If yes, deactivate it and check if a new cell editor must be
	 * activated.
	 *
	 * @param selection
	 *			the TreeItem that is selected
	 */
	private void handleSelect(TreeItem selection) {
		if (cellEditor != null) {
			applyEditorValue();
			deactivateCellEditor();
		}

		if (selection == null) {
			setMessage(null);
			setErrorMessage(null);
		} else {
			Object object = selection.getData();
			if (object instanceof IPropertySheetEntry) {
				IPropertySheetEntry activeEntry = (IPropertySheetEntry) object;

				setMessage(activeEntry.getDescription());

				activateCellEditor(selection);
			}
		}
		entrySelectionChanged();
	}

	/**
	 * The expand icon for a node in this viewer has been selected to collapse a
	 * subtree. Deactivate the cell editor
	 *
	 * @param event
	 *			the SWT tree event
	 */
	private void handleTreeCollapse(TreeEvent event) {
		if (cellEditor != null) {
			applyEditorValue();
			deactivateCellEditor();
		}
	}

	/**
	 * The expand icon for a node in this viewer has been selected to expand the
	 * subtree. Create the children 1 level deep.
	 * <p>
	 * Note that we use a "dummy" item (no user data) to show a "+" icon beside
	 * an item which has children before the item is expanded now that it is
	 * being expanded we have to create the real child items
	 * </p>
	 *
	 * @param event
	 *			the SWT tree event
	 */
	private void handleTreeExpand(TreeEvent event) {
		createChildren(event.item);
	}

	/**
	 * Hides the categories.
	 */
	/* package */
	void hideCategories() {
		isShowingCategories = false;
		categories = null;
		refresh();
	}

	/**
	 * Hides the expert properties.
	 */
	/* package */
	void hideExpert() {
		isShowingExpertProperties = false;
		refresh();
	}

	/**
	 * Establish this viewer as a listener on the control
	 */
	private void hookControl() {
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cellEditor == null || !cellEditor.isActivated()) {
					updateStatusLine(e.item);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.item instanceof TreeItem)
					handleSelect((TreeItem) e.item);
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				Point pt = new Point(event.x, event.y);
				TreeItem item = tree.getItem(pt);
				if (item != null) {
					handleSelect(item);
				}
			}
		});

		tree.addTreeListener(new TreeListener() {
			@Override
			public void treeExpanded(final TreeEvent event) {
				handleTreeExpand(event);
			}

			@Override
			public void treeCollapsed(final TreeEvent event) {
				handleTreeCollapse(event);
			}
		});

		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.ESC) {
					deactivateCellEditor();
				} else if (e.keyCode == SWT.F5) {
					setInput(getInput());
				}
			}
		});
	}

	/**
	 * Update the status line based on the data of item.
	 * @param item
	 */
	protected void updateStatusLine(Widget item) {
		setMessage(null);
		setErrorMessage(null);

		if (item != null) {
			if (item.getData() instanceof PropertySheetEntry) {
				PropertySheetEntry psEntry = (PropertySheetEntry) item.getData();

				String desc = psEntry.getDescription();
				if (desc != null && desc.length() > 0) {
					setMessage(psEntry.getDescription());
				} else {
					setMessage(psEntry.getDisplayName());
				}
			}

			else if (item.getData() instanceof PropertySheetCategory) {
				PropertySheetCategory psCat = (PropertySheetCategory) item.getData();
				setMessage(psCat.getCategoryName());
			}
		}
	}

	/**
	 * Updates all of the items in the tree.
	 * <p>
	 * Note that this means ensuring that the tree items reflect the state of
	 * the model (entry tree) it does not mean telling the model to update
	 * itself.
	 * </p>
	 */
	@Override
	public void refresh() {
		if (rootEntry != null) {
			updateChildrenOf(rootEntry, tree);
		}
	}

	/**
	 * Removes the given cell editor activation listener from this viewer. Has
	 * no effect if an identical activation listener is not registered.
	 *
	 * @param listener
	 *			a cell editor activation listener
	 */
	/* package */
	void removeActivationListener(ICellEditorActivationListener listener) {
		activationListeners.remove(listener);
	}

	/**
	 * Remove the given item from the tree. Remove our listener if the
	 * item's user data is a an entry then set the user data to null
	 *
	 * @param item
	 *			the item to remove
	 */
	private void removeItem(TreeItem item) {
		Object data = item.getData();
		if (data instanceof IPropertySheetEntry) {
			((IPropertySheetEntry) data)
					.removePropertySheetEntryListener(entryListener);
		}
		item.setData(null);

		entryToItemMap.remove(data);

		item.dispose();
	}

	/**
	 * Reset the selected properties to their default values.
	 */
	public void resetProperties() {
		IStructuredSelection selection = (IStructuredSelection) getSelection();

		Iterator<IPropertySheetEntry> itr = selection.iterator();
		while (itr.hasNext()) {
			itr.next().resetPropertyValue();
		}
	}

	/**
	 * Sets the error message to be displayed in the status line.
	 *
	 * @param errorMessage
	 *			the message to be displayed, or <code>null</code>
	 */
	private void setErrorMessage(String errorMessage) {
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage(errorMessage);
		}
	}

	/**
	 * The <code>PropertySheetViewer</code> implementation of this method
	 * declared on <code>Viewer</code> method sets the objects for which the
	 * viewer is currently showing properties.
	 * <p>
	 * The input must be an <code>Object[]</code> or <code>null</code>.
	 * </p>
	 *
	 * @param newInput
	 *			the input of this viewer, or <code>null</code> if none
	 */
	@Override
	public void setInput(Object newInput) {
		applyEditorValue();
		deactivateCellEditor();

		input = (Object[]) newInput;
		if (input == null) {
			input = new Object[0];
		}

		if (rootEntry != null) {
			rootEntry.setValues(input);
			updateChildrenOf(rootEntry, tree);
		}

		updateStatusLine(null);
	}

	/**
	 * Sets the message to be displayed in the status line. This message is
	 * displayed when there is no error message.
	 *
	 * @param message
	 *			the message to be displayed, or <code>null</code>
	 */
	private void setMessage(String message) {
		if (statusLineManager != null) {
			statusLineManager.setMessage(message);
		}
	}

	/**
	 * Sets the root entry for this property sheet viewer. The root entry is not
	 * visible in the viewer.
	 *
	 * @param root
	 *			the root entry
	 */
	public void setRootEntry(IPropertySheetEntry root) {
		if (rootEntry != null) {
			rootEntry.removePropertySheetEntryListener(entryListener);
		}

		rootEntry = root;

		tree.setData(rootEntry);

		rootEntry.addPropertySheetEntryListener(entryListener);

		setInput(input);
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
	}

	/**
	 * Sets the sorter for this viewer.
	 * <p>
	 * The default sorter sorts categories and entries alphabetically.
	 * A viewer update needs to be triggered after the sorter has changed.
	 * </p>
	 * @param sorter the sorter to set (<code>null</code> will reset to the
	 * default sorter)
	 * @since 3.1
	 */
	public void setSorter(PropertySheetSorter sorter) {
		if (null == sorter) {
			sorter = new PropertySheetSorter();
		}
		this.sorter = sorter;
	}

	/**
	 * Sets the status line manager this view will use to show messages.
	 *
	 * @param manager
	 *			the status line manager
	 */
	public void setStatusLineManager(IStatusLineManager manager) {
		statusLineManager = manager;
	}

	/**
	 * Shows the categories.
	 */
	/* package */
	void showCategories() {
		isShowingCategories = true;
		refresh();
	}

	/**
	 * Shows the expert properties.
	 */
	/* package */
	void showExpert() {
		isShowingExpertProperties = true;
		refresh();
	}

	/**
	 * Updates the categories. Reuses old categories if possible.
	 */
	private void updateCategories() {
		if (categories == null) {
			categories = new PropertySheetCategory[0];
		}

		List<IPropertySheetEntry> childEntries = getFilteredEntries(rootEntry.getChildEntries());

		if (childEntries.size() == 0) {
			categories = new PropertySheetCategory[0];
			return;
		}

		Map<String, PropertySheetCategory> categoryCache = new HashMap<>(categories.length * 2 + 1);
		for (PropertySheetCategory categorie : categories) {
			categorie.removeAllEntries();
			categoryCache.put(categorie.getCategoryName(), categorie);
		}

		List<PropertySheetCategory> categoriesToRemove = new ArrayList<>(Arrays.asList(categories));

		PropertySheetCategory misc = categoryCache
				.get(MISCELLANEOUS_CATEGORY_NAME);
		if (misc == null) {
			misc = new PropertySheetCategory(MISCELLANEOUS_CATEGORY_NAME);
		}
		boolean addMisc = false;

		for (int i = 0; i < childEntries.size(); i++) {
			IPropertySheetEntry childEntry = childEntries.get(i);
			String categoryName = childEntry.getCategory();
			if (categoryName == null) {
				misc.addEntry(childEntry);
				addMisc = true;
				categoriesToRemove.remove(misc);
			} else {
				PropertySheetCategory category = categoryCache.get(categoryName);
				if (category == null) {
					category = new PropertySheetCategory(categoryName);
					categoryCache.put(categoryName, category);
				} else {
					categoriesToRemove.remove(category);
				}
				category.addEntry(childEntry);
			}
		}

		if (addMisc) {
			categoryCache.put(MISCELLANEOUS_CATEGORY_NAME, misc);
		}

		ArrayList<PropertySheetCategory> categoryList = new ArrayList<>();
		Set<String> seen = new HashSet<>(childEntries.size());
		for (int i = 0; i < childEntries.size(); i++) {
			IPropertySheetEntry childEntry = childEntries
					.get(i);
			String categoryName = childEntry.getCategory();
			if (categoryName != null && !seen.contains(categoryName)) {
				seen.add(categoryName);
				PropertySheetCategory category = categoryCache.get(categoryName);
				if (category != null) {
					categoryList.add(category);
				}
			}
		}
		if (addMisc && !seen.contains(MISCELLANEOUS_CATEGORY_NAME)) {
			categoryList.add(misc);
		}

		PropertySheetCategory[] categoryArray = categoryList
			.toArray(new PropertySheetCategory[categoryList.size()]);
		sorter.sort(categoryArray);
		categories = categoryArray;
	}

	/**
	 * Update the category (but not its parent or children).
	 *
	 * @param category
	 *			the category to update
	 * @param item
	 *			the tree item for the given entry
	 */
	private void updateCategory(PropertySheetCategory category,
			TreeItem item) {
		item.setData(category);

		entryToItemMap.put(category, item);

		item.setText(0, category.getCategoryName());
		item.setText(1, ""); //$NON-NLS-1$

		if (category.getAutoExpand()) {
			createChildren(item);
			item.setExpanded(true);
			category.setAutoExpand(false);
		} else {
			updatePlus(category, item);
		}
	}

	/**
	 * Update the child entries or categories of the given entry or category. If
	 * the given node is the root entry and we are showing categories then the
	 * child entries are categories, otherwise they are entries.
	 *
	 * @param node
	 *			the entry or category whose children we will update
	 * @param widget
	 *			the widget for the given entry, either a
	 *			<code>TableTree</code> if the node is the root node or a
	 *			<code>TreeItem</code> otherwise.
	 */
	private void updateChildrenOf(Object node, Widget widget) {
		IPropertySheetEntry entry = null;
		PropertySheetCategory category = null;
		if (node instanceof IPropertySheetEntry) {
			entry = (IPropertySheetEntry) node;
		} else {
			category = (PropertySheetCategory) node;
		}

		TreeItem[] childItems = getChildItems(widget);

		TreeItem item = null;
		if (widget instanceof TreeItem) {
			item = (TreeItem) widget;
		}
		if (item != null && !item.getExpanded()) {
			for (TreeItem childItem : childItems) {
				if (childItem.getData() != null) {
					removeItem(childItem);
				}
			}

			if (category != null || entry.hasChildEntries()) {
				childItems = getChildItems(widget);
				if (childItems.length == 0) {
					new TreeItem(item, SWT.NULL);
				}
			}
			return;
		}

		if (node == rootEntry && isShowingCategories) {
			updateCategories();
		}
		List<?> children = getChildren(node);

		Set<Object> set = new HashSet<>(childItems.length * 2 + 1);

		for (TreeItem childItem : childItems) {
			Object data = childItem.getData();
			if (data != null) {
				Object e = data;
				int ix = children.indexOf(e);
					removeItem(childItem);
					set.add(e);
				}
				childItem.dispose();
			}
		}

		int oldCnt = -1;
		if (widget == tree) {
			oldCnt = tree.getItemCount();
		}

		int newSize = children.size();
		for (int i = 0; i < newSize; i++) {
			Object el = children.get(i);
			if (!set.contains(el)) {
				createItem(el, widget, i);
			}
		}

		if (widget == tree && oldCnt == 0 && tree.getItemCount() == 1) {
			tree.setRedraw(false);
			tree.setRedraw(true);
		}

		childItems = getChildItems(widget);

		for (int i = 0; i < newSize; i++) {
			Object el = children.get(i);
			if (el instanceof IPropertySheetEntry) {
				updateEntry((IPropertySheetEntry) el, childItems[i]);
			} else {
				updateCategory((PropertySheetCategory) el, childItems[i]);
				updateChildrenOf(el, childItems[i]);
			}
		}
		entrySelectionChanged();
	}

	/**
	 * Update the given entry (but not its children or parent)
	 *
	 * @param entry
	 *			the entry we will update
	 * @param item
	 *			the tree item for the given entry
	 */
	private void updateEntry(IPropertySheetEntry entry, TreeItem item) {
		item.setData(entry);

		entryToItemMap.put(entry, item);

		item.setText(0, entry.getDisplayName());
		item.setText(1, entry.getValueAsString());
		Image image = entry.getImage();
		if (item.getImage(1) != image) {
			item.setImage(1, image);
		}

		if (entry instanceof PropertySheetEntry) {
			PropertySheetEntry entry2 = (PropertySheetEntry) entry;

			Color color = entry2.getForeground();
			if (item.getForeground() != color) {
				item.setForeground(color);
				}

			color = entry2.getBackground();
			if (item.getBackground() != color) {
				item.setBackground(color);
			}

			Font font = entry2.getFont();
			if (item.getFont() != font) {
				item.setFont(font);
			}
		}

		updatePlus(entry, item);
	}

	/**
	 * Updates the "+"/"-" icon of the tree item from the given entry
	 * or category.
	 *
	 * @param node the entry or category
	 * @param item the tree item being updated
	 */
	private void updatePlus(Object node, TreeItem item) {
		IPropertySheetEntry entry = null;
		PropertySheetCategory category = null;
		if (node instanceof IPropertySheetEntry) {
			entry = (IPropertySheetEntry) node;
		} else {
			category = (PropertySheetCategory) node;
		}

		boolean hasPlus = item.getItemCount() > 0;
		boolean needsPlus = category != null || entry.hasChildEntries();
		boolean removeAll = false;
		boolean addDummy = false;

		if (hasPlus != needsPlus) {
			if (needsPlus) {
				addDummy = true;
			} else {
				removeAll = true;
			}
		}
		if (removeAll) {
			TreeItem[] items = item.getItems();
			for (TreeItem item2 : items) {
				removeItem(item2);
			}
		}

		if (addDummy) {
			new TreeItem(item, SWT.NULL); // append a dummy to create the
		}
	}

	void dispose() {
		if (tree != null && !tree.isDisposed()) {
			tree.dispose();
		}
		if (rootEntry != null) {
			if (entryListener != null) {
				rootEntry.removePropertySheetEntryListener(entryListener);
			}
			rootEntry = null;
		}
		activationListeners.clear();
		entryToItemMap.clear();
		cellEditor = null;
		editorListener = null;
		entryListener = null;
		input = null;
		sorter = null;
		statusLineManager = null;
		tree = null;
		treeEditor = null;
	}
}
