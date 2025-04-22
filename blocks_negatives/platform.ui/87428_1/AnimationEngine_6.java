		animationFeedback.getAnimationShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				cancelAnimation();
			}
		});
