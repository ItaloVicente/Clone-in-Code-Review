
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

class PropertySheetViewer extends Viewer {
    private Object[] input;

    private IPropertySheetEntry rootEntry;

    private PropertySheetCategory[] categories;

    private Tree tree;

    private HashMap entryToItemMap = new HashMap();
    
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

    private ListenerList activationListeners = new ListenerList();
    
    private PropertySheetSorter sorter = new PropertySheetSorter();

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

    void addActivationListener(ICellEditorActivationListener listener) {
        activationListeners.add(listener);
    }

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

    private void applyEditorValue() {
        TreeItem treeItem = treeEditor.getItem();
        if (treeItem == null || treeItem.isDisposed()) {
			return;
		}
        IPropertySheetEntry entry = (IPropertySheetEntry) treeItem.getData();
        entry.applyEditorValue();
    }

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
        List children = getChildren(node);
        if (children.isEmpty()) {
            return;
		}
        for (int i = 0; i < children.size(); i++) {
            createItem(children.get(i), widget, i);
        }
    }

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
			((IPropertySheetEntry) node)
                    .addPropertySheetEntryListener(entryListener);
		}

        if (node instanceof IPropertySheetEntry) {
			updateEntry((IPropertySheetEntry) node, item);
		} else {
			updateCategory((PropertySheetCategory) node, item);
		}
    }

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

    private void entrySelectionChanged() {
        SelectionChangedEvent changeEvent = new SelectionChangedEvent(this,
                getSelection());
        fireSelectionChanged(changeEvent);
    }

    private TreeItem findItem(IPropertySheetEntry entry) {
        TreeItem[] items = tree.getItems();
        for (int i = 0; i < items.length; i++) {
            TreeItem item = items[i];
            TreeItem findItem = findItem(entry, item);
            if (findItem != null) {
				return findItem;
			}
        }
        return null;
    }

    private TreeItem findItem(IPropertySheetEntry entry, TreeItem item) {
    	Object mapItem = entryToItemMap.get(entry);
    	if (mapItem != null && mapItem instanceof TreeItem)
    		return (TreeItem) mapItem;
    	
        if (entry == item.getData()) {
			return item;
		}

        TreeItem[] items = item.getItems();
        for (int i = 0; i < items.length; i++) {
            TreeItem childItem = items[i];
            TreeItem findItem = findItem(entry, childItem);
            if (findItem != null) {
				return findItem;
			}
        }
        return null;
    }

    private void fireCellEditorActivated(CellEditor activatedCellEditor) {
        Object[] listeners = activationListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((ICellEditorActivationListener) listeners[i])
                    .cellEditorActivated(activatedCellEditor);
        }
    }

    private void fireCellEditorDeactivated(CellEditor activatedCellEditor) {
        Object[] listeners = activationListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((ICellEditorActivationListener) listeners[i])
                    .cellEditorDeactivated(activatedCellEditor);
        }
    }

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
    
    private List getChildren(Object node) {
        IPropertySheetEntry entry = null;
        PropertySheetCategory category = null;
        if (node instanceof IPropertySheetEntry) {
			entry = (IPropertySheetEntry) node;
		} else {
			category = (PropertySheetCategory) node;
		}

        List children;
        if (category == null) {
			children = getChildren(entry);
		} else {
			children = getChildren(category);
		}

        return children;
    }

    private List getChildren(IPropertySheetEntry entry) {
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

    private List getChildren(PropertySheetCategory category) {
        return getSortedEntries(getFilteredEntries(category.getChildEntries()));
    }

    @Override
	public Control getControl() {
        return tree;
    }

    private List getFilteredEntries(IPropertySheetEntry[] entries) {
        if (isShowingExpertProperties) {
			return Arrays.asList(entries);
		}

        List filteredEntries = new ArrayList(entries.length);
        for (int i = 0; i < entries.length; i++) {
            IPropertySheetEntry entry = entries[i];
            if (entry != null) {
                String[] filters = entry.getFilters();
                boolean expert = false;
                if (filters != null) {
                    for (int j = 0; j < filters.length; j++) {
                        if (filters[j].equals(IPropertySheetEntry.FILTER_ID_EXPERT)) {
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
    
	private List getSortedEntries(List unsortedEntries) {
		IPropertySheetEntry[] propertySheetEntries = (IPropertySheetEntry[]) unsortedEntries
				.toArray(new IPropertySheetEntry[unsortedEntries.size()]);
		sorter.sort(propertySheetEntries);
		return Arrays.asList(propertySheetEntries);
	}
    

    @Override
	public Object getInput() {
        return input;
    }

    public IPropertySheetEntry getRootEntry() {
        return rootEntry;
    }

    @Override
	public ISelection getSelection() {
        if (tree.getSelectionCount() == 0) {
			return StructuredSelection.EMPTY;
		}
        TreeItem[] sel = tree.getSelection();
        List entries = new ArrayList(sel.length);
        for (int i = 0; i < sel.length; i++) {
            TreeItem ti = sel[i];
            Object data = ti.getData();
            if (data instanceof IPropertySheetEntry) {
				entries.add(data);
			}
        }
        return new StructuredSelection(entries);
    }

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

    private void handleTreeCollapse(TreeEvent event) {
        if (cellEditor != null) {
            applyEditorValue();
            deactivateCellEditor();
        }
    }

    private void handleTreeExpand(TreeEvent event) {
        createChildren(event.item);
    }

    void hideCategories() {
        isShowingCategories = false;
        categories = null;
        refresh();
    }

    void hideExpert() {
        isShowingExpertProperties = false;
        refresh();
    }

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

    @Override
	public void refresh() {
        if (rootEntry != null) {
            updateChildrenOf(rootEntry, tree);
        }
    }

    void removeActivationListener(ICellEditorActivationListener listener) {
        activationListeners.remove(listener);
    }

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

    public void resetProperties() {
        IStructuredSelection selection = (IStructuredSelection) getSelection();

        Iterator itr = selection.iterator();
        while (itr.hasNext()) {
			((IPropertySheetEntry) itr.next()).resetPropertyValue();
		}
    }

    private void setErrorMessage(String errorMessage) {
        if (statusLineManager != null) {
			statusLineManager.setErrorMessage(errorMessage);
		}
    }

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

    private void setMessage(String message) {
        if (statusLineManager != null) {
			statusLineManager.setMessage(message);
		}
    }

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

	public void setSorter(PropertySheetSorter sorter) {
		if (null == sorter) {
			sorter = new PropertySheetSorter();
		}
		this.sorter = sorter;
	}

    public void setStatusLineManager(IStatusLineManager manager) {
        statusLineManager = manager;
    }

    void showCategories() {
        isShowingCategories = true;
        refresh();
    }

    void showExpert() {
        isShowingExpertProperties = true;
        refresh();
    }

    private void updateCategories() {
        if (categories == null) {
			categories = new PropertySheetCategory[0];
		}

        List childEntries = getFilteredEntries(rootEntry.getChildEntries());

        if (childEntries.size() == 0) {
            categories = new PropertySheetCategory[0];
            return;
        }

        Map categoryCache = new HashMap(categories.length * 2 + 1);
        for (int i = 0; i < categories.length; i++) {
            categories[i].removeAllEntries();
            categoryCache.put(categories[i].getCategoryName(), categories[i]);
        }

        List categoriesToRemove = new ArrayList(Arrays.asList(categories));

        PropertySheetCategory misc = (PropertySheetCategory) categoryCache
                .get(MISCELLANEOUS_CATEGORY_NAME);
        if (misc == null) {
			misc = new PropertySheetCategory(MISCELLANEOUS_CATEGORY_NAME);
		}
        boolean addMisc = false;

        for (int i = 0; i < childEntries.size(); i++) {
            IPropertySheetEntry childEntry = (IPropertySheetEntry) childEntries
                    .get(i);
            String categoryName = childEntry.getCategory();
            if (categoryName == null) {
                misc.addEntry(childEntry);
                addMisc = true;
                categoriesToRemove.remove(misc);
            } else {
                PropertySheetCategory category = (PropertySheetCategory) categoryCache
                        .get(categoryName);
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
        
        ArrayList categoryList = new ArrayList();
        Set seen = new HashSet(childEntries.size());
        for (int i = 0; i < childEntries.size(); i++) {
            IPropertySheetEntry childEntry = (IPropertySheetEntry) childEntries
                    .get(i);
            String categoryName = childEntry.getCategory();
            if (categoryName != null && !seen.contains(categoryName)) {
                seen.add(categoryName);
                PropertySheetCategory category = (PropertySheetCategory) categoryCache
                        .get(categoryName);
                if (category != null) { 
                    categoryList.add(category);
                }
            }
        }
        if (addMisc && !seen.contains(MISCELLANEOUS_CATEGORY_NAME)) {
            categoryList.add(misc);
        }
        
        PropertySheetCategory[] categoryArray = (PropertySheetCategory[]) categoryList
        	.toArray(new PropertySheetCategory[categoryList.size()]);
        sorter.sort(categoryArray);
        categories = categoryArray;
    }

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
            for (int i = 0; i < childItems.length; i++) {
                if (childItems[i].getData() != null) {
                    removeItem(childItems[i]);
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
        List children = getChildren(node);

        Set set = new HashSet(childItems.length * 2 + 1);

        for (int i = 0; i < childItems.length; i++) {
            Object data = childItems[i].getData();
            if (data != null) {
                Object e = data;
                int ix = children.indexOf(e);
                if (ix < 0) { // not found
                    removeItem(childItems[i]);
                } else { // found
                    set.add(e);
                }
            } else if (data == null) { // the dummy
                childItems[i].dispose();
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
            for (int i = 0; i < items.length; i++) {
                removeItem(items[i]);
            }
        }

        if (addDummy) {
            new TreeItem(item, SWT.NULL); // append a dummy to create the
        }
    }
}
