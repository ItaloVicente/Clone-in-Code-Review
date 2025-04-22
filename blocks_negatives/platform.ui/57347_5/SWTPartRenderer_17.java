		Display.getCurrent().disposeExec(new Runnable() {
			@Override
			public void run() {
				for (Image image : imageMap.values()) {
					image.dispose();
				}
