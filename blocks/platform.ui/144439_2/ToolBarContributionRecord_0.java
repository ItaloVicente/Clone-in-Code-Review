		if (!changed) {
			return;
		}
		ToolBarManager managerForModel = getManagerForModel();
		managerForModel.markDirty();

		if (!isVisible) {
			return;
		}
		boolean anyMatch = Stream.of(managerForModel.getItems()).anyMatch(IContributionItem::isVisible);
		if (anyMatch) {
			MWindow window = getWindow();
			if (window != null) {
				Object widget = window.getWidget();
				if (widget instanceof Shell) {
					((Shell) widget).requestLayout();
				}
