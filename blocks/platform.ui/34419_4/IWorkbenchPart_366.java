package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.util.IPropertyChangeListener;

public interface IWorkbenchPage extends IPartService, ISelectionService {
	@Deprecated
	public static final String EDITOR_ID_ATTR = "org.eclipse.ui.editorID"; //$NON-NLS-1$

	public static final String CHANGE_RESET = "reset"; //$NON-NLS-1$

	public static final String CHANGE_RESET_COMPLETE = "resetComplete"; //$NON-NLS-1$

	public static final String CHANGE_VIEW_SHOW = "viewShow"; //$NON-NLS-1$

	public static final String CHANGE_VIEW_HIDE = "viewHide"; //$NON-NLS-1$

	public static final String CHANGE_EDITOR_OPEN = "editorOpen"; //$NON-NLS-1$

	public static final String CHANGE_EDITOR_CLOSE = "editorClose"; //$NON-NLS-1$

	public static final String CHANGE_EDITOR_AREA_SHOW = "editorAreaShow"; //$NON-NLS-1$

	public static final String CHANGE_EDITOR_AREA_HIDE = "editorAreaHide"; //$NON-NLS-1$

	public static final String CHANGE_ACTION_SET_SHOW = "actionSetShow"; //$NON-NLS-1$

	public static final String CHANGE_ACTION_SET_HIDE = "actionSetHide"; //$NON-NLS-1$

	public static final String CHANGE_FAST_VIEW_ADD = "fastViewAdd"; //$NON-NLS-1$

	public static final String CHANGE_FAST_VIEW_REMOVE = "fastViewRemove"; //$NON-NLS-1$

	public static final String CHANGE_WORKING_SET_REPLACE = "workingSetReplace"; //$NON-NLS-1$	 

	public static final String CHANGE_WORKING_SETS_REPLACE = "workingSetsReplace"; //$NON-NLS-1$	 

	public static final int VIEW_ACTIVATE = 1;

	public static final int VIEW_VISIBLE = 2;

	public static final int VIEW_CREATE = 3;

	public static final int MATCH_NONE = 0;

	public static final int MATCH_INPUT = 1;

	public static final int MATCH_ID = 2;

	public static final int STATE_MINIMIZED = 0;

	public static final int STATE_MAXIMIZED = 1;

	public static final int STATE_RESTORED = 2;

	public void activate(IWorkbenchPart part);

	@Deprecated
	public void addPropertyChangeListener(IPropertyChangeListener listener);

	public void bringToTop(IWorkbenchPart part);

	public boolean close();

	public boolean closeAllEditors(boolean save);

	public boolean closeEditors(IEditorReference[] editorRefs, boolean save);

	public boolean closeEditor(IEditorPart editor, boolean save);

	public IViewPart findView(String viewId);

	public IViewReference findViewReference(String viewId);

	public IViewReference findViewReference(String viewId, String secondaryId);

	public IEditorPart getActiveEditor();

	public IEditorPart findEditor(IEditorInput input);

	public IEditorReference[] findEditors(IEditorInput input, String editorId,
			int matchFlags);

	@Deprecated
	public IEditorPart[] getEditors();

	public IEditorReference[] getEditorReferences();

	public IEditorPart[] getDirtyEditors();

	public IAdaptable getInput();

	public String getLabel();

	public IPerspectiveDescriptor getPerspective();

	public IViewReference[] getViewReferences();

	@Deprecated
	public IViewPart[] getViews();

	public IWorkbenchWindow getWorkbenchWindow();

	@Deprecated
	public IWorkingSet getWorkingSet();

	public void hideActionSet(String actionSetID);

	public void hideView(IViewPart view);

	public void hideView(IViewReference view);

	public boolean isPartVisible(IWorkbenchPart part);

	public boolean isEditorAreaVisible();

	public void reuseEditor(IReusableEditor editor, IEditorInput input);

	public IEditorPart openEditor(IEditorInput input, String editorId)
			throws PartInitException;

	public IEditorPart openEditor(IEditorInput input, String editorId,
			boolean activate) throws PartInitException;

	public IEditorPart openEditor(final IEditorInput input,
			final String editorId, final boolean activate, final int matchFlags)
			throws PartInitException;

	public void removePropertyChangeListener(IPropertyChangeListener listener);

	public void resetPerspective();

	public boolean saveAllEditors(boolean confirm);

	public boolean saveEditor(IEditorPart editor, boolean confirm);

	public void savePerspective();

	public void savePerspectiveAs(IPerspectiveDescriptor perspective);

	public void setEditorAreaVisible(boolean showEditorArea);

	public void setPerspective(IPerspectiveDescriptor perspective);

	public void showActionSet(String actionSetID);

	public IViewPart showView(String viewId) throws PartInitException;

	public IViewPart showView(String viewId, String secondaryId, int mode)
			throws PartInitException;

	public boolean isEditorPinned(IEditorPart editor);

	@Deprecated
	public int getEditorReuseThreshold();

	@Deprecated
	public void setEditorReuseThreshold(int openEditors);

	public INavigationHistory getNavigationHistory();

	IViewPart[] getViewStack(IViewPart part);

	public String[] getNewWizardShortcuts();

	public String[] getPerspectiveShortcuts();

	public String[] getShowViewShortcuts();

	public IPerspectiveDescriptor[] getOpenPerspectives();

	public IPerspectiveDescriptor[] getSortedPerspectives();

	public void closePerspective(IPerspectiveDescriptor desc,
			boolean saveParts, boolean closePage);

	public void closeAllPerspectives(boolean saveEditors, boolean closePage);

	public IExtensionTracker getExtensionTracker();

	public IWorkingSet[] getWorkingSets();

	public void setWorkingSets(IWorkingSet[] sets);

	public IWorkingSet getAggregateWorkingSet();

	public boolean isPageZoomed();

	public void zoomOut();

	public void toggleZoom(IWorkbenchPartReference ref);

	public int getPartState(IWorkbenchPartReference ref);

	public void setPartState(IWorkbenchPartReference ref, int state);

	public IWorkbenchPartReference getReference(IWorkbenchPart part);

	public void showEditor(IEditorReference ref);

	public void hideEditor(IEditorReference ref);

	public IEditorReference[] openEditors(final IEditorInput[] inputs, final String[] editorIDs, 
			final int matchFlags) throws MultiPartInitException;

	public IEditorReference[] openEditors(final IEditorInput[] inputs, final String[] editorIDs,
			final IMemento[] mementos, final int matchFlags, final int activateIndex)
			throws MultiPartInitException;

	public IMemento[] getEditorState(IEditorReference[] editorRefs, boolean includeInputState);
}
