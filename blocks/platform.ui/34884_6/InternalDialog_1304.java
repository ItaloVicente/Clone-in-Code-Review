
package org.eclipse.ui.internal.statushandlers;

import java.util.Collection;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.statushandlers.AbstractStatusAreaProvider;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.WorkbenchStatusDialogManager;

public interface IStatusDialogConstants {
	
	public static final Object SHELL = Shell.class;
	
	public static final Object SHOW_SUPPORT = new Object();
	
	public static final Object ERRORLOG_LINK = new Object();

	public static final Object HANDLE_OK_STATUSES = new Object();

	public static final Object TITLE = new Object();

	public static final Object MASK = new Object();

	public static final Object DETAILS_OPENED = new Object();

	public static final Object TRAY_OPENED = new Object();

	public static final Object ENABLE_DEFAULT_SUPPORT_AREA = new Object();

	public static final Object HIDE_SUPPORT_BUTTON = new Object();

	public static final Object CUSTOM_SUPPORT_PROVIDER = new Object();

	public static final Object CUSTOM_DETAILS_PROVIDER = new Object();

	public static final Object CURRENT_STATUS_ADAPTER = new Object();

	public static final Object STATUS_ADAPTERS = new Object();

	public static final Object STATUS_MODALS = new Object();

	public static final Object SHELL_BOUNDS = new Object();

	public static final Object LABEL_PROVIDER = new Object();

	public static final Object CUSTOM_LABEL_PROVIDER = new Object();

	public static final Object DECORATOR = new Object();

	public static final Object MODALITY_SWITCH = new Object();

	public static final Object ANIMATION = new Object();

	public static final Object MANAGER_IMPL = WorkbenchStatusDialogManagerImpl.class;
}
