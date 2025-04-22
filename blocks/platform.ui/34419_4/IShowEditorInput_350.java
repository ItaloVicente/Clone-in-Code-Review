package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Device;

public interface ISharedImages {
    public final static String IMG_DEC_FIELD_ERROR = "IMG_DEC_FIELD_ERROR"; //$NON-NLS-1$

    public final static String IMG_DEC_FIELD_WARNING = "IMG_DEC_FIELD_WARNING"; //$NON-NLS-1$

    public final static String IMG_DEF_VIEW = "IMG_DEF_VIEW"; //$NON-NLS-1$

    public final static String IMG_ELCL_COLLAPSEALL = "IMG_ELCL_COLLAPSEALL"; //$NON-NLS-1$

    public final static String IMG_ELCL_COLLAPSEALL_DISABLED = "IMG_ELCL_COLLAPSEALL_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ELCL_REMOVE = "IMG_ELCL_REMOVE"; //$NON-NLS-1$

    public final static String IMG_ELCL_REMOVE_DISABLED = "IMG_ELCL_REMOVE_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ELCL_REMOVEALL = "IMG_ELCL_REMOVEALL"; //$NON-NLS-1$

    public final static String IMG_ELCL_REMOVEALL_DISABLED = "IMG_ELCL_REMOVEALL_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ELCL_STOP = "IMG_ELCL_STOP"; //$NON-NLS-1$

    public final static String IMG_ELCL_STOP_DISABLED = "IMG_ELCL_STOP_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ELCL_SYNCED = "IMG_ELCL_SYNCED"; //$NON-NLS-1$

    public final static String IMG_ELCL_SYNCED_DISABLED = "IMG_ELCL_SYNCED_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_CLEAR = "IMG_ETOOL_CLEAR"; //$NON-NLS-1$

    public final static String IMG_ETOOL_CLEAR_DISABLED = "IMG_ETOOL_CLEAR_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_DEF_PERSPECTIVE = "IMG_ETOOL_DEF_PERSPECTIVE"; //$NON-NLS-1$

    public final static String IMG_ETOOL_DELETE = "IMG_ETOOL_DELETE"; //$NON-NLS-1$

    public final static String IMG_ETOOL_DELETE_DISABLED = "IMG_ETOOL_DELETE_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_HOME_NAV = "IMG_ETOOL_HOME_NAV"; //$NON-NLS-1$

    public final static String IMG_ETOOL_HOME_NAV_DISABLED = "IMG_ETOOL_HOME_NAV_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_PRINT_EDIT = "IMG_ETOOL_PRINT_EDIT"; //$NON-NLS-1$

    public final static String IMG_ETOOL_PRINT_EDIT_DISABLED = "IMG_ETOOL_PRINT_EDIT_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVE_EDIT = "IMG_ETOOL_SAVE_EDIT"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVE_EDIT_DISABLED = "IMG_ETOOL_SAVE_EDIT_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVEALL_EDIT = "IMG_ETOOL_SAVEALL_EDIT"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVEALL_EDIT_DISABLED = "IMG_ETOOL_SAVEALL_EDIT_DISABLED"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVEAS_EDIT = "IMG_ETOOL_SAVEAS_EDIT"; //$NON-NLS-1$

    public final static String IMG_ETOOL_SAVEAS_EDIT_DISABLED = "IMG_ETOOL_SAVEAS_EDIT_DISABLED"; //$NON-NLS-1$

    public final static String IMG_LCL_LINKTO_HELP = "IMG_LCL_LINKTO_HELP"; //$NON-NLS-1$

    public final static String IMG_OBJ_ADD = "IMG_OBJ_ADD"; //$NON-NLS-1$

    public final static String IMG_OBJ_ELEMENT = "IMG_OBJ_ELEMENTS"; //$NON-NLS-1$

    public final static String IMG_OBJ_FILE = "IMG_OBJ_FILE"; //$NON-NLS-1$

    public final static String IMG_OBJ_FOLDER = "IMG_OBJ_FOLDER"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_OBJ_PROJECT = "IMG_OBJ_PROJECT"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_OBJ_PROJECT_CLOSED = "IMG_OBJ_PROJECT_CLOSED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_OBJS_BKMRK_TSK = "IMG_OBJS_BKMRK_TSK"; //$NON-NLS-1$

    public final static String IMG_OBJS_ERROR_TSK = "IMG_OBJS_ERROR_TSK"; //$NON-NLS-1$

    public final static String IMG_OBJS_INFO_TSK = "IMG_OBJS_INFO_TSK"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_OBJS_TASK_TSK = "IMG_OBJS_TASK_TSK"; //$NON-NLS-1$

    public final static String IMG_OBJS_WARN_TSK = "IMG_OBJS_WARN_TSK"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_OPEN_MARKER = "IMG_OPEN_MARKER"; //$NON-NLS-1$

