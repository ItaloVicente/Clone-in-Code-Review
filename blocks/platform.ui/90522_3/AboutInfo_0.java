		for (IBundleGroupProvider provider : Platform.getBundleGroupProviders()) {
            IBundleGroup[] groups = provider.getBundleGroups();
            for (IBundleGroup group : groups) {
				if (id.equals(group.getIdentifier())
                        && versionId.equals(group.getVersion())) {
					return group;
