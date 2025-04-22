	@Override
	public boolean close() {
		if (fetchResultImage != null) {
			fetchResultImage.dispose();
		}
		return super.close();
	}
