
		Display.getCurrent().disposeExec(() -> {
			for (Image image : imageMap.values()) {
				image.dispose();
			}
		});
