		top.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				FinishedJobs.getInstance().removeListener(
						ProgressAnimationItem.this);
				noneImage.dispose();
				okImage.dispose();
				errorImage.dispose();
			}
