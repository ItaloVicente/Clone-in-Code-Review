	
	private void addHandleImageDisposedListener(ImageBasedFrame frame,
			final Control toFrame, final String classId, final boolean vertical) {
		frame.addListener(ImageBasedFrame.ImageDisposed, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Widget widget = event.widget;
				if (!(widget instanceof ImageBasedFrame)) {
					return;
				}
				Image handleImage = createImage(toFrame, classId,
						HANDLE_IMAGE_PROP, null);
				if (vertical && handleImage != null) {
					handleImage = rotateImage(toFrame.getDisplay(),
							handleImage, null);
				}
				if (handleImage != null) {
					((ImageBasedFrame) widget).setImages(null, null,
							handleImage);
				}
			}
		});
	}

	private void addFrameImageDisposedListener(ImageBasedFrame frame,
			final Control toFrame, final String classId, final boolean vertical) {
		frame.addListener(ImageBasedFrame.ImageDisposed, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Widget widget = event.widget;
				if (!(widget instanceof ImageBasedFrame)) {
					return;
				}

				ImageBasedFrame frame = (ImageBasedFrame) widget;
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
					addFrameImageDisposedListener(frame, toFrame, classId,
							vertical);
				}

			}
		});
	}
