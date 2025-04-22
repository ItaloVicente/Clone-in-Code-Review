
package org.eclipse.ui.views.markers.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.UpdateMarkersOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.CellEditorActionHandler;

public class BookmarkView extends MarkerView {

	private final IField[] HIDDEN_FIELDS = { new FieldCreationTime() };

	private final static String[] ROOT_TYPES = { IMarker.BOOKMARK };

	private final static String[] TABLE_COLUMN_PROPERTIES = {IMarker.MESSAGE,
		Util.EMPTY_STRING,
		Util.EMPTY_STRING,
		Util.EMPTY_STRING
	};

	private final static String TAG_DIALOG_SECTION = "org.eclipse.ui.views.bookmark"; //$NON-NLS-1$

	private final IField[] VISIBLE_FIELDS = {new FieldMessage(),
			new FieldResource(), new FieldFolder(), new FieldLineNumber() };

	private ICellModifier cellModifier = new ICellModifier() {
		@Override
		public Object getValue(Object element, String property) {
			if (element instanceof ConcreteMarker
					&& IMarker.MESSAGE.equals(property)) {
				return ((ConcreteMarker) element).getDescription();
			}
			return null;
		}

		@Override
		public boolean canModify(Object element, String property) {
			return element instanceof ConcreteMarker && IMarker.MESSAGE.equals(property);
		}

		@Override
		public void modify(Object element, String property, Object value) {
			if (element instanceof Item) {
				Item item = (Item) element;
				Object data = item.getData();

				if (data instanceof ConcreteMarker) {
					IMarker marker = ((ConcreteMarker) data).getMarker();

					try {
						if (!marker.getAttribute(property).equals(value)) {
							if (IMarker.MESSAGE.equals(property)) {
								Map attrs = new HashMap();
								attrs.put(IMarker.MESSAGE, value);
								IUndoableOperation op = new UpdateMarkersOperation(marker, attrs, MarkerMessages.modifyBookmark_title, true);
						           PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(
						        		   op, null, WorkspaceUndoUtil.getUIInfoAdapter(getSite().getShell()));
							}
						}
					} catch (ExecutionException e) {
						if (e.getCause() instanceof CoreException) {
							ErrorDialog.openError(
									getSite().getShell(),
									MarkerMessages.errorModifyingBookmark, null, ((CoreException)e.getCause()).getStatus()); 
						} else {
							IDEWorkbenchPlugin.log(MarkerMessages.errorModifyingBookmark, e); 
						}
					} catch (CoreException e) {
						ErrorDialog.openError(
								getSite().getShell(),
								MarkerMessages.errorModifyingBookmark, null, e.getStatus()); 
					}
				}
			}
		}
	};

	private CellEditorActionHandler cellEditorActionHandler;

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		TreeViewer treeViewer = getViewer();
		CellEditor cellEditors[] = new CellEditor[treeViewer.getTree()
				.getColumnCount()];
		CellEditor descriptionCellEditor = new TextCellEditor(treeViewer
				.getTree());
		cellEditors[0] = descriptionCellEditor;
		treeViewer.setCellEditors(cellEditors);
		treeViewer.setCellModifier(cellModifier);
		treeViewer.setColumnProperties(TABLE_COLUMN_PROPERTIES);

		cellEditorActionHandler = new CellEditorActionHandler(getViewSite()
				.getActionBars());
		cellEditorActionHandler.addCellEditor(descriptionCellEditor);
		cellEditorActionHandler.setCopyAction(copyAction);
		cellEditorActionHandler.setPasteAction(pasteAction);
		cellEditorActionHandler.setDeleteAction(deleteAction);
		cellEditorActionHandler.setSelectAllAction(selectAllAction);
		cellEditorActionHandler.setUndoAction(undoAction);
		cellEditorActionHandler.setRedoAction(redoAction);
	}

	@Override
	public void dispose() {
		if (cellEditorActionHandler != null) {
			cellEditorActionHandler.dispose();
		}

		super.dispose();
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings workbenchSettings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings settings = workbenchSettings
				.getSection(TAG_DIALOG_SECTION);

		if (settings == null) {
			settings = workbenchSettings.addNewSection(TAG_DIALOG_SECTION);
		}

		return settings;
	}

	@Override
	protected IField[] getSortingFields() {
		IField[] all = new IField[VISIBLE_FIELDS.length + HIDDEN_FIELDS.length];
		
		System.arraycopy(VISIBLE_FIELDS, 0, all, 0, VISIBLE_FIELDS.length);
		System.arraycopy(HIDDEN_FIELDS, 0, all, VISIBLE_FIELDS.length, HIDDEN_FIELDS.length);
		
		return all;
	}
	
	@Override
	protected IField[] getAllFields() {
		return getSortingFields();
	}

	@Override
	protected String[] getRootTypes() {
		return ROOT_TYPES;
	}


	@Override
	public void setSelection(IStructuredSelection structuredSelection,
			boolean reveal) {
		super.setSelection(structuredSelection, reveal);
	}

	@Override
	protected String[] getMarkerTypes() {
		return new String[] { IMarker.BOOKMARK };
	}

	@Override
	protected DialogMarkerFilter createFiltersDialog() {

		MarkerFilter[] filters = getUserFilters();
		BookmarkFilter[] bookmarkFilters = new BookmarkFilter[filters.length];
		System.arraycopy(filters, 0, bookmarkFilters, 0, filters.length);
		return new DialogBookmarkFilter(getSite().getShell(), bookmarkFilters);
	}

	@Override
	protected String getStaticContextId() {
		return PlatformUI.PLUGIN_ID + ".bookmark_view_context"; //$NON-NLS-1$
	}

	@Override
	protected MarkerFilter createFilter(String name) {
		return new BookmarkFilter(name);
	}

	@Override
	protected String getSectionTag() {
		return TAG_DIALOG_SECTION;
	}
	
	@Override
	void fillContextMenuAdditions(IMenuManager manager) {
		
	}
	
	@Override
	String getMarkerEnablementPreferenceName() {
		return IDEInternalPreferences.LIMIT_BOOKMARKS;
	}
	
	@Override
	String getMarkerLimitPreferenceName() {
		return IDEInternalPreferences.BOOKMARKS_LIMIT;
	}
	
	@Override
	String getFiltersPreferenceName() {
		return IDEInternalPreferences.BOOKMARKS_FILTERS;
	}
	
	@Override
	protected String getMarkerName() {
		return MarkerMessages.bookmark_title;
	}
	
	@Override
	protected IUndoContext getUndoContext() {
		return WorkspaceUndoUtil.getBookmarksUndoContext();
	}
	
}
