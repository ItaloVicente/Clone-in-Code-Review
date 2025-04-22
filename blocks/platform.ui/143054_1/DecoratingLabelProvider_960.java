		super.addListener(listener);
		provider.addListener(listener);
		if (decorator != null) {
			decorator.addListener(listener);
		}
		listeners.add(listener);
	}

	@Override
