		for (Map.Entry<ContributionManager, MenuLocationURI> entry : managers.entrySet()) {
			MenuLocationURI mgrURI = entry.getValue();
			if (mgrURI.getScheme().equals(location.getScheme())
					&& mgrURI.getPath().equals(location.getPath())) {
				ContributionManager mgr = entry.getKey();
				populateContributionManager(mgr, mgrURI.toString());
				mgr.update(true);
			}
		}
