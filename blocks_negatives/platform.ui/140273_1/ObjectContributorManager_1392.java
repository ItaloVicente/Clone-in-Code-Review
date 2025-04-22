        for (Object object : objects) {
            if (object instanceof ContributorRecord) {
                ContributorRecord contributorRecord = (ContributorRecord) object;
                unregisterContributor((contributorRecord).contributor, (contributorRecord).objectClassName);
                contributorRecordSet.remove(contributorRecord);
            }
        }
    }

    /**
     * Remove listeners and dispose of this manager.
     *
     * @since 3.1
     */
    public void dispose() {
    	if(getExtensionPointFilter() != null) {
