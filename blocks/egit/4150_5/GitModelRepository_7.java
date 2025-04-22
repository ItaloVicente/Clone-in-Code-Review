	@Override
	public void dispose() {
		for (GitModelObject objects : children)
			objects.dispose();
	}

