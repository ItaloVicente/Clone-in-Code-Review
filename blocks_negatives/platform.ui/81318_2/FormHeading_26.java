		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event e) {
				onPaint(e.gc);
			}
		});
		addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (gradientImage != null) {
					FormImages.getInstance().markFinished(gradientImage, getDisplay());
					gradientImage = null;
				}
			}
		});
		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (gradientInfo != null
						|| (backgroundImage != null && !isBackgroundImageTiled()))
					updateGradientImage();
