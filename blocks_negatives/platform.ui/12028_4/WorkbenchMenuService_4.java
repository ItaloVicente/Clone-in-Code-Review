		MenuLocationURI location = new MenuLocationURI(factory.getLocation());
		List<ContributionManager> toRemove = new ArrayList<ContributionManager>();
		for (Map.Entry<ContributionManager, MenuLocationURI> entry : managers.entrySet()) {
			MenuLocationURI mgrURI = entry.getValue();
			if (mgrURI.getScheme().equals(location.getScheme())
					&& mgrURI.getPath().equals(location.getPath())) {
				toRemove.add(entry.getKey());
			}
		}
		for (ContributionManager mgr : toRemove) {
			mgr.removeAll();
			managers.remove(mgr);
		}
