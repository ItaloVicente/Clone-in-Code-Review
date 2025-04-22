	/*
	 * org.eclipse.ui.internal.dialogs.ViewsPreferencePage controls currently the
	 * MRU behavior via IEclipsePreferences, so that CSS values from the themes
	 * aren't used.
	 *
	 * TODO once we can use preferences from CSS (and update the value on the fly)
	 * we can switch this default to true, see discussion on bug 388476.
	 */
	private static final boolean MRU_CONTROLLED_BY_CSS_DEFAULT = false;

