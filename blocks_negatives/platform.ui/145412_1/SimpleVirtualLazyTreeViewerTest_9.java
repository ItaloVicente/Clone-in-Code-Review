	 * Checks if the virtual tree / table functionality can be tested in the
	 * current settings. The virtual trees and tables rely on SWT.SetData event
	 * which is only sent if OS requests information about the tree / table. If
	 * the window is not visible (obscured by another window, outside of visible
	 * area, or OS determined that it can skip drawing), then OS request won't
	 * be send, causing automated tests to fail. See
