
	protected MPart getModel() {
		if (getSite() instanceof PartSite) {
			return ((PartSite) getSite()).getModel();
		}
		return null;
	}
