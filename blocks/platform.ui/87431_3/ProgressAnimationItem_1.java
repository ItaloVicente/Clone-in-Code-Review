		top.addDisposeListener(e -> {
			FinishedJobs.getInstance().removeListener(
					ProgressAnimationItem.this);
			noneImage.dispose();
			okImage.dispose();
			errorImage.dispose();
