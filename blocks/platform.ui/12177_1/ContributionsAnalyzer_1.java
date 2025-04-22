	static boolean isFiltered(MToolBarContribution toolBarContribution,
			HashSet<String> existingToolbarIds) {
		String elemId = toolBarContribution.getElementId();
		if (elemId != null) {
			if (existingToolbarIds.contains(elemId)) {
				return true;
			}

			String[] elemIdParts = elemId.split("/"); //$NON-NLS-1$ 
			if (elemIdParts.length == 2
					&& (existingToolbarIds.contains(elemIdParts[0]) || existingToolbarIds
							.contains(elemIdParts[1]))) {
				return true;
			}
		}
		return false;
	}

