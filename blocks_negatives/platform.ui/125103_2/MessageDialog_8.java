	/**
	 * Convenience method to open a simple dialog as specified by the
	 * <code>kind</code> flag.
	 *
	 * @param kind          the kind of dialog to open, one of {@link #ERROR},
	 *                      {@link #INFORMATION}, {@link #QUESTION},
	 *                      {@link #WARNING}, {@link #CONFIRM}, or
	 *                      {@link #QUESTION_WITH_CANCEL}.
	 * @param parent        the parent shell of the dialog, or <code>null</code> if
	 *                      none
	 * @param title         the dialog's title, or <code>null</code> if none
	 * @param message       the message
	 * @param style         {@link SWT#NONE} for a default dialog, or
	 *                      {@link SWT#SHEET} for a dialog with sheet behavior
	 * @param linkListeners listeners for click on links in the message
	 * @return <code>true</code> if the user presses the OK or Yes button,
	 *         <code>false</code> otherwise
	 * @since 3.15
	 */
	public static boolean open(int kind, Shell parent, String title, String message, int style,
			SelectionListener[] linkListeners) {
		MessageDialog dialog = new MessageDialog(parent, title, null, message, kind, 0, getButtonLabels(kind),
				linkListeners);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		return dialog.open() == 0;
	}

