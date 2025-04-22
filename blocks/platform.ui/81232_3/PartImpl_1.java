	public List<MTrimBar> getTrimBars() {
		if (trimBars == null) {
			trimBars = new EObjectContainmentEList<MTrimBar>(MTrimBar.class, this, BasicPackageImpl.PART__TRIM_BARS);
		}
		return trimBars;
	}

