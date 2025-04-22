		if (changed) {
			ToolBarManager managerForModel = getManagerForModel();
			managerForModel.markDirty();
			if (isVisible) {
				Stream.of(managerForModel.getItems()).filter(i -> i.isVisible()).findFirst().ifPresent((i) -> {
					MWindow window = getWindow();
					if (window != null) {
						Object widget = window.getWidget();
						if (widget instanceof Shell) {
							((Shell) widget).requestLayout();
						}
					}
				});
