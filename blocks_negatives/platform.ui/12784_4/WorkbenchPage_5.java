	public boolean saveAllEditors(boolean confirm, boolean closing) {
		List<MPart> dirtyParts = new ArrayList<MPart>();
		for (MPart currentPart : modelService.findElements(window, null, MPart.class, null)) {
			if (currentPart.isDirty()) {
				Object object = currentPart.getObject();
				if (object == null) {
					continue;
				} else if (object instanceof CompatibilityPart) {
					CompatibilityPart compatibilityPart = (CompatibilityPart) object;
					if (closing
							&& !((ISaveablePart) compatibilityPart.getPart()).isSaveOnCloseNeeded()) {
						continue;
					}
				}
