
	private void addHandleImageDisposedListener(
			ImageBasedFrame imageBasedFrame, final Control toFrame,
			final String classId, final boolean vertical) {
		final Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!(event.widget instanceof ImageBasedFrame)) {
					return;
				}

				ImageBasedFrame frame = (ImageBasedFrame) event.widget;
				if (!isImagesRefreshRequired(frame)) {
					return;
				}

				Image handleImage = createImage(toFrame, classId,
						HANDLE_IMAGE_PROP, null);
				if (vertical && handleImage != null) {
					handleImage = rotateImage(toFrame.getDisplay(),
							handleImage, null);
				}
				if (handleImage != null) {
					frame.setImages(null, null, handleImage);
				}
			}
		};

		toFrame.getDisplay().addListener(SWT.Skin, listener);

		imageBasedFrame.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.getDisplay().removeListener(SWT.Skin, listener);
			}
		});
	}

	private void addFrameImageDisposedListener(ImageBasedFrame imageBasedFrame,
			final Control toFrame, final String classId, final boolean vertical) {
		final Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!(event.widget instanceof ImageBasedFrame)) {
					return;
				}

				ImageBasedFrame frame = (ImageBasedFrame) event.widget;
				if (!isImagesRefreshRequired(frame)) {
					return;
				}

				Integer[] frameInts = new Integer[4];
				Image frameImage = createImage(toFrame, classId,
						FRAME_IMAGE_PROP, frameInts);
				if (vertical && frameImage != null) {
					frameImage = rotateImage(toFrame.getDisplay(), frameImage,
							frameInts);
				}

				Image handleImage = createImage(toFrame, classId,
						HANDLE_IMAGE_PROP, null);
				if (vertical && handleImage != null) {
					handleImage = rotateImage(toFrame.getDisplay(),
							handleImage, null);
				}
				if (frameImage != null) {
					frame.setImages(frameImage, frameInts, handleImage);
				}
			 }
		};
			
		toFrame.getDisplay().addListener(SWT.Skin, listener);

		imageBasedFrame.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.getDisplay().removeListener(SWT.Skin, listener);
			}
		});
	}

	private boolean isImagesRefreshRequired(ImageBasedFrame frame) {
		Object handleImage = frame.getData("handleImage");
		if (handleImage instanceof Image && ((Image) handleImage).isDisposed()) {
			return true;
		}

		Object frameImage = frame.getData("frameImage");
		if (frameImage instanceof Image && ((Image) frameImage).isDisposed()) {
			return true;
		}

		return false;
	}
