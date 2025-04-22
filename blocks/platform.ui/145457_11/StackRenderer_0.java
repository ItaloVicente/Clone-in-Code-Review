	private void changePartTabImage(MPart part, CTabItem item) {
		this.imageChanged = true;
		item.setImage(getImage(part));
		this.imageChanged = false;
	}

