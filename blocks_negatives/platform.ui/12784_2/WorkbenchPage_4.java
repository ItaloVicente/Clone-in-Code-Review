	public boolean saveSaveable(ISaveablePart saveable, boolean confirm, boolean closing) {
		MPart part = findPart((IWorkbenchPart) saveable);
		if (part != null) {
			if (saveable.isDirty()) {
				if (closing) {
					if (saveable.isSaveOnCloseNeeded()) {
						return partService.savePart(part, confirm);
					}
					part.setDirty(false);
				} else {
					return partService.savePart(part, confirm);
				}
			}
			return true;
		}
		return false;
