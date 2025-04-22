		cachedValues = new IdentityMap();
		staleElements = new IdentitySet();
		knownMasterElements.addSetChangeListener(new ISetChangeListener() {
			@Override
			public void handleSetChange(SetChangeEvent event) {
				for (Iterator it = event.diff.getRemovals().iterator(); it
