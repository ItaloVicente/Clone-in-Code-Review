		String eclipseLauncher = getEclipseLauncher();
		if (eclipseLauncher != null) {
			for (IScheme scheme : toAdd) {
				registryWriter.addScheme(scheme.getName(), eclipseLauncher);
			}
			for (IScheme scheme : toRemove) {
				registryWriter.removeScheme(scheme.getName());
			}
