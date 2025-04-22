		verboseBranchModeState.removeListener(this);
		for (Image image : decoratedImages.values()) {
			image.dispose();
		}
		resourceManager.dispose();
		decoratedImages.clear();
