	public List<MTrimBar> getTrimBars() {
		if (trimBars == null) {
			trimBars = new EObjectContainmentEList<MTrimBar>(MTrimBar.class, this, BasicPackageImpl.PART_DESCRIPTOR__TRIM_BARS);
		}
		return trimBars;
	}

