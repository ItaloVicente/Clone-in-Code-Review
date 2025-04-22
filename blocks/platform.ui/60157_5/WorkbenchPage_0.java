	private void configureExistingWindows() {
		List<MArea> elements = modelService.findElements(window, null, MArea.class, null);
		for (MArea area : elements) {
			Object widget = area.getWidget();
			if (widget instanceof Control) {
				installAreaDropSupport((Control) widget);
			}
		}
	}

