	public List<MTrimBar> getTrimBars() {
		if (trimBars == null) {
			trimBars = new EObjectContainmentEList<MTrimBar>(MTrimBar.class, this, AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS);
		}
		return trimBars;
	}

