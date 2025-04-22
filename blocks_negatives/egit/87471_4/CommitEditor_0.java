	@Override
	public void setFocus() {
		IFormPage currentPage = getActivePageInstance();
		if (currentPage != null) {
			currentPage.setFocus();
		}
	}

