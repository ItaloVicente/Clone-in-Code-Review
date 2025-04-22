package org.eclipse.ui.internal.preferences;

import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PreferenceTransferRegistryReader;

public class PreferenceTransferManager {

    public static PreferenceTransferElement[] getPreferenceTransfers() {
        return new PreferenceTransferRegistryReader(
                    IWorkbenchRegistryConstants.PL_PREFERENCE_TRANSFER)
                    .getPreferenceTransfers();
    }
}
