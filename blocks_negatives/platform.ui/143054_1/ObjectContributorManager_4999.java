			PlatformUI.getWorkbench().getExtensionTracker().registerObject(
					element.getDeclaringExtension(), contributorRecord,
					IExtensionTracker.REF_WEAK);
        }
    }

    /**
     * Unregister all contributors.
     */
    public void unregisterAllContributors() {
        contributors = new Hashtable(5);
        flushLookup();
    }

    /**
     * Unregister a contributor from the target type.
     *
     * @param contributor the contributor
     * @param targetType the target type
     */
    public void unregisterContributor(IObjectContributor contributor,
            String targetType) {
        List contributorList = (List) contributors.get(targetType);
        if (contributorList == null) {
