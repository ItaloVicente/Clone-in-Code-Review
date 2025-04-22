		top.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				finishedJobs.removeListener(
						ProgressAnimationItem.this);
				noneImage.dispose();
				okImage.dispose();
				errorImage.dispose();
			}
