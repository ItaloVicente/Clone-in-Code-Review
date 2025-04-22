
	private void addHandleImageDisposedListener(final Control toFrame,
			final String classId, final boolean vertical) {
		toFrame.getDisplay().addListener(SWT.Skin, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!(event.widget instanceof ImageBasedFrame)) {
					return;
				}

				Image handleImage = createImage(toFrame, classId,
						HANDLE_IMAGE_PROP, null);
				if (vertical && handleImage != null) {
					handleImage = rotateImage(toFrame.getDisplay(),
							handleImage, null);
				}
				if (handleImage != null) {
					((ImageBasedFrame) event.widget).setImages(null, null,
							handleImage);
				}
			}
		});
	}

	private void addFrameImageDisposedListener(final Control toFrame,
			final String classId, final boolean vertical) {
		toFrame.getDisplay().addListener(SWT.Skin, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!(event.widget instanceof ImageBasedFrame)) {
					return;
				}

				ImageBasedFrame frame = (ImageBasedFrame) event.widget;
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
		});
	}
