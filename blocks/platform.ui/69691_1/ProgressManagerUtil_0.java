		EPartService partService = services.getPartService();
		if (progressView == null) {
			progressView = partService.createPart(ProgressManager.PROGRESS_VIEW_NAME);
			if (progressView != null)
				partService.showPart(progressView, PartState.VISIBLE);
		}
