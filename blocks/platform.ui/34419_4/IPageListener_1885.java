package org.eclipse.ui;

public interface IPageLayout {

    public static String ID_EDITOR_AREA = "org.eclipse.ui.editorss"; //$NON-NLS-1$

    @Deprecated
	public static String ID_RES_NAV = "org.eclipse.ui.views.ResourceNavigator"; //$NON-NLS-1$

    public static String ID_PROJECT_EXPLORER = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    public static String ID_PROP_SHEET = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

    public static String ID_OUTLINE = "org.eclipse.ui.views.ContentOutline"; //$NON-NLS-1$

    public static String ID_BOOKMARKS = "org.eclipse.ui.views.BookmarkView"; //$NON-NLS-1$

    public static String ID_PROBLEM_VIEW = "org.eclipse.ui.views.ProblemView"; //$NON-NLS-1$
    
    public static String ID_PROGRESS_VIEW = "org.eclipse.ui.views.ProgressView"; //$NON-NLS-1$

    public static String ID_TASK_LIST = "org.eclipse.ui.views.TaskList"; //$NON-NLS-1$

    public static final String ID_NAVIGATE_ACTION_SET = "org.eclipse.ui.NavigateActionSet"; //$NON-NLS-1$

    public static final int LEFT = 1;

    public static final int RIGHT = 2;

    public static final int TOP = 3;

    public static final int BOTTOM = 4;

    public static final float RATIO_MIN = 0.05f;

    public static final float RATIO_MAX = 0.95f;

    public static final float DEFAULT_FASTVIEW_RATIO = 0.3f;

    public static final float DEFAULT_VIEW_RATIO = 0.5f;

    public static final float INVALID_RATIO = -1f;

    public static final float NULL_RATIO = -2f;

    public void addActionSet(String actionSetId);

    public void addFastView(String viewId);

    public void addFastView(String viewId, float ratio);

    public void addNewWizardShortcut(String id);

    public void addPerspectiveShortcut(String id);

    public void addPlaceholder(String viewId, int relationship, float ratio,
            String refId);

    public void addShowInPart(String id);

    public void addShowViewShortcut(String id);

    public void addView(String viewId, int relationship, float ratio,
            String refId);

    public IFolderLayout createFolder(String folderId, int relationship,
            float ratio, String refId);

    public IPlaceholderFolderLayout createPlaceholderFolder(String folderId,
            int relationship, float ratio, String refId);

    public String getEditorArea();

    public boolean isEditorAreaVisible();

    public void setEditorAreaVisible(boolean showEditorArea);

    @Deprecated
	public int getEditorReuseThreshold();

    @Deprecated
	public void setEditorReuseThreshold(int openEditors);

    public void setFixed(boolean isFixed);

    public boolean isFixed();

    public IViewLayout getViewLayout(String id);

    public void addStandaloneView(String viewId, boolean showTitle,
            int relationship, float ratio, String refId);
    
    public void addStandaloneViewPlaceholder(String viewId, int relationship,
			float ratio, String refId, boolean showTitle);


    public IPerspectiveDescriptor getDescriptor();
    
    public IPlaceholderFolderLayout getFolderForView(String id);
}
