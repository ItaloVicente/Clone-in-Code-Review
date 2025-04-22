package org.eclipse.ui.internal.ide;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.ide.IDE;

public class OpenUnknownFileTypesInTextEditorPreference {

	public static boolean getValue() {
		return getPreferenceStore().getBoolean(IDE.Preferences.OPEN_UNKNOWN_TEXT_FILE_IN_TEXT_EDITOR);
	}

	public static void setValue(boolean value) {
		getPreferenceStore().setValue(IDE.Preferences.OPEN_UNKNOWN_TEXT_FILE_IN_TEXT_EDITOR, value);
	}

	private static IPreferenceStore getPreferenceStore() {
		return IDEWorkbenchPlugin.getDefault().getPreferenceStore();
	}

}
