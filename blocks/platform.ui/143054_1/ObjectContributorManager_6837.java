			PlatformUI.getWorkbench().getExtensionTracker().registerObject(element.getDeclaringExtension(),
					contributorRecord, IExtensionTracker.REF_WEAK);
		}
	}

	public void unregisterAllContributors() {
		contributors = new Hashtable(5);
		flushLookup();
	}

	public void unregisterContributor(IObjectContributor contributor, String targetType) {
		List contributorList = (List) contributors.get(targetType);
		if (contributorList == null) {
