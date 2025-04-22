package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class ReadmePreferenceInitializer extends AbstractPreferenceInitializer {

    public void initializeDefaultPreferences() {
        IPreferenceStore store = ReadmePlugin.getDefault().getPreferenceStore();
        store.setDefault(IReadmeConstants.PRE_CHECK1, true);
        store.setDefault(IReadmeConstants.PRE_CHECK2, true);
        store.setDefault(IReadmeConstants.PRE_CHECK3, false);
        store.setDefault(IReadmeConstants.PRE_RADIO_CHOICE, 2);
        store.setDefault(IReadmeConstants.PRE_TEXT, MessageUtil
                .getString("Default_text")); //$NON-NLS-1$
    }

}
