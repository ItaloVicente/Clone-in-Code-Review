    /**
     * Return an array of <code>IPreferenceTransfer</code> objects
     * @return an array of <code>IPreferenceTransfer</code> objects
     */
    public static PreferenceTransferElement[] getPreferenceTransfers() {
        return new PreferenceTransferRegistryReader(
                    IWorkbenchRegistryConstants.PL_PREFERENCE_TRANSFER)
                    .getPreferenceTransfers();
    }
