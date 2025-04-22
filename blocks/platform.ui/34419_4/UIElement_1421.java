
package org.eclipse.ui.menus;

public class MenuUtil {
	public final static String WORKBENCH_MENU = "menu:org.eclipse.ui.workbench.menu"; //$NON-NLS-1$
	public final static String MAIN_MENU = "menu:org.eclipse.ui.main.menu"; //$NON-NLS-1$
	public final static String MAIN_TOOLBAR = "toolbar:org.eclipse.ui.main.toolbar"; //$NON-NLS-1$

	public final static String ANY_POPUP = "popup:org.eclipse.ui.popup.any"; //$NON-NLS-1$

	public final static String TRIM_COMMAND1 = "toolbar:org.eclipse.ui.trim.command1"; //$NON-NLS-1$
	public final static String TRIM_COMMAND2 = "toolbar:org.eclipse.ui.trim.command2"; //$NON-NLS-1$
	public final static String TRIM_VERTICAL1 = "toolbar:org.eclipse.ui.trim.vertical1"; //$NON-NLS-1$
	public final static String TRIM_VERTICAL2 = "toolbar:org.eclipse.ui.trim.vertical2"; //$NON-NLS-1$
	public final static String TRIM_STATUS = "toolbar:org.eclipse.ui.trim.status"; //$NON-NLS-1$

	public final static String QUERY_BEFORE = "before"; //$NON-NLS-1$

	public final static String QUERY_AFTER = "after"; //$NON-NLS-1$

	public final static String QUERY_ENDOF = "endof"; //$NON-NLS-1$

	public final static String SHOW_IN_MENU_ID = "popup:org.eclipse.ui.menus.showInMenu"; //$NON-NLS-1$

	public static String menuUri(String id) {
		return "menu:" + id; //$NON-NLS-1$
	}

	public static String menuAddition(String id, String location, String refId) {
		return menuUri(id) + '?' + location + '=' + refId;
	}

	public static String menuAddition(String id) {
		return menuAddition(id, "after", "additions"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public static String toolbarUri(String id) {
		return "toolbar:" + id; //$NON-NLS-1$
	}

	public static String toolbarAddition(String id, String location,
			String refId) {
		return toolbarUri(id) + '?' + location + '=' + refId;
	}

	public static String toolbarAddition(String id) {
		return toolbarAddition(id, "after", "additions"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
