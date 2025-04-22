	@Override
	public void reset() {
		super.reset();
		Form formHeading = (Form) getWidget();
		for (String headerColorKey : headerColorKeys) {
			formHeading.setHeadColor(headerColorKey, headerColors.get(headerColorKey));
		}
	}

	@Override
	public void dispose() {
		headerColors.clear();
		super.dispose();
	}
