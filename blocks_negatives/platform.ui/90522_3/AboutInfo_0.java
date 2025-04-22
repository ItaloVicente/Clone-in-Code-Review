        IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
        for (int p = 0; p < providers.length; ++p) {
            IBundleGroup[] groups = providers[p].getBundleGroups();
            for (int g = 0; g < groups.length; ++g) {
				if (id.equals(groups[g].getIdentifier())
                        && versionId.equals(groups[g].getVersion())) {
					return groups[g];
