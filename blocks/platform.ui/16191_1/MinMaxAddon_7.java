		newID = '(' + newID + ')';
		oldID = '(' + oldID + ')';

		MWindow perspWin = modelService.getTopLevelWindowFor(perspective);
		if (perspWin == null)
			return;

		List<MToolControl> trimStacks = modelService.findElements(perspWin, null,
				MToolControl.class, null);
		for (MToolControl trimStack : trimStacks) {
			if (TrimStack.CONTRIBUTION_URI.equals(trimStack.getContributionURI()))
				trimStack.setElementId(trimStack.getElementId().replace(oldID, newID));
