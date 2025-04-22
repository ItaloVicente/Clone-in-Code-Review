
	private boolean isChildVisible(IContributionItem item) {
		Boolean v;

		IContributionManagerOverrides overrides = getOverrides();
		if (overrides == null) {
			v = null;
		} else {
			v = getOverrides().getVisible(item);
		}

		if (v != null) {
			return v.booleanValue();
		}
		return item.isVisible();
	}
