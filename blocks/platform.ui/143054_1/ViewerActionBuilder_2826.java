		return new ViewerContribution(provider);
	}

	public void dispose() {
		if (cache != null) {
			for (int i = 0; i < cache.size(); i++) {
				((BasicContribution) cache.get(i)).dispose();
			}
			cache = null;
		}
	}

	@Override
