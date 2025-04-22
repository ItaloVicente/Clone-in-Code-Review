	private static final String DEFAULT_FILTER_TRACES;

	/*
	 * Scenarios that probably need a default filter trace:
	 *
	 * - open main menu
	 * - open context menu
	 * - open window manager menu (e.g. Alt+Space on Windows)
	 *
	 * - drag and resize of:
	 *   - shell
	 *   - child shell (e.g. Open Type dialog)
	 *   - part
	 *
	 * - drag scroll bar thumb
	 *
	 * - native dialogs: see SWT's ControlExample, "Dialog" tab
	 */
	static {
		String defaultFilterTraces;
		if (Util.isGtk()) {
					+ ",org.eclipse.swt.internal.gtk.OS._gtk_dialog_run"; //$NON-NLS-1$
		} else if (Util.isWin32()) {
					+ ",org.eclipse.swt.internal.win32.OS.DefWindowProcW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.CallWindowProcW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.GetMessageW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.ChooseColorW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.SHBrowseForFolderW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.GetOpenFileNameW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.ChooseFontW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.PrintDlgW" //$NON-NLS-1$
					+ ",org.eclipse.swt.internal.win32.OS.MessageBoxW"; //$NON-NLS-1$
		} else if (Util.isCocoa()) {
		} else {
		}
		DEFAULT_FILTER_TRACES = defaultFilterTraces;
	}
