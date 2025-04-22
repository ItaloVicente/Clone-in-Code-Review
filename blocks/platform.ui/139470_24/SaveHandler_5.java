		if (saveablePart == null) {
			if (activeMPart != null && activeMPart.isDirty() && partService != null) {
				partService.savePart(activeMPart, false);
			}
			return null;
		}

		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		ISaveablePart part = SaveableHelper.getSaveable(activePart);
		if (part == null && activeMPart != null && activeMPart.isDirty() && partService != null) {
			partService.savePart(activeMPart, false);
