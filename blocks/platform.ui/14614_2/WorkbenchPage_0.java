
	public void firePartDeactivatedIfActive(MPart part) {
		if (partService.getActivePart() == part) {
			firePartDeactivated(part);
		}
	}