    public final static String IMG_TOOL_BACK = "IMG_TOOL_BACK"; //$NON-NLS-1$
    
    public final static String IMG_TOOL_BACK_DISABLED = "IMG_TOOL_BACK_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_BACK_HOVER = "IMG_TOOL_BACK_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_COPY = "IMG_TOOL_COPY"; //$NON-NLS-1$

    public final static String IMG_TOOL_COPY_DISABLED = "IMG_TOOL_COPY_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_COPY_HOVER = "IMG_TOOL_COPY_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_CUT = "IMG_TOOL_CUT"; //$NON-NLS-1$

    public final static String IMG_TOOL_CUT_DISABLED = "IMG_TOOL_CUT_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_CUT_HOVER = "IMG_TOOL_CUT_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_DELETE = "IMG_TOOL_DELETE"; //$NON-NLS-1$

    public final static String IMG_TOOL_DELETE_DISABLED = "IMG_TOOL_DELETE_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_DELETE_HOVER = "IMG_TOOL_DELETE_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_FORWARD = "IMG_TOOL_FORWARD"; //$NON-NLS-1$

    public final static String IMG_TOOL_FORWARD_DISABLED = "IMG_TOOL_FORWARD_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_FORWARD_HOVER = "IMG_TOOL_FORWARD_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_NEW_WIZARD = "IMG_TOOL_NEW_WIZARD"; //$NON-NLS-1$

    public final static String IMG_TOOL_NEW_WIZARD_DISABLED = "IMG_TOOL_NEW_WIZARD_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_NEW_WIZARD_HOVER = "IMG_TOOL_NEW_WIZARD_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_PASTE = "IMG_TOOL_PASTE"; //$NON-NLS-1$

    public final static String IMG_TOOL_PASTE_DISABLED = "IMG_TOOL_PASTE_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_PASTE_HOVER = "IMG_TOOL_PASTE_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_REDO = "IMG_TOOL_REDO"; //$NON-NLS-1$

    public final static String IMG_TOOL_REDO_DISABLED = "IMG_TOOL_REDO_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_REDO_HOVER = "IMG_TOOL_REDO_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_UNDO = "IMG_TOOL_UNDO"; //$NON-NLS-1$

    public final static String IMG_TOOL_UNDO_DISABLED = "IMG_TOOL_UNDO_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_UNDO_HOVER = "IMG_TOOL_UNDO_HOVER"; //$NON-NLS-1$

    public final static String IMG_TOOL_UP = "IMG_TOOL_UP"; //$NON-NLS-1$

    public final static String IMG_TOOL_UP_DISABLED = "IMG_TOOL_UP_DISABLED"; //$NON-NLS-1$

    @Deprecated
	public final static String IMG_TOOL_UP_HOVER = "IMG_TOOL_UP_HOVER"; //$NON-NLS-1$


    public final static String IMG_OBJS_DND_LEFT_SOURCE = "IMG_OBJS_DND_LEFT_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_LEFT_MASK = "IMG_OBJS_DND_LEFT_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_RIGHT_SOURCE = "IMG_OBJS_DND_RIGHT_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_RIGHT_MASK = "IMG_OBJS_DND_RIGHT_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_TOP_SOURCE = "IMG_OBJS_DND_TOP_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_TOP_MASK = "IMG_OBJS_DND_TOP_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_BOTTOM_SOURCE = "IMG_OBJS_DND_BOTTOM_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_BOTTOM_MASK = "IMG_OBJS_DND_BOTTOM_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_INVALID_SOURCE = "IMG_OBJS_DND_INVALID_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_INVALID_MASK = "IMG_OBJS_DND_INVALID_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_STACK_SOURCE = "IMG_OBJS_DND_STACK_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_STACK_MASK = "IMG_OBJS_DND_STACK_MASK"; //$NON-NLS-1$

    public final static String IMG_OBJS_DND_OFFSCREEN_SOURCE = "IMG_OBJS_DND_OFFSCREEN_SOURCE"; //$NON-NLS-1$
    public final static String IMG_OBJS_DND_OFFSCREEN_MASK = "IMG_OBJS_DND_OFFSCREEN_MASK"; //$NON-NLS-1$

    public static final String IMG_OBJS_DND_TOFASTVIEW_SOURCE = "IMG_OBJS_DND_TOFASTVIEW_SOURCE"; //$NON-NLS-1$
    public static final String IMG_OBJS_DND_TOFASTVIEW_MASK = "IMG_OBJS_DND_TOFASTVIEW_MASK"; //$NON-NLS-1$    
    
    public Image getImage(String symbolicName);

    public ImageDescriptor getImageDescriptor(String symbolicName);
}
