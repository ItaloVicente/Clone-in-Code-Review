	public void setSupressSymbolicLinksCheckBoxVisible(boolean suppressSymbolicLinksCheckBoxVisible) {
		if (generator !=null)
			generator.setSuppressSymbolicLinksVisible(suppressSymbolicLinksCheckBoxVisible);
	}

	public boolean isSupressSymbolicLinksChecked() {
		if (generator !=null)
			return generator.isSuppressSymbolicLinksChecked();
		return false;
	}
