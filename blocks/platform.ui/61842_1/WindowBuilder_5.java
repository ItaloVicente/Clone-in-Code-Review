		List<String> tags = window.getTags();
		if (windowReader.isMinimized()) {
			tags.add(IPresentationEngine.WINDOW_MINIMIZED_TAG);
		} else if (windowReader.isMaximized()) {
			tags.add(IPresentationEngine.WINDOW_MAXIMIZED_TAG);
		}

