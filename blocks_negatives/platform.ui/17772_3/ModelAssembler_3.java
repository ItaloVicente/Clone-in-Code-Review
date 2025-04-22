		for (String bundleId : mappedExtensions.keySet()) {
			assert requires.containsKey(bundleId) && depends.containsKey(bundleId);
			for (RequiredBundle requiredBundle : admin.getRequiredBundles(bundleId)) {
				assert requiredBundle.getSymbolicName().equals(bundleId);
				for (Bundle dependentBundle : requiredBundle.getRequiringBundles()) {
					if (!mappedExtensions.containsKey(dependentBundle.getSymbolicName())) {
						continue;
