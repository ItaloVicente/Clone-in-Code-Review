	private HashSet<String> getExistingToolbarIds(MTrimmedWindow topWin) {
		HashSet<String> result = new HashSet<String>();
		for (MTrimBar bar : topWin.getTrimBars()) {
			for (MTrimElement trimElem : bar.getChildren()) {
				if (trimElem.getElementId() != null) {
					result.add(trimElem.getElementId());
				}
				if (trimElem instanceof MToolBar) {
					for (MToolBarElement toolElem : ((MToolBar) trimElem)
							.getChildren()) {
						if (toolElem.getElementId() != null) {
							result.add(toolElem.getElementId());
						}
					}
				}
			}
		}
		return result;
	}

